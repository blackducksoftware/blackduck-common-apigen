/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public enum ClassTypeEnum {
    VIEW(UtilStrings.VIEW_BASE_CLASS),
    RESPONSE(UtilStrings.RESPONSE_BASE_CLASS),
    COMPONENT(UtilStrings.COMPONENT_BASE_CLASS),
    ENUM,
    COMMON,
    NON_ENUM_CONTAINING_TYPE,
    NULL;

    private final String importClass;

    ClassTypeEnum(final String importClass) {
        this.importClass = importClass;
    }

    ClassTypeEnum() {
        this(null);
    }

    public Optional<String> getImportClass() {
        return Optional.ofNullable(importClass);
    }

    public String getFormattedValue() {
        return StringUtils.capitalize(this.toString().toLowerCase());
    }

    public boolean isView() {
        return this.equals(VIEW);
    }

    public boolean isResponse() {
        return this.equals(RESPONSE);
    }

    public boolean isComponent() {
        return this.equals(COMPONENT);
    }

    public boolean isEnum() {
        return this.equals(ENUM);
    }

    public boolean isCommon() {
        return this.equals(COMMON);
    }

    public boolean isNotCommonTypeOrEnum() {
        return (!this.isCommon() && !this.isEnum());
    }
}