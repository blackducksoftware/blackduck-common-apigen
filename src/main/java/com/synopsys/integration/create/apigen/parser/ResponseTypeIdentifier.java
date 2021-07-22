/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.parser;

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ResponseTypeIdentifier {

    private static List<String> getArrayResponseFieldNames() {
        final List<String> dudFieldNames = new ArrayList<>();
        dudFieldNames.add(UtilStrings.ITEMS);
        dudFieldNames.add(UtilStrings.META);
        dudFieldNames.add("totalCount");
        return dudFieldNames;
    }

    public static ResponseType getResponseType(final ResponseDefinition response) {
        final List<String> arrayFieldNames = getArrayResponseFieldNames();
        boolean isArrayResponse = true;
        for (final FieldDefinition field : response.getFields()) {
            final String fieldName = field.getPath();
            if (fieldName.equals(UtilStrings.ITEMS) && !field.getSubFields().isEmpty() && response.getFields().stream().allMatch(it -> arrayFieldNames.contains(it.getPath()))) {
                // If the only field with relevant data in the response object is a field 'items', then we will want to extract solely that field's subfields
                return ResponseType.DATA_IS_SUBFIELD_OF_ITEMS;
            }
            if (!arrayFieldNames.contains(fieldName)) {
                isArrayResponse = false;
            }
        }
        return isArrayResponse ? ResponseType.ARRAY : ResponseType.STANDARD;
    }

}
