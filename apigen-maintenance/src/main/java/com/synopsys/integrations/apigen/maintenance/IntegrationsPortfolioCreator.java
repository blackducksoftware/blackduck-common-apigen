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

import static com.synopsys.integrations.apigen.maintenance.MaintenanceRunner.GIT_PATH;
import static com.synopsys.integrations.apigen.maintenance.MaintenanceRunner.INTEGRATIONS_PORTFOLIO_PATH;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synopsys.integrations.apigen.maintenance.model.BlackDuckGitHubRepo;

public class IntegrationsPortfolioCreator {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createOrUpdateIntegrationsPortfolio() throws IOException {
        String portfolioPath = System.getenv(INTEGRATIONS_PORTFOLIO_PATH);
        if (portfolioPath == null) {
            logger.info("You must set the environment variable " + INTEGRATIONS_PORTFOLIO_PATH);
            return "";
        }
        File portfolioDirectory = new File(portfolioPath);
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
            githubProjectCloner.cloneOrUpdateProject(project);
        }

        return portfolioDirectory.getAbsolutePath();
    }

}
