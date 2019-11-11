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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassCategoryData;
import com.synopsys.integration.create.apigen.data.ClassSourceEnum;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class EnumGenerator extends ClassGenerator {

    private final ClassCategories classCategories;
    private final GeneratedClassWriter generatedClassWriter;
    private final InputDataFinder inputDataFinder;
    private final TypeTranslator typeTranslator;

    @Autowired
    public EnumGenerator(final ClassCategories classCategories, final GeneratedClassWriter generatedClassWriter, final InputDataFinder inputDataFinder, final TypeTranslator typeTranslator) {
        this.classCategories = classCategories;
        this.generatedClassWriter = generatedClassWriter;
        this.inputDataFinder = inputDataFinder;
        this.typeTranslator = typeTranslator;
    }

    @Override
    public boolean isApplicable(final FieldDefinition field) {
        final String fieldType = NameParser.stripListAndOptionalNotation(field.getType());
        final ClassCategoryData classCategoryData = ClassCategoryData.computeData(fieldType, classCategories);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        final ClassTypeEnum classType = classCategoryData.getType();
        return (classType.isEnum() && field.getAllowedValues().size() != 0 && !classSource.equals(ClassSourceEnum.THROWAWAY));
    }

    @Override
    public void generateClass(final FieldDefinition field, final String responseMediaType, final Template template) throws Exception {
        String classType = NameParser.stripListAndOptionalNotation(field.getType());
        classType = typeTranslator.getSimplifiedClassName(classType);
        final Map<String, Object> input = inputDataFinder.getEnumInputData(UtilStrings.GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), responseMediaType);
        String swaggerName = typeTranslator.getClassSwaggerName(classType);
        if (swaggerName != null) {
            if (typeTranslator.getApiGenClassName(swaggerName) == null) {
                classCategories.addDeprecatedClass(typeTranslator.getClassSwaggerName(classType), classType, template, input, UtilStrings.PATH_TO_ENUM_FILES);
            }
        }
        if (typeTranslator.getApiGenClassName(classType) != null) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, typeTranslator.getApiGenClassName(classType));
        }
        generatedClassWriter.writeFile(classType, template, input, UtilStrings.PATH_TO_ENUM_FILES);

        for (final FieldDefinition subField : field.getSubFields()) {
            generateClass(subField, responseMediaType, template);
        }
    }

    @Override
    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("enumTemplate.ftl");
    }
}
