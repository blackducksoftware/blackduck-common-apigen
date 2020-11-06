package com.synopsys.integration.create.apigen.parser;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DifferentiatingResponseNamePrefixBuilderTest {

    @Test
    public void testBuildsCorrectNameWithPrefix() {
        Set<String> redundantPrefixes = getRedundantNamePrefixes();

        DifferentiatingResponseNamePrefixBuilder prefixBuilder = new DifferentiatingResponseNamePrefixBuilder(redundantPrefixes);

        prefixBuilder.addPiece("Projects");
        prefixBuilder.addPiece("Project");
        prefixBuilder.addPiece("Versions");
        prefixBuilder.addPiece("ProjectVersion");
        Assertions.assertEquals("ProjectVersionComponentVersionView", prefixBuilder.getNameWithPrefix("ComponentVersionView"));

        prefixBuilder.addPiece("Components");
        prefixBuilder.addPiece("Component");
        prefixBuilder.addPiece("Versions");
        prefixBuilder.addPiece("ComponentVersion");
        Assertions.assertEquals("ProjectVersionComponentVersionView", prefixBuilder.getNameWithPrefix("ComponentVersionView"));
    }

    private Set<String> getRedundantNamePrefixes() {
        final Set<String> redundantNamePrefixes = new HashSet<>();

        redundantNamePrefixes.add("Components");
        redundantNamePrefixes.add("Projects");
        redundantNamePrefixes.add("Versions");

        return redundantNamePrefixes;
    }
}
