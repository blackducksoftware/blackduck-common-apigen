/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

public class LinkDefinition extends ThirdPartyDefinition {
    private final String rel;

    public LinkDefinition(final String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return rel;
    }

}
