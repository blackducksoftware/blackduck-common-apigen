package com.synopsys.integration.blackduck.api.core;

public class LinkStringResponse extends LinkResponse {
    private final String link;

    public LinkStringResponse(final String link, final Class<String> stringClass) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

}
