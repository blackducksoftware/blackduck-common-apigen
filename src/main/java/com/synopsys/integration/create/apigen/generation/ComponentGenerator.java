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
import com.synopsys.integration.create.apigen.data.ClassCategoryData;
import com.synopsys.integration.create.apigen.data.ClassSourceEnum;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ComponentGenerator extends ClassGenerator {

    private final ClassCategories classCategories;
    private final ImportFinder importFinder;
    private final GeneratedClassWriter generatedClassWriter;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final TypeTranslator typeTranslator;

    @Autowired
    public ComponentGenerator(final ClassCategories classCategories, final ImportFinder importFinder, final GeneratedClassWriter generatedClassWriter, final InputDataFinder inputDataFinder, final NameAndPathManager nameAndPathManager,
        final MediaVersionDataManager mediaVersionDataManager, TypeTranslator typeTranslator) {
        this.classCategories = classCategories;
        this.importFinder = importFinder;
        this.generatedClassWriter = generatedClassWriter;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.typeTranslator = typeTranslator;
    }

    @Override
    public boolean isApplicable(final FieldDefinition field) {
        final String fieldType = NameParser.stripListNotation(field.getType());
        final ClassCategoryData classCategoryData = ClassCategoryData.computeData(fieldType, classCategories);
        final ClassTypeEnum classType = classCategoryData.getType();
        final ClassSourceEnum classSource = classCategoryData.getSource();
        return classType.isNotCommonTypeOrEnum() && !classSource.isThrowaway() && !classSource.isManual();
    }

    @Override
    public void generateClass(final FieldDefinition field, final String responseMediaType, final Template template) throws Exception {
        final Set<String> imports = new HashSet<>();
        final Set<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            importFinder.addFieldImports(imports, subField.getType(), subField.isOptional());
            if (isApplicable(subField)) {
                generateClass(subField, responseMediaType, template);
            }
        }
        String fieldType = NameParser.stripListAndOptionalNotation(field.getType());
        fieldType = typeTranslator.getSimplifiedClassName(fieldType);
        final String fieldPackage;
        final String fieldBaseClass;
        final String pathToFiles;
        if (classCategories.computeType(NameParser.getNonVersionedName(fieldType)).isView()) {
            fieldPackage = UtilStrings.GENERATED_VIEW_PACKAGE;
            fieldBaseClass = UtilStrings.VIEW_BASE_CLASS;
            pathToFiles = UtilStrings.PATH_TO_VIEW_FILES;
            imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS);
        } else {
            fieldPackage = UtilStrings.GENERATED_COMPONENT_PACKAGE;
            fieldBaseClass = UtilStrings.COMPONENT_BASE_CLASS;
            pathToFiles = UtilStrings.PATH_TO_COMPONENT_FILES;
        }
        final Map<String, Object> input = inputDataFinder.getViewInputData(fieldPackage, imports, fieldType, fieldBaseClass, subFields, responseMediaType);
        mediaVersionDataManager.updateLatestMediaVersions(fieldType, input, responseMediaType);

        if (isApplicable(field)) {
            String swaggerName = typeTranslator.getClassSwaggerName(fieldType);
            if (swaggerName != null) {
                if (typeTranslator.getClassSwaggerName(swaggerName) == null) {
                    classCategories.addDeprecatedClass(swaggerName, fieldType, template, input, pathToFiles);
                }
            }
            if (typeTranslator.getApiGenClassName(fieldType) != null) {
                input.put(UtilStrings.HAS_NEW_NAME, true);
                input.put(UtilStrings.NEW_NAME, typeTranslator.getApiGenClassName(fieldType));
            }
            generatedClassWriter.writeFile(fieldType, template, input, pathToFiles);
        }
        nameAndPathManager.addNonLinkClassName(fieldType);
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(fieldType));
    }

    @Override
    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
