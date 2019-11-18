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
package com.synopsys.integration.create.apigen.generation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.GeneratorRunner;
import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.MediaVersionData;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class MediaVersionGenerator {

    private final MediaVersionDataManager mediaVersionDataManager;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final ClassCategories classCategories;
    private final GeneratedClassWriter generatedClassWriter;
    private final NameAndPathManager nameAndPathManager;
    private final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);

    public MediaVersionGenerator(final MediaVersionDataManager mediaVersionDataManager, final MediaTypeMapGenerator mediaTypeMapGenerator, final ClassCategories classCategories, final GeneratedClassWriter generatedClassWriter,
        final NameAndPathManager nameAndPathManager) {
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.classCategories = classCategories;
        this.generatedClassWriter = generatedClassWriter;
        this.nameAndPathManager = nameAndPathManager;
    }

    public void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToViewFiles, final String pathToResponseFiles, final String pathToComponentFiles)
        throws Exception {
        final Collection<MediaVersionData> latestViewMediaVersions = mediaVersionDataManager.getLatestViewMediaVersions().values();
        final Collection<MediaVersionData> latestResponseMediaVersions = mediaVersionDataManager.getLatestResponseMediaVersions().values();
        final Collection<MediaVersionData> latestComponentMediaVersions = mediaVersionDataManager.getLatestComponentMediaVersions().values();

        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, latestViewMediaVersions);
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToResponseFiles, latestResponseMediaVersions);
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToComponentFiles, latestComponentMediaVersions);

        final Set<MediaVersionData> latestMediaVersions = new HashSet<>();
        latestMediaVersions.addAll(latestComponentMediaVersions);
        latestMediaVersions.addAll(latestViewMediaVersions);
        mediaTypeMapGenerator.generateMediaTypeMap(latestMediaVersions);
    }

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToFiles, final Collection<MediaVersionData> latestMediaVersions) throws Exception {
        for (final MediaVersionData latestMediaVersion : latestMediaVersions) {
            final Map<String, Object> input = latestMediaVersion.getInput();
            final String className = latestMediaVersion.getNonVersionedClassName();
            input.put(UtilStrings.CLASS_NAME, className);
            try {
                final ClassTypeEnum classType = classCategories.computeType(className);
                final String importClass = classType.getImportClass().get();

                final String importPath = UtilStrings.CORE_CLASS_PATH_PREFIX + importClass;
                input.put(UtilStrings.IMPORT_PATH, importPath);

                //debug
                if (latestMediaVersion.getVersionedClassName().endsWith("0")) {
                    continue;
                }

                input.put(UtilStrings.PARENT_CLASS, latestMediaVersion.getVersionedClassName());
                generatedClassWriter.writeFile(className, randomTemplate, input, pathToFiles);
            } catch (final NoSuchElementException e) {
                logger.info("Class not categorized");
            }
            nameAndPathManager.addNonLinkClassName(className);
            nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(className));
        }
    }
}
