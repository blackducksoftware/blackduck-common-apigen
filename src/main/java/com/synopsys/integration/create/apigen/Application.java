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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 To Use: Specify path to API specification source, path to where API output should be generated. If source is in src/main/resources, you need only specify the version of the API.
 Download API specification source at https://artifactory.internal.synopsys.com/ui/repos/tree/General/bds-hub-snapshot%2Fcom%2Fblackducksoftware%2Fhub%2Fapi-specification
 If you wish to see a maintenance report, specify where you'd like the report to be generated.
 */

@SpringBootApplication
@Configuration
public class Application {
    static final String PATH_TO_API_SPECIFICATION = "";
    static final String PATH_TO_API_OUTPUT = "";
    static final String PATH_TO_MAINTENANCE_REPORT = "";
    static final String API_SPECIFICATION_VERSION = "";
    private static final String FREEMARKER_TEMPLATE_DIRECTORY_NAME = "templates";

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Gson gson() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }

    @Bean
    public freemarker.template.Configuration configuration() throws IOException {
        final freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        File templateDirectory = pathMatchingResourcePatternResolver().getResource(FREEMARKER_TEMPLATE_DIRECTORY_NAME).getFile();
        cfg.setDirectoryForTemplateLoading(templateDirectory);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }

    @Bean
    public PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver(Application.class.getClassLoader());
    }

}
