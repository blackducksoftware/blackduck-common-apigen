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
package com.synopsys.integration.create.apigen.generation.finder;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ClassNameManager {
    private static final Map<String, String> classToPackage = new HashMap<>();

    public static final String CORE_PACKAGE = "com.synopsys.integration.blackduck.api.core";
    public static final String RESPONSES_PACKAGE = CORE_PACKAGE + ".response";

    public static final String BLACKDUCK_PATH = addClass("BlackDuckPath", CORE_PACKAGE);
    public static final String BLACKDUCK_COMPONENT = addClass("BlackDuckComponent", CORE_PACKAGE);
    public static final String BLACKDUCK_RESPONSE = addClass("BlackDuckResponse", CORE_PACKAGE);
    public static final String BLACKDUCK_VIEW = addClass("BlackDuckView", CORE_PACKAGE);

    public static final String API_RESPONSE = addClass("ApiResponse", RESPONSES_PACKAGE);
    public static final String LINK_RESPONSE = addClass("LinkResponse", RESPONSES_PACKAGE);

    public static final String LINK_BLACKDUCK_RESPONSE = addClass("LinkBlackDuckResponse", RESPONSES_PACKAGE);
    public static final String LINK_SINGLE_RESPONSE = addClass("LinkSingleResponse", RESPONSES_PACKAGE);
    public static final String LINK_MULTIPLE_RESPONSES = addClass("LinkMultipleResponses", RESPONSES_PACKAGE);

    public static final String BLACKDUCK_PATH_RESPONSE = addClass("BlackDuckPathResponse", RESPONSES_PACKAGE);
    public static final String BLACKDUCK_PATH_SINGLE_RESPONSE = addClass("BlackDuckPathSingleResponse", RESPONSES_PACKAGE);
    public static final String BLACKDUCK_PATH_MULTIPLE_RESPONSE = addClass("BlackDuckPathMultipleResponses", RESPONSES_PACKAGE);

    public static final String LINK_STRING_RESPONSE = addClass("LinkStringResponse", RESPONSES_PACKAGE);

    private static String addClass(String className, String packageName) {
        classToPackage.put(className, packageName);
        return className;
    }

    private Map<String, String> simpleNameToClassName = new HashMap<>();

    public ClassNameManager() {
        for (Map.Entry<String, String> entry : classToPackage.entrySet()) {
            addClassWithPackage(entry.getKey(), entry.getValue());
        }
    }

    public String getFullyQualifiedClassName(String simpleName) {
        if (simpleName.contains("<")) {
            simpleName = simpleName.substring(0, simpleName.indexOf('<'));
        }
        return simpleNameToClassName.get(simpleName);
    }

    public Set<String> getClassNames() {
        return simpleNameToClassName.keySet();
    }

    private void addClassWithPackage(String simpleName, String pkg) {
        simpleNameToClassName.put(simpleName, pkg + "." + simpleName);
    }

}
