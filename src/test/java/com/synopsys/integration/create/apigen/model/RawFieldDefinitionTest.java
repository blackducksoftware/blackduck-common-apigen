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
