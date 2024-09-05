/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import java.util.HashSet;
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
