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

import com.synopsys.integration.create.apigen.model.ApiPathData;

@Component
public class NameAndPathManager {

    private final Set<ApiPathData> apiDiscoveryData;
    private final Set<String> apiDiscoveryDataPaths;
    private final Map<String, String> responseNamesAndEndpoints; // should this be in NameParser?
    private final Map<String, String> responseNameOverrides; // should this be in NameParser?  is there a better way to handle this?
    private final Set<String> nonLinkClassNames;
    private final Set<String> linkClassNames;
    private final Map<String, String> nullLinkResultClasses;

    public NameAndPathManager() {
        apiDiscoveryData = new HashSet<>();
        apiDiscoveryDataPaths = new HashSet<>();
        responseNamesAndEndpoints = new HashMap<>();
        responseNameOverrides = populateResponseNameOverrides();
        nonLinkClassNames = new HashSet<>();
        linkClassNames = new HashSet<>();
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

    public Map<String, String> getResponseNamesAndEndpoints() {
        return responseNamesAndEndpoints;
    }

    public void addResponseNameAndEndpoint(String name, String path) {
        responseNamesAndEndpoints.put(name, path);
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

    public Map<String, String> getNullLinkResultClasses() {
        return nullLinkResultClasses;
    }

    public void addNullLinkResultClass(final String key, final String value) {
        nullLinkResultClasses.put(key, value);
    }

    private Map<String, String> populateResponseNameOverrides() {
        Map<String, String> overrides = new HashMap<>();
        overrides.put("components/componentId/versions/componentVersionId/licenses/licenseId", "ComponentVersionLicenseLicensesLicense");
        // Because the responses from the endpoints at users/userId/projects and usergroups/userGroupId/projects are identical, only usergroups/...
        // will be generated. The context of where this object is used in the rest of the API makes more sense for it to be called AssignedProjectView
        overrides.put("usergroups/userGroupId/projects", "AssignedProject");
        return overrides;
    }

    public String getResponseNameOverride(String responsePath) {
        String key = responsePath.split("/GET")[0];
        return responseNameOverrides.get(key);
    }

}
