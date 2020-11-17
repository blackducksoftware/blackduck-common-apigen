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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassCategoryData;
import com.synopsys.integration.create.apigen.data.ClassSourceEnum;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class EnumGenerator extends ClassGenerator {
    private final ClassCategories classCategories;
    private final InputDataFinder inputDataFinder;
    private final TypeTranslator typeTranslator;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final DeprecatedClassGenerator deprecatedClassGenerator;

    @Autowired
    public EnumGenerator(ClassCategories classCategories, InputDataFinder inputDataFinder, TypeTranslator typeTranslator, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager, final DeprecatedClassGenerator deprecatedClassGenerator) {
        this.classCategories = classCategories;
        this.inputDataFinder = inputDataFinder;
        this.typeTranslator = typeTranslator;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
    }

    @Override
    public boolean isApplicable(FieldDefinition field) {
        final String fieldType = NameParser.stripListAndOptionalNotation(field.getType());
        final ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        final ClassTypeEnum classType = classCategoryData.getType();
        return (classType.isEnum() && field.getAllowedValues() != null && !field.getAllowedValues().isEmpty() && !classSource.equals(ClassSourceEnum.TEMPORARY) && !field.typeWasOverrided());
    }

    @Override
    public void generateClass(FieldDefinition field, String responseMediaType, Template template) {
        String classType = NameParser.stripListAndOptionalNotation(field.getType());
        final Map<String, Object> input = inputDataFinder.getEnumInputData(UtilStrings.GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), responseMediaType);
        String deprecatedName = typeTranslator.getNameOfDeprecatedEquivalent(classType);
        if (deprecatedName != null && typeTranslator.getNameOfDeprecatedEquivalent(deprecatedName) == null) {
            deprecatedClassGenerator.addDeprecatedClass(typeTranslator.getNameOfDeprecatedEquivalent(classType), classType, template, input, filePathUtil.getOutputPathToEnumFiles().replace(UtilStrings.GENERATED, "generated/deprecated"), UtilStrings.DEPRECATED_ENUM_PACKAGE);
        }
        if (typeTranslator.getNewName(classType) != null) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, typeTranslator.getNewName(classType));
        }
        generatorDataManager.addFileData(new FileGenerationData(classType, template, input, filePathUtil.getOutputPathToEnumFiles()));
    }

    @Override
    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("enumTemplate.ftl");
    }
}
