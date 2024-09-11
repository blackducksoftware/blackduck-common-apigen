/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

public class ApiPathData {
    public final String path;
    public final String javaConstant;
    public final String javaLabel;
    public final String urlResponseClass;
    public final String urlResponseMethod;
    public final String resultClass;
    public final boolean hasMultipleResults;

    public ApiPathData(final String path, final String resultClass, final boolean hasMultipleResults) {
        this.path = "/api/" + path;
        JavaStrings javaStrings = new JavaStrings(path, "PATH");
        this.javaConstant = javaStrings.getConstant();
        this.javaLabel = javaStrings.getLabel();
        this.resultClass = resultClass;
        this.hasMultipleResults = hasMultipleResults;
        if (this.hasMultipleResults) {
            this.urlResponseClass = "UrlMultipleResponses";
            this.urlResponseMethod = "metaMultipleResponses";
        } else {
            this.urlResponseClass = "UrlSingleResponse";
            this.urlResponseMethod = "metaSingleResponse";
        }
    }

    public String getPath() {
        return path;
    }

    public String getJavaConstant() {
        return javaConstant;
    }

    public String getJavaLabel() {
        return javaLabel;
    }

    public String getUrlResponseClass() {
        return urlResponseClass;
    }

    public String getUrlResponseMethod() {
        return urlResponseMethod;
    }

    public String getResultClass() {
        return resultClass;
    }

    public boolean hasMultipleResults() {
        return hasMultipleResults;
    }

}
