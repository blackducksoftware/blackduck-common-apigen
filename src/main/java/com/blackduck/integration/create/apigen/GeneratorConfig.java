/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;

@Component
public class GeneratorConfig {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);

    public String getInputPath() {
        return Application.PATH_TO_API_SPECIFICATION;
    }

    public String getOutputPath() {
        return Application.PATH_TO_API_GENERATED_DIRECTORY;
    }

    public String getApiSpecificationVersion() {
        return Application.API_SPECIFICATION_VERSION;
    }

    public File getInputDirectory() throws URISyntaxException {
        File inputDirectory = null;
        String inputPath = getInputPath();
        if (StringUtils.isNotBlank(inputPath)) {
            inputDirectory = new File(inputPath);
        } else if (StringUtils.isNotBlank(getApiSpecificationVersion())) {
            File inputFromResources = new File(GeneratorRunner.class.getClassLoader().getResource("api-specification/" + getApiSpecificationVersion()).toURI());
            if (inputFromResources.exists()) {
                inputDirectory = inputFromResources;
            } else {
                //TODO - pull API specification from artifactory
            }
        }
        validateDirectory(inputDirectory, String.format("Could not find input directory at %s", getInputPath()));
        return inputDirectory;
    }

    public File getOutputDirectory() {
        File outputDirectory = null;
        final String outputPath = getOutputPath();
        if (StringUtils.isNotBlank(outputPath)) {
            outputDirectory = new File(outputPath);
            outputDirectory.mkdirs();
        }
        validateDirectory(outputDirectory, String.format("Could not find output directory at %s", getOutputPath()));
        return outputDirectory;
    }

    private void validateDirectory(File directory, String errorMessage) {
        if (directory == null || !directory.exists()) {
            logger.info(errorMessage);
            System.exit(0);
        }
    }

    public void logConfig() {
        logger.info("Config:");
        logger.info("--------------");
        logger.info("Input Path  = {}", getInputPath());
        logger.info("Output Path = {}", getOutputPath());
    }

}
