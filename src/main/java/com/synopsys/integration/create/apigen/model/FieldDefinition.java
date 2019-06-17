package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

import java.util.List;

public class FieldDefinition extends Stringable {
    private String path;
    private String type;
    private boolean optional;
    private List<String> allowedValues;

    public FieldDefinition(String path, String type, boolean optional, List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
    }

    public FieldDefinition(String path, String type, boolean optional) {
        this.path = path;
        this.type = type;
        this.optional = optional;
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

}
