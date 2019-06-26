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
                ResponseNameParser responseNameParser = new ResponseNameParser();
                String responseName = responseNameParser.getResponseName(responseRelativePath, multipleResponses);
                String responseMediaType = mediaTypes.getLongName(child.getParentFile().getName());

                responseDefinitions.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

            } else if (child.isDirectory()) {
                responseDefinitions.addAll(parseResponses(child, prefixLength));
            }
        }
        return responseDefinitions;
    }
}

