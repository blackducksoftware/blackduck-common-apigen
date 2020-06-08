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
package com.synopsys.integration.create.apigen.generation.generators;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.GeneratorRunner;
import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.ImportComparator;
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.MediaVersionData;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class MediaVersionGenerator {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);

    private final MediaVersionDataManager mediaVersionDataManager;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final ClassCategories classCategories;
    private final NameAndPathManager nameAndPathManager;
    private final GeneratorDataManager generatorDataManager;

    public MediaVersionGenerator(MediaVersionDataManager mediaVersionDataManager, MediaTypeMapGenerator mediaTypeMapGenerator, ClassCategories classCategories,
        NameAndPathManager nameAndPathManager, GeneratorDataManager generatorDataManager) {
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.classCategories = classCategories;
        this.nameAndPathManager = nameAndPathManager;
        this.generatorDataManager = generatorDataManager;
    }

    public void generateMostRecentViewAndComponentMediaVersions(Template randomTemplate, String pathToViewFiles, final String pathToResponseFiles, final String pathToComponentFiles)
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

    private void generateMostRecentViewAndComponentMediaVersions(Template randomTemplate, String pathToFiles, Collection<MediaVersionData> latestMediaVersions) throws Exception {
        for (final MediaVersionData latestMediaVersion : latestMediaVersions) {
            final Map<String, Object> input = latestMediaVersion.getInput();
            final String className = latestMediaVersion.getNonVersionedClassName();
            input.put(UtilStrings.CLASS_NAME, className);
            try {
                Collection<String> oldImports = (Collection<String>) input.get("imports");
                //List<String> newImports = removeNonLinkRelatedImports(oldImports);
                //input.put("imports", newImports);
                final ClassTypeEnum classType = classCategories.computeType(className);
                final String importClass = classType.getImportClass().get();

                final String importPath = UtilStrings.CORE_CLASS_PATH_PREFIX + importClass;
                input.put(UtilStrings.IMPORT_PATH, importPath);

                if (latestMediaVersion.getVersionedClassName().endsWith("0")) {
                    continue;
                }

                input.put(UtilStrings.PARENT_CLASS, latestMediaVersion.getVersionedClassName());
                generatorDataManager.addFileData(new FileGenerationData(className, randomTemplate, input, pathToFiles));
            } catch (final NoSuchElementException e) {
                logger.info(className + " not categorized in ClassCategories");
            }
            nameAndPathManager.addNonLinkClassName(className);
            nameAndPathManager.addNonLinkClassName(NameParser.getNonVersionedName(className));
        }
    }

    private List<String> removeNonLinkRelatedImports(Collection<String> oldImports) {
        Predicate<String> filterCriteria = item -> StringUtils.containsAny(item,
            ImportFinder.LINK_MULTIPLE_RESPONSES, ImportFinder.LINK_SINGLE_RESPONSE, ImportFinder.LINK_RESPONSE, ImportFinder.LINK_STRING_RESPONSE, InputDataFinder.IMPORT_HASHMAP, InputDataFinder.IMPORT_MAP);
        return oldImports.stream()
                   .filter(filterCriteria)
                   .sorted(ImportComparator.of())
                   .collect(Collectors.toList());
    }
}
