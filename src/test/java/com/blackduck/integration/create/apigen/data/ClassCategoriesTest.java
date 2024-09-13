package com.blackduck.integration.create.apigen.data;

import com.blackduck.integration.create.apigen.generation.finder.ClassNameManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassCategoriesTest {

    private final ClassCategories classCategories = new ClassCategories(new ClassNameManager());

    @Test
    public void computeTypeTest() {
        assertTrue(classCategories.computeData("ProjectVersionView").getType().isView());
        assertTrue(classCategories.computeData("PolicyStatusType").getType().isEnum());
        assertFalse(classCategories.computeData("TypesView").getType().isEnum());
        assertTrue(classCategories.computeData("CustomFieldTypeView").getType().isResponse());
        assertTrue(classCategories.computeData("ProjectVersionLicenseView").getType().isView());
        assertTrue(classCategories.computeData("BigDecimal").getType().isCommon());
    }

    @Test
    public void computeSourceTest() {
        assertTrue(classCategories.computeData("ProjectVersionView").getSource().isGenerated());
        assertTrue(classCategories.computeData("ProjectVersionViewV4").getSource().isGenerated());
        assertFalse(classCategories.computeData("String").getSource().isTemporary());
        assertTrue(classCategories.computeData("VulnerableComponentView").getSource().isDeprecated());
        assertTrue(classCategories.computeData("BlackDuckResponse").getSource().isManual());
    }
}
