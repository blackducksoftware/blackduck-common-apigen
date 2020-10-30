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

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FieldDefinitionProcessor {
    private FieldDataProcessor fieldDataProcessor;
    private final MissingFieldsAndLinks missingFieldsAndLinks;

    @Autowired
    public FieldDefinitionProcessor(FieldDataProcessor fieldDataProcessor, final MissingFieldsAndLinks missingFieldsAndLinks) {
        this.fieldDataProcessor = fieldDataProcessor;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
    }

    public Set<FieldDefinition> processFieldDefinitions(final String parentDefinitionName, final Set<RawFieldDefinition> rawFieldDefinitions) {
        final Set<FieldDefinition> fieldDefinitions = new HashSet<>();
        for (final RawFieldDefinition rawField : rawFieldDefinitions) {
            // Ignore 'data' and '_meta' fields
            if (rawField.getPath().equals(UtilStrings.DATA) || rawField.getPath().equals(UtilStrings.META)) {
                continue;
            }

            final FieldData fieldData = fieldDataProcessor.process(rawField, parentDefinitionName);
            final FieldDefinition fieldDefinition = new FieldDefinition(fieldData.getPath(), fieldData.getType(), rawField.isOptional(), rawField.getAllowedValues(), fieldData.typeWasOverrided());
            final Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(NameParser.getNonVersionedName(fieldData.getType()));
            fieldDefinition.addSubFields(missingFields);

            if (rawField.getSubFields() != null && !fieldDefinition.typeWasOverrided()) {
                // If field has subfields, and its type was not overrided, recursively parse and add its subfields
                final Set<FieldDefinition> subFields = processFieldDefinitions(NameParser.stripListNotation(fieldDefinition.getType()), rawField.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

}
