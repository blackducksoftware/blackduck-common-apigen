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
package com.synopsys.integration.create.apigen.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldDefinition extends Definition {
    private final String path;
    private String type;
    private final boolean optional;
    private final Set<String> allowedValues;
    private final Set<FieldDefinition> fields;

    public FieldDefinition(final String path, final String type, final boolean optional, final Set<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new HashSet<>();
    }

    public FieldDefinition(final String path, final String type, final boolean optional) {
        this(path, type, optional, Collections.emptySet());
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public void setType(final String newType) { this.type = newType; }

    public boolean isOptional() {
        return optional;
    }

    public Set<String> getAllowedValues() {
        return allowedValues;
    }

    public Set<FieldDefinition> getSubFields() { return fields; }

    public void addSubFields(final Set<FieldDefinition> subFields) { fields.addAll(subFields); }

    // When comparing constructed FieldDefinition's, we will only consider path and type, as the construction process will have already
    // assigned distinct types to distinct definitions.
    @Override
    public int hashCode() {
        return this.path.length()/11 * this.type.length();
    }

    @Override
    public boolean equals(final Object obj) {
        // null check
        if (obj == null) {
            return false;
        }

        // this instance check
        if (this == obj) {
            return true;
        }

        if (obj instanceof FieldDefinition) {
            FieldDefinition field = ((FieldDefinition) obj);
            return field.getPath().equals(this.path) &&
                       field.getType().equals(this.type);
        }
        return false;
    }
}
