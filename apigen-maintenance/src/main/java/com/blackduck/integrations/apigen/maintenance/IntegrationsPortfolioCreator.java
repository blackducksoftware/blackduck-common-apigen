/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance;

import java.io.File;
import java.io.IOException;

import com.blackduck.integrations.apigen.maintenance.utility.DirectoryFinder;
import com.blackduck.integrations.apigen.maintenance.utility.GithubProjectCloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
