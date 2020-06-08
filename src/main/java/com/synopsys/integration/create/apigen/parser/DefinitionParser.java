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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.DefinitionParseParameters;
import com.synopsys.integration.create.apigen.model.ThirdPartyDefinition;

public class DefinitionParser {
    private final Gson gson;

    public DefinitionParser(final Gson gson) {
        this.gson = gson;
    }

    public <T extends ThirdPartyDefinition> Set<T> getDefinitions(InputStream definitionInputStream, final DefinitionParseParameters<T> parameters) {
        String jsonText = null;
        try {
            jsonText = IOUtils.toString(definitionInputStream, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final JsonObject json = gson.fromJson(jsonText, JsonObject.class);
        final Set<T> definitions = new HashSet<>();
        if (json != null && json.has(parameters.getJsonField())) {
            for (final JsonElement jsonElement : json.getAsJsonArray(parameters.getJsonField())) {
                final T definition = gson.fromJson(jsonElement, parameters.getResultClass());
                definitions.add(definition);
            }
        }
        return definitions;
    }

}
