package com.synopsys.integration.blackduck.api.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * All Hub API JSON Responses should be marshaled to instances of this class.
 */
public class BlackDuckResponse extends BlackDuckComponent {
    // these are transient to prevent gson serialization
    private transient String json;
    private transient JsonElement jsonElement;
    private transient Gson gson;
    private transient JsonNode patch;

    public boolean hasSubclasses() {
        return false;
    }

    public Class<? extends BlackDuckResponse> getSubclass() {
        throw new UnsupportedOperationException("A subclass must implement this with its specific behavior");
    }

    public String getJson() {
        return json;
    }

    public void setJson(final String json) {
        this.json = json;
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }

    public void setJsonElement(final JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(final Gson gson) {
        this.gson = gson;
    }

    public JsonNode getPatch() {
        return patch;
    }

    public void setPatch(final JsonNode patch) {
        this.patch = patch;
    }

}
