package com.synopsys.integration.create.apigen.parser;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.comparator.NameFileComparator;

import java.io.File;
import java.util.*;

public class ResponseParser {

    public static void main(String[] args) {

        ResponseParser parser = new ResponseParser();

        String resourceName = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3";

        ArrayList<ResponseDefinition> responses = parser.parseResponses(new File(resourceName));
        for (ResponseDefinition response : responses) {
            System.out.println(response.getName());
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
        //Arrays.sort(children, NameFileComparator.NAME_COMPARATOR);
        for (File child : children) {
            if (child.getName().equals("response-specification.json") && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                //System.out.println(child.getAbsolutePath().substring(prefixLength));
                String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                String responseName = getResponseName(responseRelativePath);
                String responseMediaType = getResponseMediaType(responseRelativePath);

                responses.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

                //System.out.println(child.getAbsolutePath().substring(116));

            } else if (child.isDirectory()) {
                //if (child.getName().equals("GET")) System.out.println(child.getAbsolutePath().substring(116));
                populateResponses(responses, child, prefixLength);
            }
        }
    }

    private String getResponseName(String responsePath) {
        List<String> resourceNamePieces = new ArrayList<>();
        for (String piece : responsePath.split("/") ) {
            if (piece.endsWith("Id")) {
                String pieceToAdd = StringUtils.capitalize(piece.substring(0,piece.length()-2));
                String lastPiece = resourceNamePieces.size() > 0 ? resourceNamePieces.get(resourceNamePieces.size()-1) : pieceToAdd;
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

