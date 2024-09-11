/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

public class RequestDefinition extends Definition {
    private final String responseSpecificationPath;
    private final String mediaType;

    public RequestDefinition(final String responseSpecificationPath, final String mediaType) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getResponseSpecificationPath() {
        return responseSpecificationPath;
    }
}
