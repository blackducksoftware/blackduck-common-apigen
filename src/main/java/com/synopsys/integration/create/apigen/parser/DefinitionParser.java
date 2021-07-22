/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.DefinitionParseParameters;
import com.synopsys.integration.create.apigen.model.ThirdPartyDefinition;

public class DefinitionParser {
    private final Gson gson;
    private final File definitionFile;

    public DefinitionParser(final Gson gson, final File definitionFile) {
        this.gson = gson;
        this.definitionFile = definitionFile;
    }

    public <T extends ThirdPartyDefinition> Set<T> getDefinitions(final DefinitionParseParameters<T> parameters) {
        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(definitionFile, StandardCharsets.UTF_8);
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
