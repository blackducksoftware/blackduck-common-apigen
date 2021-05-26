package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.DuplicateOverrides;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

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
