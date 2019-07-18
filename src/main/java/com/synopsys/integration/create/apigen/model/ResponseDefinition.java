package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.util.Stringable;

public class ResponseDefinition extends Stringable {
    private final String responseSpecificationPath;
    private final String name;
    private final String mediaType;
    private final List<FieldDefinition> fields;
    private final List<LinkDefinition> links;

    public ResponseDefinition(final String responseSpecificationPath, final String name, final String mediaType) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.name = name;
        this.mediaType = mediaType;
        this.fields = new ArrayList<>();
        this.links = new ArrayList<>();
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

    public void addField(final FieldDefinition fieldDefinition) { fields.add(fieldDefinition); }

    public void addFields(final List<FieldDefinition> fieldDefinitions) { fields.addAll(fieldDefinitions); }

    public List<LinkDefinition> getLinks() { return links; }

    public void addLink(final LinkDefinition linkDefinition) { links.add(linkDefinition); }

    public void addLinks(final List<LinkDefinition> linkDefinitions) { links.addAll(linkDefinitions); }

}
