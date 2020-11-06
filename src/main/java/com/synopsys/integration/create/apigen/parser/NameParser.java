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

import static java.lang.String.join;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;

public class NameParser {
    private static String VIEW = "View";

    private NameAndPathManager nameAndPathManager;
    private Set<String> redundantNamePrefixes = getRedundantNamePrefixes();
    private String GET_ENDPOINT = "GET";

    public NameParser(final NameAndPathManager nameAndPathManager) {
        this.nameAndPathManager = nameAndPathManager;
    }

    public String computeResponseName(final String responsePath) {
        /*
            Traverse the response file's path, use the last two 'pieces' of the path to construct a name.
            If that algorithm creates a duplicate name, then prepend a differentiating prefix.
            For some response paths, we provide an override for the name assigned to it.
         */
        String firstPiece = null;
        String lastPiece = null;
        DifferentiatingResponseNamePrefixBuilder differentiatingPrefixBuilder = new DifferentiatingResponseNamePrefixBuilder(redundantNamePrefixes);
        final ListIterator<String> pathPieces = Arrays.asList(responsePath.split("/")).listIterator();
        String nextPiece = getGroomedPiece(pathPieces.next());
        while (pathPieces.hasNext()) {
            firstPiece = lastPiece;
            lastPiece = nextPiece;
            nextPiece = getGroomedPiece(pathPieces.next());
            if (!nextPiece.equals(GET_ENDPOINT)) {
                differentiatingPrefixBuilder.addPiece(nextPiece);
            } else {
                // Once we've hit the GET endpoint, we have what we need to compute a name for the response
                break;
            }
        }
        if (pathPieces.hasNext()) {
            return computeResponseNameFromPieces(firstPiece, lastPiece, differentiatingPrefixBuilder, nameAndPathManager.getResponseNameOverride(responsePath));
        } else {
            return "";
        }
    }

    private String computeResponseNameFromPieces(final String firstPiece, final String lastPiece, DifferentiatingResponseNamePrefixBuilder differentiatingPrefixBuilder, String responseNameOverride) {
        final String responseName;
        if (responseNameOverride != null) {
            responseName = getJoinedResponseNamePieces(null, responseNameOverride);
        } else {
            responseName = getJoinedResponseNamePieces(firstPiece, lastPiece);
        }
        final String finalResponseName;
        if (nameNeedsDifferentiatingPrefix(responseName, differentiatingPrefixBuilder.getPrefix())) {
            finalResponseName = differentiatingPrefixBuilder.getNameWithPrefix(responseName);
        } else {
            finalResponseName = responseName;
        }
        return finalResponseName;
    }

    private boolean nameNeedsDifferentiatingPrefix(final String responseName, final String differentiatingPrefix) {
        return differentiatingPrefix != null && !responseName.startsWith(differentiatingPrefix);
    }

    private String getGroomedPiece(final String piece) {
        return StringUtils.capitalize(concatHyphenatedString(piece.replace("Id", "")));
    }

    private String getJoinedResponseNamePieces(final String firstPiece, final String lastPiece) {
        String responseName;
        if (lastPiece == null) {
            return "";
        } else if (firstPiece == null) {
            responseName = lastPiece + VIEW;
        } else {
            responseName = firstPiece + lastPiece + VIEW;
        }
        return stripRedundantNamePrefix(responseName);
    }

    private Set<String> getRedundantNamePrefixes() {
        final Set<String> redundantNamePrefixes = new HashSet<>();

        redundantNamePrefixes.add("Codelocations");
        redundantNamePrefixes.add("Components");
        redundantNamePrefixes.add("Comments");
        redundantNamePrefixes.add("CustomFields");
        redundantNamePrefixes.add("Cwes");
        redundantNamePrefixes.add("Issues");
        redundantNamePrefixes.add("Jobs");
        redundantNamePrefixes.add("Licenses");
        redundantNamePrefixes.add("LicenseFamilies");
        redundantNamePrefixes.add("LicenseTerms");
        redundantNamePrefixes.add("LicenseTermCategories");
        redundantNamePrefixes.add("Objects");
        redundantNamePrefixes.add("Origins");
        redundantNamePrefixes.add("PolicyRules");
        redundantNamePrefixes.add("Projects");
        redundantNamePrefixes.add("Reports");
        redundantNamePrefixes.add("Roles");
        redundantNamePrefixes.add("ScanSummaries");
        redundantNamePrefixes.add("Tags");
        redundantNamePrefixes.add("Usergroups");
        redundantNamePrefixes.add("Users");
        redundantNamePrefixes.add("Versions");
        redundantNamePrefixes.add("Vulnerabilities");

        return redundantNamePrefixes;
    }

    private String stripRedundantNamePrefix(final String responseName) {
        for (final String prefix : redundantNamePrefixes) {
            final String responseNameStrippedOfRedundantPrefix = responseName.replaceFirst(prefix, "");
            if (responseName.startsWith(prefix) && !responseNameStrippedOfRedundantPrefix.equals(VIEW)) {
                return responseNameStrippedOfRedundantPrefix;
            }
        }
        return responseName;
    }

    /* Util Methods */

    public static String getMediaVersionFromMediaType(final String mediaType) {
        // Media types are of the form <MediaType>_<MediaVersion>_<Format>
        // We can extract media version by locating the last underscore of the media type, and extracting the character behind it
        // *** This method operates under the assumption that there will be no more than 9 media versons for a given type.
        int indexOfLastUnderscore = mediaType.lastIndexOf("_");
        return mediaType.substring(indexOfLastUnderscore - 1, indexOfLastUnderscore);
    }

    public static String reorderViewInName(final String name) {
        if (name.contains(VIEW)) {
            return name.replace(VIEW, "") + VIEW;
        } else {
            return name;
        }
    }

    public static String stripListNotation(final String string) {
        return string.replace(UtilStrings.JAVA_LIST, "").replace(UtilStrings.LIST, "").replace(">", "");
    }

    public static String concatHyphenatedString(final String string) {
        final String[] pieces = string.split("-");
        final List<String> formattedPieces = Arrays.stream(pieces)
                                                 .map(StringUtils::capitalize)
                                                 .collect(Collectors.toList());
        return join("", formattedPieces);
    }

    public static String wrapInOptionalNotation(final String string) {
        return UtilStrings.OPTIONAL_WRAPPER + string + ">";
    }

    public static String unwrapOptionalNotation(final String string) {
        return string.replace(UtilStrings.OPTIONAL_WRAPPER, "").replace(">", "");
    }

    public static String stripListAndOptionalNotation(final String string) {
        return stripListNotation(unwrapOptionalNotation(string));
    }

}
