package com.synopsys.integrations.apigen.maintenance;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.synopsys.integrations.apigen.maintenance.utility.ApiDiffFinder;
import com.synopsys.integrations.apigen.maintenance.utility.ClassDirectoryToJavaClassesConverter;

public class ApiDiffFinderTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     *  We have two resource directories that contain class files, we expect two classes to be found to be missing
     *   test is missing ComponentPolicyStatusViewV4
     *   ComponentMatchedFilesViewV4 in test is corrupted (wrong type for filePath field)
     *
     */
    @Test
    public void testFindMissingClassesInDirectory() throws URISyntaxException, IOException {
        File testDirectory = new File(this.getClass().getClassLoader().getResource("classFiles/test").toURI());
        File controlDirectory = new File(this.getClass().getClassLoader().getResource("classFiles/control").toURI());

        ClassDirectoryToJavaClassesConverter converter = new ClassDirectoryToJavaClassesConverter();
        List<JavaClass> controlClasses = converter.convertClassDirectoryToJavaClassObjects(controlDirectory, new ArrayList<>());
        List<JavaClass> testClasses = converter.convertClassDirectoryToJavaClassObjects(testDirectory, new ArrayList<>());

        ApiDiffFinder apiDiffFinder = new ApiDiffFinder(logger);
        Set<String> missingFromTest = apiDiffFinder.checkForMissingClasses(controlClasses, testClasses);

        Assertions.assertEquals(2, missingFromTest.size());
        String classNamePrefix = "com.synopsys.integration.blackduck.api.generated.view.";
        Assertions.assertTrue(missingFromTest.contains(classNamePrefix + "ComponentPolicyStatusViewV4"));
        Assertions.assertTrue(missingFromTest.contains(classNamePrefix + "ComponentMatchedFilesViewV4"));
    }
}
