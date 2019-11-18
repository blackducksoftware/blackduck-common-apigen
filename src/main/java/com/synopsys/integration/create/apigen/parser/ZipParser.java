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
package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.slf4j.Logger;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.Slf4jIntLogger;
import com.synopsys.integration.util.CommonZipExpander;

public class ZipParser {
    private final Logger logger;

    public ZipParser(final Logger logger) {
        this.logger = logger;
    }

    public File getEditedZipRootDirectory(URL url, List<SpecEditData> specEdits) throws URISyntaxException {
        File zipFile = new File(url.toURI());


        return zipFile;
    }

    public File getDirectoryFromZip(File zipFile) throws IntegrationException, ArchiveException, IOException, URISyntaxException {
        String nameOfUnzippedDirectory = zipFile.getAbsolutePath().replace(".zip", "");
        File unzippedDirectory = new File(nameOfUnzippedDirectory);
        unzippedDirectory.mkdirs();
        CommonZipExpander expander = new CommonZipExpander(new Slf4jIntLogger(logger));
        expander.expand(zipFile, unzippedDirectory);
        return unzippedDirectory;
    }

}
