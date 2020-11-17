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
package com.synopsys.integrations.apigen.maintenance.utility;

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

public class ClassUsageSearcher {

    private static final String THROWAWAY_CLASS_USAGE_TOKEN = "import .*throwaway.*";
    private static final String TEMPORARY_CLASS_USAGE_TOKEN = "import .*temporary.*";
    private static final String DEPRECATED_CLASS_USAGE_TOKEN = "import .*deprecated.*";
    private static final String MISSING_USAGE_OUTPUT_FILE_PATH = "You must provide a path to the directory where information files on class usage should be written.";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void findUsersOfThrowawayClasses(String pathToSource, String pathToUsageOutputFile) throws IOException {
        findUsersOfSpecificClass(THROWAWAY_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile);
    }

    public void findUsersOfTemporaryClasses(String pathToSource, String pathToUsageOutputFile) throws IOException {
        findUsersOfSpecificClass(TEMPORARY_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile);
    }

    public void findUsersOfDeprecatedClasses(String pathToSource, String pathToUsageOutputFile) throws IOException {
        findUsersOfSpecificClass(DEPRECATED_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile);
    }

    public void findUsersOfSpecificClass(String className, String pathToSource, String pathToUsageOutputFile) throws IOException {
        // Can adjust method to use Java or grep search implementation
        Process findings = searchForTokenGrep(className, pathToSource);
        writeUsageToOutputFile(findings, pathToUsageOutputFile);
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

    private void writeUsageToOutputFile(Process process, String pathToUsageOutputFile) throws IOException {
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        File outputFile = DirectoryFinder.getDirectoryFromPath(pathToUsageOutputFile, MISSING_USAGE_OUTPUT_FILE_PATH);
        FileWriter writer = new FileWriter(outputFile);
        String line;
        while ((line = stdInput.readLine()) != null) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }

    private void writeUsageToOutputFile(Set<String> usage, String pathToUsageOutputFile) throws IOException {
        File outputFile = DirectoryFinder.getDirectoryFromPath(pathToUsageOutputFile, MISSING_USAGE_OUTPUT_FILE_PATH);
        FileWriter writer = new FileWriter(outputFile);
        for (String line : usage) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }
}