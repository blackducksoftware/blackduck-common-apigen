/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.generation.generators;

import com.synopsys.integration.create.apigen.data.mediatype.MediaTypePathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
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
