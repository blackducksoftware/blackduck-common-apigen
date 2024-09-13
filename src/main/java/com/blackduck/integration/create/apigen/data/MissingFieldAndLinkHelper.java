/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.LinkDefinition;

import java.util.HashSet;
import java.util.Set;

public class MissingFieldAndLinkHelper {
    private final Set<FieldDefinition> missingFields;
    private final Set<LinkDefinition> missingLinks;

    public MissingFieldAndLinkHelper() {
        this.missingFields = new HashSet<>();
        this.missingLinks = new HashSet<>();
    }

    public void addField(final FieldDefinition field) {
        missingFields.add(field);
    }

    public void addLink(final LinkDefinition link) {
        missingLinks.add(link);
    }

    public Set<FieldDefinition> getMissingFields() {
        return this.missingFields;
    }

    public Set<LinkDefinition> getMissingLinks() {
        return this.missingLinks;
    }
}
