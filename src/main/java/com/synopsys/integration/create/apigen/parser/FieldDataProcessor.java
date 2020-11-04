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

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
public class FieldDataProcessor {
    private final TypeTranslator typeTranslator;
    private final DuplicateTypeIdentifier duplicateTypeIdentifier;
    private static final Set<String> javaKeyWords = UtilStrings.getJavaKeyWords();
    private static final Set<String> dateSuffixes = UtilStrings.getDateSuffixes();
    private boolean typeWasOverrided;

    public FieldDataProcessor(final TypeTranslator typeTranslator, DuplicateTypeIdentifier duplicateTypeIdentifier) {
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
        return path.replace("[]", "");
    }

    private String getProcessedType(RawFieldDefinition rawFieldDefinition, String parentDefinitionName, String processedPath) {
        final String mediaVersion = NameParser.getMediaVersionFromResponseName(parentDefinitionName);
        final String nonVersionedParentDefinitionName = NameParser.getNonVersionedName(parentDefinitionName);
        typeWasOverrided = false;

        String processedType;
        processedType = processFirstPassType(rawFieldDefinition, processedPath, nonVersionedParentDefinitionName, mediaVersion);
        processedType = overrideErrantType(nonVersionedParentDefinitionName, rawFieldDefinition.getPath(), rawFieldDefinition.getType(), processedType, mediaVersion);
        processedType = filterDuplicateType(rawFieldDefinition, processedType);

        return processedType;
    }

    private String processFirstPassType(RawFieldDefinition rawFieldDefinition, String processedPath, String nonVersionedParentDefinitionName, String mediaVersion) {
        // shouldBeVersioned will be true for generated types (aside from duplicate overrides), and false for common/non-generated types (ex. String)
        boolean shouldBeVersioned = false;
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
        if (rawFieldDefinition.getSubFields() != null) {
            // append path (name) of field to create new type
            processedType = NameParser.reorderViewInName(nonVersionedParentDefinitionName + StringUtils.capitalize(processedPath));
            shouldBeVersioned = true;
        }

        // Handle enums (field definitions that have a set of allowed values)
        if (rawFieldDefinition.getAllowedValues() != null) {
            // If it is an enum with integer values, it is just an integer
            if (NumberUtils.isCreatable(rawFieldDefinition.getAllowedValues().iterator().next())) {
                processedType = UtilStrings.INTEGER;
            } else {
                processedType = nonVersionedParentDefinitionName.replace("View", "") + StringUtils.capitalize(processedPath) + UtilStrings.ENUM;
                shouldBeVersioned = true;
            }
        }

        // Append media version
        if (mediaVersion != null && shouldBeVersioned) {
            processedType = appendVersionToType(processedType, mediaVersion);
        }

        // Appropriately wrap list types
        if (rawFieldDefinition.getType().equals(UtilStrings.ARRAY)) {
            final String coreType = processedType.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : processedType;
            processedType = "java.util.List<" + coreType + ">";
        }

        return processedType;
    }

    private String overrideErrantType(String nonVersionedParentDefinitionName, String rawPath, String rawType, String processedType, String mediaVersion) {
        // Override type of certain fields
        String overrideType = typeTranslator.getTrueFieldName(nonVersionedParentDefinitionName, rawPath, rawType);
        if (overrideType != null) {
            overrideType = appendVersionToType(overrideType, mediaVersion);
            overrideType = restoreListNotation(processedType, overrideType);
            typeWasOverrided = true;
            return overrideType;
        }
        return processedType;
    }

    private String filterDuplicateType(RawFieldDefinition rawFieldDefinition, String processedType) {
        // Make sure this is not a duplicate type
        String trueType = duplicateTypeIdentifier.screenForDuplicateType(rawFieldDefinition, processedType);
        if (!trueType.equals(processedType)) {
            trueType = restoreListNotation(processedType, trueType);
            typeWasOverrided = true;
            return trueType;
        }
        return processedType;
    }

    private String appendVersionToType(String unversionedType, String mediaVersion) {
        return String.format("%sV%s", unversionedType, mediaVersion);
    }

    private String restoreListNotation(String originalType, String trueType) {
        if (trueType != null && !trueType.contains(UtilStrings.JAVA_LIST) && originalType.contains(UtilStrings.JAVA_LIST)) {
            trueType = UtilStrings.JAVA_LIST + trueType + ">";
        }
        return trueType;
    }

}
