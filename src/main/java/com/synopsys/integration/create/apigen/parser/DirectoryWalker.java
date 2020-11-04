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
package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.GeneratorRunner;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class DirectoryWalker {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);
    private final Gson gson;
    private final ApiParser apiParser;

    public DirectoryWalker(final Gson gson, ApiParser apiParser) {
        this.gson = gson;
        this.apiParser = apiParser;
    }

    public List<ResponseDefinition> parseDirectoryForResponses(boolean showOutput, boolean controlRun, File target) throws IOException {
        // Get response-specification.json files from directory
        List<ResponseDefinition> responses = apiParser.parseApi(target);

        // log output
        if (showOutput) {
            logResponses(responses);
        }
        // Write output of FieldsParser to test data file
        if (controlRun) {
            DirectoryPathParserTestDataCollector.writeControlData(gson, responses);
        }
        return responses;
    }

    private void logResponses(Collection<ResponseDefinition> responseDefinitions) {
        for (final ResponseDefinition response : responseDefinitions) {
            // Filter out 'Array' responses
            ResponseType responseType = ResponseTypeIdentifier.getResponseType(response);
            if (!responseType.equals(ResponseType.ARRAY)) {
                logger.info("***********************\n" + gson.toJson(response));
            }
        }
    }

}
