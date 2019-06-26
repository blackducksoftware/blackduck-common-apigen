package com.synopsys.integration.create.apigen.parser;

import static java.lang.String.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class ResponseNameParser {
    public String getResponseName(final String responsePath, final Boolean multipleResponses) {
        final List<String> resourceNamePieces = new ArrayList<>();
        boolean nextPieceImportant = false;
        final String idSuffix = "Id";
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
                    pieceToAdd = piece.substring(piece.length() - 6, piece.length() - 5);
                }
                resourceNamePieces.add(pieceToAdd);
                nextPieceImportant = false;
            }
        }

        if (resourceNamePieces.size() > 0) {
            return join("", resourceNamePieces) + "View";
        } else {
            return "";
        }
    }
}
