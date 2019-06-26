package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.util.Stringable;

public class ResponseDefinition extends Stringable {
    private final String responseSpecificationPath;
    private final String name;
    private final String mediaType;
    private final List<FieldDefinition> fields;

    public ResponseDefinition(final String responseSpecificationPath, final String name, final String mediaType) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.name = name;
        this.mediaType = mediaType;
        this.fields = new ArrayList<>();
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

    public List<FieldDefinition> getFields() { return fields; }

    public void addField(final FieldDefinition field) { fields.add(field); }

    public void addFields(final List<FieldDefinition> fieldDefinitionss) { fields.addAll(fieldDefinitionss); }

}
