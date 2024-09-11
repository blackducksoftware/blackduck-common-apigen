/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class ClassDirectoryToJavaClassesConverter {
    public List<JavaClass> convertBdCommonApiGeneratedToJavaClassObjects(File apiDirectory) throws IOException {
        File apiBuildDirectory = new File(apiDirectory, "build/classes/java/main/com/synopsys/integration/blackduck/api");
        File generatedDirectory = new File(apiBuildDirectory, "generated");
        List<File> excludedDirectories = Arrays.asList(
            new File(generatedDirectory, "discovery")
        );
        return convertClassDirectoryToJavaClassObjects(generatedDirectory, excludedDirectories);
    }

    public List<JavaClass> convertClassDirectoryToJavaClassObjects(File classFileDirectory, List<File> excludedDirectories) throws IOException {
        List<JavaClass> javaClasses = new ArrayList<>();
        if (classFileDirectory.listFiles() == null) {
            return new ArrayList<>();
        }
        for (File file : classFileDirectory.listFiles()) {
            if (file.isDirectory() && !excludedDirectories.contains(file)) {
                javaClasses.addAll(convertClassDirectoryToJavaClassObjects(file, excludedDirectories));
            } else if (file.getName().endsWith(".class")) {
                // TODO - this utility gets confused by classes with links (it recognizes the links as fields, and then not any of the class's other fields-which we are more interested in), so should explore other options
                ClassParser classParser = new ClassParser(file.getAbsolutePath());
                javaClasses.add(classParser.parse());
            }
        }
        return javaClasses;
    }

}
