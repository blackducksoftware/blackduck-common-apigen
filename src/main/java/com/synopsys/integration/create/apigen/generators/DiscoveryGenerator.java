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
package com.synopsys.integration.create.apigen.generators;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ResultClassData;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.parser.ApiPathData;

import freemarker.template.Template;

@Component
public class DiscoveryGenerator {
    private final ClassCategories classCategories;
    private final DataManager dataManager;
    private final FreeMarkerHelper freeMarkerHelper;

    @Autowired
    public DiscoveryGenerator(final ClassCategories classCategories, final DataManager dataManager, final FreeMarkerHelper freeMarkerHelper) {
        this.classCategories = classCategories;
        this.dataManager = dataManager;
        this.freeMarkerHelper = freeMarkerHelper;
    }

    // taken from Swaggerhub
    public void createDiscoveryFile(final File baseDirectory, final Template template) throws Exception {

        final Map<String, Object> model = new HashMap<>();
        model.put("discoveryPackage", UtilStrings.GENERATED_DISCOVERY_PACKAGE);

        final Set<String> imports = new HashSet<>();
        final Set<ApiPathData> apiData = dataManager.getApiDiscoveryData();
        model.put("apiPathData", apiData);
        for (final ApiPathData data : apiData) {
            final String resultClass = data.getResultClass();
            final ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
            final String resultImportPath = resultClassData.getResultImportPath();
            final String resultImportType = resultClassData.getResultImportType();
            final String importPackage = resultImportPath + resultImportType + "." + resultClass;
            if (null == importPackage || null == resultImportType) {
                System.out.println("couldn't find package for: " + resultClass);
            } else {
                imports.add(importPackage);
                imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + "LinkResponse");
                imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + "BlackDuckPath");
                imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + "BlackDuckPathSingleResponse");
                imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + "BlackDuckPathMultipleResponses");
            }
        }
        final List sortedImports = new ArrayList<>(imports);
        Collections.sort(sortedImports);
        model.put("imports", sortedImports);

        freeMarkerHelper.writeFile("ApiDiscovery", template, model, baseDirectory.getAbsolutePath());
    }

}
