/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import com.blackduck.integration.util.Stringable;

public class MediaTypeDefinition extends Stringable {
    public final String pathRegex;
    public final String mediaType;

    public MediaTypeDefinition(final String pathRegex, final String mediaType) {
        this.pathRegex = pathRegex;
        this.mediaType = mediaType;
    }

    public String getPathRegex() {
        return pathRegex;
    }

    public String getMediaType() {
        return mediaType;
    }

}
