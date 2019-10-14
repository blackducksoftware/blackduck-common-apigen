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

import java.util.Map;

public class MediaVersionHelper {
    // TODO - Make this a MediaVersions class, that contains the map passed into updateLatestMediaVersions
    private final String nonVersionedClassName;
    private final Integer mediaVersion;
    private final String mediaType;
    private final Map<String, Object> input;

    public MediaVersionHelper(final String className, final Integer mediaVersion, final Map<String, Object> input, final String mediaType) {
        this.nonVersionedClassName = className.endsWith("V") ? className.substring(0, className.length() - 1) : className.replace("ViewV", "View");
        this.mediaVersion = mediaVersion;
        this.mediaType = mediaType;
        this.input = input;
    }

    public String getNonVersionedClassName() { return this.nonVersionedClassName; }

    public Integer getMediaVersion() { return this.mediaVersion; }

    public Map<String, Object> getInput() { return this.input; }

    public String getVersionedClassName() { return this.nonVersionedClassName + "V" + this.mediaVersion.toString(); }

    public String getMediaType() {
        return mediaType;
    }

    public String toString() {
        return this.nonVersionedClassName + "\n" + this.mediaVersion + "\n\t" + this.input.get("className");
    }

    public static void updateLatestMediaVersions(final String className, final Map<String, Object> input, final Map<String, MediaVersionHelper> latestMediaVersions, final String mediaType) {
        final MediaVersionHelper newHelper = getMediaVersionHelper(className, input, mediaType);
        if (newHelper == null) {
            return;
        }
        final String nonVersionedClass = newHelper.getNonVersionedClassName();
        final Integer mediaVersion = newHelper.getMediaVersion();
        final MediaVersionHelper oldHelper = latestMediaVersions.get(nonVersionedClass);

        if (mediaVersion != null) {
            if (oldHelper == null || mediaVersion > oldHelper.getMediaVersion()) {
                latestMediaVersions.put(nonVersionedClass, newHelper);
            }
        }
    }

    private static MediaVersionHelper getMediaVersionHelper(final String className, final Map<String, Object> input, final String mediaType) {
        Integer mediaVersion = null;
        String nonVersionedClassName = null;
        // Assuming no more than 9 mediaVersions per View class
        final String[] MEDIA_VERSION_NUMBERS = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        try {
            for (final String number : MEDIA_VERSION_NUMBERS) {
                if (className.contains(number)) {
                    mediaVersion = Integer.decode(number);
                    nonVersionedClassName = className.replace(number, "");
                    input.put("className", className);
                }
            }
            return new MediaVersionHelper(nonVersionedClassName, mediaVersion, input, mediaType);
        } catch (final NullPointerException e) {
            return null;
        }
    }

}
