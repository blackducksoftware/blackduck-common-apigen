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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResponseDefinition extends Definition {
    private final String responseSpecificationPath;
    private final String name;
    private final String mediaType;
    private final Set<FieldDefinition> fields;
    private final Set<LinkDefinition> links;
    private final boolean hasMultipleResults;

    public ResponseDefinition(final String responseSpecificationPath, final String name, final String mediaType, final boolean hasMultipleResults) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.name = name;
        this.mediaType = mediaType;
        this.fields = new HashSet<>();
        this.links = new HashSet<>();
        this.hasMultipleResults = hasMultipleResults;
    }

    public String getResponseSpecificationPath() {
        return responseSpecificationPath;
    }

    public String getName() {
        return name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Set<FieldDefinition> getFields() { return fields; }

    public void addField(final FieldDefinition fieldDefinition) { fields.add(fieldDefinition); }

    public void addFields(final Set<FieldDefinition> fieldDefinitions) { fields.addAll(fieldDefinitions); }

    public Set<LinkDefinition> getLinks() { return links; }

    public void addLink(final LinkDefinition linkDefinition) { links.add(linkDefinition); }

    public void addLinks(final Set<LinkDefinition> linkDefinitions) { links.addAll(linkDefinitions); }

    public boolean hasMultipleResults() {
        return hasMultipleResults;
    }

}
