/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.exception;

import com.synopsys.integration.exception.IntegrationException;

public class NullMediaTypeException extends IntegrationException {

    public NullMediaTypeException(String path) {
        super(String.format("No media type for path: %s.", path));
    }
}
