/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.model;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class JavaStrings {
    private final String label;
    private final String constant;

    public JavaStrings(String value, String constantSuffix) {
        label = Arrays
                        .stream(StringUtils.split(value, "-"))
                        .map(StringUtils::capitalize)
                        .collect(Collectors.joining());
        constant = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(label), "_").toUpperCase() + "_" + constantSuffix;
    }

    public String getLabel() {
        return label;
    }

    public String getConstant() {
        return constant;
    }

}
