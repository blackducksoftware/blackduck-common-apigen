/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.ClassCategories;
import com.blackduck.integration.create.apigen.data.ClassCategoryData;
import com.blackduck.integration.create.apigen.data.ClassSourceEnum;
import com.blackduck.integration.create.apigen.data.ClassTypeEnum;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.generation.FileGenerationData;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import com.blackduck.integration.create.apigen.generation.finder.FilePathUtil;
import com.blackduck.integration.create.apigen.generation.finder.InputDataFinder;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.parser.NameParser;

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
