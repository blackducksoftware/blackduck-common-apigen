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
package com.synopsys.integration.create.apigen.data.mediatype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class MediaTypes {
    public static final String LONG_FORM_PREFIX = "application/vnd.blackducksoftware.";
    public static final String LONG_FORM_SUFFIX = "+json";

    public static final String SHORT_FORM_PREFIX = "bds_";
    public static final String SHORT_FORM_SUFFIX = "_json";

    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>();

    public static Set<String> getLongNames() {
        return new HashSet<>(SHORT_TO_LONG.values());
    }

    public static String getLongName(final String shortName) {
        return SHORT_TO_LONG.computeIfAbsent(shortName, (key) -> {
            String mediaItem = key.replace(SHORT_FORM_PREFIX, "");
            mediaItem = mediaItem.replace(SHORT_FORM_SUFFIX, "");
            mediaItem = mediaItem.replace("_", "-");
            return LONG_FORM_PREFIX + mediaItem + LONG_FORM_SUFFIX;
        });
    }

}
