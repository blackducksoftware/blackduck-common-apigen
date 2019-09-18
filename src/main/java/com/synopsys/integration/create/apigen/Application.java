/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    //public static final String RESPONSE_TOKEN = "Id" + File.separator + "GET" + File.separator; <-- old token requiring "Id" in path
    public static final String RESPONSE_TOKEN = "GET" + File.separator;
    public static final String API_SPECIFICATION_VERSION = "api-specification/2019.8.0";
    public static final String PATH_TO_GENERATED_FILES_KEY = "BLACKDUCK_COMMON_API_BASE_DIRECTORY";
    public static final String PATH_TO_TEST_RESOURCES = "src/test/resources/";

    static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}