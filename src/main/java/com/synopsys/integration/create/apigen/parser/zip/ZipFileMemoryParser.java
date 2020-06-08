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
package com.synopsys.integration.create.apigen.parser.zip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.synopsys.integration.create.apigen.parser.FieldDefinitionProcessor;
import com.synopsys.integration.create.apigen.parser.NameParser;
import com.synopsys.integration.create.apigen.parser.ResponseType;
import com.synopsys.integration.create.apigen.parser.ResponseTypeIdentifier;

public class ZipFileMemoryParser implements ApiParser {
    private static final Logger logger = LoggerFactory.getLogger(ZipFileMemoryParser.class);
    private final MediaTypes mediaTypes;
    private final Gson gson;
    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final FieldDefinitionProcessor processor;

    public ZipFileMemoryParser(final MediaTypes mediaTypes, final Gson gson, final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager, final MissingFieldsAndLinks missingFieldsAndLinks,
        FieldDefinitionProcessor processor) {
        this.mediaTypes = mediaTypes;
        this.gson = gson;
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.processor = processor;
    }

    @Override
    public List<ResponseDefinition> parseApi(final File target) {
        List<ResponseDefinition> responses = new LinkedList<>();

        try (ZipFile zipFile = new ZipFile(target)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    byte[] buffer = new byte[1024];
                    try (InputStream zipInputStream = zipFile.getInputStream(entry);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream)) {

                        Optional<ResponseDefinition> optionalResponse = convertToResponse(entry, "endpoints/api/".length());
                        if (optionalResponse.isPresent()) {
                            DefinitionParser definitionParser = new DefinitionParser(gson);
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                bufferedOutputStream.write(buffer, 0, length);
                            }
                            bufferedOutputStream.flush();
                            byte[] entryContent = byteArrayOutputStream.toByteArray();

                            ResponseDefinition response = optionalResponse.get();
                            try (InputStream fileInputStream = new ByteArrayInputStream(entryContent)) {
                                final Set<RawFieldDefinition> fields = definitionParser.getDefinitions(fileInputStream, DefinitionParseParameters.RAW_FIELD_PARAMETERS);
                                response.addFields(processor.parseFieldDefinitions(response.getName(), fields));
                            } catch (IOException ex) {

                            }
                            try (InputStream fileInputStream = new ByteArrayInputStream(entryContent)) {
                                final Set<LinkDefinition> links = definitionParser.getDefinitions(fileInputStream, DefinitionParseParameters.LINK_PARAMETERS);
                                response.addLinks(links);
                            } catch (IOException ex) {

                            }

                            // Filter out 'Array' responses and extract data from responses where data is subfield of "items" field
                            ResponseType responseType = ResponseTypeIdentifier.getResponseType(response);
                            if (responseType.equals(ResponseType.ARRAY)) {
                            } else if (responseType.equals(ResponseType.DATA_IS_SUBFIELD_OF_ITEMS)) {
                                responses.add(extractResponseFromSubfieldsOfItems(response));
                            } else {
                                responses.add(response);
                            }
                            logger.debug("Entry: {}, content: {}", entry.getName(), entryContent);
                        }
                    } catch (IOException ex) {
                        logger.error("Error reading entry", entry.getName());
                        logger.error("Caused by: ", ex);
                    }
                }
            }
        } catch (IOException ex) {
            logger.error("Error reading zip file: {}", target);
            logger.error("Caused by: ", ex);
        }
        return responses;
    }

    private Optional<ResponseDefinition> convertToResponse(ZipEntry entry, int prefixLength) {
        Optional<ResponseDefinition> responseDefinition = Optional.empty();
        String fullPath = entry.getName();
        File file = new File(fullPath);
        String fileName = file.getName();
        boolean notNotifications = !fullPath.contains(UtilStrings.PATH_NOTIFICATIONS);
        boolean requestOrResponseFile = fileName.equals(UtilStrings.RESPONSE_SPECIFICATION_JSON);
        int responseTokenIndex = fullPath.indexOf(Application.RESPONSE_TOKEN);
        if (notNotifications && requestOrResponseFile && responseTokenIndex > -1) {
            final String relativePath = fullPath.substring(prefixLength);
            final String mediaType = mediaTypes.getLongName(file.getParentFile().getName());
            if (fileName.equals(UtilStrings.RESPONSE_SPECIFICATION_JSON)) {
                final NameParser nameParser = new NameParser(nameAndPathManager);
                final String responseName = nameParser.getResponseName(relativePath);
                final boolean doesHaveMultipleResults = computeIfHasMultipleResults(file);
                responseDefinition = Optional.of(new ResponseDefinition(relativePath, responseName, mediaType, doesHaveMultipleResults));
            }
        }
        return responseDefinition;
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

    private ResponseDefinition buildDummyResponseDefinitionFromFile(final File file) {
        final ResponseDefinition response = new ResponseDefinition("", "", "", false);
        try (InputStream fileInputStream = new FileInputStream(file)) {
            final DefinitionParser definitionParser = new DefinitionParser(gson);
            final Set<RawFieldDefinition> rawFieldDefinitions = definitionParser.getDefinitions(fileInputStream, DefinitionParseParameters.RAW_FIELD_PARAMETERS);
            final FieldDefinitionProcessor fieldDefinitionProcessor = new FieldDefinitionProcessor(typeTranslator, nameAndPathManager, missingFieldsAndLinks);
            final Set<FieldDefinition> fieldDefinitions = fieldDefinitionProcessor.parseFieldDefinitions("", rawFieldDefinitions);

            response.addFields(fieldDefinitions);
        } catch (IOException ex) {

        }
        return response;
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
}
