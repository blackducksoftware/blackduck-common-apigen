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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaintenanceRunner {

    // Environment Variable Keys
    protected static String API_DIRECTORY_PATH = "API_DIRECTORY_PATH";
    protected static final String INTEGRATIONS_PORTFOLIO_PATH = "INTEGRATIONS_PORTFOLIO_PATH";
    protected static final String GIT_PATH = "GIT_PATH";
    protected static final String GENERATED_TEMPORARY_EQUIVALENTS_PATH = "GENERATED_TEMPORARY_EQUIVALENTS_PATH";
    protected static final String CLASS_USAGE_OUTPUT_PATH = "CLASS_USAGE_OUTPUT_PATH";

    public static void main(String[] args) throws IOException {

        Logger logger = LoggerFactory.getLogger(MaintenanceRunner.class);

        String apiDirectoryPath = System.getenv(API_DIRECTORY_PATH);
        if (apiDirectoryPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where to find your local blackduck-common-api directory.", API_DIRECTORY_PATH));
            return;
        }
        File apiDirectory = new File(apiDirectoryPath);

        IntegrationsPortfolioCreator creator = new IntegrationsPortfolioCreator();
        //String pathToPortfolio = creator.createOrUpdateIntegrationsPortfolio();

        IntegrationsPortfolioSearcher searcher = new IntegrationsPortfolioSearcher();
        //searcher.findUsersOfThrowawayClasses(pathToPortfolio);
        //searcher.findUsersOfTemporaryClasses(pathToPortfolio);

        GeneratedTemporaryClassIdentifier generatedTemporaryClassIdentifier = new GeneratedTemporaryClassIdentifier(logger);
        //generatedTemporaryClassIdentifier.identifyGeneratedTemporaryClasses(apiDirectory);

        MissingClassFinder missingClassFinder = new MissingClassFinder(logger);
        String integrationsPortfolioPath = System.getenv(INTEGRATIONS_PORTFOLIO_PATH);
        if (integrationsPortfolioPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where to find your local integrations-portfolio directory is being generated.", INTEGRATIONS_PORTFOLIO_PATH));
            return;
        }
        File controlDirectory = new File(new File(integrationsPortfolioPath), "blackduck-common-api");
        //missingClassFinder.findMissingClassesInOutput(apiDirectory, controlDirectory);
    }
}
