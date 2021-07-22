/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.model;

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
