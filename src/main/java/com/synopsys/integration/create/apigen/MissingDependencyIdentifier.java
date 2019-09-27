/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.TypeTranslator;

public class MissingDependencyIdentifier {

    static Set<String> missingClasses = new HashSet<>();
    static Set<String> bdCommonDependencies = new HashSet<>();
    static TypeTranslator TYPE_TRANSLATOR;
    static ClassCategories CLASS_CATEGORIES;

    public MissingDependencyIdentifier(final TypeTranslator typeTranslator, final ClassCategories classCategories) {
        this.TYPE_TRANSLATOR = typeTranslator;
        this.CLASS_CATEGORIES = classCategories;
    }

    public static void main(final String[] args) throws IOException {
        // Go over bd-common import statements, parse for Class dependencies
        //      if not in classTranslations, add to HashSet (bdCommonDependency)
        final String pathToBlackDuckCommon = "/Users/crowley/Documents/source/blackduck-common/src/main/java/com/synopsys/integration/blackduck/";
        final String pathToAPI = "/Users/crowley/Documents/source/blackduck-common-api/src/main/java/com/synopsys/integration/blackduck/";
        final String pathToThrowaway = pathToAPI + "manual/throwaway/generated/";
        //getDependencies(pathToBlackDuckCommon, true);
        for (final String bdCommonDependency : bdCommonDependencies) {
            final String pathToFile;
            if (!CLASS_CATEGORIES.isGenerated(bdCommonDependency) && !missingClasses.contains(bdCommonDependency) && TYPE_TRANSLATOR.getApiGenClassName(bdCommonDependency) == null && !CLASS_CATEGORIES.isManual(bdCommonDependency)) {
                if (CLASS_CATEGORIES.isView(bdCommonDependency)) {
                    pathToFile = pathToThrowaway + "view/";
                } else if (CLASS_CATEGORIES.isResponse(bdCommonDependency)) {
                    pathToFile = pathToThrowaway + "response/";
                } else if (CLASS_CATEGORIES.isComponent(bdCommonDependency)) {
                    pathToFile = pathToThrowaway + "component/";
                } else {
                    pathToFile = pathToThrowaway + "enumeration/";
                }

                final File dependencyFile = new File(pathToFile);
                //parseImports(dependencyFile, false);
            }
        }

        final File missingStuff = new File("/Users/crowley/Documents/source/blackduck-common-apigen/src/main/java/com/synopsys/integration/create/apigen/missing_stuff1.txt");
        final BufferedWriter writer = new BufferedWriter(new FileWriter(missingStuff));

        missingClasses.remove("*");
        final List<String> missingClassesSorted = new ArrayList<>(missingClasses);
        Collections.sort(missingClassesSorted);
        writer.write("********************************************\nThere are " + missingClassesSorted.size() + " missing classes:\n********************************************\n");
        final List<String> requests = new ArrayList<>();
        for (final String missingClass : missingClassesSorted) {
            if (!missingClass.contains("Request")) {
                writer.write((missingClass + "\n"));
                System.out.println(missingClass);
            } else {
                requests.add(missingClass);
            }
        }
        writer.write("\n****** " + requests.size() + " of the missing classes were request objects (generator only parses response specs at the moment) *********");
        writer.close();
    }

    private static void getDependencies(final String root, final boolean forBDCommon) throws IOException {
        final File bdCommon = new File(root);
        final List<File> files = new ArrayList<>();
        files.add(bdCommon);
        while (!files.isEmpty()) {
            final File currentFile = files.get(files.size() - 1);
            files.remove(currentFile);
            final File[] children = currentFile.listFiles();
            if (children != null) {
                for (final File childFile : children) {
                    files.add(childFile);
                }
            }
            if (!currentFile.isDirectory()) {
                parseImports(currentFile, forBDCommon);
            }
        }
    }

    private static void parseImports(final File file, final boolean forBDCommon) throws IOException {
        BufferedReader reader = null;
        try {
            if (!file.exists()) {
                return;
            }
            reader = new BufferedReader(new FileReader(file));

        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        while ((line = reader.readLine()) != null) {
            final String[] linePieces = line.split(" ");
            if (linePieces[0].equals("import")) {
                final String importedFilePath = linePieces[1];
                if (importedFilePath.contains("api")) {
                    final String[] pathPieces = importedFilePath.split("\\.");
                    final String importedFile = pathPieces[pathPieces.length - 1].replace(";", "");
                    if (!CLASS_CATEGORIES.isGenerated(importedFile) && !missingClasses.contains(importedFile) && TYPE_TRANSLATOR.getApiGenClassName(importedFile) == null && !CLASS_CATEGORIES.isManual(importedFile)) {
                        missingClasses.add(importedFile);
                        if (forBDCommon) {
                            bdCommonDependencies.add(importedFile);
                        }
                    }
                }
            } else {
                continue;
            }
        }
    }
}
