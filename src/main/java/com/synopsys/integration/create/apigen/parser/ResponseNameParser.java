/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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

import static java.lang.String.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ResponseNameParser {

    public static final String[] DIGIT_STRINGS = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public String getResponseName(final String responsePath, final Boolean multipleResponses) {
        final List<String> resourceNamePieces = new ArrayList<>();
        boolean nextPieceImportant = false;
        final String idSuffix = "Id";
        String mediaVersion = null;
        for (final String piece : responsePath.split("/")) {
            if (piece.endsWith(idSuffix)) {
                String pieceToAdd = StringUtils.capitalize(piece.substring(0, piece.length() - idSuffix.length()));
                final String lastPiece = resourceNamePieces.size() > 0 ? resourceNamePieces.get(resourceNamePieces.size() - 1) : pieceToAdd;
                if (resourceNamePieces.size() > 0 && pieceToAdd.startsWith(lastPiece)) {
                    pieceToAdd = pieceToAdd.substring(lastPiece.length());
                }
                resourceNamePieces.add(pieceToAdd);
            } else if (piece.equals("GET") && multipleResponses) {
                nextPieceImportant = true;
            } else if (nextPieceImportant) {
                // Differentiate names of responses tied to a single request
                String pieceToAdd = null;
                if (!piece.startsWith("bds")) {
                    // Parse subfolder name and add to list of pieces
                    final String[] subPieces = piece.split("_");
                    final List<String> formattedSubPieces = Arrays.stream(subPieces)
                                                                .map(String::toLowerCase)
                                                                .map(StringUtils::capitalize)
                                                                .collect(Collectors.toList());

                    pieceToAdd = join("", formattedSubPieces);
                } else {
                    // Get # of response (ie. bds_policy_4_json vs bds_policy_5_json) <-- relies on assumption that there will be < 10 numbered responses
                    mediaVersion = getMediaVersion(piece.substring(piece.length() - 6, piece.length() - 5));
                }
                if (pieceToAdd != null) {
                    resourceNamePieces.add(pieceToAdd);
                }
                nextPieceImportant = false;
            }
        }

        if (resourceNamePieces.size() > 0) {
            String result = join("", resourceNamePieces) + "View";
            if (mediaVersion != null) {
                result += "V" + mediaVersion;
            }

            if (result.endsWith("ViewV")) {
                System.out.println("***** " + result);
                result = result.replace("ViewV", "View");
            }

            return result;
        } else {
            return "";
        }
    }

    public static String getNonVersionedName(final String responseName) {
        // relies on assumption that there will be < 10 numbered responses
        final String mediaVersion = getMediaVersion(responseName);
        if (mediaVersion != null) {
            return responseName.replace("V" + mediaVersion, "");
        } else {
            return responseName.replace("ViewV", "View");
        }
    }

    public static String getMediaVersion(final String responseName) {
        for (final String digit : DIGIT_STRINGS) {
            if (responseName.endsWith(digit)) {
                return digit;
            }
        }
        return null;
    }
}
