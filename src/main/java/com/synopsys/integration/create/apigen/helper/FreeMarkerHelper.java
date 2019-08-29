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
package com.synopsys.integration.create.apigen.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;

import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.Generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FreeMarkerHelper {

    // taken from SwaggerHub
    public static File getBaseDirectory() {
        final String baseDirectory = System.getenv(Application.PATH_TO_GENERATED_FILES_KEY);
        if (baseDirectory == null) {
            System.out.println("Please set Environment variable 'BLACKDUCK_COMMON_API_BASE_DIRECTORY' to directory in which generated files will live");
            for (final Map.Entry<String, String> var : System.getenv().entrySet()) {
                System.out.println(var.getKey() + " : " + var.getValue());
            }
            System.exit(0);
        }
        return new File(baseDirectory);
    }

    public Configuration configureFreeMarker() throws URISyntaxException, IOException {
        final Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // Where do we load the templates from:
        final URL templateDirectory = Generator.class.getClassLoader().getResource("templates");
        cfg.setDirectoryForTemplateLoading(new File(templateDirectory.toURI()));
        // Other Settings
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }

    public static void writeFile(final String className, final Template template, final Map<String, Object> input, final String destination) throws Exception {
        if (className.equals("null")) {
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
