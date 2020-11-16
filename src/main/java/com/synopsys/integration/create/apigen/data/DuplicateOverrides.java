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
