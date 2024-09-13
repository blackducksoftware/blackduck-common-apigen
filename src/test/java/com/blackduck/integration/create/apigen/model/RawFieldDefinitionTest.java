package com.blackduck.integration.create.apigen.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RawFieldDefinitionTest {

    @Test
    public void testRawFieldDefinitionEquals() {
        RawFieldDefinition a = new RawFieldDefinition("a", "a", true);
        RawFieldDefinition b = new RawFieldDefinition("b", "b", true);
        RawFieldDefinition a2 = new RawFieldDefinition("a", "a", true);
        RawFieldDefinition a3 = new RawFieldDefinition("a", "a", true);

        // reflexive property
        assertEquals(a, a);

        // symmetric property
        assertTrue(a.equals(b) == b.equals(a));
        assertTrue(a.equals(a2) == a2.equals(a));

        // transitive property
        if (a.equals(a2) && a2.equals(a3)) {
            assertEquals(a3, a);
        }

        // consistency property
        assertTrue(a.equals(b) == a.equals(b));
        assertTrue(a.equals(a2) == a.equals(a2));

        // non-null
        assertFalse(a.equals(null));
    }
}
