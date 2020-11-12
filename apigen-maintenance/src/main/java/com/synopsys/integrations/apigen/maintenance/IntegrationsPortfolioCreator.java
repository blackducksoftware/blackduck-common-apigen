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

import com.synopsys.integrations.apigen.maintenance.utility.DirectoryFinder;
import com.synopsys.integrations.apigen.maintenance.utility.GithubProjectCloner;

/**
 * This class can be used to create a local portfolio of Integrations projects.
 * To Use:
 * Provide the path to the location where you would like the portfolio of projects to be generated.
 */

public class IntegrationsPortfolioCreator {
    private static Logger logger = LoggerFactory.getLogger(IntegrationsPortfolioCreator.class);
    private static final String INTEGRATIONS_PORTFOLIO_PATH = "";
    private static final String MISSING_PATH_MESSAGE = "You must specify where to create the Integrations portfolio " + INTEGRATIONS_PORTFOLIO_PATH;

    public String createOrUpdateIntegrationsPortfolio() throws IOException {
        File portfolioDirectory = DirectoryFinder.getDirectoryFromPath(INTEGRATIONS_PORTFOLIO_PATH, MISSING_PATH_MESSAGE);
        portfolioDirectory.mkdirs();

        String[] projects = {
            "synopsys-detect",
            "blackduck-alert",
            "blackduck-docker-inspector",
            "blackduck-artifactory",
            "blackduck-common",
            "blackduck-common-api"
        };

        GithubProjectCloner githubProjectCloner = new GithubProjectCloner(logger);
        for (String project : projects) {
            githubProjectCloner.cloneOrUpdateProject(project, new File(portfolioDirectory, project));
        }

        return portfolioDirectory.getAbsolutePath();
    }

    public static void main(String[] args) throws IOException {
        IntegrationsPortfolioCreator integrationsPortfolioCreator = new IntegrationsPortfolioCreator();
        integrationsPortfolioCreator.createOrUpdateIntegrationsPortfolio();
    }

}
