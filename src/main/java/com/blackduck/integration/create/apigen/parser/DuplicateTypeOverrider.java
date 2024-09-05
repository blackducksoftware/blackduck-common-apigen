/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.util.List;

import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.model.FieldDefinition;

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
