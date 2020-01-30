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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class GeneratedClassWriter {
    private final ClassCategories classCategories;

    @Autowired
    public GeneratedClassWriter(final ClassCategories classCategories) {
        this.classCategories = classCategories;
    }

    //    // taken from SwaggerHub
    //    public static File getBaseDirectory() {
    //        final String baseDirectory = generator;
    //        if (baseDirectory == null) {
    //            logger.info("Please set Environment variable API_GEN_OUTPUT_PATH or the application property api.gen.output.path to directory in which generated files will live");
    //            System.exit(0);
    //        }
    //        return new File(baseDirectory);
    //    }

    public void writeFile(String className, final Template template, final Map<String, Object> input, final String destination) throws Exception {
        className = NameParser.stripListAndOptionalNotation(className);
        final ClassTypeEnum classType = classCategories.computeType(className);
        if (classType.isCommon()) {
            return;
        }

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
