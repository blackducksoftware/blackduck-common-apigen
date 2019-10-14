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

import com.synopsys.integration.create.apigen.helper.UtilStrings;

public class NameParser {

    public static final String[] DIGIT_STRINGS = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    private final Set<String> REDUNDANT_NAME_PREFIXES = getRedundantNamePrefixes();
    private static final String VIEWV = "ViewV";

    public String getResponseName(final String responsePath) {
        String firstPiece = null;
        String lastPiece = null;
        final String mediaVersion;
        final ListIterator<String> pathPieces = Arrays.asList(responsePath.split("/")).listIterator();
        String nextPiece = getGroomedPiece(pathPieces.next());
        while (!nextPiece.equals("GET") && pathPieces.hasNext()) {
            firstPiece = lastPiece;
            lastPiece = nextPiece;
            nextPiece = getGroomedPiece(pathPieces.next());
        }
        if (pathPieces.hasNext()) {
            final String mediaType = pathPieces.next();
            mediaVersion = getMediaVersion(mediaType.substring(mediaType.length() - 6, mediaType.length() - 5));
            final String responseName = getResponseNameJoinHelper(firstPiece, lastPiece, mediaVersion);
            return stripRedundantNamePrefix(responseName, mediaType);
        } else {
            return "";
        }
    }

    private String getGroomedPiece(final String piece) {
        return StringUtils.capitalize(concatHyphenatedString(piece.replace("Id", "")));
    }

    private String getResponseNameJoinHelper(final String firstPiece, final String lastPiece, final String mediaVersion) {
        String responseName;
        if (lastPiece == null) {
            return "";
        } else if (firstPiece == null) {
            responseName = lastPiece + VIEWV + mediaVersion;
        } else {
            responseName = firstPiece + lastPiece + VIEWV + mediaVersion;
        }

        if (responseName.endsWith(VIEWV)) {
            System.out.println("***** " + responseName);
            responseName = responseName.replace(VIEWV, UtilStrings.VIEW);
        }
        return responseName;
    }

    public static String getNonVersionedName(final String responseName) {
        // relies on assumption that there will be < 10 numbered responses
        final String mediaVersion = getMediaVersion(responseName);
        if (mediaVersion != null) {
            return responseName.replace("V" + mediaVersion, "");
        } else {
            return responseName.replace(VIEWV, UtilStrings.VIEW);
        }
    }

    public static String getMediaVersion(final String responseName) {
        for (final String digit : DIGIT_STRINGS) {
            if (responseName.contains(digit)) {
                return digit;
            }
        }
        return null;
    }

    private Set<String> getRedundantNamePrefixes() {
        final Set<String> redundantNamePrefixes = new HashSet<>();

        redundantNamePrefixes.add("Components");
        redundantNamePrefixes.add("PolicyRules");
        redundantNamePrefixes.add("Versions");
        redundantNamePrefixes.add("Licenses");
        redundantNamePrefixes.add("CustomFields");
        redundantNamePrefixes.add("Projects");
        redundantNamePrefixes.add("ScanSummaries");
        redundantNamePrefixes.add("Roles");
        redundantNamePrefixes.add("Usergroups");
        redundantNamePrefixes.add("Users");
        redundantNamePrefixes.add("Jobs");
        redundantNamePrefixes.add("Codelocations");
        redundantNamePrefixes.add("Objects");
        redundantNamePrefixes.add("LicenseTerms");

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

    public static String stripS(final String string) {
        if (string.endsWith("s")) {
            return string.substring(0, string.length() - 1);
        }
        return string;
    }

}
