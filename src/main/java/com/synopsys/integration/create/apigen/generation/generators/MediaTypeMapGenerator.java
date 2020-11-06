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

import com.synopsys.integration.create.apigen.GeneratorConfig;
import com.synopsys.integration.create.apigen.data.ImportComparator;
import com.synopsys.integration.create.apigen.data.MediaTypePathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;

import freemarker.template.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MediaTypeMapGenerator {

    private final ImportFinder importFinder;
    private final Configuration config;
    private final GeneratorConfig generatorConfig;
    private final GeneratorDataManager generatorDataManager;
    private final MediaTypePathManager mediaTypePathManager;

    public MediaTypeMapGenerator(ImportFinder importFinder, Configuration config, GeneratorConfig generatorConfig, GeneratorDataManager generatorDataManager, MediaTypePathManager mediaTypePathManager) {
        this.importFinder = importFinder;
        this.config = config;
        this.generatorConfig = generatorConfig;
        this.generatorDataManager = generatorDataManager;
        this.mediaTypePathManager = mediaTypePathManager;
    }

    public void generateMediaTypeMap(Set<MediaVersionData> latestMediaVersions) throws Exception {
        Map<String, Object> input = new HashMap<>();

        input.put("package", UtilStrings.GENERATED_DISCOVERY_PACKAGE);
        List<MediaVersionData> sortedLatestMediaVersions = latestMediaVersions.stream().collect(Collectors.toList());

        Set<String> imports = new HashSet<>();
        Set<String> classNames = new HashSet<>();
        for (MediaVersionData helper : sortedLatestMediaVersions) {
            classNames.add(helper.getNonVersionedClassName());
        }
        for (String className : classNames) {
            imports.addAll(importFinder.findFieldImports(className, false));
        }
        List sortedImports = imports.stream()
                .sorted(ImportComparator.of())
                .collect(Collectors.toList());
        input.put("imports", sortedImports);

        input.put("mediaTypeExpressions", mediaTypePathManager.getMediaTypeMappings());
        input.put("mediaTypeData", mediaTypePathManager.getMediaTypeData());
        File mediaTypeMapBaseDirectory = new File(generatorConfig.getOutputDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        generatorDataManager.addFileData(new FileGenerationData("BlackDuckMediaTypeDiscovery", config.getTemplate("blackDuckMediaTypeDiscovery.ftl"), input, mediaTypeMapBaseDirectory.getAbsolutePath()));
    }
}
