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
package com.synopsys.integration.create.apigen.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.synopsys.integration.create.apigen.data.DuplicateOverrides;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
public class DuplicateTypeIdentifier {
    private final DuplicateOverrides duplicateOverrides;
    private final Map<Set<RawFieldDefinition>, String> uniqueRawFieldsToNames;
    private final Map<Set<String>, String> uniqueEnumValuesToNames;

    public DuplicateTypeIdentifier(DuplicateOverrides duplicateOverrides) {
        this.duplicateOverrides = duplicateOverrides;
        this.uniqueRawFieldsToNames = new HashMap<>();
        this.uniqueEnumValuesToNames = new HashMap<>();
    }

    public String screenForDuplicateType(RawFieldDefinition rawField, String originalType) {
        Set<String> enumValues = rawField.getAllowedValues();
        if (CollectionUtils.isEmpty(enumValues)) {
            String trueType = uniqueRawFieldsToNames.get(rawField.getSubFields());
            if (trueType != null) {
                // TODO - should we be overriding/screening this strictly?
                duplicateOverrides.addOverride(trueType, originalType);
                return trueType;
            } else if (!CollectionUtils.isEmpty(rawField.getSubFields())) {
                uniqueRawFieldsToNames.put(rawField.getSubFields(), originalType);
            }
        } else {
            return screenForDuplicateEnum(originalType, enumValues);
        }
        return originalType;
    }

    private String screenForDuplicateEnum(String originalType, Set<String> enumValues) {
        String trueEnumType = uniqueEnumValuesToNames.get(enumValues);
        if (trueEnumType != null) {
            duplicateOverrides.addOverride(trueEnumType, originalType);
            return trueEnumType;
        } else {
            uniqueEnumValuesToNames.put(enumValues, originalType);
        }
        return originalType;
    }
}
