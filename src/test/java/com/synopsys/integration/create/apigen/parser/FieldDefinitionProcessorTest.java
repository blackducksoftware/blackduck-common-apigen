package com.synopsys.integration.create.apigen.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class FieldDefinitionProcessorTest {

    private FieldDefinitionProcessor processor = new FieldDefinitionProcessor(new FieldDataProcessor(new TypeTranslator(), new DuplicateTypeIdentifier()), new MissingFieldsAndLinks());

    @Test
    public void testProcessesNestedFieldDefinitions() {
        RawFieldDefinition license = new RawFieldDefinition("license", "Object", false);
        RawFieldDefinition licenseLicenses = new RawFieldDefinition("licenses", "Array", false);
        licenseLicenses.addSubField(new RawFieldDefinition("license", "String", false));
        license.addSubField(licenseLicenses);

        Set<FieldDefinition> fields = processor.processFieldDefinitions("ComponentVersionViewV4", new HashSet<>(Arrays.asList(license)));
        try {
            FieldDefinition licenseView = fields.iterator().next();
            Assertions.assertEquals("ComponentVersionLicenseViewV4", licenseView.getType());

            FieldDefinition licenseLicensesView = licenseView.getSubFields().iterator().next();
            Assertions.assertEquals("java.util.List<ComponentVersionLicenseLicensesViewV4>", licenseLicensesView.getType());

            FieldDefinition licenseLicensesLicenseView = licenseLicensesView.getSubFields().iterator().next();
            Assertions.assertEquals("ComponentVersionLicenseLicensesLicenseViewV4", licenseLicensesLicenseView.getType());
        } catch (NullPointerException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testDoesNotProcessDefinitionsWithOverridedTypes() {
        RawFieldDefinition license = new RawFieldDefinition("license", "Object", false);
        license.addSubField(new RawFieldDefinition("path", "type", false));
        try {
            FieldDefinition licenseView = processor.processFieldDefinitions("ProjectVersionViewV4", new HashSet<>(Arrays.asList(license))).iterator().next();
            // Depends on an override from TypeTranslator
            Assertions.assertEquals("ComponentVersionLicenseViewV4", licenseView.getType());
            // Since this definition's type was overrided, we should not have processed its subfield
            Assertions.assertTrue(licenseView.getSubFields().isEmpty());
        } catch (NullPointerException e) {
            Assertions.fail();
        }
    }

    @Test
    public void testSkipDataAndMetaFields() {
        Set<RawFieldDefinition> rawFieldDefinitions = new HashSet<>(Arrays.asList(
            new RawFieldDefinition("data", "type", false),
            new RawFieldDefinition("_meta", "type", false),
            new RawFieldDefinition("path", "type", false)
        ));

        Assertions.assertEquals(1, processor.processFieldDefinitions("", rawFieldDefinitions).size());
    }
}
