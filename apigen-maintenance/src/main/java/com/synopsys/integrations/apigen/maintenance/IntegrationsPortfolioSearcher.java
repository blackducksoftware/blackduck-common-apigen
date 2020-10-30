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

import static com.synopsys.integrations.apigen.maintenance.MaintenanceRunner.CLASS_USAGE_OUTPUT_PATH;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationsPortfolioSearcher {

    private static final String THROWAWAY_CLASS_USAGE_TOKEN = "import .*throwaway.*";
    private static final String TEMPORARY_CLASS_USAGE_TOKEN = "import .*temporary.*";
    private static final String DEPRECATED_CLASS_USAGE_TOKEN = "import .*deprecated.*";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void findUsersOfThrowawayClasses(String pathToSource) throws IOException {
        Process findings = searchForTokenGrep(THROWAWAY_CLASS_USAGE_TOKEN, pathToSource);
        writeUsageToOutputFile(findings, "throwaway-usage.txt");
    }

    public void findUsersOfTemporaryClasses(String pathToSource) throws IOException {
        Process findings = searchForTokenGrep(TEMPORARY_CLASS_USAGE_TOKEN, pathToSource);
        writeUsageToOutputFile(findings, "temporary-usage.txt");
    }

    public void findUsersOfDeprecatedClasses(String pathToSource) throws IOException {
        Process findings = searchForTokenGrep(DEPRECATED_CLASS_USAGE_TOKEN, pathToSource);
        writeUsageToOutputFile(findings, "deprecated-usage.txt");
    }

    public void findUsersOfSpecificClass(String className, String pathToSource) throws IOException {
        searchForTokenGrep(className, pathToSource);
    }

    private Process searchForTokenGrep(String token, String pathToSource) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String grepCommand = String.format("grep -r -l \"%s %s\"", token, pathToSource);
        return runtime.exec(grepCommand);
    }

    private Set<String> searchForTokenJava(String token, String pathToSource) throws IOException {
        File source = new File(pathToSource);
        Pattern pattern = Pattern.compile(token);
        return searchForTokenJava(pattern, source);
    }

    private Set<String> searchForTokenJava(Pattern pattern, File file) throws IOException {
        Set<String> filesContainingToken = new HashSet<>();
        if (file.isDirectory() && file.listFiles() != null) {
            for (File child : file.listFiles()) {
                filesContainingToken.addAll(searchForTokenJava(pattern, child));
            }
        } else {
            for (String line : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                if (pattern.matcher(line).matches()) {
                    filesContainingToken.add(file.getName());
                }
            }
        }
        return filesContainingToken;
    }

    private void writeUsageToOutputFile(Process process, String usageFileName) throws IOException {
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        File outputFile = getUsageOutputFile(usageFileName);
        FileWriter writer = new FileWriter(outputFile);
        String line;
        while ((line = stdInput.readLine()) != null) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }

    private void writeUsageToOutputFile(Set<String> usage, String usageFileName) throws IOException {
        File outputFile = getUsageOutputFile(usageFileName);
        FileWriter writer = new FileWriter(outputFile);
        for (String line : usage) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }

    private File getUsageOutputFile(String usageFileName) {
        String classUsageOutputPath = System.getenv(CLASS_USAGE_OUTPUT_PATH);
        if (classUsageOutputPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where information on class usage should be written.", CLASS_USAGE_OUTPUT_PATH));
        }

        File outputDirectory = new File(classUsageOutputPath);
        outputDirectory.mkdirs();
        return new File(outputDirectory, usageFileName);
    }
}
