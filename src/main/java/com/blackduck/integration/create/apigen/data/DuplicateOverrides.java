/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * Mechanism for storing data collected by DuplicateTypeIdentifier.
 */
@Component
public class DuplicateOverrides {
    private Map<String, Set<String>> firstPassOverrides;

    public DuplicateOverrides() {
        this.firstPassOverrides = new HashMap<>();
    }

    public void addFirstPassOverride(String newType, String oldType) {
        firstPassOverrides.computeIfAbsent(newType, type -> new HashSet<>()).add(oldType);
    }

    public String getOverride(String type) {
        return getFinalOverrides().get(type);
    }

    public Map<String, String> getFinalOverrides() {
        Map<String, String> finalOverrides = new HashMap<>();
        for (Map.Entry<String, Set<String>> firstPassOverride : firstPassOverrides.entrySet()) {
            Set<String> allTypes = firstPassOverride.getValue();
            String firstPassOverrideType = firstPassOverride.getKey();
            allTypes.add(firstPassOverrideType);
            String finalOverrideType = getShortest(allTypes);
            for (String type : allTypes) {
                if (!type.equals(finalOverrideType)) {
                    finalOverrides.put(type, finalOverrideType);
                }
            }
        }
        return finalOverrides;
    }

    private String getShortest(Set<String> strings) {
        String shortest = strings.iterator().next();
        for (String string : strings) {
            if (string.length() < shortest.length()) {
                shortest = string;
            }
        }
        return shortest;
    }
}
