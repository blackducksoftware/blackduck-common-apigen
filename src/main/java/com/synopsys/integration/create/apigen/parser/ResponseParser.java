package com.synopsys.integration.create.apigen.parser;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResponseParser {

    public static void main(String[] args) {
        ResponseParser responseParser = new ResponseParser();
    }

    Set<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        File endpointsPath = new File(specificationRootDirectory, "endpoints");
        File apiPath = new File(endpointsPath, "api");
        //JsonSlurper;
        Set<ResponseDefinition> responseDefinitions = new HashSet<>();
        populateResponses(responseDefinitions, apiPath, endpointsPath.getAbsolutePath().length() + 1);

        return responseDefinitions;
    }

    // def --> Object ?
    private void populateResponses(Set<ResponseDefinition> responses, File parent, int prefixLength) {

        for (File child : parent.listFiles()) {
            if (child.getName().equals("response-specification.json") && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                Object responseRelativePath = parent.getAbsolutePath().substring(prefixLength);
                responses.add((ResponseDefinition) responseRelativePath);
            } else if (child.isDirectory()) {
                populateResponses(responses, child, prefixLength);
            }
        }
    }

    private String getResponseName(String responsePath) {
        List<String> resourceNamePieces = new ArrayList<String>();
        for (String piece : responsePath.split("\\\\")) {
            if (piece.endsWith("Id")) {
                String pieceToAdd = piece.substring(0,-3).toUpperCase();
                String lastPiece = resourceNamePieces.get(resourceNamePieces.size()-1);
                if (resourceNamePieces.size() > 0 && pieceToAdd.startsWith(lastPiece)) {
                    pieceToAdd = pieceToAdd.substring(lastPiece.length());
                }
                resourceNamePieces.add(pieceToAdd);
            }
        }

        if (resourceNamePieces.size() > 0) {
            return String.join("",resourceNamePieces) + "View";
        } else {
            return "";
        }
    }

}

