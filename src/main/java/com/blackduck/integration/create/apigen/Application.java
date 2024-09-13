/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * To Use: Specify path to API specification source at {@link #PATH_TO_API_SPECIFICATION}, path to where API output should be generated at {@link #PATH_TO_API_GENERATED_DIRECTORY}.
 * If source is in src/main/resources, you need only specify the version of the API at {@link #API_SPECIFICATION_VERSION}.
 * Download API specification source at https://artifactory.internal.synopsys.com/ui/repos/tree/General/bds-hub-snapshot%2Fcom%2Fblackducksoftware%2Fhub%2Fapi-specification
 */

@SpringBootApplication
@Configuration
public class Application {
    static String PATH_TO_API_SPECIFICATION = "";
    static String PATH_TO_API_GENERATED_DIRECTORY = "";
    static String API_SPECIFICATION_VERSION = "";

    public static String MEDIA_TYPES_CSV_NAME = "minified-media-types.csv";

    public static final String API_PATH_SPECIFICATION_KEY = "APIGEN_SPECIFICATION_API_PATH";
    public static final String API_GENERATED_DIRECTORY_PATH_KEY = "APIGEN_GENERATED_DIRECTORY_PATH";
    public static final String API_SPECIFICATION_VERSION_KEY = "APIGEN_SPECIFICATION_VERSION";
    public static final String MEDIA_TYPES_CSV_NAME_KEY = "MEDIA_TYPES_CSV_NAME";

    private static final String FREEMARKER_TEMPLATE_DIRECTORY_NAME = "templates";

    public static void main(String[] args) {
        PATH_TO_API_SPECIFICATION = StringUtils.defaultIfBlank(System.getenv(API_PATH_SPECIFICATION_KEY), PATH_TO_API_SPECIFICATION);
        PATH_TO_API_GENERATED_DIRECTORY = StringUtils.defaultIfBlank(System.getenv(API_GENERATED_DIRECTORY_PATH_KEY), PATH_TO_API_GENERATED_DIRECTORY);
        API_SPECIFICATION_VERSION = StringUtils.defaultIfBlank(System.getenv(API_SPECIFICATION_VERSION_KEY), API_SPECIFICATION_VERSION);
        MEDIA_TYPES_CSV_NAME = StringUtils.defaultIfBlank(System.getenv(MEDIA_TYPES_CSV_NAME_KEY), MEDIA_TYPES_CSV_NAME);

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public freemarker.template.Configuration configuration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
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
