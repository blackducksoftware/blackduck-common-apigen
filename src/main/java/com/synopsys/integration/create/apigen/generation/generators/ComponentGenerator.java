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
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.ClassTypeData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ComponentGenerator extends ClassGenerator {
    private final ClassCategories classCategories;
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final TypeTranslator typeTranslator;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;

    @Autowired
    public ComponentGenerator(ClassCategories classCategories, ImportFinder importFinder, InputDataFinder inputDataFinder, NameAndPathManager nameAndPathManager,
        MediaVersionDataManager mediaVersionDataManager, TypeTranslator typeTranslator, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager) {
        this.classCategories = classCategories;
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.typeTranslator = typeTranslator;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
    }

    @Override
    public boolean isApplicable(FieldDefinition field) {
        final String fieldType = NameParser.stripListNotation(field.getType());
        final ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        final ClassTypeEnum classType = classCategoryData.getType();
        final ClassSourceEnum classSource = classCategoryData.getSource();
        return classType.isNotCommonTypeOrEnum() && !classSource.isTemporary() && !classSource.isManual() && !field.typeWasOverrided();
    }

    @Override
    public void generateClass(FieldDefinition field, String responseMediaType, Template template) {
        final Set<String> imports = new HashSet<>();
        final Set<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            imports.addAll(importFinder.findFieldImports(subField.getType(), subField.isOptional()));
        }

        String fieldType = NameParser.stripListAndOptionalNotation(field.getType());
        final ClassTypeEnum classType = classCategories.computeData(fieldType).getType();
        ClassTypeData classTypeData = new ClassTypeData(classType, filePathUtil);
        final Map<String, Object> input = inputDataFinder.getInputData(classTypeData, imports, fieldType, subFields, responseMediaType);
        mediaVersionDataManager.updateLatestMediaVersions(fieldType, input, responseMediaType);

        if (isApplicable(field)) {
            String deprecatedName = typeTranslator.getNameOfDeprecatedEquivalent(fieldType);
            if (deprecatedName != null) {
                if (typeTranslator.getNameOfDeprecatedEquivalent(deprecatedName) == null) {
                    String pathToDeprecatedFiles = classTypeData.getPathToOutputDirectory().replace(UtilStrings.GENERATED, "generated.deprecated");
                    String deprecatedPackage = classTypeData.getPackageName().replace(UtilStrings.GENERATED, "generated.deprecated");
                    classCategories.addDeprecatedClass(deprecatedName, fieldType, template, input, pathToDeprecatedFiles, deprecatedPackage);
                }
            }
            // If a class has the same name as a class from the previous API, but is a different class, then this will be noted at the top of the class.
            if (typeTranslator.getNewName(fieldType) != null) {
                input.put(UtilStrings.HAS_NEW_NAME, true);
                input.put(UtilStrings.NEW_NAME, typeTranslator.getNewName(fieldType));
            }
            generatorDataManager.addFileData(new FileGenerationData(fieldType, template, input, classTypeData.getPathToOutputDirectory()));
        }
        nameAndPathManager.addNonLinkClassName(fieldType);
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(fieldType));
    }

    @Override
    public Template getTemplate(Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
