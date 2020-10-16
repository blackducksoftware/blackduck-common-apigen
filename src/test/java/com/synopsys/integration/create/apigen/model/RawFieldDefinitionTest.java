package com.synopsys.integration.create.apigen.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RawFieldDefinitionTest {


    @Test
    public void testIdentifyDuplicateSetsOfFields() {
        Set<RawFieldDefinition> set1 = new HashSet<>();
        RawFieldDefinition emailObject1 = new RawFieldDefinition("email", "String", false);
        set1.add(emailObject1);
        set1.add(new RawFieldDefinition("userName", "String", false));
        set1.add(new RawFieldDefinition("firstName", "String", false));
        set1.add(new RawFieldDefinition("lastName", "String", false));
        set1.add(new RawFieldDefinition("active", "Boolean", false));

        Set<RawFieldDefinition> set2 = new HashSet<>();
        RawFieldDefinition emailObject2 = new RawFieldDefinition("email", "String", true);
        set2.add(emailObject2);
        set2.add(new RawFieldDefinition("firstName", "String", false));
        set2.add(new RawFieldDefinition("userName", "String", false));
        set2.add(new RawFieldDefinition("active", "Boolean", false));
        set2.add(new RawFieldDefinition("lastName", "String", false));

        Assertions.assertEquals(set1, set2);
        Assertions.assertEquals(emailObject1, emailObject2);
        Assertions.assertTrue(set1.contains(emailObject1));

        Map<Set<RawFieldDefinition>, String> map = new HashMap<>();
        map.put(set1, "1");
        Assertions.assertEquals("1", map.get(set2));
    }

    @Test
    public void testIdentifyDuplicateComplexFields() {
        RawFieldDefinition subField = new RawFieldDefinition("path", "type", true);
        RawFieldDefinition subSubField = new RawFieldDefinition("path1", "type1", true, new HashSet<>(Arrays.asList("val", "val2")));
        subField.addSubField(subSubField);
        RawFieldDefinition complexField = new RawFieldDefinition("path2", "type2", true);
        complexField.addSubField(subField);

        RawFieldDefinition subField2 = new RawFieldDefinition("path", "type", false);
        RawFieldDefinition subSubField2 = new RawFieldDefinition("path1", "type1", false, new HashSet<>(Arrays.asList("val", "val2")));
        subField2.addSubField(subSubField2);
        RawFieldDefinition complexField2 = new RawFieldDefinition("path2", "type2", false);
        complexField2.addSubField(subField2);

        Set<RawFieldDefinition> set1 = new HashSet<>();
        set1.add(complexField);
        set1.add(complexField2);

        Set<RawFieldDefinition> set2 = new HashSet<>();
        set2.add(complexField2);
        set2.add(complexField);

        Map<Set<RawFieldDefinition>, String> map = new HashMap<>();
        map.put(set1, "1");
        Assertions.assertEquals(map.get(set2), "1");
    }

    @Test
    public void testRawFieldDefinitionEquals() {
        RawFieldDefinition a = new RawFieldDefinition("a", "a", true);
        RawFieldDefinition b = new RawFieldDefinition("b", "b", true);
        RawFieldDefinition a2 = new RawFieldDefinition("a", "a", true);
        RawFieldDefinition a3 = new RawFieldDefinition("a", "a", true);

        // reflexive property
        Assertions.assertEquals(a, a);

        // symmetric property
        Assertions.assertTrue(a.equals(b) == b.equals(a));
        Assertions.assertTrue(a.equals(a2) == a2.equals(a));

        // transitive property
        if (a.equals(a2) && a2.equals(a3)) {
            Assertions.assertEquals(a3, a);
        }

        // consistency property
        Assertions.assertTrue(a.equals(b) == a.equals(b));
        Assertions.assertTrue(a.equals(a2) == a.equals(a2));

        // non-null
        Assertions.assertFalse(a.equals(null));
    }
}
