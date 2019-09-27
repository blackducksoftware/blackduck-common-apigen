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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DataManager {

    public final Set<String> nonLinkClassNames;
    public final Set<String> linkClassNames;
    public final List<String> randomLinkClassNames;
    public final Map<String, String> nullLinkResultClasses;

    private final Map<String, MediaVersionHelper> latestViewMediaVersions;
    private final Map<String, MediaVersionHelper> latestComponentMediaVersions;

    public DataManager() {
        nonLinkClassNames = new HashSet<>();
        linkClassNames = new HashSet<>();
        randomLinkClassNames = new ArrayList<>();
        nullLinkResultClasses = new HashMap<>();
        latestViewMediaVersions = new HashMap<>();
        latestComponentMediaVersions = new HashMap<>();
    }

    public Set<String> getNonLinkClassNames() {
        return nonLinkClassNames;
    }

    public void addNonLinkClassName(final String className) {
        nonLinkClassNames.add(className);
    }

    public Set<String> getLinkClassNames() {
        return linkClassNames;
    }

    public void addLinkClassName(final String className) {
        linkClassNames.add(className);
    }

    public List<String> getRandomLinkClassNames() {
        return randomLinkClassNames;
    }

    public void addRandomLinkClassName(final String randomLinkClassName) {
        randomLinkClassNames.add(randomLinkClassName);
    }

    public Map<String, String> getNullLinkResultClasses() {
        return nullLinkResultClasses;
    }

    public void addNullLinkResultClass(final String key, final String value) {
        nullLinkResultClasses.put(key, value);
    }

    public Map<String, MediaVersionHelper> getLatestViewMediaVersions() {
        return latestViewMediaVersions;
    }

    public Map<String, MediaVersionHelper> getLatestComponentMediaVersions() {
        return latestComponentMediaVersions;
    }
}