package com.synopsys.integration.blackduck.api.core;

/**
 * This will represent a starting point for a REST conversation with Black Duck, such as '/api/codelocations' or '/api/projects'
 */
public class BlackDuckPath {
    private final String path;

    public BlackDuckPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }

}
