/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.model;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.create.apigen.data.mediatype.MediaTypes;


public class ApiSpecification {
    private final String MEDIA_TYPES_CSV_NAME_KEY = "MEDIA_TYPES_CSV_NAME";
    private File root;
    private String mediaTypesCsvName;

    public ApiSpecification(final File root, String mediaTypesCsvName) {
        this.root = root;
        this.mediaTypesCsvName = mediaTypesCsvName;
    }

    public File getRoot() {
        return root;
    }

    public MediaTypes getMediaTypesFile() {
        mediaTypesCsvName = StringUtils.defaultIfBlank(System.getenv(MEDIA_TYPES_CSV_NAME_KEY), mediaTypesCsvName);
        File mediaTypesCsv = new File(root, mediaTypesCsvName);
        return new MediaTypes(mediaTypesCsv);
    }
}
