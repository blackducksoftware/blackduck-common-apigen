/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.util.List;

import com.synopsys.integration.create.apigen.data.mediatype.MediaTypes;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public interface ApiParser {
    List<ResponseDefinition> parseApi(File target, MediaTypes mediaTypes);
}
