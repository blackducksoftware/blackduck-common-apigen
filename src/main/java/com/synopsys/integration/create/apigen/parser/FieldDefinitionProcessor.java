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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FieldDefinitionProcessor {

    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private final DuplicateTypeIdentifier duplicateTypeIdentifier;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final Map<Set<RawFieldDefinition>, String> uniqueFieldsToNames;
    private final Map<Set<String>, String> uniqueEnumsToNames;

    @Autowired
    public FieldDefinitionProcessor(final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager, final DuplicateTypeIdentifier duplicateTypeIdentifier, final MissingFieldsAndLinks missingFieldsAndLinks) {
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
        this.duplicateTypeIdentifier = duplicateTypeIdentifier;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.uniqueFieldsToNames = new HashMap<>();
        this.uniqueEnumsToNames = new HashMap<>();
    }

    public Set<FieldDefinition> parseFieldDefinitions(final String fieldDefinitionName, final Set<RawFieldDefinition> rawFieldDefinitions) {
        final Set<FieldDefinition> fieldDefinitions = new HashSet<>();
        for (final RawFieldDefinition rawField : rawFieldDefinitions) {
            final String path = rawField.getPath();
            final String type = rawField.getType();
            final boolean optional = rawField.isOptional();

            final FieldData fieldData = new FieldData(path, type, fieldDefinitionName, rawField.getSubFields() != null, typeTranslator, nameAndPathManager);

            final FieldDefinition fieldDefinition;

            // Ignore 'data' and '_meta' fields
            if (path.equals(UtilStrings.DATA) || path.equals(UtilStrings.META)) {
                continue;
            }

            final FieldDefinitionBuilder builder = new FieldDefinitionBuilder(fieldData, rawField.getAllowedValues(), missingFieldsAndLinks, typeTranslator);
            builder.setOptional(optional);
            fieldDefinition = builder.build();
            fieldDefinition.setType(duplicateTypeIdentifier.screenForDuplicateType(rawField, fieldDefinition.getType()));

            // If field has subfields, recursively parse and link its subfields
            if (rawField.getSubFields() != null) {

                // append subclass to create new field data type
                final Set<FieldDefinition> subFields = parseFieldDefinitions(NameParser.stripListNotation(fieldDefinition.getType()), rawField.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

}
