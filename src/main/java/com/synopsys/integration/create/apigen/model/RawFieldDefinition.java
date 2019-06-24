package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawFieldDefinition {

    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<RawFieldDefinition> fields;
    private final Map<String, String[]> fieldEnums;

    public RawFieldDefinition(String path, String type, boolean optional, List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new ArrayList<>();
        this.fieldEnums = new HashMap<>();
    }

    public RawFieldDefinition(String path, String type, boolean optional) {
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

    public Map<String, String[]> getFieldEnums() { return fieldEnums; }

    @Override
    public String toString() {
        return "path: " + path + " type: " + type + " optional: " + optional + " fields: " + fields;
    }
}
