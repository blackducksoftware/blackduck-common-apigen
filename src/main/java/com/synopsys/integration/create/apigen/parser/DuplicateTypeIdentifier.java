package com.synopsys.integration.create.apigen.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
public class DuplicateTypeIdentifier {

    private final Map<Set<RawFieldDefinition>, String> uniqueRawFieldsToNames;
    private final Map<Set<String>, String> uniqueEnumValuesToNames;

    public DuplicateTypeIdentifier() {
        this.uniqueRawFieldsToNames = new HashMap<>();
        this.uniqueEnumValuesToNames = new HashMap<>();
    }

    public String screenForDuplicateType(RawFieldDefinition rawField, String originalType) {
        Set<String> enumValues = rawField.getAllowedValues();
        if (enumValues == null) {
            String trueType = uniqueRawFieldsToNames.get(rawField.getSubFields());
            if (trueType != null) {
                return trueType;
            } else if (rawField.getSubFields() != null) {
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
            return trueEnumType;
        } else {
            uniqueEnumValuesToNames.put(enumValues, originalType);
        }
        return originalType;
    }
}
