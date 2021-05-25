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
    private final Map<String, String> responseNameOverrides; // should this be in NameParser?  is there a better way to handle this?
    private final Set<String> nonLinkClassNames;
    private final Set<String> linkClassNames;
    private final Map<String, String> nullLinkResultClasses;

    public NameAndPathManager() {
        apiDiscoveryData = new HashSet<>();
        apiDiscoveryDataPaths = new HashSet<>();
        responseNameOverrides = populateResponseNameOverrides();
        nonLinkClassNames = new HashSet<>();
        linkClassNames = new HashSet<>();
        nullLinkResultClasses = new HashMap<>();
    }

    public Set<ApiPathData> getApiDiscoveryData() {
        return apiDiscoveryData;
    }

    public void addApiDiscoveryData(ApiPathData data) { apiDiscoveryData.add(data); }

    public boolean isRepeatApiDiscoveryPath(String path) {
        return apiDiscoveryDataPaths.contains(path);
    }

    public void addApiDiscoveryPath(String path) {
        apiDiscoveryDataPaths.add(path);
    }

    public Set<String> getNonLinkClassNames() {
        return nonLinkClassNames;
    }

    public void addNonLinkClassName(String className) {
        nonLinkClassNames.add(className);
    }

    public Set<String> getLinkClassNames() {
        return linkClassNames;
    }

    public void addLinkClassName(String className) {
        linkClassNames.add(className);
    }

    public Map<String, String> getNullLinkResultClasses() {
        return nullLinkResultClasses;
    }

    public void addNullLinkResultClass(String key, String value) {
        nullLinkResultClasses.put(key, value);
    }

    private Map<String, String> populateResponseNameOverrides() {
        Map<String, String> overrides = new HashMap<>();
        overrides.put("components/componentId/versions/componentVersionId/licenses/licenseId", "ComponentVersionLicenseLicensesLicense");
        overrides.put("licenses/licenseId/license-terms/licenseTermId", "LicenseTermAssociation");
        overrides.put("users/userId/projects", "AssignedProject");
        return overrides;
    }

    public String getResponseNameOverride(String responsePath) {
        String key = responsePath.split("/GET")[0];
        return responseNameOverrides.get(key);
    }

}
