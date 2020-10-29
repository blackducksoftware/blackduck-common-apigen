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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.parser.DuplicateTypeIdentifier;
import com.synopsys.integration.create.apigen.parser.NameParser;

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
        this.typeWasOverrided = false;
    }

    public FieldData process(RawFieldDefinition rawFieldDefinition, String fieldDefinitionName) {
        String path = getProcessedPath(rawFieldDefinition.getPath());
        String type = getProcessedType(rawFieldDefinition, fieldDefinitionName);
        return new FieldData(path, type, this.typeWasOverrided);
    }

    public String getProcessedPath(String path) {
        if (javaKeyWords.contains(path)) {
            path = path + "_";
        }
        return path.replace("[]", "");
    }

    public String getProcessedType(RawFieldDefinition rawFieldDefinition, String fieldDefinitionName) {
        String rawPath = rawFieldDefinition.getPath();
        String rawType = rawFieldDefinition.getType();
        final String mediaVersion = NameParser.getMediaVersionFromResponseName(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = NameParser.getNonVersionedName(fieldDefinitionName);
        // shouldBeVersioned will be true for generated types (aside from duplicate overrides), and false for common/non-generated types (ex. String)
        boolean shouldBeVersioned = false;
        this.typeWasOverrided = false;
        String processedType = rawType;

        // Handle fields of type 'Number'
        if (processedType.equals(UtilStrings.NUMBER)) {
            processedType = UtilStrings.BIG_DECIMAL;
        }

        // Handle Date fields
        for (String dateSuffix : dateSuffixes) {
            if (rawPath.endsWith(dateSuffix)) {
                processedType = UtilStrings.JAVA_DATE;
            }
        }
        
        // Handle objects that have subfields
        if (rawFieldDefinition.getSubFields() != null) {
            // append path (name) of field to create new type
            processedType = NameParser.reorderViewInName(nonVersionedFieldDefinitionName + StringUtils.capitalize(getProcessedPath(rawPath)));
            shouldBeVersioned = true;
        }

        // Handle enums (field definitions that have a set of allowed values)
        if (rawFieldDefinition.getAllowedValues() != null) {
            // If it is an enum with integer values, it is just an integer
            if (NumberUtils.isCreatable(rawFieldDefinition.getAllowedValues().iterator().next())) {
                processedType = UtilStrings.INTEGER;
            } else {
                processedType = nonVersionedFieldDefinitionName.replace("View", "") + StringUtils.capitalize(getProcessedPath(rawPath)) + UtilStrings.ENUM;
                shouldBeVersioned = true;
            }
        }

        // Override type of certain fields
        final String overrideType = typeTranslator.getTrueFieldName(nonVersionedFieldDefinitionName, rawPath, rawType);
        if (overrideType != null) {
            processedType = overrideType;
            shouldBeVersioned = true;
            this.typeWasOverrided = true;
        }

        // Append media version
        if (mediaVersion != null && shouldBeVersioned) {
            processedType = String.format("%sV%s", processedType, mediaVersion);
        }

        // Make sure this is not a duplicate type
        String trueType = duplicateTypeIdentifier.screenForDuplicateType(rawFieldDefinition, processedType);
        if (!trueType.equals(processedType)) {
            processedType = trueType;
            this.typeWasOverrided = true;
        }

        // Appropriately wrap list types
        if (rawType.equals(UtilStrings.ARRAY)) {
            final String coreType = processedType.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : processedType;
            processedType = "java.util.List<" + coreType + ">";
        }

        return processedType;
    }

}
