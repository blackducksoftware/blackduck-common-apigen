/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

public class FieldTranslation {
    private String path;
    private String trueType;
    private String apiSpecsType;
    private boolean typeWasOverrided; // This will be true if the override is set to eplace the original type with another generated type, false if just to modify a verbose or unexpressive name.

    public FieldTranslation(final String path, final String trueType, final String apiSpecsType, final boolean typeWasOverrided) {
        this.path = path;
        this.trueType = trueType;
        this.apiSpecsType = apiSpecsType;
        this.typeWasOverrided = typeWasOverrided;
    }

    public FieldTranslation(final String path, final String trueType, final String apiSpecsType) {
        this.path = path;
        this.trueType = trueType;
        this.apiSpecsType = apiSpecsType;
        this.typeWasOverrided = true;
    }

    public String getPath() {
        return path;
    }

    public String getTrueType() {
        return trueType;
    }

    public String getApiSpecsType() {
        return apiSpecsType;
    }

    public boolean typeWasOverrided() {
        return typeWasOverrided;
    }
}
