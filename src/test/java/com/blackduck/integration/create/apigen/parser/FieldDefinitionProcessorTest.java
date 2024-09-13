package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.data.MissingFieldsAndLinks;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FieldDefinitionProcessorTest {

    private FieldDefinitionProcessor processor = new FieldDefinitionProcessor(new FieldDataProcessor(new TypeTranslator(), new DuplicateTypeIdentifier(new DuplicateOverrides())), new MissingFieldsAndLinks());

    @Test
    public void testProcessesNestedFieldDefinitions() {
        RawFieldDefinition license = new RawFieldDefinition("license", "Object", false);
        RawFieldDefinition licenseLicenses = new RawFieldDefinition("licenses", "Array", false);
        licenseLicenses.addSubField(new RawFieldDefinition("name", "String", false));
        license.addSubField(licenseLicenses);

        Set<FieldDefinition> fields = processor.processFieldDefinitions("ComponentVersionView", new HashSet<>(Arrays.asList(license)));
        try {
            FieldDefinition licenseView = fields.iterator().next();
            assertEquals("ComponentVersionLicenseView", licenseView.getType());

            FieldDefinition licenseLicensesView = licenseView.getSubFields().iterator().next();
            assertEquals("java.util.List<ComponentVersionLicenseLicensesView>", licenseLicensesView.getType());
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testDoesNotProcessDefinitionsWithOverridedTypes() {
        RawFieldDefinition license = new RawFieldDefinition("expression", "Object", false);
        license.addSubField(new RawFieldDefinition("path", "type", false));
        try {
            FieldDefinition licenseView = processor.processFieldDefinitions("ComponentPolicyRulesItemsView", new HashSet<>(Arrays.asList(license))).iterator().next();
            // Depends on an override from TypeTranslator
            assertEquals("PolicyRuleExpressionView", licenseView.getType());
            // Since this definition's type was overrided, we should not have processed its subfield
            assertTrue(licenseView.getSubFields().isEmpty());
        } catch (NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testSkipDataAndMetaFields() {
        Set<RawFieldDefinition> rawFieldDefinitions = new HashSet<>(Arrays.asList(
            new RawFieldDefinition("data", "type", false),
            new RawFieldDefinition("_meta", "type", false),
            new RawFieldDefinition("path", "type", false)
        ));

        assertEquals(1, processor.processFieldDefinitions("", rawFieldDefinitions).size());
    }
}
