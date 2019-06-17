package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

public class ResponseDefinition extends Stringable {
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
