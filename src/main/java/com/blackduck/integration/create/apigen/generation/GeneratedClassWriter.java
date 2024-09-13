/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation;

import com.blackduck.integration.create.apigen.data.ClassCategories;
import com.blackduck.integration.create.apigen.data.ClassTypeEnum;
import com.blackduck.integration.create.apigen.parser.NameParser;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

@Component
public class GeneratedClassWriter {
    private static final Logger logger = LoggerFactory.getLogger(GeneratedClassWriter.class);
    private final ClassCategories classCategories;

    @Autowired
    public GeneratedClassWriter(final ClassCategories classCategories) {
        this.classCategories = classCategories;
    }

    public void writeFile(FileGenerationData fileData) throws Exception {
        String className = fileData.getClassName();
        Template template = fileData.getTemplate();
        Map<String, Object> input = fileData.getInput();
        String destination = fileData.getDestination();

        className = NameParser.stripListAndOptionalNotation(className);
        final ClassTypeEnum classType = classCategories.computeData(className).getType();
        if (classType.isCommon()) {
            return;
        }
        logger.info("Writing class: {} - {}", className, destination);
        final File testFile = new File(destination);
        testFile.mkdirs();
        final Writer fileWriter = new FileWriter(new File(testFile, className + ".java"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }

}
