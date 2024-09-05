/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.exception;

import com.synopsys.integration.exception.IntegrationException;

public class NullMediaTypeException extends IntegrationException {

    public NullMediaTypeException(String path) {
        super(String.format("No media type for path: %s.", path));
    }
}
