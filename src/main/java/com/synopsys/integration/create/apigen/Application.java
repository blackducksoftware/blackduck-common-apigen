package com.synopsys.integration.create.apigen;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static final String RESPONSE_TOKEN = "Id" + File.separator + "GET" + File.separator;
    public static final String API_SPECIFICATION_VERSION = "api-specification/2019.6.0";
    static final String PATH_TO_GENERATED_FILES_KEY = "BLACKDUCK_COMMON_API_BASE_DIRECTORY";

    static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}