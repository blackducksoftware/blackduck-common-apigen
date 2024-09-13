/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
