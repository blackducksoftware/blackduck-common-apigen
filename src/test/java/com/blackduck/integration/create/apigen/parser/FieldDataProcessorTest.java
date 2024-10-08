package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldDataProcessorTest {

    @Test
    void testCorrectProcessedType() {
        RawFieldDefinition object1 = new RawFieldDefinition("createdAt", "type", false);
        assertHasExpectedType(object1, "", "java.util.Date");

        RawFieldDefinition object2 = new RawFieldDefinition("path", "Number", false);
        assertHasExpectedType(object2, "", "BigDecimal");

        RawFieldDefinition object3 = new RawFieldDefinition("tier", "Enum", false, new HashSet<>(Arrays.asList("1", "2", "3")));
        assertHasExpectedType(object3, "", "Integer");

        RawFieldDefinition object4 = new RawFieldDefinition("license", "String", false);
        object4.addSubField(new RawFieldDefinition("", "", false));
        assertHasExpectedType(object4, "ProjectView", "ProjectLicenseView");

        RawFieldDefinition object5 = new RawFieldDefinition("license", "String", false, new HashSet<>(Arrays.asList("val1", "val2")));
        assertHasExpectedType(object5, "ProjectView", "ProjectLicenseType");

        RawFieldDefinition object6 = new RawFieldDefinition("counts", "Array", false);
        object6.addSubField(new RawFieldDefinition("", "", false));
        assertHasExpectedType(object6, "ProjectView", "java.util.List<ProjectCountsView>");

        RawFieldDefinition object7 = new RawFieldDefinition("counts[]", "Array", false);
        object7.addSubField(new RawFieldDefinition("", "", false));
        assertHasExpectedType(object7, "ProjectView", "java.util.List<ProjectCountsView>");

        // 2 dimensional array test
        RawFieldDefinition object8 = new RawFieldDefinition("dependencyTrees[][]", "Array", false);
        object8.addSubField(new RawFieldDefinition("", "", false));
        assertHasExpectedType(object8, "ProjectView", "java.util.List<java.util.List<ProjectDependencyTreesView>>");

    }

    @Test
    void testCorrectProcessedPath() {
        RawFieldDefinition object1 = new RawFieldDefinition("default", "type", false);
        assertHasExpectedPath(object1, "", "default_");

        RawFieldDefinition object2 = new RawFieldDefinition("counts[]", "type", false);
        assertHasExpectedPath(object2, "", "counts");
    }

    private void assertHasExpectedType(RawFieldDefinition object, String parentName, String expectedType) {
        FieldDataProcessor fieldDataProcessor = new FieldDataProcessor(new TypeTranslator(), new DuplicateTypeIdentifier(new DuplicateOverrides()));
        assertTrue(fieldDataProcessor.process(object, parentName).getType().equals(expectedType));
    }

    private void assertHasExpectedPath(RawFieldDefinition object, String parentName, String expectedPath) {
        FieldDataProcessor fieldDataProcessor = new FieldDataProcessor(new TypeTranslator(), new DuplicateTypeIdentifier(new DuplicateOverrides()));
        assertTrue(fieldDataProcessor.process(object, parentName).getPath().equals(expectedPath));
    }
}
