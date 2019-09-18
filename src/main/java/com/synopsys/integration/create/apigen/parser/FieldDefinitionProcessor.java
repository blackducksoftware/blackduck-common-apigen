package com.synopsys.integration.create.apigen.parser;

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class FieldDefinitionProcessor {

    public List<FieldDefinition> parseFieldDefinitions(final String fieldDefinitionName, final List<RawFieldDefinition> rawFieldDefinitions) {
        final List<FieldDefinition> fieldDefinitions = new ArrayList<>();

        for (final RawFieldDefinition field : rawFieldDefinitions) {
            final String path = field.getPath();
            final String type = field.getType();
            final boolean optional = field.isOptional();

            final FieldData fieldData = new FieldData(path, type, fieldDefinitionName, field.getSubFields() != null);

            FieldDefinition fieldDefinition = null;

            // Ignore 'data' and '_meta' fields
            if (path.equals(UtilStrings.DATA) || path.equals(UtilStrings.META)) {
                continue;
            }

            final FieldDefinitionBuilder builder = new FieldDefinitionBuilder(fieldData, field.getAllowedValues());
            builder.setOptional(optional);
            fieldDefinition = builder.build();

            // If field has subfields, recursively parse and link its subfields
            if ((type.equals(UtilStrings.OBJECT) || type.equals(UtilStrings.ARRAY)) && field.getSubFields() != null) {

                // append subclass to create new field definition type
                final List<FieldDefinition> subFields = parseFieldDefinitions(NameParser.stripListNotation(fieldDefinition.getType()), field.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }
}
