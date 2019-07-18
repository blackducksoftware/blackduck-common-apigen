package com.synopsys.integration.create.apigen.model;

public class LinkDefinition {

    private final String rel;
    private final boolean optional;

    public LinkDefinition(final String rel, final boolean optional) {
        this.rel = rel;
        this.optional = optional;
    }

    public String getRel() {
        return rel;
    }

    public boolean isOptional() {
        return optional;
    }

}
