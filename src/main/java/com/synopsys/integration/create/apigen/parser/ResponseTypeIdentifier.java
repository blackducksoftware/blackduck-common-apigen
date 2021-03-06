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
