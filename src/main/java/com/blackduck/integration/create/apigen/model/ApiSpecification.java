/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import java.io.File;

import com.blackduck.integration.create.apigen.Application;
import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;

public class ApiSpecification {
    private File root;

    public ApiSpecification(final File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public MediaTypes getMediaTypesFile() {
        File mediaTypesCsv = new File(root, Application.MEDIA_TYPES_CSV_NAME);
        return new MediaTypes(mediaTypesCsv);
    }
}
