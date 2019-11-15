/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class FieldDefinitionProcessor {

    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private final MissingFieldsAndLinks missingFieldsAndLinks;

    public FieldDefinitionProcessor(final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager, final MissingFieldsAndLinks missingFieldsAndLinks) {
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
    }

    public Set<FieldDefinition> parseFieldDefinitions(final String fieldDefinitionName, final Set<RawFieldDefinition> rawFieldDefinitions) {
        final Set<FieldDefinition> fieldDefinitions = new HashSet<>();

        for (final RawFieldDefinition field : rawFieldDefinitions) {
            final String path = field.getPath();
            final String type = field.getType();
            final boolean optional = field.isOptional();

            final FieldData fieldData = new FieldData(path, type, fieldDefinitionName, field.getSubFields() != null, typeTranslator, nameAndPathManager);

            final FieldDefinition fieldDefinition;

            // Ignore 'data' and '_meta' fields
            if (path.equals(UtilStrings.DATA) || path.equals(UtilStrings.META)) {
                continue;
            }

            final FieldDefinitionBuilder builder = new FieldDefinitionBuilder(fieldData, field.getAllowedValues(), missingFieldsAndLinks, typeTranslator);
            builder.setOptional(optional);
            fieldDefinition = builder.build();
            // PUT HASH-OF-RAWFIELDDEFINITION, FINAL-TYPE-OF-FIELDDEFINITION TO OBJECT-RECOGNITION-MAP

            // If field has subfields, recursively parse and link its subfields
            if ((type.equals(UtilStrings.OBJECT) || type.equals(UtilStrings.ARRAY)) && field.getSubFields() != null) {

                // append subclass to create new field data type
                final Set<FieldDefinition> subFields = parseFieldDefinitions(NameParser.stripListNotation(fieldDefinition.getType()), field.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }
}
