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
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.LinkData;
import com.synopsys.integration.create.apigen.model.LinksAndImportsData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ViewGenerator {
    private final MediaTypes mediaTypes;
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final TypeTranslator typeTranslator;
    private final ClassCategories classCategories;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;

    @Autowired
    public ViewGenerator(MediaTypes mediaTypes, ImportFinder importFinder, InputDataFinder inputDataFinder, NameAndPathManager nameAndPathManager, MediaVersionDataManager mediaVersionDataManager, TypeTranslator typeTranslator,
        ClassCategories classCategories, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager) {
        this.mediaTypes = mediaTypes;
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.typeTranslator = typeTranslator;
        this.classCategories = classCategories;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
    }

    public boolean isApplicable(final ResponseDefinition response) {
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        return (longMediaTypes.contains(response.getMediaType()));
    }

    public void generateClasses(final ResponseDefinition response, final Template template) {
        Set<String> imports = new HashSet<>();
        importFinder.addFieldImports(imports, response.getFields());
        final LinksAndImportsData helper = importFinder.getLinkImports(imports, response);
        imports = helper.getImports();
        final Set<LinkData> links = helper.getLinks();

        final String responseMediaType = response.getMediaType();
        final String viewName = response.getName();

        final String fieldPackage;
        final String fieldBaseClass;
        final String pathToFiles;
        final ClassTypeEnum classType = classCategories.computeData(viewName).getType();
        if (classType.isView()) {
            fieldPackage = UtilStrings.GENERATED_VIEW_PACKAGE;
            fieldBaseClass = UtilStrings.VIEW_BASE_CLASS;
            pathToFiles = filePathUtil.getOutputPathToViewFiles();
            imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS);
        } else if (classType.isResponse()) {
            fieldPackage = UtilStrings.GENERATED_RESPONSE_PACKAGE;
            fieldBaseClass = UtilStrings.RESPONSE_BASE_CLASS;
            pathToFiles = filePathUtil.getOutputPathToResponseFiles();
            imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.RESPONSE_BASE_CLASS);
        } else {
            fieldPackage = UtilStrings.GENERATED_COMPONENT_PACKAGE;
            fieldBaseClass = UtilStrings.COMPONENT_BASE_CLASS;
            pathToFiles = filePathUtil.getOutputPathToComponentFiles();
            imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.COMPONENT_BASE_CLASS);
        }
        final Map<String, Object> input = inputDataFinder.getViewInputData(fieldPackage, imports, viewName, fieldBaseClass, response.getFields(), links, responseMediaType);

        mediaVersionDataManager.updateLatestMediaVersions(viewName, input, responseMediaType);
        String swaggerName = typeTranslator.getClassSwaggerName(viewName);
        if (StringUtils.isNotBlank(swaggerName)) {
            if (StringUtils.isBlank(typeTranslator.getClassSwaggerName(swaggerName))) {
                classCategories.addDeprecatedClass(swaggerName, viewName, template, input, pathToFiles.replace(UtilStrings.GENERATED, "generated.deprecated"), fieldPackage.replace(UtilStrings.GENERATED, "generated.deprecated"));
            }
        }
        String apiGenClassName = typeTranslator.getApiGenClassName(viewName);
        if (StringUtils.isNotBlank(apiGenClassName)) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, apiGenClassName);
        }
        generatorDataManager.addFileData(new FileGenerationData(viewName, template, input, pathToFiles));

        nameAndPathManager.addNonLinkClassName(viewName);
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(viewName));
    }

    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
