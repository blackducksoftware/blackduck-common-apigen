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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integrations.apigen.maintenance.model.ApiDiff;
import com.synopsys.integrations.apigen.maintenance.utility.ApiDiffFinder;
import com.synopsys.integrations.apigen.maintenance.utility.DirectoryFinder;

/**
 * This class can be used to compare two blackduck-common-api directories, a "test" and a "control", via ApiDiffFinder.
 * To Use: Provide the paths to both a "test" and a "control" blackduck-common-api directory.
 * Typically, one would be your local generated blackduck-common-api, while the other (your "control" to compare against) might be the blackduck-common-api located
 * in the Integrations portfolio you create with IntegrationsPortfolioCreator.
 */
public class ApiDiffFinderRunner {
    private static final String TEST_API__PATH = "/Users/crowley/Documents/source/blackduck-common-api";
    private static final String CONTROL_API_PATH = "/Users/crowley/Desktop/test-projects/integrations-portfolio/blackduck-common-api";
    private static final String MISSSING_API_PATH_MESSAGE = "You have not provided a path to an API directory you wish to compare.";
    private static final String API_DIFF_OUTPUT_PATH = "/Users/crowley/Desktop/bd-api-maintenance-data/api-diffs.txt";
    private static Logger logger = LoggerFactory.getLogger(ApiDiffFinderRunner.class);

    public static void main(String[] args) throws IOException {
        ApiDiffFinder apiDiffFinder = new ApiDiffFinder(logger);
        File testApi = DirectoryFinder.getDirectoryFromPath(TEST_API__PATH, MISSSING_API_PATH_MESSAGE);
        File controlApi = DirectoryFinder.getDirectoryFromPath(CONTROL_API_PATH, MISSSING_API_PATH_MESSAGE);
        ApiDiff apiDiff = apiDiffFinder.findDiffInApi(testApi, controlApi);
        apiDiffFinder.writeDiffToFile(apiDiff, API_DIFF_OUTPUT_PATH);
    }

}
