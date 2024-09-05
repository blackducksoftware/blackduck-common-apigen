/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import static java.lang.String.join;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.NameAndPathManager;
import com.blackduck.integration.create.apigen.data.UtilStrings;

@Component
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
        if (nameNeedsDifferentiatingPrefix(responseName)) {
            finalResponseName = differentiatingPrefixBuilder.getNameWithPrefix(responseName);
        } else {
            finalResponseName = responseName;
        }

        nameAndPathManager.addNonLinkClassName(finalResponseName);
        return finalResponseName;
    }

    private boolean nameNeedsDifferentiatingPrefix(final String responseName) {
        return nameAndPathManager.getNonLinkClassNames().contains(responseName);
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

    public static String restoreListNotation(String originalType, String trueType) {
        if (trueType != null && !trueType.contains(UtilStrings.JAVA_LIST) && originalType.contains(UtilStrings.JAVA_LIST)) {
            trueType = UtilStrings.JAVA_LIST + trueType + ">";
        }
        return trueType;
    }

    public static String concatHyphenatedString(final String string) {
        final String[] pieces = string.split("-");
        final List<String> formattedPieces = Arrays.stream(pieces)
                                                 .map(StringUtils::capitalize)
                                                 .collect(Collectors.toList());
        return join("", formattedPieces);
    }

    public static String buildNonRedundantStringFromPieces(List<String> pieces) {
        Iterator<String> iterator = pieces.listIterator();
        StringBuilder newString = new StringBuilder();
        if (!iterator.hasNext()) {
            return "";
        }

        String currentPiece = iterator.next();
        while (iterator.hasNext()) {
            String nextPiece = iterator.next();
            // Only add piece if it's not redundant with the current piece
            if (!nextPiece.startsWith(currentPiece)) {
                newString.append(currentPiece);
            }
            currentPiece = nextPiece;
        }
        if (!newString.toString().endsWith(currentPiece)) {
            newString.append(currentPiece);
        }
        return newString.toString();
    }

    public static List<String> extractPiecesFromCamelCase(String s) {
        return Arrays.asList(s.replaceAll(
            String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
            ),
            " "
        ).split(" "));
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
