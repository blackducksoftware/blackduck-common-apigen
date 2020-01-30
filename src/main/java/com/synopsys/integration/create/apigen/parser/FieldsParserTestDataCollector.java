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
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class FieldsParserTestDataCollector {

    public static void writeControlData(final Gson gson, final List<ResponseDefinition> responses) throws IOException {
        writeData(gson, responses, new File(Application.PATH_TO_TEST_RESOURCES, "FieldsParserTestControlData.txt"));
    }

    public static void writeTestData(final Gson gson, final List<ResponseDefinition> responses, File testFile) throws IOException {
        writeData(gson, responses, testFile);
    }

    public static void writeData(final Gson gson, final List<ResponseDefinition> responses, File dataFile) throws IOException {
        final FileWriter writer = new FileWriter(dataFile);
        dataFile.mkdirs();
        writer.write("[");
        final Iterator<ResponseDefinition> responseIterator = responses.iterator();
        while (responseIterator.hasNext()) {
            writer.write(gson.toJson(responseIterator.next()));
            if (responseIterator.hasNext()) {
                writer.write(",\n");
            }
        }
        writer.write("]");
        writer.close();
    }
}
