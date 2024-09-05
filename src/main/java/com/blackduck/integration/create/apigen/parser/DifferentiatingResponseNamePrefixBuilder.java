/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferentiatingResponseNamePrefixBuilder {
    private List<String> prefixPieces = new ArrayList<>();
    private Set<String> redundantNamePrefixes;

    public DifferentiatingResponseNamePrefixBuilder(Set<String> redundantNamePrefixes) {
        this.redundantNamePrefixes = redundantNamePrefixes;
    }

    public void addPiece(String piece) {
        if (piece != null) {
            prefixPieces.add(piece);
        }
    }

    public String getPrefix() {
        List<String> pieces = prefixPieces.stream()
                                        .filter(it -> !redundantNamePrefixes.contains(it))
                                        .collect(Collectors.toList());
        return NameParser.buildNonRedundantStringFromPieces(pieces);
    }

    public String getNameWithPrefix(String responseName) {
        String prefix = getPrefix();
        // If prefix is redundant with the response's current name, just return the prefix
        String name = responseName.replace("View", "");
        if (prefix.endsWith(name)) {
            return String.format("%sView", prefix);
        } else {
            return prefix + responseName;
        }
    }


}
