package com.synopsys.integrations.apigen.maintenance;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.synopsys.integrations.apigen.maintenance.model.ClassCharacteristics;
import com.synopsys.integrations.apigen.maintenance.model.ClassCharacteristicsBuilder;
import com.synopsys.integrations.apigen.maintenance.utility.ClassFinder;

/***
 * This class can be used to search blackduck-common-api for classes that have certain characteristics via ClassFinder.
 * To Use: Provide the path to a built (important, since ClassFinder searches through .class files) blackduck-common-api directory, and
 *  specify some class characteristics via ClassCharacteristicsBuilder (eg. .addField("name", "String"), .addField("name"), .addNamePiece("Policy"))
 * If you wish for found classes to be written to a text file, specify the path to which you would like the file to be written to.
 */
public class ClassFinderRunner {
    private static final String API_PATH = "";
    private static final String CLASS_FINDINGS_OUTPUT_PATH = "";

    private static ClassCharacteristics classCharacteristics = buildClassCharacteristics();

    /***
     * Complete this method to specify characteristics you which to search for.
     */
    private static ClassCharacteristics buildClassCharacteristics() {
        ClassCharacteristicsBuilder classCharacteristicsBuilder = new ClassCharacteristicsBuilder();
        // characteristics go here
        return classCharacteristicsBuilder.build();
    }

    public static void main(String[] args) throws IOException {
        ClassFinder classFinder = new ClassFinder();
        File apiDirectory = new File(API_PATH);
        Set<String> foundClasses = classFinder.findClasses(apiDirectory, classCharacteristics);
        classFinder.writeFoundClassesToFile(foundClasses, CLASS_FINDINGS_OUTPUT_PATH);
    }
}
