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