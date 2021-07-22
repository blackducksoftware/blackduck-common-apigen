/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.parser.zip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ApiParser;
import com.synopsys.integration.create.apigen.parser.file.DirectoryPathParser;
import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.Slf4jIntLogger;
import com.synopsys.integration.util.CommonZipExpander;

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
    public List<ResponseDefinition> parseApi(File targetZipFile) {
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
            responses = directoryPathParser.parseApi(expandedZipDirectory);
        } catch (IOException | ArchiveException | IntegrationException ex) {
            logger.error("Error expanding archive file.", ex);
        } finally {
            FileUtils.deleteQuietly(expandedZipDirectory);
        }
        return responses;
    }
}
