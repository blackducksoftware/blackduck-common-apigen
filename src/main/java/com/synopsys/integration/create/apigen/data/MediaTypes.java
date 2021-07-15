/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.data;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.parser.MediaTypeFileParser;

public class MediaTypes {
    private Map<String, String> LONG_TO_SHORT = new HashMap<>();
    private Map<String, String> SHORT_TO_LONG = new HashMap<>();


    public MediaTypes(File mediaTypesFile) {
        MediaTypeFileParser mediaTypeFileParser = new MediaTypeFileParser(mediaTypesFile);
        LONG_TO_SHORT.putAll(mediaTypeFileParser.getLongToShort());
        SHORT_TO_LONG.putAll(mediaTypeFileParser.getShortToLong());
    }

    public Set<String> getShortNames() {
        return new HashSet<>(LONG_TO_SHORT.values());
    }

    public Set<String> getLongNames() {
        return new HashSet<>(LONG_TO_SHORT.keySet());
    }

    public String getShortName(final String longName) {
        return LONG_TO_SHORT.get(longName);
    }

    public String getLongName(final String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }
}
