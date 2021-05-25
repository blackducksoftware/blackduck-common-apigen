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
