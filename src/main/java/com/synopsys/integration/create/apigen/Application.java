package com.synopsys.integration.create.apigen;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static final String CURRENT_API_SPECIFICATION = "/api-specification/2019.6.0";
    public static final String RESPONSE_TOKEN = "Id" + File.separator + "GET" + File.separator;
    public static final String API_SPECIFICATION_VERSION = "api-specification/2019.4.3";
    public static final String PATH_TO_GENERATED_FILES = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/java/com.synopsys.integration.blackduck.api/generated/";

    static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

}