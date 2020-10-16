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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class GeneratedTemporaryClassIdentifier {

    private static final String GENERATED_TEMPORARY_EQUIVALENTS_PATH = "GENERATED_TEMPORARY_EQUIVALENTS_PATH";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void identifyGeneratedTemporaryClasses() throws IOException {
        File apiDirectory = new File("/Users/crowley/Documents/source/blackduck-common-api"); // TODO - where can I get this from? command line argument?  env var?  maybe add bd-common-api to integrations portfolio?
        File apiBuildDirectory = new File(apiDirectory, "build/classes/java/main/com/synopsys/integration/blackduck/api");
        File generatedDirectory = new File(apiBuildDirectory, "generated");
        File temporaryDirectory = new File(apiBuildDirectory, "manual/temporary");
        File[] excludedDirectories = {
            new File(generatedDirectory, "discovery"),
            new File(generatedDirectory,"deprecated")
        };
        List<JavaClass> generatedClasses = convertClassDirectoryToJavaClassObjects(generatedDirectory, excludedDirectories);
        List<JavaClass> temporaryClasses = convertClassDirectoryToJavaClassObjects(temporaryDirectory, excludedDirectories).stream()
                                               .filter(it -> !it.getClassName().endsWith("Request"))
                                               .collect(Collectors.toList());

        Map<String, Set<String>> potentialEquivalents = checkForEquivalentClasses(generatedClasses, temporaryClasses);
        writePotentialEquivalentsToFile(potentialEquivalents);
    }

    private List<JavaClass> convertClassDirectoryToJavaClassObjects(File classFileDirectory, File[] excludedDirectories) throws IOException {
        List<JavaClass> javaClasses = new ArrayList<>();
        for (File javaFile : classFileDirectory.listFiles()) {
            if (javaFile.isDirectory()) {
                if (!isExcludedDirectory(javaFile, excludedDirectories)) {
                    javaClasses.addAll(convertClassDirectoryToJavaClassObjects(javaFile, excludedDirectories));
                }
            } else {
                // TODO - this parser gets confused by classes with links (it recognizes the links as fields, and then not any of the class's other fields-which we are more interested in), so should explore other options
                ClassParser classParser = new ClassParser(javaFile.getAbsolutePath());
                javaClasses.add(classParser.parse());
            }
        }
        return javaClasses;
    }

    private boolean isExcludedDirectory(File directory, File[] excludedDirectories) {
        for (File excludedDirectory : excludedDirectories) {
            if (directory.equals(excludedDirectory)) {
                return true;
            }
        }
        return false;
    }

    private Map<String, Set<String>> checkForEquivalentClasses(List<JavaClass> generatedClasses, List<JavaClass> temporaryClasses) {
        Map<String, Set<String>> temporaryClassesAndGeneratedEquivalents = new HashMap<>();
        for (JavaClass temporaryClass : temporaryClasses) {
            Set<String> potentialEquivalents = findPotentialGeneratedEquivalents(temporaryClass, generatedClasses);
            if (!potentialEquivalents.isEmpty()) {
                temporaryClassesAndGeneratedEquivalents.put(temporaryClass.getClassName(), potentialEquivalents);
            }
        }
        return temporaryClassesAndGeneratedEquivalents;
    }

    private Set<String> findPotentialGeneratedEquivalents(JavaClass temporaryClass, List<JavaClass> generatedClasses) {
        Set<String> potentialEquivalents = new HashSet<>();
        for (JavaClass generatedClass : generatedClasses) {
            int generatedClassFields = generatedClass.getFields().length;
            if (generatedClassFields == 0) {
                continue;
            }
            int fieldNamesInCommon = 0;
            for (Field generatedField : generatedClass.getFields()) {
                for (Field temporaryField : temporaryClass.getFields()) {
                    if (generatedField.getName().equals(temporaryField.getName())) {
                        fieldNamesInCommon++;
                    }
                }
            }
            if (fieldNamesInCommon/generatedClassFields > .8 && fieldNamesInCommon/temporaryClass.getFields().length > .8) {
                potentialEquivalents.add(generatedClass.getClassName());
            }
        }
        return potentialEquivalents;
    }

    private void writePotentialEquivalentsToFile(Map<String, Set<String>> equivalencies) throws IOException {
        String equivalencyOutputPath = System.getenv(GENERATED_TEMPORARY_EQUIVALENTS_PATH);
        if (equivalencyOutputPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where information on class usage should be written.", GENERATED_TEMPORARY_EQUIVALENTS_PATH));
        }

        File outputDirectory = new File(equivalencyOutputPath);
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