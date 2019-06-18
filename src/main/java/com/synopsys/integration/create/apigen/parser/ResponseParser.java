package com.synopsys.integration.create.apigen.parser;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

import static java.lang.String.join;

public class ResponseParser {

    Boolean multipleResponses = false;

    public static void main(String[] args) {

        ResponseParser parser = new ResponseParser();

        String resourceName = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3";

        ArrayList<ResponseDefinition> responses = parser.parseResponses(new File(resourceName));
        for (ResponseDefinition response : responses) {
            //System.out.println(response.getName());
        }
    }

    public ArrayList<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        File endpointsPath = new File(specificationRootDirectory, "endpoints");
        File apiPath = new File(endpointsPath, "api");

        ArrayList<ResponseDefinition> responseDefinitions = new ArrayList<>();
        populateResponses(responseDefinitions, apiPath, apiPath.getAbsolutePath().length() + 1);

        return responseDefinitions;
    }

    // def --> Object ?
    private void populateResponses(ArrayList<ResponseDefinition> responses, File parent, int prefixLength) {

        //System.out.println(parent.getAbsolutePath().substring(115));

        File[] children = parent.listFiles();

        // Deal with case where multiple responses for one request (ie. <...Id>/GET/ is a directory with many response-specification.json files)
        if (parent.getName().equals("GET") && parent.getParent().endsWith("Id") && children.length > 2) {
            multipleResponses = true;
        }

        for (File child : children) {
            if (child.getName().equals("response-specification.json") && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {

                String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                String responseName = getResponseName(responseRelativePath, multipleResponses);
                String responseMediaType = getResponseMediaType(responseRelativePath);

                responses.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

                //System.out.println(child.getAbsolutePath().substring(116));

            } else if (child.isDirectory()) {
                populateResponses(responses, child, prefixLength);
            }
        }
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
                    List<String> formattedSubPieces = new ArrayList<>();
                    for (String subPiece : subPieces) {
                        formattedSubPieces.add(StringUtils.capitalize(subPiece.toLowerCase()));
                    }
                    pieceToAdd = join("", formattedSubPieces);
                } else {
                    // Get # of response (ie. bds_policy_4_json vs bds_policy_5_json) <-- relies on assumption that there will be < 10 numbered responses
                    pieceToAdd = piece.substring(piece.length()-6, piece.length()-5);
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
        /* Path should be of format .../<folder>/<file>.<mediaType>, so we can split on a '.' to get the type */
        int charsBeforeMediaType = 0;
        for (char c : responsePath.toCharArray()) {
            if (c == '.') return responsePath.substring(charsBeforeMediaType);
            charsBeforeMediaType++;
        }

        return null;
    }

}

