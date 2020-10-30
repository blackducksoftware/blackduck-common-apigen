package com.synopsys.integration.create.apigen.model;

import java.util.List;
import java.util.Set;

public class FirstPassFieldDefinition {

    private String firstPassType;
    private String path;
    private RawFieldDefinition rawFieldDefinition;
    private Set<FirstPassFieldDefinition> subFields;

    public FirstPassFieldDefinition(String firstPassType, String path, RawFieldDefinition rawFieldDefinition) {
        this.firstPassType = firstPassType;
        this.path = path;
        this.rawFieldDefinition = rawFieldDefinition;
    }

    public String getFirstPassType() {
        return firstPassType;
    }

    public String getPath() {
        return path;
    }

    public RawFieldDefinition getRawFieldDefinition() {
        return rawFieldDefinition;
    }

    public Set<FirstPassFieldDefinition> getSubFields() {
        return subFields;
    }

    public void addSubFields(final Set<FirstPassFieldDefinition> subFields) {
        this.subFields.addAll(subFields);
    }
}
