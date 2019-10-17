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
package com.synopsys.integration.create.apigen.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ApiPathDataPopulator {

    final DataManager dataManager;
    private final Set<String> apiPathsToIgnore;
    private final Map<String, String> apiPathResultClassOverrides;
    private final Set<ApiPathData> addOnApiPaths;

    public ApiPathDataPopulator(final DataManager dataManager) {
        this.dataManager = dataManager;
        this.apiPathsToIgnore = populateApiPathsToIgnore();
        this.apiPathResultClassOverrides = populateApiPathResultClassOverrides();
        this.addOnApiPaths = populateAddOnApiPaths();
    }

    public void populateApiPathData(final List<ResponseDefinition> responses) {
        for (final ResponseDefinition response : responses) {
            if (!ArrayResponseIdentifier.isArrayResponse(response)) {
                final String apiPath = getApiDiscoveryPath(response.getResponseSpecificationPath());
                if (!dataManager.isRepeatApiDiscoveryPath(apiPath) && !apiPathsToIgnore.contains(apiPath)) {
                    final String nonVersionedResponseName = NameParser.getNonVersionedName(response.getName());
                    final ApiPathData apiDiscoveryData;
                    final String resultClassOverride = apiPathResultClassOverrides.get(apiPath);
                    if (resultClassOverride != null) {
                        apiDiscoveryData = new ApiPathData(apiPath, resultClassOverride, response.hasMultipleResults());
                    } else {
                        apiDiscoveryData = new ApiPathData(apiPath, nonVersionedResponseName, response.hasMultipleResults());
                    }
                    dataManager.addApiDiscoveryData(apiDiscoveryData);
                    dataManager.addApiDiscoveryPath(apiPath);
                }
            }
        }
        for (final ApiPathData addOn : addOnApiPaths) {
            dataManager.addApiDiscoveryData(addOn);
            dataManager.addApiDiscoveryPath(addOn.path);
        }
    }

    private String getApiDiscoveryPath(final String responseRelativePath) {
        final String[] pathPieces = responseRelativePath.split("/");
        return pathPieces[0];
    }

    private Set<String> populateApiPathsToIgnore() {
        final Set<String> apiPathsToIgnore = new HashSet<>();

        apiPathsToIgnore.add("GET");

        return apiPathsToIgnore;
    }

    private Map<String, String> populateApiPathResultClassOverrides() {
        final Map<String, String> apiPathResultClassOverrides = new HashMap<>();

        apiPathResultClassOverrides.put("projects", "ProjectView");
        apiPathResultClassOverrides.put("current-user", "UserView");
        apiPathResultClassOverrides.put("components", "ComponentSearchResultView");

        return apiPathResultClassOverrides;
    }

    private Set<ApiPathData> populateAddOnApiPaths() {
        final Set<ApiPathData> addOns = new HashSet<>();

        addOns.add(new ApiPathData("notifications", "NotificationView", true));
        addOns.add(new ApiPathData("current-version", "CurrentVersionView", false));

        return addOns;
    }
}
