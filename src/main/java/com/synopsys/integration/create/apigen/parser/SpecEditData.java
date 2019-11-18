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

import java.util.List;
import java.util.Map;

import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class SpecEditData {

    /*
        Get to a file
        Get to field in a file
        Edits you'd want to make:
            Add a subfield
            Remove a subfield
            Edit attribute of a field
     */

    private final List<String> pathToField;
    // attribute, replacementValue
    private final Map<String, String> attributeEdits;
    private final List<FieldDefinition> fieldsToAdd;
    private final List<FieldDefinition> fieldsToRemove;

    public SpecEditData(final List<String> pathToField, final Map<String, String> attributeEdits, final List<FieldDefinition> fieldsToAdd, final List<FieldDefinition> fieldsToRemove) {
        this.pathToField = pathToField;
        this.attributeEdits = attributeEdits;
        this.fieldsToAdd = fieldsToAdd;
        this.fieldsToRemove = fieldsToRemove;
    }

    public List<String> getPathToField() {
        return pathToField;
    }

    public Map<String, String> getAttributeEdits() {
        return attributeEdits;
    }

    public List<FieldDefinition> getFieldsToAdd() {
        return fieldsToAdd;
    }

    public List<FieldDefinition> getFieldsToRemove() {
        return fieldsToRemove;
    }
}
