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
package com.synopsys.integration.create.apigen.model;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.parser.DuplicateTypeIdentifier;
import com.synopsys.integration.create.apigen.parser.NameParser;

public class FieldData {
    private final TypeTranslator typeTranslator;
    private final DuplicateTypeIdentifier duplicateTypeIdentifier;
    private static final Set<String> javaKeyWords = UtilStrings.getJavaKeyWords();
    private static final Set<String> dateSuffixes = UtilStrings.getDateSuffixes();
    
    private final RawFieldDefinition rawFieldDefinition;
    private final String fieldDefinitionName;
    private String path;
    private final String type;

    @Autowired
    public FieldData(final RawFieldDefinition rawFieldDefinition, final String fieldDefinitionName, final TypeTranslator typeTranslator, DuplicateTypeIdentifier duplicateTypeIdentifier) {
        this.rawFieldDefinition = rawFieldDefinition;
        this.fieldDefinitionName = fieldDefinitionName;
        this.path = rawFieldDefinition.getPath();
        this.type = rawFieldDefinition.getType();
        this.typeTranslator = typeTranslator;
        this.duplicateTypeIdentifier = duplicateTypeIdentifier;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return rawFieldDefinition.isOptional();
    }

    public String getProcessedPath() {
        if (javaKeyWords.contains(path)) {
            path = path + "_";
        }
        return path.replace("[]", "");
    }

    public String getProcessedType() {
        final String mediaVersion = NameParser.getMediaVersion(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = NameParser.getNonVersionedName(fieldDefinitionName);
        String processedType = type;

        // Handle fields of type 'Number'
        if (processedType.equals(UtilStrings.NUMBER)) {
            processedType = UtilStrings.BIG_DECIMAL;
        }

        // Handle Date fields
        for (String dateSuffix : dateSuffixes) {
            if (path.endsWith(dateSuffix)) {
                processedType = UtilStrings.JAVA_DATE;
            }
        }
        
        // Handle objects that have subfields
        if (rawFieldDefinition.getSubFields() != null) {
            // append path (name) of field to create new type
            processedType = NameParser.reorderViewInName(nonVersionedFieldDefinitionName + StringUtils.capitalize(getProcessedPath()));
            if (mediaVersion != null) {
                processedType = processedType + "V" + mediaVersion;
            }
        }

        // Handle enums
        if (rawFieldDefinition.getAllowedValues() != null) {
            // If it is an enum with integer values, it is just an integer
            if (NumberUtils.isCreatable(rawFieldDefinition.getAllowedValues().iterator().next())) {
                processedType = UtilStrings.INTEGER;
            } else {
                String nameOfEnum = nonVersionedFieldDefinitionName.replace("View", "") + StringUtils.capitalize(getProcessedPath()) + UtilStrings.ENUM;
                processedType = nameOfEnum;
            }
        }
        
        // Handle naming simplifications
        processedType = typeTranslator.getSimplifiedClassName(processedType);

        // Override type of certain fields
        final String overrideType = typeTranslator.getTrueFieldName(nonVersionedFieldDefinitionName, path, type);
        if (overrideType != null) {
            processedType = overrideType;
        }

        // Appropriately wrap list types
        if (type.equals(UtilStrings.ARRAY)) {
            final String coreType = processedType.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : processedType;
            processedType = "java.util.List<" + coreType + ">";
        }

        // Make sure this is not a duplicate type
        processedType = duplicateTypeIdentifier.screenForDuplicateType(rawFieldDefinition, processedType);

        //debug
        if (processedType.equals("RiskProfileView")) {
            System.out.println();
        }
        
        return processedType;
    }

}
