/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.generation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
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
    private final GeneratedClassWriter generatedClassWriter;
    private final MediaTypes mediaTypes;
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final TypeTranslator typeTranslator;
    private final ClassCategories classCategories;

    @Autowired
    public ViewGenerator(final GeneratedClassWriter generatedClassWriter, final MediaTypes mediaTypes, final ImportFinder importFinder,
        final InputDataFinder inputDataFinder, final NameAndPathManager nameAndPathManager, final MediaVersionDataManager mediaVersionDataManager, TypeTranslator typeTranslator, ClassCategories classCategories) {
        this.generatedClassWriter = generatedClassWriter;
        this.mediaTypes = mediaTypes;
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.typeTranslator = typeTranslator;
        this.classCategories = classCategories;
    }

    public boolean isApplicable(final ResponseDefinition response) {
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        return (longMediaTypes.contains(response.getMediaType()));
    }

    public void generateClasses(final ResponseDefinition response, final Template template) throws Exception {
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
        if (classCategories.computeType(NameParser.getNonVersionedName(viewName)).isView()) {
            fieldPackage = UtilStrings.GENERATED_VIEW_PACKAGE;
            fieldBaseClass = UtilStrings.VIEW_BASE_CLASS;
            pathToFiles = UtilStrings.PATH_TO_VIEW_FILES;
            imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS);
        } else {
            fieldPackage = UtilStrings.GENERATED_COMPONENT_PACKAGE;
            fieldBaseClass = UtilStrings.COMPONENT_BASE_CLASS;
            pathToFiles = UtilStrings.PATH_TO_COMPONENT_FILES;
        }
        final Map<String, Object> input = inputDataFinder.getViewInputData(fieldPackage, imports, response.getName(), fieldBaseClass, response.getFields(), links, responseMediaType);

        mediaVersionDataManager.updateLatestMediaVersions(viewName, input, responseMediaType);
        String swaggerName = typeTranslator.getClassSwaggerName(viewName);
        if (swaggerName != null) {
            if (typeTranslator.getClassSwaggerName(swaggerName) == null) {
                classCategories.addDeprecatedClass(swaggerName, viewName, template, input, pathToFiles);
            }
        }
        if (typeTranslator.getApiGenClassName(viewName) != null) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, typeTranslator.getApiGenClassName(viewName));
        }
        generatedClassWriter.writeFile(viewName, template, input, pathToFiles);

        nameAndPathManager.addNonLinkClassName(viewName);
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(viewName));
    }

    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
