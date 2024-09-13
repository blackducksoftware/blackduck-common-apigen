/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.utility;

import com.blackduck.integrations.apigen.maintenance.model.ApiDiff;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApiDiffFinder {
    private Logger logger;
    private EquivalentClassIdentifier equivalentClassIdentifier;

    public ApiDiffFinder(final Logger logger) {
        this.logger = logger;
        this.equivalentClassIdentifier = new EquivalentClassIdentifier();
    }

    public ApiDiff findDiffInApi(File testApiDirectory, File controlApiDirectory) throws IOException {
        if (directoryHasBuildFolder(testApiDirectory) && directoryHasBuildFolder(controlApiDirectory)) {
            // If the two directories have build folders where .class files are located, parse .class files to compare class names and fields.
            return findDiffInApiSmart(testApiDirectory, controlApiDirectory);
        } else {
            // If one of the directories has not been built and does not contain a build folder, then simply compare the class names each directory contains.
            logger.info("At least one of the target directories is missing a build folder.  ApiDiffFinder will compare the class names of the two directories.");
            return findDiffInApiSimple(testApiDirectory, controlApiDirectory);
        }
    }

    private boolean directoryHasBuildFolder(File directory) {
        return new File(directory, "build").exists();
    }

    public ApiDiff findDiffInApiSmart(File testApiDirectory, File controlApiDirectory) throws IOException {
        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> controlApi = converter.convertBdCommonApiGeneratedToJavaClassObjects(controlApiDirectory);
        List<JavaClass> newlyGeneratedApi = converter.convertBdCommonApiGeneratedToJavaClassObjects(testApiDirectory);

        Set<String> missingClasses = checkForMissingClasses(controlApi, newlyGeneratedApi);
        Set<String> newClasses = checkForMissingClasses(newlyGeneratedApi, controlApi);
        return new ApiDiff(missingClasses, newClasses);
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

    public ApiDiff findDiffInApiSimple(File testApiDirectory, File controlApiDirectory) {
        Set<String> testClassNames =  collectJavaClassNamesFromDirectory(testApiDirectory);
        Set<String> controlClassNames = collectJavaClassNamesFromDirectory(controlApiDirectory);

        Set<String> missingClassNames = checkForMissingClasses(controlClassNames, testClassNames);
        Set<String> newClassNames = checkForMissingClasses(testClassNames, controlClassNames);

        return new ApiDiff(missingClassNames, newClassNames);
    }

    private Set<String> collectJavaClassNamesFromDirectory(File directory) {
        Set<String> classNames = new HashSet<>();
        if (directory.listFiles() == null) {
            return new HashSet<>();
        }
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                    classNames.addAll(collectJavaClassNamesFromDirectory(file));
            } else if (file.getName().endsWith(".java")){
                classNames.add(file.getName().replace(".java", ""));
            }
        }
        return classNames;
    }


    public Set<String> checkForMissingClasses(Set<String> classes1, Set<String> classes2) {
        Set<String> missingClasses = new HashSet<>();
        for (String clazz : classes1) {
            if (!classes2.contains(clazz)) {
                missingClasses.add(clazz);
            }
        }
        return missingClasses;
    }

    public void writeDiffToFile(ApiDiff apiDiff, String outputPath) throws IOException {
        File outputDirectory = new File(outputPath);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory, "api-diff.txt");
        FileWriter writer = new FileWriter(outputFile);
        writeDiffToFile(apiDiff, writer);
        writer.close();
    }

    public void writeDiffToFile(ApiDiff apiDiff, FileWriter writer) throws IOException {
        writer.write(String.format("\t\tCLASSES MISSING FROM GENERATED API: %d\n\n", apiDiff.getMissingClasses().size()));
        for (String missingClass : apiDiff.getMissingClasses()) {
            writer.write(String.format("%s\n", missingClass));
            writer.write("\n");
        }

        writer.write(String.format("\t\tNEW CLASSES IN GENERATED API: %d\n\n", apiDiff.getNewClasses().size()));
        for (String newClass : apiDiff.getNewClasses()) {
            writer.write(String.format("%s\n", newClass));
            writer.write("\n");
        }
    }

}
