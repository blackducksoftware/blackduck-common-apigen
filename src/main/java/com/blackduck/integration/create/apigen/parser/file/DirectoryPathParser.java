/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser.file;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.data.MissingFieldsAndLinks;
import com.blackduck.integration.create.apigen.data.TypeTranslator;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;
import com.blackduck.integration.create.apigen.model.DefinitionParseParameters;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.LinkDefinition;
import com.blackduck.integration.create.apigen.model.RawFieldDefinition;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import com.blackduck.integration.create.apigen.parser.ApiParser;
import com.blackduck.integration.create.apigen.parser.DefinitionParser;
import com.blackduck.integration.create.apigen.parser.DuplicateTypeIdentifier;
import com.blackduck.integration.create.apigen.parser.FieldDataProcessor;
import com.blackduck.integration.create.apigen.parser.FieldDefinitionProcessor;
import com.blackduck.integration.create.apigen.parser.NameParser;
import com.blackduck.integration.create.apigen.parser.ResponseType;
import com.blackduck.integration.create.apigen.parser.ResponseTypeIdentifier;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DirectoryPathParser implements ApiParser {
    private static final String RESPONSE_ENDPOINT_TOKEN = "GET";

    private final Gson gson;
    private final TypeTranslator typeTranslator;
    private final NameParser nameParser;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final FieldDefinitionProcessor processor;

    @Autowired
    public DirectoryPathParser(Gson gson, TypeTranslator typeTranslator, NameParser nameParser, MissingFieldsAndLinks missingFieldsAndLinks,
        FieldDefinitionProcessor processor) {
        this.gson = gson;
        this.typeTranslator = typeTranslator;
        this.nameParser = nameParser;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.processor = processor;
    }

    @Override
    public List<ResponseDefinition> parseApi(File specificationRootDirectory, MediaTypes mediaTypes) {
        File endpointsPath = new File(specificationRootDirectory, "endpoints");
        File apiPath = new File(endpointsPath, UtilStrings.API);

        List<ResponseDefinition> responseDefinitions = new LinkedList<>();
        List<ResponseDefinition> allResponseDefinitions = parseApiForAllResponses(apiPath, apiPath.getAbsolutePath().length() + 1, mediaTypes);

        // For each response file, parse the JSON for FieldDefinition objects
        for (ResponseDefinition response : allResponseDefinitions) {
            String absolutePath = specificationRootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            File responseSpecificationFile = new File(absolutePath);
            DefinitionParser definitionParser = new DefinitionParser(gson, responseSpecificationFile);
            Set<RawFieldDefinition> fields = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
            Set<LinkDefinition> links = definitionParser.getDefinitions(DefinitionParseParameters.LINK_PARAMETERS);

            response.addFields(processor.processFieldDefinitions(response.getName(), fields));
            response.addLinks(links);

            // Filter out 'Array' responses (those comprised solely of fields like 'items', 'meta', and 'totalCount') and extract data from responses where data is subfield of "items" field
            ResponseType responseType = ResponseTypeIdentifier.getResponseType(response);
            if (responseType.equals(ResponseType.DATA_IS_SUBFIELD_OF_ITEMS)) {
                responseDefinitions.add(extractResponseFromSubfieldsOfItems(response));
            } else if (!responseType.equals(ResponseType.ARRAY)) {
                responseDefinitions.add(response);
            }
        }

        return responseDefinitions;
    }

    private List<ResponseDefinition> parseApiForAllResponses(File parent, int prefixLength, MediaTypes mediaTypes) {
        List<ResponseDefinition> responseDefinitions = new LinkedList<>();
        List<File> files = Arrays.stream(parent.listFiles())
                               .filter(file -> !file.getName().equals("notifications"))
                               .sorted()
                               .collect(Collectors.toList());
        for (File file : files) {
            if (file.getName().equals(RESPONSE_ENDPOINT_TOKEN)) {
                Optional<File> responseSpecificationWithLatestMediaVersion = findResponseSpecificationWithLatestMediaVersion(file);
                if (responseSpecificationWithLatestMediaVersion.isPresent()) {
                    responseDefinitions.add(createResponseDefinitionFromSpecification(responseSpecificationWithLatestMediaVersion.get(), prefixLength, mediaTypes));
                }
            } else if (file.isDirectory()) {
                responseDefinitions.addAll(parseApiForAllResponses(file, prefixLength, mediaTypes));
            }
        }

        return responseDefinitions;
    }

    private Optional<File> findResponseSpecificationWithLatestMediaVersion(File responseEndpoint) {
        int latestMediaVersion = 0;
        File responseSpecificationWithLatestMediaVersion = null;
        for (File mediaTypeDirectory : responseEndpoint.listFiles()) {
            int mediaVersion;
            try {
                mediaVersion = Integer.valueOf(NameParser.getMediaVersionFromMediaType(mediaTypeDirectory.getName()));
            } catch (NumberFormatException e) {
                // If directory does not have a versioned media type, then we will not process it.
                continue;
            }

            if (mediaVersion > latestMediaVersion) {
                latestMediaVersion = mediaVersion;
                for (File specificationFile : mediaTypeDirectory.listFiles()) {
                    if (specificationFile.getName().equals(UtilStrings.RESPONSE_SPECIFICATION_JSON)) {
                        responseSpecificationWithLatestMediaVersion = specificationFile;
                    }
                }
            }
        }
        return Optional.ofNullable(responseSpecificationWithLatestMediaVersion);
    }

    private ResponseDefinition createResponseDefinitionFromSpecification(File responseSpecification, int prefixLength, MediaTypes mediaTypes) {
        String relativePath = responseSpecification.getAbsolutePath().substring(prefixLength);
        String mediaType = mediaTypes.getLongName(responseSpecification.getParentFile().getName());
        String responseName = nameParser.computeResponseName(relativePath);
        boolean doesHaveMultipleResults = computeIfHasMultipleResults(responseSpecification);
        return new ResponseDefinition(relativePath, responseName, mediaType, doesHaveMultipleResults);
    }

    private boolean computeIfHasMultipleResults(File file) {
        /*
            Whether a response has single or multiple results is indicated by the presence of an 'Array' response specification file
            as a sibling to the parent directory of another response specification file.  If such a file does not exist, then that
            response does not have multiple results.
         */
        File parentOfArrayResponse = getParentOfArrayResponse(file);
        if (parentOfArrayResponse == null) {
            return false;
        }
        return containsArrayResponse(parentOfArrayResponse, false);
    }

    private File getParentOfArrayResponse(File file) {
        /*
            The location of an indicating 'Array' response in the file structure is such:
                <endpoint>
                    <endpointId>
                        GET.<media type>
                            <response that has multiple results>
                    GET.<media type>
                        <array response>
         */
        File current = file;
        while (!current.getName().equals(UtilStrings.GET)) {
            current = current.getParentFile();
        }
        File getFile = current;
        current = getFile.getParentFile();
        if (current.getParentFile() != null && !current.getParentFile().getName().equals(UtilStrings.API)) {
            return current.getParentFile();
        } else {
            return null;
        }
    }

    private boolean containsArrayResponse(File file, boolean isGetFileSubDirectory) {
        File[] children = file.listFiles();
        if (children == null) {
            return false;
        }
        boolean foundArrayResponse = false;
        for (File child : children) {
            if (child.getName().equals(UtilStrings.RESPONSE_SPECIFICATION_JSON)) {
                ResponseDefinition potentialArrayResponse = buildDummyResponseDefinitionFromFile(child);
                if (!ResponseTypeIdentifier.getResponseType(potentialArrayResponse).equals(ResponseType.STANDARD)) {
                    return true;
                }
            }
            if (child.getName().equals(UtilStrings.GET) || (isGetFileSubDirectory && !child.getName().equals(UtilStrings.REQUEST_SPECIFICATION_JSON))) {
                foundArrayResponse = containsArrayResponse(child, true);
            }
            if (foundArrayResponse) {
                return true;
            }
        }
        return false;
    }

    private ResponseDefinition extractResponseFromSubfieldsOfItems(ResponseDefinition response) {
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

    private ResponseDefinition buildDummyResponseDefinitionFromFile(File file) {
        DefinitionParser definitionParser = new DefinitionParser(gson, file);
        Set<RawFieldDefinition> rawFieldDefinitions = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
        FieldDefinitionProcessor processor = new FieldDefinitionProcessor(new FieldDataProcessor(typeTranslator, new DuplicateTypeIdentifier(new DuplicateOverrides())), missingFieldsAndLinks);
        Set<FieldDefinition> fieldDefinitions = processor.processFieldDefinitions("", rawFieldDefinitions);
        ResponseDefinition response = new ResponseDefinition("", "", "", false);
        response.addFields(fieldDefinitions);
        return response;
    }
}

