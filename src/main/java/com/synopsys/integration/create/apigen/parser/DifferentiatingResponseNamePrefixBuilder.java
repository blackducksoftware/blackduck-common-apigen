/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.parser;

import java.util.ArrayList;
import java.util.Iterator;
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
        StringBuilder prefixBuilder = new StringBuilder();
        Iterator<String> iterator = prefixPieces.stream()
                                        .filter(it -> !redundantNamePrefixes.contains(it))
                                        .collect(Collectors.toList())
                                        .listIterator();
        if (!iterator.hasNext()) {
            return "";
        }

        String currentPiece = iterator.next();
        while (iterator.hasNext()) {
            String nextPiece = iterator.next();
            // Only add piece if it's not redundant with the current piece
            if (!nextPiece.startsWith(currentPiece)) {
                prefixBuilder.append(currentPiece);
            }
            currentPiece = nextPiece;
        }
        if (!prefixBuilder.toString().endsWith(currentPiece)) {
            prefixBuilder.append(currentPiece);
        }

        return prefixBuilder.toString();
    }

    public String getNameWithPrefix(String responseName) {
        String prefix = getPrefix();
        // If prefix is redundant with the response's current name, just return the prefix
        String mediaVersion = NameParser.getMediaVersionFromResponseName(responseName);
        String name = NameParser.getNonVersionedName(responseName.replace("View", ""));
        if (prefix.endsWith(name)) {
            return String.format("%sView%s", prefix, mediaVersion != null ? "V" + mediaVersion : "");
        } else {
            return prefix + responseName;
        }
    }


}
