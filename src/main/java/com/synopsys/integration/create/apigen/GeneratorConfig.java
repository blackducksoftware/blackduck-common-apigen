/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratorConfig {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);

    private final String API_PATH_SPECIFICATION_KEY = "APIGEN_SPECIFICATION_API_PATH";
    private final String API_GENERATED_DIRECTORY_PATH_KEY = "APIGEN_GENERATED_DIRECTORY_PATH";
    private final String API_SPECIFICATION_VERSION_KEY = "APIGEN_SPECIFICATION_VERSION";

    private final CommonApiGenProperties properties;

    @Autowired
    public GeneratorConfig(CommonApiGenProperties properties){
        this.properties = properties;
    }

    public String getInputPath() {
        String pathToApiSpec = properties.getPathToApiSpecification().orElse("");
        return StringUtils.defaultIfBlank(System.getenv(API_PATH_SPECIFICATION_KEY), pathToApiSpec);
    }


    public String getOutputPath() {
        String pathToApiGeneratedDirectory = properties.getPathToApiDirectory().orElse("");
        return StringUtils.defaultIfBlank(System.getenv(API_GENERATED_DIRECTORY_PATH_KEY), pathToApiGeneratedDirectory);
    }


    public String getApiSpecificationVersion() {
        String apiSpecVersion = properties.getApiVersion().orElse("");
        return StringUtils.defaultIfBlank(System.getenv(API_SPECIFICATION_VERSION_KEY), apiSpecVersion);
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
