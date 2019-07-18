package com.synopsys.integration.blackduck.api.core;

public class LinkSingleResponse<T extends BlackDuckResponse> extends LinkResponse {
    private final String link;
    private final Class<T> responseClass;

    public LinkSingleResponse(final String link, final Class<T> responseClass) {
        this.link = link;
        this.responseClass = responseClass;
    }

    public String getLink() {
        return link;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

}

