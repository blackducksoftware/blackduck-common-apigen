package com.synopsys.integration.blackduck.api.core;

public class LinkMultipleResponses<T extends BlackDuckResponse> extends LinkResponse {
    private final String link;
    private final Class<T> responseClass;

    public LinkMultipleResponses(final String link, final Class<T> responseClass) {
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
