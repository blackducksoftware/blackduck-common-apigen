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
package com.synopsys.integrations.apigen.maintenance.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class ClassDirectoryToJavaClassesConverter {

    public List<JavaClass> convertBdCommonApiGeneratedToJavaClassObjects(File apiDirectory) throws IOException {
        File apiBuildDirectory = new File(apiDirectory, "build/classes/java/main/com/synopsys/integration/blackduck/api");
        File generatedDirectory = new File(apiBuildDirectory, "generated");
        File[] excludedDirectories = {
            new File(generatedDirectory, "discovery")
        };
        return convertClassDirectoryToJavaClassObjects(generatedDirectory, excludedDirectories);
    }

    public List<JavaClass> convertClassDirectoryToJavaClassObjects(File classFileDirectory, File[] excludedDirectories) throws IOException {
        List<JavaClass> javaClasses = new ArrayList<>();
        for (File file : classFileDirectory.listFiles()) {
            if (file.isDirectory()) {
                if (!isExcludedDirectory(file, excludedDirectories)) {
                    javaClasses.addAll(convertClassDirectoryToJavaClassObjects(file, excludedDirectories));
                }
            } else if (file.getName().endsWith(".class")){
                // TODO - this parser gets confused by classes with links (it recognizes the links as fields, and then not any of the class's other fields-which we are more interested in), so should explore other options
                ClassParser classParser = new ClassParser(file.getAbsolutePath());
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
}
