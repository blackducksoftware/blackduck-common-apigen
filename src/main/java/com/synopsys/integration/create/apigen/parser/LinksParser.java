package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

public class LinksParser {

    private final String LINKS = "links";

    public LinksParser() { }

    public List<LinkDefinition> getLinksAsLinkDefinition(final Gson gson, final File file) {
        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final JsonObject json = gson.fromJson(jsonText, JsonObject.class);
        final List<LinkDefinition> linkDefinitions = new ArrayList<>();
        if (json != null && json.has(LINKS)) {
            for (final JsonElement jsonElement : json.getAsJsonArray(LINKS)) {
                final LinkDefinition linkDefinition = gson.fromJson(jsonElement, LinkDefinition.class);
                linkDefinitions.add(linkDefinition);
            }
        }
        return linkDefinitions;
    }

}
