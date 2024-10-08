/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.finder;

import com.blackduck.integration.create.apigen.GeneratorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilePathUtil {
    public static final String ENUM_DIRECTORY_SUFFIX = "/enumeration";
    public static final String VIEW_DIRECTORY_SUFFIX = "/view";
    public static final String RESPONSE_DIRECTORY_SUFFIX = "/response";
    public static final String COMPONENT_DIRECTORY_SUFFIX = "/component";
    private GeneratorConfig generatorConfig;

    @Autowired
    public FilePathUtil(final GeneratorConfig generatorConfig) {
        this.generatorConfig = generatorConfig;
    }

    public String getOutputPathToViewFiles() {
        return createPathString(VIEW_DIRECTORY_SUFFIX);
    }

    public String getOutputPathToResponseFiles() {
        return createPathString(RESPONSE_DIRECTORY_SUFFIX);
    }

    public String getOutputPathToComponentFiles() {
        return createPathString(COMPONENT_DIRECTORY_SUFFIX);
    }

    public String getOutputPathToEnumFiles() {
        return createPathString(ENUM_DIRECTORY_SUFFIX);
    }

    private String createPathString(String suffix) {
        return String.format("%s%s", generatorConfig.getOutputDirectory().getAbsolutePath(), suffix);
    }
}
