package com.synopsys.integrations.apigen.maintenance;

import com.synopsys.integrations.apigen.maintenance.model.ClassCharacteristics;
import com.synopsys.integrations.apigen.maintenance.model.ClassCharacteristicsBuilder;

/***
 * This class can be used to search blackduck-common-api for classes that have certain characteristics via ClassFinder.
 * To Use: Provide the path to a built (important, since ClassFinder searches through .class files) blackduck-common-api directory, and
 *  specify some class characteristics via ClassCharacteristicsBuilder (eg. .addField("name", "String"), .addField("name"), .addNamePiece("Policy"))
 */
public class ClassFinderRunner {
    private static final String API_PATH = "";
    private ClassCharacteristics classCharacteristics = buildClassCharacteristics();

    /***
     * Complete this method to specify characteristics you which to search for.
     */
    private ClassCharacteristics buildClassCharacteristics() {
        ClassCharacteristicsBuilder classCharacteristicsBuilder = new ClassCharacteristicsBuilder();
        // characteristics go here
        return classCharacteristicsBuilder.build();
    }
}
