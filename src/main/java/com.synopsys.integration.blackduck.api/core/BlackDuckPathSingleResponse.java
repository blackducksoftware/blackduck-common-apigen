package com.synopsys.integration.blackduck.api.core;

public class BlackDuckPathSingleResponse<T extends BlackDuckResponse> extends LinkResponse {
    private final BlackDuckPath blackDuckPath;
    private final Class<T> responseClass;

    public BlackDuckPathSingleResponse(final BlackDuckPath blackDuckPath, final Class<T> responseClass) {
        this.blackDuckPath = blackDuckPath;
        this.responseClass = responseClass;
    }

    public BlackDuckPath getBlackDuckPath() {
        return blackDuckPath;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

}
