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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.synopsys.integrations.apigen.maintenance.parser.ClassDirectoryToJavaClassesConverter;

public class ApiDiffFinder {

    protected static final String API_DIFF_PATH = "API_DIFF_PATH";
    private Logger logger;
    private EquivalentClassIdentifier equivalentClassIdentifier;

    public ApiDiffFinder(final Logger logger) {
        this.logger = logger;
        this.equivalentClassIdentifier = new EquivalentClassIdentifier();
    }

    public void findDiffInApi(File testApiDirectory, File controlApiDirectory) throws IOException {
        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> controlApi = converter.convertBdCommonApiGeneratedToJavaClassObjects(controlApiDirectory);
        List<JavaClass> newlyGeneratedApi = converter.convertBdCommonApiGeneratedToJavaClassObjects(testApiDirectory);

        Set<String> missingClasses = checkForMissingClasses(controlApi, newlyGeneratedApi);
        Set<String> newClasses = checkForMissingClasses(newlyGeneratedApi, controlApi);
        writeDiffToFile(missingClasses, newClasses);
    }

    public Set<String> checkForMissingClasses(List<JavaClass> classes1, List<JavaClass> classes2) {
        Set<String> missingClasses = new HashSet<>();
        for (JavaClass clazz : classes1) {
            if (!equivalentClassIdentifier.hasAnEquivalent(clazz, classes2)) {
                missingClasses.add(clazz.getClassName());
            }
        }
        return missingClasses;
    }

    private void writeDiffToFile(Set<String> missingClasses, Set<String> newClasses) throws IOException {
        String apiDiffOutputPath = System.getenv(API_DIFF_PATH);
        if (apiDiffOutputPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where information on missing classes should be written.", API_DIFF_PATH));
            return;
        }

        File outputDirectory = new File(apiDiffOutputPath);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory, "api-diff.txt");
        FileWriter writer = new FileWriter(outputFile);

        writer.write(String.format("************* CLASSES MISSING FROM GENERATED API: %d *************\n\n", missingClasses.size()));
        for (String missingClass : missingClasses) {
            writer.write(String.format("%s\n", missingClass));
            writer.write("\n");
        }

        writer.write(String.format("************* NEW CLASSES IN GENERATED API: %d *************\n\n", newClasses.size()));
        for (String newClass : newClasses) {
            writer.write(String.format("%s\n", newClass));
            writer.write("\n");
        }
        writer.close();
    }

}
