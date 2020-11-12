/**
 * apigen-maintenance
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
package com.synopsys.integrations.apigen.maintenance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.synopsys.integrations.apigen.maintenance.utility.DirectoryFinder;
import com.synopsys.integrations.apigen.maintenance.utility.RedundantClassFinder;

/**
 * This class can be used to determine potentially redundant classes within the API via RedundantClassFinder.
 * To use: Inspect RedundantClassFinder to find method that most supports your query, provide a path to a "test" blackduck-common-api directory,
 * provide a path to a "control" blackduck-common-api directory, and specify a path to where you'd like to have redundancy information written to.
 * Note: RedundantClassFinder has some methods that only require one directory, as they compare directories within a single blackduck-common-api.
 */
public class RedundantClassFinderRunner {
    private static final String TEST_API_PATH = "";
    private static final String CONTROL_API_PATH = "";
    private static final String MISSING_API_PATH_MESSAGE = "You have not provided the path to an API directory.";
    private static final String REDUNDANT_API_OUTPUT_PATH = "";

    public static void main(String[] args) throws IOException {
        RedundantClassFinder redundantClassFinder = new RedundantClassFinder();
        File testApiDirectory = DirectoryFinder.getDirectoryFromPath(TEST_API_PATH, MISSING_API_PATH_MESSAGE);
        File controlApiDirectory = DirectoryFinder.getDirectoryFromPath(CONTROL_API_PATH, MISSING_API_PATH_MESSAGE);
        Map<String, Set<String>> redundantClasses = redundantClassFinder.identifyRedundantClasses(testApiDirectory, controlApiDirectory, new ArrayList<>(), null);
        redundantClassFinder.writePotentialEquivalentsToFile(redundantClasses, REDUNDANT_API_OUTPUT_PATH);
    }


}
