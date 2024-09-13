/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;

import java.io.File;
import java.util.List;

public interface ApiParser {
    List<ResponseDefinition> parseApi(File target, MediaTypes mediaTypes);
}
