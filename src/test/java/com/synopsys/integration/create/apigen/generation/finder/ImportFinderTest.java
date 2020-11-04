package com.synopsys.integration.create.apigen.generation.finder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class ImportFinderTest {

    private final ImportFinder importFinder = new ImportFinder(new ClassCategories(new ClassNameManager()), new LinkResponseDefinitions(), new NameAndPathManager(), new TypeTranslator(), new ClassNameManager());

    @Test
    public void addFieldImportsTest() {
        Set<String> imports = new HashSet<>();

        Set<FieldDefinition> fields = new HashSet<>();
        fields.add(new FieldDefinition("", "ProjectVersionLicenseLicensesView", false, null, false));
        fields.add(new FieldDefinition("", "TagView", false, null, false));
        fields.add(new FieldDefinition("", "List<PolicyStatusType>", false, null, false));
        fields.add(new FieldDefinition("", "ComponentsView", false, null, false));

        imports.addAll(importFinder.getFieldImports(fields));

        assertFalse(imports.contains(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.COMPONENT_BASE_CLASS));
        assertTrue(imports.contains(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS));
        assertTrue(imports.contains(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.RESPONSE_BASE_CLASS));
        assertTrue(imports.contains(UtilStrings.GENERATED_CLASS_PATH_PREFIX + UtilStrings.ENUMERATION + "." + "PolicyStatusType"));
    }
}
