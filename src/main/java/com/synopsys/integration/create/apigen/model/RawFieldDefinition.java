package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RawFieldDefinition {

    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<RawFieldDefinition> fields;

    public RawFieldDefinition(final String path, final String type, final boolean optional, final List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new ArrayList<>();
    }

    public RawFieldDefinition(final String path, final String type, final boolean optional) {
        this(path, type, optional, Collections.emptyList());
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public List<RawFieldDefinition> getSubFields() { return fields; }

    @Override
    public String toString() {
        return "path: " + path + " type: " + type + " optional: " + optional + " fields: " + fields;
    }
}
