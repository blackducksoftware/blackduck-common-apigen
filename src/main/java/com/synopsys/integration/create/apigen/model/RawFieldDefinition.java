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

public class RawFieldDefinition {

    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<RawFieldDefinition> fields;
    private final List<LinkDefinition> links;

    public RawFieldDefinition(final String path, final String type, final boolean optional, final List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public RawFieldDefinition(final String path, final String type, final boolean optional) {
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

    public List<RawFieldDefinition> getSubFields() { return fields; }

    public List<LinkDefinition> getLinks() { return links; }

    @Override
    public String toString() {
        return "path: " + path + " type: " + type + " optional: " + optional + " fields: " + fields;
    }
}
