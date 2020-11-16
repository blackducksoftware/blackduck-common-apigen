package com.synopsys.integration.create.apigen.parser;

import java.util.List;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.DuplicateOverrides;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

@Component
public class DuplicateTypeOverrider {
    private DuplicateOverrides duplicateOverrides;

    public DuplicateTypeOverrider(final DuplicateOverrides duplicateOverrides) {
        this.duplicateOverrides = duplicateOverrides;
    }

    public void overrideDuplicateTypes(List<ResponseDefinition> responseDefinitions) {
        for (ResponseDefinition responseDefinition : responseDefinitions) {
            for (FieldDefinition field : responseDefinition.getFields()) {
                overrideDuplicateTypes(field);
            }
        }
    }

    private void overrideDuplicateTypes(FieldDefinition field) {
        String override = duplicateOverrides.getOverride(field.getType());
        if (override != null) {
            override = NameParser.restoreListNotation(field.getType(), override);
            field.setType(override);
        }
        for (FieldDefinition subField : field.getSubFields()) {
            overrideDuplicateTypes(subField);
        }
    }
}
