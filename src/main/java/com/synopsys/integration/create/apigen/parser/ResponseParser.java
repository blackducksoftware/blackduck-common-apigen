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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.DefinitionParseParameters;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ResponseParser {
    private final MediaTypes mediaTypes;
    private final Gson gson;
    private final TypeTranslator typeTranslator;

    @Autowired
    public ResponseParser(final MediaTypes mediaTypes, final Gson gson, final TypeTranslator typeTranslator) {
        this.mediaTypes = mediaTypes;
        this.gson = gson;
        this.typeTranslator = typeTranslator;
    }

    public ArrayList<ResponseDefinition> parseResponses(final File specificationRootDirectory) {
        final File endpointsPath = new File(specificationRootDirectory, "endpoints");
        final File apiPath = new File(endpointsPath, UtilStrings.API);

        return parseResponses(apiPath, apiPath.getAbsolutePath().length() + 1);
    }

    private ArrayList<ResponseDefinition> parseResponses(final File parent, final int prefixLength) {
        final ArrayList<ResponseDefinition> responseDefinitions = new ArrayList<>();
        final List<File> children = Arrays.stream(parent.listFiles())
                                        .filter(file -> !file.getName().equals("notifications"))
                                        .collect(Collectors.toList());

        // If child file of parent is response specification data, parse the file, otherwise recurse and parse the child's children
        for (final File child : children) {
            if (child.getName().equals(UtilStrings.RESPONSE_SPECIFICATION_JSON) && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                final String responseRelativePath = child.getAbsolutePath().substring(prefixLength);
                final NameParser nameParser = new NameParser();
                final String responseName = nameParser.getResponseName(responseRelativePath);
                final String responseMediaType = mediaTypes.getLongName(child.getParentFile().getName());
                final boolean doesHaveMultipleResults = computeIfHasMultipleResults(child);
                final ResponseDefinition response = new ResponseDefinition(responseRelativePath, responseName, responseMediaType, doesHaveMultipleResults);
                responseDefinitions.add(response);
            } else if (child.isDirectory()) {
                responseDefinitions.addAll(parseResponses(child, prefixLength));
            }
        }
        return responseDefinitions;
    }

    private boolean computeIfHasMultipleResults(final File file) {
        final File parentOfArrayResponse = getParentOfArrayResponse(file);
        if (parentOfArrayResponse == null) {
            return false;
        }
        return containsArrayResponse(parentOfArrayResponse, false);
    }

    private File getParentOfArrayResponse(final File file) {
        File current = file;
        while (!current.getName().equals(UtilStrings.GET)) {
            current = current.getParentFile();
        }
        final File getFile = current;
        current = getFile.getParentFile();
        if (current.getParentFile() != null && !current.getParentFile().getName().equals(UtilStrings.API)) {
            return current.getParentFile();
        } else {
            return null;
        }
    }

    private boolean containsArrayResponse(final File file, final boolean isGetFileSubDirectory) {
        final File[] children = file.listFiles();
        if (children == null) {
            return false;
        }
        for (final File child : children) {
            if (child.getName().equals(UtilStrings.RESPONSE_SPECIFICATION_JSON)) {
                final ResponseDefinition potentialArrayResponse = buildDummyResponseDefinitionFromFile(child);
                if (ArrayResponseIdentifier.isArrayResponse(potentialArrayResponse)) {
                    return true;
                }
            }
            if (child.getName().equals(UtilStrings.GET) || (isGetFileSubDirectory && !child.getName().equals(UtilStrings.REQUEST_SPECIFICATION_JSON))) {
                return containsArrayResponse(child, true);
            }
        }
        return false;
    }

    private ResponseDefinition buildDummyResponseDefinitionFromFile(final File file) {
        final DefinitionParser definitionParser = new DefinitionParser(gson, file);
        final List<RawFieldDefinition> rawFieldDefinitions = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
        final FieldDefinitionProcessor fieldDefinitionProcessor = new FieldDefinitionProcessor(typeTranslator);
        final List<FieldDefinition> fieldDefinitions = fieldDefinitionProcessor.parseFieldDefinitions("", rawFieldDefinitions);
        final ResponseDefinition response = new ResponseDefinition("", "", "", false);
        response.addFields(fieldDefinitions);
        return response;
    }
}

