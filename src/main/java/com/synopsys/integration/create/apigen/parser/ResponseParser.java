package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.MediaTypes;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ResponseParser {
    public static final String RESPONSE_SPECIFICATION_JSON = "response-specification.json";
    boolean multipleResponses = false;
    MediaTypes mediaTypes = new MediaTypes();

    public ArrayList<ResponseDefinition> parseResponses(final File specificationRootDirectory) {
        final File endpointsPath = new File(specificationRootDirectory, "endpoints");
        final File apiPath = new File(endpointsPath, "api");

        return parseResponses(apiPath, apiPath.getAbsolutePath().length() + 1);
    }

    private ArrayList<ResponseDefinition> parseResponses(final File parent, final int prefixLength) {
        final ArrayList<ResponseDefinition> responseDefinitions = new ArrayList<>();
        final List<File> children = Arrays.stream(parent.listFiles())
                                        .filter(file -> !file.getName().equals("notifications"))
                                        .collect(Collectors.toList());

        // Deal with case where multiple responses for one request (ie. <...Id>/GET/ is a directory with many response-specification.json files)
        // debug
        final String parentName = parent.getName();
        final String grandParentName = parent.getParent();
        final int numChildren = children.size();
        if (parentName.equals("policyRuleId")) {
            System.out.println("nop");
        }
        if (multipleResponses == false) {
            multipleResponses = (parent.getName().equals("GET") && parent.getParent().endsWith("Id") && children.size() >= 2);
        }

        // If child file of parent is response specification data, parse the file, otherwise recurse and parse the child's children
        for (final File child : children) {
            if (child.getName().equals(RESPONSE_SPECIFICATION_JSON) && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                final String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                final ResponseNameParser responseNameParser = new ResponseNameParser();
                final String responseName = responseNameParser.getResponseName(responseRelativePath, multipleResponses);
                final String responseMediaType = mediaTypes.getLongName(child.getParentFile().getName());

                responseDefinitions.add(new ResponseDefinition(responseRelativePath, responseName, responseMediaType));

            } else if (child.isDirectory()) {
                responseDefinitions.addAll(parseResponses(child, prefixLength));
            }
        }
        return responseDefinitions;
    }
}

