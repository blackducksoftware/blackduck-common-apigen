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
