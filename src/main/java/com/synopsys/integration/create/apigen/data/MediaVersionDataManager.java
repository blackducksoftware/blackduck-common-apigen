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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.MediaVersionData;
import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class MediaVersionDataManager {

    private final Map<String, MediaVersionData> latestViewMediaVersions = new HashMap<>();
    private final Map<String, MediaVersionData> latestComponentMediaVersions = new HashMap<>();
    private final Map<String, MediaVersionData> latestResponseMediaVersions = new HashMap<>();
    private final ClassCategories classCategories;
    private final Set<String> namesToIgnore = populateNamesToIgnore();

    public MediaVersionDataManager(final ClassCategories classCategories) {
        this.classCategories = classCategories;
    }

    private static Set<String> populateNamesToIgnore() {
        final Set<String> namesToIgnore = new HashSet<>();

        namesToIgnore.add("");
        namesToIgnore.add(UtilStrings.STRING);

        return namesToIgnore;
    }

    public Map<String, MediaVersionData> getLatestViewMediaVersions() {
        return latestViewMediaVersions;
    }

    public Map<String, MediaVersionData> getLatestComponentMediaVersions() {
        return latestComponentMediaVersions;
    }

    public Map<String, MediaVersionData> getLatestResponseMediaVersions() {
        return latestResponseMediaVersions;
    }

    public void updateLatestMediaVersions(final String className, final Map<String, Object> input, final String mediaType) {
        final MediaVersionData newData = getMediaVersionData(className, input, mediaType);
        final ClassTypeEnum classType = classCategories.computeType(className);
        if (newData == null || classType.isCommon()) {
            return;
        }
        Map<String, MediaVersionData> latestMediaVersions;
        if (classType.isView()) {
            latestMediaVersions = latestViewMediaVersions;
        } else if (classType.isResponse()) {
            latestMediaVersions = latestResponseMediaVersions;
        } else {
            latestMediaVersions = latestComponentMediaVersions;
        }
        try {
            final String nonVersionedClass = newData.getNonVersionedClassName();
            final Integer mediaVersion = newData.getMediaVersion();
            final MediaVersionData oldData = latestMediaVersions.get(nonVersionedClass);

            if (mediaVersion != null) {
                if ((oldData == null || mediaVersion > oldData.getMediaVersion()) && !namesToIgnore.contains(nonVersionedClass)) {
                    latestMediaVersions.put(nonVersionedClass, combineData(oldData, newData));
                }
            } else {
                latestMediaVersions.put(nonVersionedClass, combineData(oldData, newData));
            }
        } catch (final NullPointerException e) {
            return;
        }
    }

    private MediaVersionData combineData(MediaVersionData oldData, MediaVersionData newData) {
        Map<String, Object> newInput = new HashMap<>();
        if (null != oldData) {
            newInput.putAll(oldData.getInput());
        }

        if (null != newData) {
            newInput.putAll(newData.getInput());
        }

        return new MediaVersionData(newData.getNonVersionedClassName(), newData.getMediaVersion(), newInput, newData.getMediaType());
    }

    private MediaVersionData getMediaVersionData(final String className, final Map<String, Object> input, final String mediaType) {
        final Integer mediaVersion;
        final String nonVersionedClassName;
        try {
            nonVersionedClassName = NameParser.getNonVersionedName(className);
            final String mediaVersionStr = NameParser.getMediaVersion(className);
            if (mediaVersionStr == null) {
                return new MediaVersionData(nonVersionedClassName, new Integer(0), input, mediaType);
            }
            mediaVersion = Integer.decode(mediaVersionStr);
            input.put(UtilStrings.CLASS_NAME, className);

            return new MediaVersionData(nonVersionedClassName, mediaVersion, input, mediaType);
        } catch (final NullPointerException e) {
            return null;
        }
    }
}
