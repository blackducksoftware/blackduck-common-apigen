package com.synopsys.integration.create.apigen.model;

public class ResponseDefinition {
    private String responseSpecificationPath;
    private String name;
    private String mediaType;

    public ResponseDefinition(String responseSpecificationPath, String name, String mediaType) {
        this.responseSpecificationPath = responseSpecificationPath;
        this.name = name;
        this.mediaType = mediaType;
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

}
