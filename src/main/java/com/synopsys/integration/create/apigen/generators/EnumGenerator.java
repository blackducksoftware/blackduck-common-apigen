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
package com.synopsys.integration.create.apigen.generators;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.InputDataHelper;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

import freemarker.template.Template;

@Component
public class EnumGenerator extends ClassGenerator {

    private final ClassCategories classCategories;
    private final FreeMarkerHelper freeMarkerHelper;
    private final InputDataHelper inputDataHelper;

    @Autowired
    public EnumGenerator(final ClassCategories classCategories, final FreeMarkerHelper freeMarkerHelper, final InputDataHelper inputDataHelper) {
        this.classCategories = classCategories;
        this.freeMarkerHelper = freeMarkerHelper;
        this.inputDataHelper = inputDataHelper;
    }

    @Override
    public boolean isApplicable(final FieldDefinition field) {
        final String fieldType = field.getType();
        return (classCategories.isEnum(fieldType) && !classCategories.isThrowaway(fieldType));
    }

    @Override
    public void generateClass(final FieldDefinition field, final String responseMediaType, final Template template) throws Exception {
        final String classType = field.getType().replace(UtilStrings.JAVA_LIST, "").replace(">", "");
        final Map<String, Object> input = inputDataHelper.getEnumInputData(UtilStrings.GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), responseMediaType);
        freeMarkerHelper.writeFile(classType, template, input, UtilStrings.PATH_TO_ENUM_FILES);

        for (final FieldDefinition subField : field.getSubFields()) {
            generateClass(subField, responseMediaType, template);
        }
    }
}
