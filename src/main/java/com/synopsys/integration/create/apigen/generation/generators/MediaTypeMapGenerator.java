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

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.GeneratorConfig;
import com.synopsys.integration.create.apigen.data.MediaTypePathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.model.MediaVersionData;

import freemarker.template.Configuration;

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

    public void generateMediaTypeMap(final Set<MediaVersionData> latestMediaVersions) throws Exception {
        final Map<String, Object> input = new HashMap<>();

        input.put("package", UtilStrings.GENERATED_DISCOVERY_PACKAGE);
        final List<MediaVersionData> sortedLatestMediaVersions = latestMediaVersions.stream().collect(Collectors.toList());

        final Set<String> imports = new HashSet<>();
        final Set<String> classNames = new HashSet<>();
        for (final MediaVersionData helper : sortedLatestMediaVersions) {
            classNames.add(helper.getNonVersionedClassName());
        }
        for (final String className : classNames) {
            importFinder.addFieldImports(imports, className, false);
        }
        input.put("imports", imports);

        input.put("mediaTypeExpressions", mediaTypePathManager.getMediaTypeMappings());

        final File mediaTypeMapBaseDirectory = new File(generatorConfig.getOutputDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        generatorDataManager.addFileData(new FileGenerationData("MediaTypeDiscovery", config.getTemplate("mediaTypeDiscovery.ftl"), input, mediaTypeMapBaseDirectory.getAbsolutePath()));
    }
}
