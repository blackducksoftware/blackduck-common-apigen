/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integrations.apigen.maintenance;

import java.io.IOException;

import com.synopsys.integrations.apigen.maintenance.utility.ClassUsageSearcher;

/**
 * This class can be used to search Integrations projects for usage of specific classes in blackduck-common-api via ClassUsageSearcher.
 * Example) "I'd like to know what classes in what Integrations projects are using deprecated blackduck-common-api classes"
 * To Use: Inspect ClassUsageSearcher to find method that most supports your query, and provide specific class names to search for, the path to the directory you
 * wish to search (perhaps the product of IntegrationsPortfolioCreator), and a path to where you would like usage information to be written.
 */
public class ClassUsageSearchRunner {
    private static final String CLASS_NAME_TO_SEARCH_FOR = "";
    private static final String TARGET_DIRECTORY_PATH = "";
    private static final String USAGE_OUTPUT_FILE_PATH = "";
    private static final String USAGE_OUTPUT_FILE_NAME = "";

    public static void main(String[] args) throws IOException {
        ClassUsageSearcher searcher = new ClassUsageSearcher();

        // Examples
        searcher.findUsersOfDeprecatedClasses(TARGET_DIRECTORY_PATH, USAGE_OUTPUT_FILE_PATH, USAGE_OUTPUT_FILE_NAME);
        searcher.findUsersOfSpecificClass(CLASS_NAME_TO_SEARCH_FOR, TARGET_DIRECTORY_PATH, USAGE_OUTPUT_FILE_PATH, USAGE_OUTPUT_FILE_NAME);
    }
}
