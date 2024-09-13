package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DuplicateTypeIdentifierTest {

    @Test
    public void testIdentifyDuplicateEnums() {
        RawFieldDefinition enum1 = new RawFieldDefinition("enum1", "Enum1", false, new HashSet<>(Arrays.asList("val1", "val2", "val3")));
        RawFieldDefinition enum2 = new RawFieldDefinition("enum2", "Enum2", false, new HashSet<>(Arrays.asList("val3", "val2", "val1")));

        DuplicateOverrides duplicateOverrides = new DuplicateOverrides();
        DuplicateTypeIdentifier duplicateTypeIdentifier = new DuplicateTypeIdentifier(duplicateOverrides);
        duplicateTypeIdentifier.screenForDuplicateType(enum1, enum1.getType());
        duplicateTypeIdentifier.screenForDuplicateType(enum2, enum2.getType());

        assertEquals("Enum1", duplicateOverrides.getOverride("Enum2"));
    }
}
