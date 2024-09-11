/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import com.blackduck.integration.create.apigen.data.mediatype.MediaTypePathManager;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.generation.FileGenerationData;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import freemarker.template.Template;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MediaTypeMapGenerator {
    private final GeneratorDataManager generatorDataManager;
    private final MediaTypePathManager mediaTypePathManager;

    public MediaTypeMapGenerator(GeneratorDataManager generatorDataManager, MediaTypePathManager mediaTypePathManager) {
        this.generatorDataManager = generatorDataManager;
        this.mediaTypePathManager = mediaTypePathManager;
    }

    public void generateMediaTypeMap(String discoveryDirectoryPath, Template template) {
        Map<String, Object> input = new HashMap<>();

        input.put("package", UtilStrings.GENERATED_DISCOVERY_PACKAGE);
        input.put("mediaTypeExpressions", mediaTypePathManager.getMediaTypeMappings());
        input.put("mediaTypeData", mediaTypePathManager.getMediaTypeData());
        generatorDataManager.addFileData(new FileGenerationData("BlackDuckMediaTypeDiscovery", template, input, discoveryDirectoryPath));
    }
}
