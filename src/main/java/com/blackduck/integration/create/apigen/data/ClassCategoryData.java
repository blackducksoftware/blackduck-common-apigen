/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

public class ClassCategoryData {
    private final String className;
    private final ClassTypeEnum type;
    private final ClassSourceEnum source;

    public ClassCategoryData(String className, ClassTypeEnum type, ClassSourceEnum source) {
        this.className = className;
        this.type = type;
        this.source = source;
    }

    public String getClassName() {
        return className;
    }

    public ClassTypeEnum getType() {
        return type;
    }

    public ClassSourceEnum getSource() {
        return source;
    }

}
