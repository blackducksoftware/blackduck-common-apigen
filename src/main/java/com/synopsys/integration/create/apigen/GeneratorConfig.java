package com.synopsys.integration.create.apigen;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeneratorConfig {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorConfig.class);

    @Value("${api.gen.input.path:}")
    private String inputPath;
    @Value("${api.gen.output.path}")
    private String outputPath;

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
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
}
