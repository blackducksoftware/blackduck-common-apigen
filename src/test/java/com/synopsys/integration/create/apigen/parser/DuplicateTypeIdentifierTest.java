package com.synopsys.integration.create.apigen.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.DuplicateOverrides;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class DuplicateTypeIdentifierTest {

    @Test
    public void testIdentifySimpleDuplicates() {
        Set<RawFieldDefinition> set1 = new HashSet<>();
        set1.add(new RawFieldDefinition("email", "String", true));
        set1.add(new RawFieldDefinition("userName", "String", true));
        set1.add(new RawFieldDefinition("firstName", "String", true));
        set1.add(new RawFieldDefinition("lastName", "String", true));
        set1.add(new RawFieldDefinition("active", "Boolean", true));
        RawFieldDefinition object1 = new RawFieldDefinition("user1", "Object1", true);
        object1.addSubFields(set1);

        Set<RawFieldDefinition> set2 = new HashSet<>();
        set2.add(new RawFieldDefinition("email", "String", false));
        set2.add(new RawFieldDefinition("firstName", "String", false));
        set2.add(new RawFieldDefinition("userName", "String", false));
        set2.add(new RawFieldDefinition("active", "Boolean", false));
        set2.add(new RawFieldDefinition("lastName", "String", false));
        RawFieldDefinition object2 = new RawFieldDefinition("user2", "Object2", false);
        object2.addSubFields(set2);

        DuplicateTypeIdentifier duplicateTypeIdentifier = new DuplicateTypeIdentifier(new DuplicateOverrides());
        String object1TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(object1, object1.getType());
        String object2TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(object2, object2.getType());

        Assertions.assertEquals("Object1", object1TypeAfterScreening);
        Assertions.assertEquals("Object1", object2TypeAfterScreening);
    }

    @Test
    public void testIdentifyComplexDuplicates() {
        RawFieldDefinition subField1 = new RawFieldDefinition("pathA", "typeA", true);
        RawFieldDefinition subSubField1 = new RawFieldDefinition("pathB", "typeB", true, new HashSet<>(Arrays.asList("val1", "val2")));
        subField1.addSubField(subSubField1);
        RawFieldDefinition complexField1 = new RawFieldDefinition("path", "type", true);
        complexField1.addSubField(subField1);

        RawFieldDefinition subField2 = new RawFieldDefinition("pathA", "typeA", false);
        RawFieldDefinition subSubField2 = new RawFieldDefinition("pathB", "typeB", false, new HashSet<>(Arrays.asList("val1", "val2")));
        subField2.addSubField(subSubField2);
        RawFieldDefinition complexField2 = new RawFieldDefinition("pathComplex", "typeComplex", false);
        complexField2.addSubField(subField2);

        DuplicateTypeIdentifier duplicateTypeIdentifier = new DuplicateTypeIdentifier(new DuplicateOverrides());
        String complexObject1TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(complexField1, complexField1.getType());
        String complexObject2TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(complexField2, complexField2.getType());

        Assertions.assertEquals("type", complexObject1TypeAfterScreening);
        Assertions.assertEquals("type", complexObject2TypeAfterScreening);
    }

    @Test
    public void testIdentifyDuplicateEnums() {
        RawFieldDefinition enum1 = new RawFieldDefinition("enum1", "Enum1", false, new HashSet<>(Arrays.asList("val1", "val2", "val3")));
        RawFieldDefinition enum2 = new RawFieldDefinition("enum2", "Enum2", false, new HashSet<>(Arrays.asList("val3", "val2", "val1")));

        DuplicateTypeIdentifier duplicateTypeIdentifier = new DuplicateTypeIdentifier(new DuplicateOverrides());
        String enum1TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(enum1, enum1.getType());
        String enum2TypeAfterScreening = duplicateTypeIdentifier.screenForDuplicateType(enum2, enum2.getType());

        Assertions.assertEquals("Enum1", enum1TypeAfterScreening);
        Assertions.assertEquals("Enum1", enum2TypeAfterScreening);
    }
}
