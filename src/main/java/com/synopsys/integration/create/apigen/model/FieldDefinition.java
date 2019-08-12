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
package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.synopsys.integration.util.Stringable;

public class FieldDefinition extends Stringable {
    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<FieldDefinition> fields;

    public FieldDefinition(final String path, final String type, final boolean optional, final List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new ArrayList<>();
    }

    public FieldDefinition(final String path, final String type, final boolean optional) {
        this(path, type, optional, Collections.emptyList());
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public List<FieldDefinition> getSubFields() { return fields; }

    public void addSubFields(final List<FieldDefinition> subFields) { fields.addAll(subFields); }

}
