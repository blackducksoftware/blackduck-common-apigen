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
package com.synopsys.integration.create.apigen.generation.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.GeneratorConfig;

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
