/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.blackduck.integration.create.apigen.data.NameAndPathManager;
import com.blackduck.integration.create.apigen.model.ApiPathData;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;

public class ApiPathDataPopulator {
    final NameAndPathManager nameAndPathManager;
    private final Set<String> apiPathsToIgnore;
    private final Map<String, String> apiPathResultClassOverrides;
    private final Map<String, Boolean> apiPathHasMultipleResultsOverrides;
    private final Set<ApiPathData> addOnApiPaths;

    public ApiPathDataPopulator(NameAndPathManager nameAndPathManager) {
        this.nameAndPathManager = nameAndPathManager;
        this.apiPathsToIgnore = populateApiPathsToIgnore();
        this.apiPathResultClassOverrides = populateApiPathResultClassOverrides();
        this.apiPathHasMultipleResultsOverrides = populateApiPathHasMultipleResultsOverrides();
        this.addOnApiPaths = populateAddOnApiPaths();
    }

    public void populateApiPathData(List<ResponseDefinition> responses) {
        for (ResponseDefinition response : responses) {
            if (!ResponseTypeIdentifier.getResponseType(response).equals(ResponseType.ARRAY)) {
                String apiPath = getApiDiscoveryPath(response.getResponseSpecificationPath());
                if (!nameAndPathManager.isRepeatApiDiscoveryPath(apiPath) && !apiPathsToIgnore.contains(apiPath)) {
                    String responseName = response.getName();
                    ApiPathData apiDiscoveryData;
                    String resultClass;
                    boolean hasMultipleResults;

                    String resultClassOverride = apiPathResultClassOverrides.get(apiPath);
                    resultClass = resultClassOverride == null ? responseName : resultClassOverride;
                    Boolean hasMultipleResultsOverride = apiPathHasMultipleResultsOverrides.get(responseName);
                    hasMultipleResults = hasMultipleResultsOverride == null ? response.hasMultipleResults() : hasMultipleResultsOverride;

                    apiDiscoveryData = new ApiPathData(apiPath, resultClass, hasMultipleResults);
                    nameAndPathManager.addApiDiscoveryData(apiDiscoveryData);
                    nameAndPathManager.addApiDiscoveryPath(apiPath);
                }
            }
        }
        for (ApiPathData addOn : addOnApiPaths) {
            nameAndPathManager.addApiDiscoveryData(addOn);
            nameAndPathManager.addApiDiscoveryPath(addOn.path);
        }
    }

    private String getApiDiscoveryPath(String responseRelativePath) {
        String[] pathPieces = responseRelativePath.split("/");
        return pathPieces[0];
    }

    private Set<String> populateApiPathsToIgnore() {
        Set<String> apiPathsToIgnore = new HashSet<>();

        apiPathsToIgnore.add("GET");

        return apiPathsToIgnore;
    }

    private Map<String, String> populateApiPathResultClassOverrides() {
        Map<String, String> apiPathResultClassOverrides = new HashMap<>();

        apiPathResultClassOverrides.put("projects", "ProjectView");
        apiPathResultClassOverrides.put("current-user", "UserView");

        return apiPathResultClassOverrides;
    }

    private Map<String, Boolean> populateApiPathHasMultipleResultsOverrides() {
        Map<String, Boolean> apiPathHasMultipleResultOverrides = new HashMap<>();

        apiPathHasMultipleResultOverrides.put("ComponentVersionRiskProfileView", true);
        apiPathHasMultipleResultOverrides.put("ComponentsView", true);

        return apiPathHasMultipleResultOverrides;
    }

    private Set<ApiPathData> populateAddOnApiPaths() {
        Set<ApiPathData> addOns = new HashSet<>();

        addOns.add(new ApiPathData("intelligent-persistence-scans", "BlackDuckResponse", false));
        addOns.add(new ApiPathData("notifications", "NotificationView", true));
        addOns.add(new ApiPathData("uploads", "BlackDuckStringResponse", false));

        return addOns;
    }

}
