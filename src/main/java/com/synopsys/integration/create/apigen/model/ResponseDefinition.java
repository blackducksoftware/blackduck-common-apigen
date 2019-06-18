package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

import java.util.*;

public class ResponseDefinition extends Stringable {
    private String responseSpecificationPath;
    private String name;
    private String mediaType;
    private Map<String, List<FieldDefinition>> fields;
    private Map<String, String[]> fieldEnums;

    public ResponseDefinition(String responseSpecificationPath, String name, String mediaType) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.name = name;
        this.mediaType = mediaType;
        this.fields = new HashMap<>();
        this.fieldEnums = new HashMap<>();
    }

    public String getResponseSpecificationPath() {
        return responseSpecificationPath;
    }

    public String getName() {
        return name;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Map<String, List<FieldDefinition>> getFields() { return fields; }

    public void addField(String name, FieldDefinition field) { fields.get(name).add(field); }

    public void addFields(Map<String, List<FieldDefinition>> fields) { fields.putAll(fields); }

    public Map<String, String[]> getFieldEnums() { return fieldEnums; }

    public void addFieldEnum(String name, String fieldEnum) { Arrays.asList(fieldEnums.get(name)).add(fieldEnum); }

    public void addFieldEnums(Map<String, String[]> fieldEnums) { fieldEnums.putAll(fieldEnums); }

}
