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

import javax.swing.text.View;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;

public class NameParser {
    private static String VIEWV = "ViewV";
    private static String VIEW = "View";

    private NameAndPathManager nameAndPathManager;
    private Set<String> redundantNamePrefixes = getRedundantNamePrefixes();
    private String GET_ENDPOINT = "GET";

    public NameParser(final NameAndPathManager nameAndPathManager) {
        this.nameAndPathManager = nameAndPathManager;
    }

    public String computeResponseName(final String responsePath) {
        String firstPiece = null;
        String lastPiece = null;
        DifferentiatingClassnamePrefixBuilder differentiatingPrefixBuilder = new DifferentiatingClassnamePrefixBuilder(redundantNamePrefixes);
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
            firstPiece = !nameAndPathManager.getNonLinkClassNames().contains(firstPiece) ? firstPiece : differentiatingPrefixBuilder.getPrefix();
            final String mediaType = pathPieces.next();
            return computeResponseNameFromPieces(firstPiece, lastPiece, mediaType, differentiatingPrefixBuilder);
        } else {
            return "";
        }
    }

    private String computeResponseNameFromPieces(final String firstPiece, final String lastPiece, final String mediaType, DifferentiatingClassnamePrefixBuilder differentiatingPrefixBuilder) {
        final String mediaVersion = getMediaVersion(mediaType.substring(mediaType.length() - 6, mediaType.length() - 5));
        final String responseName = getJoinedResponseNamePieces(firstPiece, lastPiece, mediaVersion);
        final String finalResponseName;
        if (nameNeedsDifferentiatingPrefix(responseName, differentiatingPrefixBuilder.getPrefix())) {
            finalResponseName = differentiatingPrefixBuilder.getNameWithPrefix(responseName);
        } else {
            finalResponseName = responseName;
        }
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(finalResponseName));
        return finalResponseName;
    }

    private boolean nameNeedsDifferentiatingPrefix(final String responseName, final String differentiatingPrefix) {
        return nameAndPathManager.getNonLinkClassNames().contains(getNonVersionedName(responseName)) && differentiatingPrefix != null && !responseName.startsWith(differentiatingPrefix);
    }

    private String getGroomedPiece(final String piece) {
        return StringUtils.capitalize(concatHyphenatedString(piece.replace("Id", "")));
    }

    private String getJoinedResponseNamePieces(final String firstPiece, final String lastPiece, final String mediaVersion) {
        String responseName;
        if (lastPiece == null) {
            return "";
        } else if (firstPiece == null) {
            responseName = lastPiece + VIEWV + mediaVersion;
        } else {
            responseName = firstPiece + lastPiece + VIEWV + mediaVersion;
        }

        if (responseName.endsWith(VIEWV)) {
            responseName = responseName.replace(VIEWV, VIEW);
        }
        return stripRedundantNamePrefix(responseName, mediaVersion);
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

    private String stripRedundantNamePrefix(final String responseName, final String mediaType) {
        for (final String prefix : redundantNamePrefixes) {
            final String responseNameStrippedOfRedundantPrefix = responseName.replaceFirst(prefix, "");
            if (responseName.startsWith(prefix) && !responseNameStrippedOfRedundantPrefix.equals(VIEWV + mediaType)) {
                return responseNameStrippedOfRedundantPrefix;
            }
        }
        return responseName;
    }

    /* Util Methods */

    public static String getNonVersionedName(final String responseName) {
        // relies on assumption that there will be < 10 numbered responses
        final String mediaVersion = getMediaVersion(responseName);
        if (mediaVersion != null) {
            return responseName.replace("V" + mediaVersion, "");
        } else {
            return responseName.replace(VIEWV, VIEW);
        }
    }

    public static String getMediaVersion(final String responseName) {
        for (final String digit : UtilStrings.DIGIT_STRINGS) {
            if (stripListNotation(responseName).endsWith(digit)) {
                return digit;
            }
        }
        return null;
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
