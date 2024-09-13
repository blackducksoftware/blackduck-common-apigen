/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import org.apache.commons.lang3.StringUtils;

public enum ClassSourceEnum {
    GENERATED,
    MANUAL,
    DEPRECATED,
    TEMPORARY,
    NULL;

    public String getFormattedValue() {
        return StringUtils.capitalize(this.toString().toLowerCase());
    }

    public boolean isGenerated() {
        return this.equals(GENERATED);
    }

    public boolean isManual() {
        return this.equals(MANUAL);
    }

    public boolean isTemporary() {
        return this.equals(TEMPORARY);
    }

    public boolean isDeprecated() {
        return this.equals(DEPRECATED);
    }
}
