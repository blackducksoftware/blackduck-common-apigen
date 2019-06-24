package com.synopsys.integration.create.apigen.parser;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.MediaTypes;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class ResponseParser {
    public static final String RESPONSE_SPECIFICATION_JSON = "response-specification.json";
    boolean multipleResponses = false;
    MediaTypes mediaTypes = new MediaTypes();


    public ArrayList<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        File endpointsPath = new File(specificationRootDirectory, "endpoints");
        File apiPath = new File(endpointsPath, "api");

        return parseResponses(apiPath, apiPath.getAbsolutePath().length() + 1);
    }

    private ArrayList<ResponseDefinition> parseResponses(File parent, int prefixLength) {
        ArrayList<ResponseDefinition> responseDefinitions = new ArrayList<>();
        List<File> children = Arrays.stream(parent.listFiles())
                .filter(file -> !file.getName().equals("notifications"))
                .collect(Collectors.toList());

        // Deal with case where multiple responses for one request (ie. <...Id>/GET/ is a directory with many response-specification.json files)
        // look out for re-usability here!
        multipleResponses = (parent.getName().equals("GET") && parent.getParent().endsWith("Id") && children.size() > 2);

        // If child file of parent is response specification data, parse the file, otherwise recurse and parse the child's children
        for (File child : children) {
            if (child.getName().equals(RESPONSE_SPECIFICATION_JSON) && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                String responseName = getResponseName(responseRelativePath, multipleResponses);
                String responseMediaType = mediaTypes.getLongName(child.getParentFile().getName());

                responseDefinitions.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

            } else if (child.isDirectory()) {
                responseDefinitions.addAll(parseResponses(child, prefixLength));
            }
        }
        return responseDefinitions;
    }

    private String getResponseName(String responsePath, Boolean multipleResponses) {
        List<String> resourceNamePieces = new ArrayList<>();
        boolean nextPieceImportant = false;
        String idSuffix = "Id";
        for (String piece : responsePath.split("/") ) {
            if (piece.endsWith(idSuffix)) {
                String pieceToAdd = StringUtils.capitalize(piece.substring(0,piece.length()-idSuffix.length()));
                String lastPiece = resourceNamePieces.size() > 0 ? resourceNamePieces.get(resourceNamePieces.size()-1) : pieceToAdd;
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

}

