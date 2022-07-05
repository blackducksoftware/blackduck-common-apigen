/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
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
 * This class can be used to compare two blackduck-common-api directories (a "test" and a "control") via ApiDiffFinder.
 * To Use: Provide the paths to both a "test" and a "control" blackduck-common-api directory.
 * Note: Both directories must be built first (the diff finder parses the .class files in the "build" subdirectory).
 * Typically, one would be your local generated blackduck-common-api, while the other (your "control" to compare against) might be the blackduck-common-api located
 * in the Integrations portfolio you create with IntegrationsPortfolioCreator.
 */
public class ApiDiffFinderRunner {
    private static final String TEST_API__PATH = "";
    private static final String CONTROL_API_PATH = "";
    private static final String API_DIFF_OUTPUT_PATH = "";
    private static final String MISSSING_API_PATH_MESSAGE = "Could not find file at provided path.";
    private static Logger logger = LoggerFactory.getLogger(ApiDiffFinderRunner.class);

    public static void main(String[] args) throws IOException {
        ApiDiffFinder apiDiffFinder = new ApiDiffFinder(logger);
        File testApi = DirectoryFinder.getDirectoryFromPath(TEST_API__PATH, MISSSING_API_PATH_MESSAGE);
        File controlApi = DirectoryFinder.getDirectoryFromPath(CONTROL_API_PATH, MISSSING_API_PATH_MESSAGE);
        ApiDiff apiDiff = apiDiffFinder.findDiffInApi(testApi, controlApi);
        apiDiffFinder.writeDiffToFile(apiDiff, API_DIFF_OUTPUT_PATH);
    }

}
