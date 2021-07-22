/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

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
