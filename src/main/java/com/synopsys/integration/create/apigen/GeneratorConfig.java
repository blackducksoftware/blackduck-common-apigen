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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class GeneratorConfig {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);

    @Value("${api.gen.input.path:}")
    private String inputPath;
    @Value("${api.gen.output.path}")
    private String outputPath;

    @Value("${api.gen.control.run:false}")
    private Boolean controlRun;

    @Value("${api.gen.show.output:false}")
    private Boolean showOutput;

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public Boolean getControlRun() {
        return controlRun;
    }

    public Boolean getShowOutput() {
        return showOutput;
    }

    // taken from SwaggerHub
    public File getOutputDirectory() {
        final String baseDirectory = getOutputPath();
        if (baseDirectory == null) {
            logger.error("Please set Environment variable API_GEN_OUTPUT_PATH or the application property api.gen.output.path to directory in which generated files will live");
            System.exit(0);
        }
        return new File(baseDirectory);
    }

    public void logConfig() {
        logger.info("Config:");
        logger.info("--------------");
        logger.info("Input Path  = {}", getInputPath());
        logger.info("Output Path = {}", getOutputPath());
        logger.info("Control Run = {}", getControlRun());
        logger.info("Show Output = {}", getShowOutput());
    }
}
