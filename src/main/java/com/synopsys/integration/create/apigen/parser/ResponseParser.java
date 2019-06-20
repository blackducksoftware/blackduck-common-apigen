package com.synopsys.integration.create.apigen.parser;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.MediaTypes;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class ResponseParser {

    Boolean multipleResponses = false;

    public static void main(String[] args) {

    }

    public ArrayList<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        File endpointsPath = new File(specificationRootDirectory, "endpoints");
        File apiPath = new File(endpointsPath, "api");

        return parseResponses(apiPath, apiPath.getAbsolutePath().length() + 1);
    }

    private ArrayList<ResponseDefinition> parseResponses(File parent, int prefixLength) {
        ArrayList<ResponseDefinition> responseDefinitions = new ArrayList<>();
        File[] children = parent.listFiles();

        // Deal with case where multiple responses for one request (ie. <...Id>/GET/ is a directory with many response-specification.json files)
        if (parent.getName().equals("GET") && parent.getParent().endsWith("Id") && children.length > 2) {
            multipleResponses = true;
        }

        // If child file of parent is response specification data, parse the file, otherwise recurse and parse the child's children
        for (File child : children) {
            if (child.getName().equals("response-specification.json") && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN) && !child.getName().equals("notifications")) {
                String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                String responseName = getResponseName(responseRelativePath, multipleResponses);
                String responseMediaType = getResponseMediaType(responseRelativePath);

                responseDefinitions.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

            } else if (child.isDirectory() && !child.getName().equals("notifications")) {
                responseDefinitions.addAll(parseResponses(child, prefixLength));
            }
        }
        return responseDefinitions;
    }

    private String getResponseName(String responsePath, Boolean multipleResponses) {
        List<String> resourceNamePieces = new ArrayList<>();
        Boolean nextPieceImportant = false;

        for (String piece : responsePath.split("/") ) {
            if (piece.endsWith("Id")) {
                String pieceToAdd = StringUtils.capitalize(piece.substring(0,piece.length()-2));
                String lastPiece = resourceNamePieces.size() > 0 ? resourceNamePieces.get(resourceNamePieces.size()-1) : pieceToAdd;
                if (resourceNamePieces.size() > 0 && pieceToAdd.startsWith(lastPiece)) {
                    pieceToAdd = pieceToAdd.substring(lastPiece.length());
                }
                resourceNamePieces.add(pieceToAdd);
            } else if (piece.equals("GET") && multipleResponses) {
                nextPieceImportant = true;
            } else if (nextPieceImportant) { // Differentiate names of responses tied to a single request
                String pieceToAdd = null;
                if (!piece.startsWith("bds")) {
                    // Parse subfolder name and add to list of pieces
                    String[] subPieces = piece.split("_");
                    List<String> formattedSubPieces = Arrays.stream(subPieces)
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
            return join("",resourceNamePieces) + "View";
        } else {
            return "";
        }
    }

    private String getResponseMediaType(String responsePath) {
        /* Path should be of format .../<folder>/<mediaType>/response-specification.json */
        List<String> pieces = Arrays.asList(responsePath.split("/"));
        ListIterator<String> iterator = pieces.listIterator();
        String piece = iterator.next();
        String lastPiece = null;
        while (!(piece.equals("response-specification.json"))) {
            lastPiece = piece;
            piece = iterator.next();
        }

        MediaTypes mediaTypes = new MediaTypes();
        return mediaTypes.getLongName(lastPiece);
    }

}

