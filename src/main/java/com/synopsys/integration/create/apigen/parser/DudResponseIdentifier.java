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
import java.util.List;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class DudResponseIdentifier {

    private static List<FieldDefinition> getDudFields() {
        final List<FieldDefinition> dudFields = new ArrayList<>();
        final FieldDefinition items = new FieldDefinition("items", "java.util.List<String>", false);
        dudFields.add(items);
        final FieldDefinition meta = new FieldDefinition("_meta", "Object", false);
        dudFields.add(meta);
        final FieldDefinition totalCount = new FieldDefinition("totalCount", "BigDecimal", false);
        dudFields.add(totalCount);

        return dudFields;
    }

    private static List<String> getDudFieldNames() {
        final List<String> dudFieldNames = new ArrayList<>();
        dudFieldNames.add("items");
        dudFieldNames.add("_meta");
        dudFieldNames.add("totalCount");
        return dudFieldNames;
    }

    public static boolean isDudResponse(final ResponseDefinition response) {
        final List<String> dudFieldNames = getDudFieldNames();
        for (final FieldDefinition field : response.getFields()) {
            final String fieldName = field.getPath();
            if (!dudFieldNames.contains(fieldName)) {
                return false;
            }
        }
        return true;
    }

}
