/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen;

import java.io.File;
import java.io.IOException;
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
 * To Use: Specify path to API specification source at {@link #PATH_TO_API_SPECIFICATION}, path to where API output should be generated at {@link #PATH_TO_API_GENERATED_DIRECTORY}.
 * If source is in src/main/resources, you need only specify the version of the API at {@link #API_SPECIFICATION_VERSION}.
 * Download API specification source at https://artifactory.internal.synopsys.com/ui/repos/tree/General/bds-hub-snapshot%2Fcom%2Fblackducksoftware%2Fhub%2Fapi-specification
 */

@SpringBootApplication
@Configuration
public class Application {
    private static final String FREEMARKER_TEMPLATE_DIRECTORY_NAME = "templates";

    public static void main(String[] args) {
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

    @Bean
    public static CommonApiGenProperties commonApiGenProperties(){
        return new CommonApiGenProperties();
    }
}
