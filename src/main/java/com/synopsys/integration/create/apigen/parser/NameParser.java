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
    private final NameAndPathManager nameAndPathManager;

    private final Set<String> REDUNDANT_NAME_PREFIXES = getRedundantNamePrefixes();
    private static final String VIEWV = "ViewV";
    private static final String VIEW = "View";

    public NameParser(final NameAndPathManager nameAndPathManager) {
        this.nameAndPathManager = nameAndPathManager;
    }

    public String getResponseName(final String responsePath) {
        String firstPiece = null;
        String lastPiece = null;
        String differentiatingPrefix = null;
        final ListIterator<String> pathPieces = Arrays.asList(responsePath.split("/")).listIterator();
        String nextPiece = getGroomedPiece(pathPieces.next());
        while (!nextPiece.equals("GET") && pathPieces.hasNext()) {
            differentiatingPrefix = firstPiece;
            firstPiece = lastPiece;
            lastPiece = nextPiece;
            nextPiece = getGroomedPiece(pathPieces.next());
        }
        if (pathPieces.hasNext()) {
            firstPiece = !nameAndPathManager.getNonLinkClassNames().contains(firstPiece) ? firstPiece : differentiatingPrefix;
            final String mediaType = pathPieces.next();
            final String responseName = computeResponseNameFromPieces(firstPiece, lastPiece, mediaType);
            if (nameNeedsDifferentiatingPrefix(responseName, differentiatingPrefix)) {
                return differentiatingPrefix + responseName;
            } else {
                return responseName;
            }
        } else {
            return "";
        }
    }

    private String computeResponseNameFromPieces(final String firstPiece, final String lastPiece, final String mediaType) {
        final String mediaVersion = getMediaVersion(mediaType.substring(mediaType.length() - 6, mediaType.length() - 5));
        final String responseName = getJoinedResponseNamePieces(firstPiece, lastPiece, mediaVersion);
        nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(stripRedundantNamePrefix(responseName, mediaVersion)));
        return stripRedundantNamePrefix(responseName, mediaVersion);
    }

    // FIXME - this is an in-ideal hack at the moment
    private boolean nameNeedsDifferentiatingPrefix(final String responseName, final String differentiatingPrefix) {
        //return nameAndPathManager.getNonLinkClassNames().contains(NameParser.getNonVersionedName(responseName)) && differentiatingPrefix != null && !REDUNDANT_NAME_PREFIXES.contains(differentiatingPrefix) && !responseName.startsWith(differentiatingPrefix);
        return responseName.contains("ComponentView") && differentiatingPrefix != null && differentiatingPrefix.equals("ProjectVersion");
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
        return responseName;
    }

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

    private Set<String> getRedundantNamePrefixes() {
        final Set<String> redundantNamePrefixes = new HashSet<>();

        redundantNamePrefixes.add("Codelocations");
        redundantNamePrefixes.add("Components");
        redundantNamePrefixes.add("Comments");
        redundantNamePrefixes.add("CustomFields");
        redundantNamePrefixes.add("Cwes");
        redundantNamePrefixes.add("Jobs");
        redundantNamePrefixes.add("Licenses");
        redundantNamePrefixes.add("LicenseFamilies");
        redundantNamePrefixes.add("LicenseTerms");
        redundantNamePrefixes.add("Objects");
        redundantNamePrefixes.add("PolicyRules");
        redundantNamePrefixes.add("Projects");
        redundantNamePrefixes.add("Reports");
        redundantNamePrefixes.add("Roles");
        redundantNamePrefixes.add("ScanSummaries");
        redundantNamePrefixes.add("Usergroups");
        redundantNamePrefixes.add("Users");
        redundantNamePrefixes.add("Versions");
        redundantNamePrefixes.add("Objects");
        redundantNamePrefixes.add("Vulnerabilities");

        return redundantNamePrefixes;
    }

    private String stripRedundantNamePrefix(final String responseName, final String mediaType) {
        for (final String prefix : REDUNDANT_NAME_PREFIXES) {
            final String responseNameStrippedOfRedundantPrefix = responseName.replaceFirst(prefix, "");
            if (responseName.startsWith(prefix) && !responseNameStrippedOfRedundantPrefix.equals(VIEWV + mediaType)) {
                return responseNameStrippedOfRedundantPrefix;
            }
        }
        return responseName;
    }

    public static String reorderViewInName(final String name) {
        if (name.contains("View")) {
            return name.replace("View", "") + "View";
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
