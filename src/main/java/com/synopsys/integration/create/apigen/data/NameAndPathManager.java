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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.ApiPathData;

@Component
public class NameAndPathManager {

    private final Set<ApiPathData> apiDiscoveryData;
    private final Set<String> apiDiscoveryDataPaths;
    private final Set<String> nonLinkClassNames;
    private final Set<String> linkClassNames;
    private final List<String> randomLinkClassNames;
    private final Map<String, String> nullLinkResultClasses;

    public NameAndPathManager() {
        apiDiscoveryData = new HashSet<>();
        apiDiscoveryDataPaths = new HashSet<>();
        nonLinkClassNames = new HashSet<>();
        linkClassNames = new HashSet<>();
        randomLinkClassNames = new ArrayList<>();
        nullLinkResultClasses = new HashMap<>();
    }

    public Set<ApiPathData> getApiDiscoveryData() {
        return apiDiscoveryData;
    }

    public void addApiDiscoveryData(final ApiPathData data) { apiDiscoveryData.add(data); }

    public boolean isRepeatApiDiscoveryPath(final String path) {
        return apiDiscoveryDataPaths.contains(path);
    }

    public void addApiDiscoveryPath(final String path) {
        apiDiscoveryDataPaths.add(path);
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

}
