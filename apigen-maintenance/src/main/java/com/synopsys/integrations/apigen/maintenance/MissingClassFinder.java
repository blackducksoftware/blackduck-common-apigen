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

public class MissingClassFinder {

    protected static final String MISSING_CLASSES_PATH = "MISSING_CLASSES_PATH";
    private Logger logger;
    private EquivalentClassIdentifier equivalentClassIdentifier;

    public MissingClassFinder(final Logger logger) {
        this.logger = logger;
        this.equivalentClassIdentifier = new EquivalentClassIdentifier();
    }

    public void findMissingClassesInOutput(File testApiDirectory, File controlApiDirectory) throws IOException {
        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> controlClasses = converter.convertBdCommonApiGeneratedToJavaClassObjects(controlApiDirectory);
        List<JavaClass> testClasses = converter.convertBdCommonApiGeneratedToJavaClassObjects(testApiDirectory);

        Set<String> missingFromTest = checkForMissingClasses(controlClasses, testClasses);
        writeMissingClassesToFile(missingFromTest);
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

    private void writeMissingClassesToFile(Set<String> missingClasses) throws IOException {
        String missingClassesOutputPath = System.getenv(MISSING_CLASSES_PATH);
        if (missingClassesOutputPath == null) {
            logger.info(String.format("You must set the environment variable %s to specify where information on missing classes should be written.", MISSING_CLASSES_PATH));
            return;
        }

        File outputDirectory = new File(missingClassesOutputPath);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory, "missing-classes.txt");
        FileWriter writer = new FileWriter(outputFile);

        writer.write(String.format("************* CLASSES MISSING FROM GENERATED API: %d *************\n\n", missingClasses.size()));
        for (String missingClass : missingClasses) {
            writer.write(String.format("%s\n", missingClass));
            writer.write("\n");
        }
        writer.close();
    }
}
