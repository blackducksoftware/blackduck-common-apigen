/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.parser.zip;

import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import com.blackduck.integration.create.apigen.parser.ApiParser;
import com.blackduck.integration.create.apigen.parser.file.DirectoryPathParser;
import com.blackduck.integration.exception.IntegrationException;
import com.blackduck.integration.log.Slf4jIntLogger;
import com.blackduck.integration.util.CommonZipExpander;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ZipFileExpandingParser implements ApiParser {
    private static final Logger logger = LoggerFactory.getLogger(ZipFileExpandingParser.class);
    private DirectoryPathParser directoryPathParser;

    @Autowired
    public ZipFileExpandingParser(DirectoryPathParser directoryPathParser) {
        this.directoryPathParser = directoryPathParser;
    }

    @Override
    public List<ResponseDefinition> parseApi(File targetZipFile, MediaTypes mediaTypes) {
        List<ResponseDefinition> responses = Collections.emptyList();
        File expandedZipDirectory = null;
        try {
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.OWNER_EXECUTE);

            expandedZipDirectory = Files.createTempDirectory("apigen_tmpzip", PosixFilePermissions.asFileAttribute(permissions)).toFile();
            Slf4jIntLogger intLogger = new Slf4jIntLogger(logger);
            CommonZipExpander expander = new CommonZipExpander(intLogger);
            expander.expand(targetZipFile, expandedZipDirectory);
            responses = directoryPathParser.parseApi(expandedZipDirectory, mediaTypes);
        } catch (IOException | ArchiveException | IntegrationException ex) {
            logger.error("Error expanding archive file.", ex);
        } finally {
            FileUtils.deleteQuietly(expandedZipDirectory);
        }
        return responses;
    }
}
