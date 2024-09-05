/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.blackduck.integration.create.apigen.data.MissingFieldsAndLinks;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.model.FieldData;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;

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
            final Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(fieldData.getType());
            fieldDefinition.addSubFields(missingFields);

            if (!CollectionUtils.isEmpty(rawField.getSubFields()) && !fieldDefinition.typeWasOverrided()) {
                // If field has subfields, and its type was not overrided, recursively parse and add its subfields.
                final Set<FieldDefinition> subFields = processFieldDefinitions(NameParser.stripListNotation(fieldDefinition.getType()), rawField.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

}
