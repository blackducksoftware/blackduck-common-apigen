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

    public void screenForDuplicateType(RawFieldDefinition rawField, String originalType) {
        Set<String> enumValues = rawField.getAllowedValues();
        if (CollectionUtils.isEmpty(enumValues)) {
            // Until we can determine a more accurate method of determining equivalence between non-enum types, we will exclusively screen enums.
        } else {
            screenForDuplicateEnum(originalType, enumValues);
        }
    }

    private void screenForDuplicateEnum(String originalType, Set<String> enumValues) {
        String trueEnumType = uniqueEnumValuesToNames.get(enumValues);
        if (trueEnumType != null) {
            duplicateOverrides.addFirstPassOverride(trueEnumType, originalType);
        } else if (!shouldNotFilterDuplicateEnum(originalType)){
            uniqueEnumValuesToNames.put(enumValues, originalType);
        }
    }

    private boolean shouldNotFilterDuplicateEnum(String enumType) {
        // Check for special cases where duplicates should not be filtered
        return enumType.contains("Cvss");
    }
}
