/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.parser.file;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.DefinitionParseParameters;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ApiParser;
import com.synopsys.integration.create.apigen.parser.DefinitionParser;
import com.synopsys.integration.create.apigen.parser.DuplicateTypeIdentifier;
import com.synopsys.integration.create.apigen.parser.FieldDataProcessor;
import com.synopsys.integration.create.apigen.parser.FieldDefinitionProcessor;
import com.synopsys.integration.create.apigen.parser.NameParser;
import com.synopsys.integration.create.apigen.parser.ResponseType;
import com.synopsys.integration.create.apigen.parser.ResponseTypeIdentifier;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DirectoryPathParser implements ApiParser {
    private final MediaTypes mediaTypes;
    private final Gson gson;
    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private final NameParser nameParser;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final FieldDefinitionProcessor processor;

    @Autowired
    public DirectoryPathParser(final MediaTypes mediaTypes, final Gson gson, final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager, final MissingFieldsAndLinks missingFieldsAndLinks,
        FieldDefinitionProcessor processor) {
        this.mediaTypes = mediaTypes;
        this.gson = gson;
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
        this.nameParser = new NameParser(nameAndPathManager);
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.processor = processor;
    }

    @Override
    public List<ResponseDefinition> parseApi(File specificationRootDirectory) {
        final File endpointsPath = new File(specificationRootDirectory, "endpoints");
        final File apiPath = new File(endpointsPath, UtilStrings.API);

        List<ResponseDefinition> responseDefinitions = new LinkedList<>();
        List<ResponseDefinition> tempResponseDefinitions = new LinkedList<>();
        parseApi(apiPath, apiPath.getAbsolutePath().length() + 1, tempResponseDefinitions);

        // For each response file, parse the JSON for FieldDefinition objects
        for (final ResponseDefinition response : tempResponseDefinitions) {
            final String absolutePath = specificationRootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            final File responseSpecificationFile = new File(absolutePath);
            final DefinitionParser definitionParser = new DefinitionParser(gson, responseSpecificationFile);
            final Set<RawFieldDefinition> fields = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
            final Set<LinkDefinition> links = definitionParser.getDefinitions(DefinitionParseParameters.LINK_PARAMETERS);

            response.addFields(processor.processFieldDefinitions(response.getName(), fields));
            response.addLinks(links);

            // Filter out 'Array' responses (those comprised solely of fields like 'items', 'meta', and 'totalCount') and extract data from responses where data is subfield of "items" field
            ResponseType responseType = ResponseTypeIdentifier.getResponseType(response);
            if (responseType.equals(ResponseType.DATA_IS_SUBFIELD_OF_ITEMS)) {
                responseDefinitions.add(extractResponseFromSubfieldsOfItems(response));
            } else if (!responseType.equals(ResponseType.ARRAY)){
                responseDefinitions.add(response);
            }
        }

        return responseDefinitions;
    }

    private void parseApi(final File parent, final int prefixLength, List<ResponseDefinition> responseDefinitions) {
        final List<File> children = Arrays.stream(parent.listFiles())
                                        .filter(file -> !file.getName().equals("notifications"))
                                        .sorted()
                                        .collect(Collectors.toList());

        // If child file of parent is response specification data, parse the file, otherwise recurse and parse the child's children
        for (final File child : children) {
            String fileName = child.getName();
            boolean requestOrResponseFile = fileName.equals(UtilStrings.RESPONSE_SPECIFICATION_JSON);
            if (requestOrResponseFile && parent.getAbsolutePath().contains(Application.RESPONSE_TOKEN)) {
                final String relativePath = child.getAbsolutePath().substring(prefixLength);
                final String mediaType = mediaTypes.getLongName(child.getParentFile().getName());
                if (fileName.equals(UtilStrings.RESPONSE_SPECIFICATION_JSON)) {
                    final String responseName = nameParser.computeResponseName(relativePath);
                    final boolean doesHaveMultipleResults = computeIfHasMultipleResults(child);
                    responseDefinitions.add(new ResponseDefinition(relativePath, responseName, mediaType, doesHaveMultipleResults));
                }
            } else if (child.isDirectory()) {
                parseApi(child, prefixLength, responseDefinitions);
            }
        }
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
                if (!ResponseTypeIdentifier.getResponseType(potentialArrayResponse).equals(ResponseType.STANDARD)) {
                    return true;
                }
            }
            if (child.getName().equals(UtilStrings.GET) || (isGetFileSubDirectory && !child.getName().equals(UtilStrings.REQUEST_SPECIFICATION_JSON))) {
                return containsArrayResponse(child, true);
            }
        }
        return false;
    }

    public ResponseDefinition extractResponseFromSubfieldsOfItems(ResponseDefinition response) {
        FieldDefinition items = null;
        for (FieldDefinition field : response.getFields()) {
            if (field.getPath().equals(UtilStrings.ITEMS)) {
                items = field;
            }
        }
        ResponseDefinition newResponse = new ResponseDefinition(response.getResponseSpecificationPath(), response.getName(), response.getMediaType(), response.hasMultipleResults());
        newResponse.addFields(items.getSubFields());

        return newResponse;
    }

    private ResponseDefinition buildDummyResponseDefinitionFromFile(final File file) {
        final DefinitionParser definitionParser = new DefinitionParser(gson, file);
        final Set<RawFieldDefinition> rawFieldDefinitions = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
        FieldDefinitionProcessor processor = new FieldDefinitionProcessor(new FieldDataProcessor(typeTranslator, new DuplicateTypeIdentifier()), missingFieldsAndLinks);
        final Set<FieldDefinition> fieldDefinitions = processor.processFieldDefinitions("", rawFieldDefinitions);
        final ResponseDefinition response = new ResponseDefinition("", "", "", false);
        response.addFields(fieldDefinitions);
        return response;
    }
}

