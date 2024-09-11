/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.ClassCategories;
import com.blackduck.integration.create.apigen.data.ClassCategoryData;
import com.blackduck.integration.create.apigen.data.ClassSourceEnum;
import com.blackduck.integration.create.apigen.data.ClassTypeEnum;
import com.blackduck.integration.create.apigen.data.NameAndPathManager;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.generation.FileGenerationData;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import com.blackduck.integration.create.apigen.generation.finder.FilePathUtil;
import com.blackduck.integration.create.apigen.generation.finder.ImportFinder;
import com.blackduck.integration.create.apigen.generation.finder.InputDataFinder;
import com.blackduck.integration.create.apigen.model.ClassTypeData;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ComponentGenerator extends ClassGenerator {
    private final ClassCategories classCategories;
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final NameAndPathManager nameAndPathManager;
    private final TypeTranslator typeTranslator;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final DeprecatedClassGenerator deprecatedClassGenerator;

    @Autowired
    public ComponentGenerator(ClassCategories classCategories, ImportFinder importFinder, InputDataFinder inputDataFinder, NameAndPathManager nameAndPathManager, TypeTranslator typeTranslator, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager, DeprecatedClassGenerator deprecatedClassGenerator) {
        this.classCategories = classCategories;
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.typeTranslator = typeTranslator;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
    }

    @Override
    public boolean isApplicable(FieldDefinition field) {
        String fieldType = NameParser.stripListNotation(field.getType());
        ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        ClassTypeEnum classType = classCategoryData.getType();
        ClassSourceEnum classSource = classCategoryData.getSource();
        return classType.isNotCommonTypeOrEnum() && !classSource.isTemporary() && !classSource.isManual() && !field.typeWasOverrided();
    }

    @Override
    public void generateClass(FieldDefinition field, String responseMediaType, Template template) {
        Set<String> imports = new HashSet<>();
        Set<FieldDefinition> subFields = field.getSubFields();
        for (FieldDefinition subField : subFields) {
            imports.addAll(importFinder.findFieldImports(subField.getType()));
        }

        String fieldType = NameParser.stripListAndOptionalNotation(field.getType());
        ClassTypeEnum classType = classCategories.computeData(fieldType).getType();
        ClassTypeData classTypeData = new ClassTypeData(classType, filePathUtil);
        Map<String, Object> input = inputDataFinder.getInputData(classTypeData, imports, fieldType, subFields, responseMediaType);

        if (isApplicable(field)) {
            String deprecatedName = typeTranslator.getNameOfDeprecatedEquivalent(fieldType);
            if (deprecatedName != null) {
                if (typeTranslator.getNameOfDeprecatedEquivalent(deprecatedName) == null) {
                    String pathToDeprecatedFiles = classTypeData.getPathToOutputDirectory().replace(UtilStrings.GENERATED, "generated/deprecated");
                    String deprecatedPackage = classTypeData.getPackageName().replace(UtilStrings.GENERATED, "generated.deprecated");
                    deprecatedClassGenerator.addDeprecatedClass(deprecatedName, fieldType, template, input, pathToDeprecatedFiles, deprecatedPackage);
                }
            }
            // If a class has the same name as a class from the previous API, but is a different class, then this will be noted at the top of the class.
            if (typeTranslator.getNewName(fieldType) != null) {
                input.put(UtilStrings.HAS_NEW_NAME, true);
                input.put(UtilStrings.NEW_NAME, typeTranslator.getNewName(fieldType));
            }
            generatorDataManager.addFileData(new FileGenerationData(fieldType, template, input, classTypeData.getPathToOutputDirectory()));
        }
    }

    @Override
    public Template getTemplate(Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
