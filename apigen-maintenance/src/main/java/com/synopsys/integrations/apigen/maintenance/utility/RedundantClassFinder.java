/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integrations.apigen.maintenance.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.synopsys.integrations.apigen.maintenance.utility.ClassDirectoryToJavaClassesConverter;
import com.synopsys.integrations.apigen.maintenance.utility.EquivalentClassIdentifier;

public class RedundantClassFinder {
    public void identifyGeneratedTemporaryClasses(File apiDirectory) throws IOException {
        File apiBuildDirectory = new File(apiDirectory, "build/classes/java/main/com/synopsys/integration/blackduck/api");
        File generatedDirectory = new File(apiBuildDirectory, "generated");
        File temporaryDirectory = new File(apiBuildDirectory, "manual/temporary");
        List<File> excludedDirectories = Arrays.asList(
            new File(generatedDirectory, "discovery"),
            new File(generatedDirectory,"deprecated")
        );
        Pattern namesToIgnore = Pattern.compile(".*Request");
        identifyRedundantClasses(generatedDirectory, temporaryDirectory, excludedDirectories, namesToIgnore);
    }

    public Map<String, Set<String>> identifyRedundantClasses(File directory1, File directory2, List<File> excludedDirectories, Pattern namesToIgnore) throws IOException {
        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> generatedClasses = converter.convertClassDirectoryToJavaClassObjects(directory1, excludedDirectories);
        List<JavaClass> temporaryClasses = converter.convertClassDirectoryToJavaClassObjects(directory2, excludedDirectories);

        EquivalentClassIdentifier equivalentClassIdentifier = new EquivalentClassIdentifier();
        return equivalentClassIdentifier.checkForPotentiallyEquivalentClasses(generatedClasses, temporaryClasses, namesToIgnore);
    }

    public void writePotentialEquivalentsToFile(Map<String, Set<String>> equivalencies, String outputPath) throws IOException {
        File outputDirectory = new File(outputPath);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory, "potential-equivalents.txt");
        FileWriter writer = new FileWriter(outputFile);

        writer.write("************* POTENTIAL GENERATED EQUIVALENTS FOR TEMPORARY CLASSES *************\n\n");
        writer.write("Note: temporary classes will not be compared against generated classes that have links\n\n");
        for (Map.Entry<String, Set<String>> equivalency : equivalencies.entrySet()) {
            writer.write(String.format("%s\n", equivalency.getKey()));
            for (String equivalent : equivalency.getValue()) {
                writer.write(String.format("\t%s\n", equivalent));
            }
            writer.write("\n");
        }
        writer.close();
    }

}