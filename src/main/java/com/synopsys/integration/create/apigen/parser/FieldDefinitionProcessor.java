package com.synopsys.integration.create.apigen.parser;

import static com.synopsys.integration.create.apigen.parser.FieldsParser.ARRAY;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.DATA;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.META;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.OBJECT;

import java.util.ArrayList;
import java.util.List;

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
            if (path.equals(DATA) || path.equals(META)) {
                continue;
            }

            final FieldDefinitionBuilder builder = new FieldDefinitionBuilder(fieldData, field.getAllowedValues());
            builder.setOptional(optional);
            fieldDefinition = builder.build();

            // If field has subfields, recursively parse and link its subfields
            if ((type.equals(OBJECT) || type.equals(ARRAY)) && field.getSubFields() != null) {
                // append subclass to create new field definition type
                final List<FieldDefinition> subFields = parseFieldDefinitions(type, field.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }
}
