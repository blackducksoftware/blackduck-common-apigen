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
package com.synopsys.integration.create.apigen.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.ParsedApiData;
import com.synopsys.integration.create.apigen.parser.directory.DirectoryPathParser;
import com.synopsys.integration.create.apigen.parser.zip.ZipFileExpandingParser;

@Component
public class ApiGeneratorParser implements ApiParser {
    private static final Logger logger = LoggerFactory.getLogger(ApiGeneratorParser.class);
    private DirectoryPathParser directoryPathParser;
    private ZipFileExpandingParser zipFileExpandingParser;

    @Autowired
    public ApiGeneratorParser(DirectoryPathParser directoryPathParser, ZipFileExpandingParser zipFileExpandingParser) {
        this.directoryPathParser = directoryPathParser;
        this.zipFileExpandingParser = zipFileExpandingParser;
    }

    @Override
    public ParsedApiData parseApi(final File target) {
        if (target.isFile()) {
            String format;
            // we need to use an InputStream where inputStream.markSupported() == true
            try (InputStream in = new BufferedInputStream(Files.newInputStream(target.toPath()))) {
                format = ArchiveStreamFactory.detect(in);
                if (ArchiveStreamFactory.ZIP.equals(format)) {
                    logger.info("Parsing ZIP file for definitions: {}", target.getAbsolutePath());
                    return zipFileExpandingParser.parseApi(target);
                }
            } catch (IOException | ArchiveException ex) {
                logger.error("Error processing zip file target.", ex);
            }
        }
        logger.info("Parsing target directory for definitions: {}", target.getAbsolutePath());
        return directoryPathParser.parseApi(target);
    }
}
