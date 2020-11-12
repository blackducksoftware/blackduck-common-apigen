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

    public static void main(String[] args) throws IOException {
        ClassUsageSearcher searcher = new ClassUsageSearcher();

        // Examples
        searcher.findUsersOfDeprecatedClasses(TARGET_DIRECTORY_PATH, USAGE_OUTPUT_FILE_PATH);
        searcher.findUsersOfSpecificClass(CLASS_NAME_TO_SEARCH_FOR, TARGET_DIRECTORY_PATH, USAGE_OUTPUT_FILE_PATH);
    }
}
