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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.DefinitionParseParameters;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class DirectoryWalker {
    private final File rootDirectoryFile;
    private final Gson gson;
    private final MediaTypes mediaTypes;
    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;

    public DirectoryWalker(final File rootDirectory, final Gson gson, final MediaTypes mediaTypes, final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager) {
        this.rootDirectoryFile = rootDirectory;
        this.gson = gson;
        this.mediaTypes = mediaTypes;
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
    }

    public List<ResponseDefinition> parseDirectoryForResponses(final boolean showOutput, final boolean controlRun) throws IOException {
        final ResponseParser responseParser = new ResponseParser(mediaTypes, gson, typeTranslator);
        final FieldDefinitionProcessor processor = new FieldDefinitionProcessor(typeTranslator);
        boolean actuallyShowOutput = showOutput;

        // Get response-specification.json files from directory
        final List<ResponseDefinition> responseDefinitions = responseParser.parseResponses(rootDirectoryFile);
        final List<ResponseDefinition> finalResponseDefinitions = new ArrayList<>();

        // For each response file, parse the JSON for FieldDefinition objects
        for (final ResponseDefinition response : responseDefinitions) {
            final String absolutePath = rootDirectoryFile.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            final File responseSpecificationFile = new File(absolutePath);
            final DefinitionParser definitionParser = new DefinitionParser(gson, responseSpecificationFile);

            final List<RawFieldDefinition> fields = definitionParser.getDefinitions(DefinitionParseParameters.RAW_FIELD_PARAMETERS);
            final List<LinkDefinition> links = definitionParser.getDefinitions(DefinitionParseParameters.LINK_PARAMETERS);
            response.addFields(processor.parseFieldDefinitions(response.getName(), fields));
            response.addLinks(links);

            // Filter out 'Array' responses
            if (ArrayResponseIdentifier.isArrayResponse(response)) {
                actuallyShowOutput = false;
            } else {
                finalResponseDefinitions.add(response);
            }

            if (actuallyShowOutput) {
                System.out.println("***********************\n" + gson.toJson(response));
            }
            actuallyShowOutput = showOutput;

        }
        // Write output of FieldsParser to test data file
        if (controlRun) {
            FieldsParserTestDataCollector.writeControlData(gson, finalResponseDefinitions);
        }
        return finalResponseDefinitions;
    }

}
