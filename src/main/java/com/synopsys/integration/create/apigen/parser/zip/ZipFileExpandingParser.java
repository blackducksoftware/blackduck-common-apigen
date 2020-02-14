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
package com.synopsys.integration.create.apigen.parser.zip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.ParsedApiData;
import com.synopsys.integration.create.apigen.parser.ApiParser;
import com.synopsys.integration.create.apigen.parser.directory.DirectoryPathParser;
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
    public ParsedApiData parseApi(File targetZipFile) {
        ParsedApiData data = new ParsedApiData(Collections.emptyList(), Collections.emptyList());
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
            data = directoryPathParser.parseApi(expandedZipDirectory);
        } catch (IOException | ArchiveException | IntegrationException ex) {
            logger.error("Error expanding archive file.", ex);
        } finally {
            FileUtils.deleteQuietly(expandedZipDirectory);
        }
        return data;
    }
}
