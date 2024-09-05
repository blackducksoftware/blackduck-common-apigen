package com.blackduck.integration.create.apigen.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class LinkDataTest {
    @Test
    public void testCamelLabel() {
        String label = "canonicalVersion";
        assertEquals("CanonicalVersion", computeLabel(label));
    }

    @Test
    public void testHyphenLabel() {
        String label = "custom-fields";
        assertEquals("CustomFields", computeLabel(label));
    }

    @Test
    public void testSimpleLabel() {
        String label = "tags";
        assertEquals("Tags", computeLabel(label));
    }

    public String computeLabel(String label) {
        return Arrays
                   .stream(StringUtils.split(label, "-"))
                   .map(StringUtils::capitalize)
                   .collect(Collectors.joining());
    }

}
