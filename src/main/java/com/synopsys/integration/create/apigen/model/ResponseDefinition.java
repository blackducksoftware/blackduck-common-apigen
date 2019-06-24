package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

import java.util.*;

public class ResponseDefinition extends Stringable {
    private String responseSpecificationPath;
    private String name;
    private String mediaType;
    private List<FieldDefinition> fields;

    public ResponseDefinition(String responseSpecificationPath, String name, String mediaType) {
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

    public void addField(FieldDefinition field) { fields.add(field); }

    public void addFields(List<FieldDefinition> fieldDefinitionss) { fields.addAll(fieldDefinitionss); }

    /*
    public void printResponseDefinition() {
        System.out.println("\n**********************************************************\n" + name + " : " + mediaType);

        for (FieldDefinition field : fields) {
            field.printFieldDefinition(5);
        }
    }*/

}
