/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen;

import java.io.File;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeneratorConfig {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);

    private final GeneratorPropertiesConfig generatorPropertiesConfig;

    public GeneratorConfig(GeneratorPropertiesConfig generatorPropertiesConfig) {
        this.generatorPropertiesConfig = generatorPropertiesConfig;}

    public String getInputPath() {
        return generatorPropertiesConfig.apiSpecInputPath;
    }

    public String getOutputPath() {
        return generatorPropertiesConfig.generatorOutputPath;
    }

    public String getApiSpecificationVersion() {
        return generatorPropertiesConfig.apiSpecVersion;
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
