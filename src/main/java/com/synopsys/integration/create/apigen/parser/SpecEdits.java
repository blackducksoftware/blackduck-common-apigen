package com.synopsys.integration.create.apigen.parser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class SpecEdits {

    private static final String PATH_PREFIX = "endpoints/api/";
    private static final String EDITED = "[EDITED]";
    private static final String PATH = "path";
    private static final String OPTIONAL = "optional";
    private static final String DESCRIPTION = "description";
    private static final String FALSE = "false";
    private static final String TRUE = "true";

    // pathToSpecFile, infoOnHowToEditSpecFile
    private final Map<String, List<SpecEditData>> edits = populateEdits();

    private Map<String, List<SpecEditData>> populateEdits() {
        Map<String, List<SpecEditData>> edits = new HashMap<>();

        // ComponentVersionView
        List<SpecEditData> cvvEditData = new ArrayList<>();
        SpecEditData cvvLicenseLicensesEditData = createCvvLicenseLicensesEditData();
        cvvEditData.add(cvvLicenseLicensesEditData);
        SpecEditData cvvLicenseLicenseEditData = createCvvLicenseLicenseEditData();
        cvvEditData.add(cvvLicenseLicenseEditData);
        edits.put(PATH_PREFIX + "components/componentId/versions/componentVersionId/GET", cvvEditData);

        return edits;
    }

    private SpecEditData createCvvLicenseLicensesEditData() {
        List<String> pathToLicenseLicenses = new ArrayList<>();
        pathToLicenseLicenses.add("license");
        pathToLicenseLicenses.add("licenses");

        Map<String, String> licenseLicensesAttributeEdits = new HashMap<>();
        licenseLicensesAttributeEdits.put(DESCRIPTION, EDITED);
        licenseLicensesAttributeEdits.put(OPTIONAL, TRUE);

        List<FieldDefinition> fieldsToAdd = new ArrayList<>();
        fieldsToAdd.add(new FieldDefinition("license", UtilStrings.STRING, true));
        fieldsToAdd.add(new FieldDefinition("name", UtilStrings.STRING, true));
        fieldsToAdd.add(new FieldDefinition("licenses", UtilStrings.ARRAY, true));
        FieldDefinition licenseLicensesLicenseFamilySummary = new FieldDefinition("licenseFamilySummary", UtilStrings.OBJECT, true);
        Set<FieldDefinition> licenseLicensesLicenseFamilySummarySubFields = new HashSet<>();
        licenseLicensesLicenseFamilySummarySubFields.add(new FieldDefinition("name", UtilStrings.STRING, true));
        licenseLicensesLicenseFamilySummarySubFields.add(new FieldDefinition("href", UtilStrings.STRING, true));
        licenseLicensesLicenseFamilySummary.addSubFields(licenseLicensesLicenseFamilySummarySubFields);
        fieldsToAdd.add(licenseLicensesLicenseFamilySummary);

        return new SpecEditData(pathToLicenseLicenses, licenseLicensesAttributeEdits, fieldsToAdd, new ArrayList<>());
    }

    private SpecEditData createCvvLicenseLicenseEditData() {
        List<String> pathToLicenseLicense = new ArrayList<>();
        pathToLicenseLicense.add("license");
        pathToLicenseLicense.add("license");

        Map<String, String> licenseLicenseAttributeEdits = new HashMap<>();
        licenseLicenseAttributeEdits.put(PATH, "licenseDisplay");
        licenseLicenseAttributeEdits.put(OPTIONAL, TRUE);
        licenseLicenseAttributeEdits.put(DESCRIPTION, EDITED);

        return new SpecEditData(pathToLicenseLicense, licenseLicenseAttributeEdits, new ArrayList<>(), new ArrayList<>());
    }
}
