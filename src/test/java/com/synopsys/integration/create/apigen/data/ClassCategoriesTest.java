package com.synopsys.integration.create.apigen.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClassCategoriesTest {

    private final ClassCategories classCategories = new ClassCategories();

    @Test
    public void computeTypeTest() {
        assertTrue(classCategories.computeType("ProjectVersionView").isView());
        assertTrue(classCategories.computeType("ProjectVersionViewV4").isView());
        assertTrue(classCategories.computeType("PolicyStatusType").isEnum());
        assertFalse(classCategories.computeType("TypesView").isEnum());
        assertTrue(classCategories.computeType("CustomFieldTypeView").isResponse());
        assertTrue(classCategories.computeType("ProjectVersionLicenseView").isView());
        assertTrue(classCategories.computeType("ProjectVersionLicenseViewV5").isView());
        assertTrue(classCategories.computeType("BigDecimal").isCommon());
    }

    @Test
    public void computeSourceTest() {
        assertTrue(classCategories.computeSource("ProjectVersionView").isGenerated());
        assertTrue(classCategories.computeSource("ProjectVersionViewV4").isGenerated());
        assertFalse(classCategories.computeSource("String").isThrowaway());
        assertTrue(classCategories.computeSource("VulnerableComponentView").isThrowaway());
        assertTrue(classCategories.computeSource("BlackDuckResponse").isManual());
    }
}
