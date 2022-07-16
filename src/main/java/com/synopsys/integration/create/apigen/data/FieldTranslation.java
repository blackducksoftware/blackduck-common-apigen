/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

/***
 * typeWasOverrided will be true if the override is set to replace the original type with another generated type, false if just to modify a verbose or unexpressive name.
 * This is value is passed along during field processing so that, during, field processing, there will not be redundant recursive processing of types
 * eg. A ProjectVersionComponentVersionView has both a securityRiskProfile and a licenseRiskProfile. In the specification for a ProjectVersionComponentVersionView,
 *  each field has an identical definition. Having a SecurityRiskProfileView and a LicenseRiskProfileView that have the same fields would be redundant, so we override them to both just be RiskProfileView's
 */
public class FieldTranslation {
    private String path;
    private String trueType;
    private String apiSpecsType;
    private boolean typeWasOverrided;

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
