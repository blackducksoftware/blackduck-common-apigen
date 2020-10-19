package com.synopsys.integration.create.apigen.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class DuplicateTypeIdentifier {

    private final Map<Set<RawFieldDefinition>, String> uniqueFieldsToNames;
    private final Map<Set<String>, String> uniqueEnumsToNames;

    public DuplicateTypeIdentifier() {
        this.uniqueFieldsToNames = new HashMap<>();
        this.uniqueEnumsToNames = new HashMap<>();
    }

    public String screenForDuplicateField(RawFieldDefinition rawField, String originalType) {
        Set<String> enumValues = rawField.getAllowedValues();
        if (enumValues == null) {
            originalType = NameParser.getNonVersionedName(originalType);
            originalType = NameParser.stripListNotation(originalType);
            String trueType = uniqueFieldsToNames.get(rawField.getSubFields());
            if (trueType != null) {
                trueType = restoreListNotation(originalType, trueType);
                return trueType;
            } else if (rawField.getSubFields() != null) {
                uniqueFieldsToNames.put(rawField.getSubFields(), originalType);
            }
        } else {
            screenForDuplicateEnum(originalType, enumValues);
        }
        return originalType;
    }

    private String screenForDuplicateEnum(String originalType, Set<String> enumValues) {
        String trueEnumType = uniqueEnumsToNames.get(enumValues);
        trueEnumType = restoreListNotation(originalType, trueEnumType);

        if (trueEnumType != null) {
            return trueEnumType;
        } else {
            uniqueEnumsToNames.put(enumValues, originalType);
        }
        return originalType;
    }

    private String restoreListNotation(String originalType, String trueType) {
        if (trueType != null && !trueType.contains(UtilStrings.JAVA_LIST) && originalType.contains(UtilStrings.JAVA_LIST)) {
            trueType = UtilStrings.JAVA_LIST + trueType + ">";
        }
        return trueType;
    }
}
