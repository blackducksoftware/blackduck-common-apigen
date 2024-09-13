/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.utility;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ClassUsageSearcher {

    private static final String THROWAWAY_CLASS_USAGE_TOKEN = "import .*throwaway.*";
    private static final String TEMPORARY_CLASS_USAGE_TOKEN = "import .*temporary.*";
    private static final String DEPRECATED_CLASS_USAGE_TOKEN = "import .*deprecated.*";
    private static final String MISSING_USAGE_OUTPUT_FILE_PATH = "You must provide a path to the directory where information files on class usage should be written.";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void findUsersOfThrowawayClasses(String pathToSource, String pathToUsageOutputFile, String usageOutputFileName) throws IOException {
        findUsersOfSpecificClass(THROWAWAY_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile, usageOutputFileName);
    }

    public void findUsersOfTemporaryClasses(String pathToSource, String pathToUsageOutputFile, String usageOutputFileName) throws IOException {
        findUsersOfSpecificClass(TEMPORARY_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile, usageOutputFileName);
    }

    public void findUsersOfDeprecatedClasses(String pathToSource, String pathToUsageOutputFile, String usageOutputFileName) throws IOException {
        findUsersOfSpecificClass(DEPRECATED_CLASS_USAGE_TOKEN, pathToSource, pathToUsageOutputFile, usageOutputFileName);
    }

    public void findUsersOfSpecificClass(String className, String pathToSource, String pathToUsageOutputFile, String usageOutputFileName) throws IOException {
        // Can adjust method to use Java or grep search implementation
        Set<String> findings = searchForToken(className, pathToSource);
        writeUsageToOutputFile(findings, pathToUsageOutputFile, usageOutputFileName);
    }

    private Set<String> searchForToken(String token, String pathToSource) throws IOException {
        File source = new File(pathToSource);
        String regex = String.format(".*%s.*", token);
        Pattern pattern = Pattern.compile(regex);
        return searchForToken(pattern, source);
    }

    private Set<String> searchForToken(Pattern pattern, File file) throws IOException {
        Set<String> filesContainingToken = new HashSet<>();
        if (file.isDirectory() && file.listFiles() != null) {
            for (File child : file.listFiles()) {
                filesContainingToken.addAll(searchForToken(pattern, child));
            }
        } else {
            for (String line : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                if (pattern.matcher(line).matches()) {
                    filesContainingToken.add(file.getCanonicalPath());
                }
            }
        }
        return filesContainingToken;
    }

    private void writeUsageToOutputFile(Set<String> usage, String pathToUsageOutputFile, String usageOutputFileName) throws IOException {
        File outputDirectory = DirectoryFinder.getDirectoryFromPath(pathToUsageOutputFile, MISSING_USAGE_OUTPUT_FILE_PATH);
        File outputFile = new File(outputDirectory, usageOutputFileName);
        FileWriter writer = new FileWriter(outputFile);
        for (String line : usage) {
            writer.write(line);
            writer.write("\n");
        }
        writer.close();
    }
}
