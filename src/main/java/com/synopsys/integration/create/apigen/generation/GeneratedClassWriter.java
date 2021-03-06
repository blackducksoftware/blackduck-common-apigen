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
package com.synopsys.integration.create.apigen.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

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
