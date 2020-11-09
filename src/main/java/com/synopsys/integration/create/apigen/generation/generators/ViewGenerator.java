/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.generation.generators;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.ClassTypeData;
import com.synopsys.integration.create.apigen.model.LinksAndImportsData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ViewGenerator {
    private final MediaTypes mediaTypes;
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final TypeTranslator typeTranslator;
    private final ClassCategories classCategories;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final DeprecatedClassGenerator deprecatedClassGenerator;

    @Autowired
    public ViewGenerator(MediaTypes mediaTypes, ImportFinder importFinder, InputDataFinder inputDataFinder, NameAndPathManager nameAndPathManager, TypeTranslator typeTranslator,
        ClassCategories classCategories, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager, final DeprecatedClassGenerator deprecatedClassGenerator) {
        this.mediaTypes = mediaTypes;
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.typeTranslator = typeTranslator;
        this.classCategories = classCategories;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
    }

    public boolean isApplicable(final ResponseDefinition response) {
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        return (longMediaTypes.contains(response.getMediaType()));
    }

    public void generateClasses(final ResponseDefinition response, final Template template) {
        Set<String> imports = new HashSet<>();
        imports.addAll(importFinder.findFieldImports(response.getFields()));
        final LinksAndImportsData linkAndImportsData = importFinder.findLinkAndImportsData(response);
        imports.addAll(linkAndImportsData.getImports());

        final String responseMediaType = response.getMediaType();
        final String viewName = response.getName();

        final ClassTypeEnum classType = classCategories.computeData(viewName).getType();
        ClassTypeData classTypeData = new ClassTypeData(classType, filePathUtil);
        final Map<String, Object> input = inputDataFinder.getInputData(classTypeData, imports, viewName, response.getFields(), linkAndImportsData.getLinks(), responseMediaType);

        String deprecatedName = typeTranslator.getNameOfDeprecatedEquivalent(viewName);
        if (StringUtils.isNotBlank(deprecatedName)) {
            if (StringUtils.isBlank(typeTranslator.getNameOfDeprecatedEquivalent(deprecatedName))) {
                String pathToDeprecatedFiles = classTypeData.getPathToOutputDirectory().replace(UtilStrings.GENERATED, "generated.deprecated");
                String deprecatedPackage = classTypeData.getPackageName().replace(UtilStrings.GENERATED, "generated.deprecated");
                deprecatedClassGenerator.addDeprecatedClass(deprecatedName, viewName, template, input, pathToDeprecatedFiles, deprecatedPackage);
            }
        }
        String apiGenClassName = typeTranslator.getNewName(viewName);
        if (StringUtils.isNotBlank(apiGenClassName)) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, apiGenClassName);
        }
        generatorDataManager.addFileData(new FileGenerationData(viewName, template, input, classTypeData.getPathToOutputDirectory()));
    }

    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
