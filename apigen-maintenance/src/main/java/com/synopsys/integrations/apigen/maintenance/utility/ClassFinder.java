package com.synopsys.integrations.apigen.maintenance.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.synopsys.integrations.apigen.maintenance.model.ClassCharacteristics;
import com.synopsys.integrations.apigen.maintenance.model.Field;

public class ClassFinder {
    public Set<String> findClasses(File apiDirectory, ClassCharacteristics classCharacteristics) throws IOException {
        File apiBuildDirectory = new File(apiDirectory, "build/classes/java/main/com/synopsys/integration/blackduck/api");
        File generatedDirectory = new File(apiBuildDirectory, "generated");
        List<File> excludedDirectories = Arrays.asList(
            new File(generatedDirectory, "discovery"),
            new File(generatedDirectory,"deprecated")
        );

        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> generatedClasses = converter.convertClassDirectoryToJavaClassObjects(generatedDirectory, excludedDirectories);
        return generatedClasses.stream()
            .filter(generatedClass -> classHasCharacteristics(generatedClass, classCharacteristics))
            .map(JavaClass::getClassName)
            .collect(Collectors.toSet());
    }

    private boolean classHasCharacteristics(JavaClass javaClass, ClassCharacteristics classCharacteristics) {
        // Check name pieces
        if (classCharacteristics.mustHaveAllNamePieces()) {
            for (String namePiece : classCharacteristics.getNamePieces()) {
                if (!javaClass.getClassName().contains(namePiece)) {
                    return false;
                }
            }
        } else if (classCharacteristics.getNamePieces().stream().noneMatch(namePiece -> javaClass.getClassName().contains(namePiece))) {
            return false;
        }

        // Check fields
        if (classCharacteristics.mustHaveAllFields()) {
            for (Field expectedField : classCharacteristics.getFields()) {
                if (!classHasField(javaClass, expectedField)) {
                    return false;
                }
            }
        } else if (classCharacteristics.getFields().stream().noneMatch(field -> classHasField(javaClass, field))) {
                return false;
        }

        // If not missing any necessary fields or name pieces, this class meets our criteria
        return true;
    }

    private boolean classHasField(JavaClass javaClass, Field expectedField) {
        for (com.sun.org.apache.bcel.internal.classfile.Field field : javaClass.getFields()) {
            if (field.getName().equals(expectedField.getName())) {
                if (expectedField.getType().isPresent()) {
                    if (expectedField.getType().get().equals(field.getType().toString())) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public void writeFoundClassesToFile(Set<String> foundClasses, String outputPath) throws IOException {
        File outputDirectory = new File(outputPath);
        outputDirectory.mkdirs();
        File outputFile = new File(outputDirectory, "found-classes.txt");
        FileWriter writer = new FileWriter(outputFile);

        writer.write("************* Classes that meet specified characeristics *************\n\n");
        for (String foundClass: foundClasses) {
            writer.write(String.format("%s\n", foundClass));
        }
        writer.close();
    }
}
