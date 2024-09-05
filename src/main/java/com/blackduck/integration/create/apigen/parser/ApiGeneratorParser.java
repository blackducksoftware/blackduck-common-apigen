/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import com.blackduck.integration.create.apigen.parser.file.DirectoryPathParser;
import com.blackduck.integration.create.apigen.parser.zip.ZipFileExpandingParser;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;

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
    public List<ResponseDefinition> parseApi(final File target, MediaTypes mediaTypes) {
        if (target.isFile()) {
            String format;
            // we need to use an InputStream where inputStream.markSupported() == true
            try (InputStream in = new BufferedInputStream(Files.newInputStream(target.toPath()))) {
                format = ArchiveStreamFactory.detect(in);
                if (ArchiveStreamFactory.ZIP.equals(format)) {
                    logger.info("Parsing ZIP file for definitions: {}", target.getAbsolutePath());
                    return zipFileExpandingParser.parseApi(target, mediaTypes);
                }
            } catch (IOException | ArchiveException ex) {
                logger.error("Error processing zip file target.", ex);
            }
        }
        logger.info("Parsing target directory for definitions: {}", target.getAbsolutePath());
        return directoryPathParser.parseApi(target, mediaTypes);
    }
}
