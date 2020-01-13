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
package com.synopsys.integration.create.apigen.generation.finder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkData;
import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class InputDataFinder {

    public Map<String, Object> getEnumInputData(final String enumPackage, final String enumClassName, final Set<String> enumValues, final String mediaType) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put(UtilStrings.PACKAGE_NAME, enumPackage);
        inputData.put(UtilStrings.MEDIA_TYPE, mediaType);
        inputData.put(UtilStrings.CLASS_NAME, NameParser.stripListAndOptionalNotation(enumClassName));
        inputData.put("enumValues", enumValues);

        return inputData;
    }

    public HashMap<String, Object> getViewInputData(final String viewPackage, final Set<String> imports, final String className, final String baseClass, final Set<FieldDefinition> classFields, final String mediaType) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put(UtilStrings.PACKAGE_NAME, viewPackage);
        inputData.put(UtilStrings.CLASS_NAME, NameParser.stripListAndOptionalNotation(className));
        inputData.put(UtilStrings.MEDIA_TYPE, mediaType);
        inputData.put("imports", imports);
        inputData.put("baseClass", baseClass);

        final Set<FieldDefinition> nonOptionalClassFields = new HashSet<>();
        final Set<FieldDefinition> optionalClassFields = new HashSet<>();
        for (final FieldDefinition classField : classFields) {
            final String oldType = classField.getType();
            final String newType = NameParser.getNonVersionedName(oldType);
            classField.setType(newType);
            // For optional fields
            if (classField.isOptional()) {
                optionalClassFields.add(classField);
            } else {
                nonOptionalClassFields.add(classField);
            }
        }
        inputData.put(UtilStrings.CLASS_FIELDS, classFields);
        inputData.put("optionalClassFields", optionalClassFields);
        inputData.put("nonOptionalClassFields", nonOptionalClassFields);

        return inputData;
    }

    public HashMap<String, Object> getViewInputData(final String viewPackage, final Set<String> imports, final String className, final String baseClass, final Set<FieldDefinition> classFields, final Set<LinkData> links,
        final String mediaType) {
        final HashMap<String, Object> inputData = getViewInputData(viewPackage, imports, className, baseClass, classFields, mediaType);
        if (links != null && links.size() > 0) {
            inputData.put("hasLinksWithResults", true);
            inputData.put("hasLinks", true);
            inputData.put(UtilStrings.LINKS, links);
        }
        return inputData;
    }

    private void identifyOptionalFields(final List<FieldDefinition> classFields) {
        for (final FieldDefinition field : classFields) {
            if (field.isOptional()) {
                final String typeWrappedInOptional = NameParser.wrapInOptionalNotation(field.getType());
                field.setType(typeWrappedInOptional);
            }
        }
    }
}
