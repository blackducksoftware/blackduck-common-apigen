package com.synopsys.integration.create.apigen.model;

public class FieldData {

    private String path;
    private String type;
    private boolean typeWasOverrided;

    public FieldData(final String path, final String type, final boolean typeWasOverrided) {
        this.path = path;
        this.type = type;
        this.typeWasOverrided = typeWasOverrided;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean typeWasOverrided() {
        return typeWasOverrided;
    }
}
