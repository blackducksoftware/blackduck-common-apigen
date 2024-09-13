/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance;

import com.blackduck.integrations.apigen.maintenance.utility.DirectoryFinder;
import com.blackduck.integrations.apigen.maintenance.utility.RedundantClassFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
