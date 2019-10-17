/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class MediaVersions {

    private static final Map<String, MediaVersionData> latestViewMediaVersions = new HashMap<>();
    private static final Map<String, MediaVersionData> latestComponentMediaVersions = new HashMap<>();

    public Map<String, MediaVersionData> getLatestViewMediaVersions() {
        return latestViewMediaVersions;
    }

    public Map<String, MediaVersionData> getLatestComponentMediaVersions() {
        return latestComponentMediaVersions;
    }

    public static void updateLatestViewMediaVersions(final String className, final Map<String, Object> input, final String mediaType) {
        updateLatestMediaVersions(className, input, latestViewMediaVersions, mediaType);
    }

    public static void updateLatestComponentMediaVersions(final String className, final Map<String, Object> input, final String mediaType) {
        updateLatestMediaVersions(className, input, latestComponentMediaVersions, mediaType);
    }

    public static void updateLatestMediaVersions(final String className, final Map<String, Object> input, final Map<String, MediaVersionData> latestMediaVersions, final String mediaType) {
        final MediaVersionData newHelper = getMediaVersionHelper(className, input, mediaType);
        if (newHelper == null) {
            return;
        }
        final String nonVersionedClass = newHelper.getNonVersionedClassName();
        final Integer mediaVersion = newHelper.getMediaVersion();
        final MediaVersionData oldHelper = latestMediaVersions.get(nonVersionedClass);

        if (mediaVersion != null) {
            if (oldHelper == null || mediaVersion > oldHelper.getMediaVersion()) {
                latestMediaVersions.put(nonVersionedClass, newHelper);
            }
        }
    }

    private static MediaVersionData getMediaVersionHelper(final String className, final Map<String, Object> input, final String mediaType) {
        final Integer mediaVersion;
        final String nonVersionedClassName;
        try {
            nonVersionedClassName = NameParser.getNonVersionedName(className);
            mediaVersion = Integer.decode(NameParser.getMediaVersion(className));
            input.put("className", className);

            return new MediaVersionData(nonVersionedClassName, mediaVersion, input, mediaType);
        } catch (final NullPointerException e) {
            return null;
        }
    }
}
