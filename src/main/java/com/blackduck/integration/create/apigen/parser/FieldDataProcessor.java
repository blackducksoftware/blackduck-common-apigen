/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.blackduck.integration.create.apigen.data.FieldTranslation;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.model.FieldData;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;

@Component
public class FieldDataProcessor {
    private final TypeTranslator typeTranslator;
    private final DuplicateTypeIdentifier duplicateTypeIdentifier;
    private static final Set<String> javaKeyWords = UtilStrings.getJavaKeyWords();
    private static final Set<String> dateSuffixes = UtilStrings.getDateSuffixes();
    private boolean typeWasOverrided;

    public FieldDataProcessor(TypeTranslator typeTranslator, DuplicateTypeIdentifier duplicateTypeIdentifier) {
        this.typeTranslator = typeTranslator;
        this.duplicateTypeIdentifier = duplicateTypeIdentifier;
    }

    public FieldData process(RawFieldDefinition rawFieldDefinition, String parentDefinitionName) {
        String path = getProcessedPath(rawFieldDefinition.getPath());
        String type = getProcessedType(rawFieldDefinition, parentDefinitionName, path);
        return new FieldData(path, type, typeWasOverrided);
    }

    private String getProcessedPath(String path) {
        if (javaKeyWords.contains(path)) {
            path = path + "_";
        }

        // Some endpoints support a raw json body. We should rename the field to reflect a wildcard
        if (path.equals("*")) {
            path = "jsonObject";
        }

        return path.replace("[]", "");
    }

    private String getProcessedType(RawFieldDefinition rawFieldDefinition, String parentDefinitionName, String processedPath) {
        typeWasOverrided = false;
        String firstPassType = processFirstPassType(rawFieldDefinition, processedPath, parentDefinitionName);
        String processedType = overrideTypeIfNecessary(rawFieldDefinition.getPath(), rawFieldDefinition.getType(), parentDefinitionName, firstPassType);
        // We will screen the type to see if it may be a duplicate, but not actually override the type until we've screened all other fields/types
        duplicateTypeIdentifier.screenForDuplicateType(rawFieldDefinition, processedType);
        return processedType;
    }

    private String processFirstPassType(RawFieldDefinition rawFieldDefinition, String processedPath, String parentDefinitionName) {
        String processedType = rawFieldDefinition.getType();

        // Handle fields of type 'Number'
        if (processedType.equals(UtilStrings.NUMBER)) {
            processedType = UtilStrings.BIG_DECIMAL;
        }

        // Handle Date fields
        for (String dateSuffix : dateSuffixes) {
            if (rawFieldDefinition.getPath().endsWith(dateSuffix)) {
                processedType = UtilStrings.JAVA_DATE;
            }
        }

        // Handle objects that have subfields
        if (!CollectionUtils.isEmpty(rawFieldDefinition.getSubFields())) {
            // append path (name) of field to create new type
            processedType = NameParser.reorderViewInName(String.format("%s%s", parentDefinitionName, StringUtils.capitalize(processedPath)));
        }

        // Handle enums (field definitions that have a set of allowed values)
        if (!CollectionUtils.isEmpty(rawFieldDefinition.getAllowedValues())) {
            // If it is an enum with integer values, it is just an integer
            if (NumberUtils.isCreatable(rawFieldDefinition.getAllowedValues().iterator().next())) {
                processedType = UtilStrings.INTEGER;
            } else {
                List<String> enumNamePieces = NameParser.extractPiecesFromCamelCase(String.format("%s%s%s", parentDefinitionName.replace("View", ""), StringUtils.capitalize(processedPath), UtilStrings.ENUM));
                processedType = NameParser.buildNonRedundantStringFromPieces(enumNamePieces);
            }
        }

        // Appropriately wrap list types
        if (rawFieldDefinition.getType().equals(UtilStrings.ARRAY)) {
            // Determine if List<T> should use String or be replaced by the new calculated processedType
            String coreType = processedType.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : processedType;
            // For multi-dimensional arrays, we must count the array dimensions from the path as the api-spec does not provide
            //  information related to the contents or dimensions of the array.
            int arrayDimensions = StringUtils.countMatches(rawFieldDefinition.getPath(), "[]");
            if (arrayDimensions > 1) {
                processedType = StringUtils.repeat(UtilStrings.JAVA_LIST, arrayDimensions)
                                    + coreType
                                    + StringUtils.repeat(">", arrayDimensions);
            } else {
                processedType = UtilStrings.JAVA_LIST + coreType + ">";
            }
        }

        // Fields marked with a wildcard indicate that the accepted type is raw json
        if (rawFieldDefinition.getPath().equals("*")) {
            processedType = UtilStrings.JSON_OBJECT;
        }

        return processedType;
    }

    private String overrideTypeIfNecessary(String rawPath, String rawType, String parentDefinitionName, String processedType) {
        // Override type of certain fields
        FieldTranslation translation = typeTranslator.getTrueFieldType(parentDefinitionName, rawPath, rawType);
        if (translation != null) {
            String overrideType = translation.getTrueType();
            overrideType = NameParser.restoreListNotation(processedType, overrideType);
            typeWasOverrided = translation.typeWasOverrided();
            return overrideType;
        }
        return processedType;
    }

}
