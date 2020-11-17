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
package com.synopsys.integration.create.apigen;

import com.synopsys.integration.create.apigen.data.*;
import com.synopsys.integration.create.apigen.exception.NullMediaTypeException;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.MaintenanceReportGenerator;
import com.synopsys.integration.create.apigen.generation.generators.*;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.*;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class GeneratorRunner {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);

    private final GeneratorPropertiesConfig generatorPropertiesConfig;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final NameAndPathManager nameAndPathManager;
    private final ViewGenerator viewGenerator;
    private final ApiDiscoveryGenerator apiDiscoveryGenerator;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final DeprecatedClassGenerator deprecatedClassGenerator;
    private final List<ClassGenerator> generators;
    private final Configuration config;
    private final GeneratorConfig generatorConfig;
    private final GeneratorDataManager generatorDataManager;
    private final MediaTypePathManager mediaTypePathManager;
    private final ObjectFactory<ApiGeneratorParser> parserFactory;
    private final DuplicateOverrides duplicateOverrides;
    private final DuplicateTypeOverrider duplicateTypeOverrider;
    private final MaintenanceReportGenerator maintenanceReportGenerator;

    @Autowired
    public GeneratorRunner(GeneratorPropertiesConfig generatorPropertiesConfig, MissingFieldsAndLinks missingFieldsAndLinks, NameAndPathManager nameAndPathManager, ViewGenerator viewGenerator,
        ApiDiscoveryGenerator apiDiscoveryGenerator, MediaTypeMapGenerator mediaTypeMapGenerator, DeprecatedClassGenerator deprecatedClassGenerator, List<ClassGenerator> generators,
        Configuration config, GeneratorConfig generatorConfig, GeneratorDataManager generatorDataManager, MediaTypePathManager mediaTypePathManager,
        ObjectFactory<ApiGeneratorParser> parserFactory, DuplicateOverrides duplicateOverrides, DuplicateTypeOverrider duplicateTypeOverrider, MaintenanceReportGenerator maintenanceReportGenerator) {
        this.generatorPropertiesConfig = generatorPropertiesConfig;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.nameAndPathManager = nameAndPathManager;
        this.viewGenerator = viewGenerator;
        this.apiDiscoveryGenerator = apiDiscoveryGenerator;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
        this.generators = generators;
        this.config = config;
        this.generatorConfig = generatorConfig;
        this.generatorDataManager = generatorDataManager;
        this.mediaTypePathManager = mediaTypePathManager;
        this.parserFactory = parserFactory;
        this.duplicateOverrides = duplicateOverrides;
        this.duplicateTypeOverrider = duplicateTypeOverrider;
        this.maintenanceReportGenerator = maintenanceReportGenerator;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        generatorConfig.logConfig();
        File inputDirectory = generatorConfig.getInputDirectory();
        generateFiles(inputDirectory);
        maintenanceReportGenerator.generateMaintenanceReport(generatorPropertiesConfig.generatorOutputPath, duplicateOverrides, generatorPropertiesConfig.maintenanceReportPath);
    }

    private void generateFiles(File apiSpecification) throws Exception {
        List<ResponseDefinition> responses = parseResponseDefinitionsFromApiSpecifications(apiSpecification);
        accumulateMediaTypes(responses);
        accumulateGeneratedResponseClassData(responses);
        accumulateApiDiscoveryClassData(responses);
        generateDiscoveryClasses();
        deprecatedClassGenerator.generateDeprecatedClasses();
        generatorDataManager.writeFiles();
    }

    private List<ResponseDefinition> parseResponseDefinitionsFromApiSpecifications(File apiSpecification) throws IOException {
        ApiParser apiParser = parserFactory.getObject();
        List<ResponseDefinition> responses = apiParser.parseApi(apiSpecification);
        duplicateTypeOverrider.overrideDuplicateTypes(responses);
        for (ResponseDefinition response : responses) {
            String responseName = response.getName();
            Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(responseName);
            response.addFields(missingFields);

            Set<LinkDefinition> missingLinks = missingFieldsAndLinks.getMissingLinks(responseName);
            response.addLinks(missingLinks);
        }

        return responses;
    }

    private void accumulateMediaTypes(List<ResponseDefinition> responses) throws NullMediaTypeException {
        for (ResponseDefinition response : responses) {
            mediaTypePathManager.addMapping(response);
        }
    }

    private void accumulateGeneratedResponseClassData(List<ResponseDefinition> responses) throws Exception {
        Template template = viewGenerator.getTemplate(config);
        for (ResponseDefinition response : responses) {
            if (viewGenerator.isApplicable(response)) {
                viewGenerator.generateClasses(response, template);
            } else {
                logger.info("Non-applicable response!");
            }
            for (FieldDefinition field : response.getFields()) {
                generateClasses(field, generators, response.getMediaType());
            }
        }
    }

    private void accumulateApiDiscoveryClassData(List<ResponseDefinition> responses) {
        ApiPathDataPopulator apiPathDataPopulator = new ApiPathDataPopulator(nameAndPathManager);
        apiPathDataPopulator.populateApiPathData(responses);
    }

    private void generateDiscoveryClasses() throws IOException {
        File discoveryDirectory = new File(generatorConfig.getOutputDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);

        Template discoveryTemplate = config.getTemplate("discoveryTemplate.ftl");
        apiDiscoveryGenerator.createDiscoveryFile(discoveryDirectory.getAbsolutePath(), discoveryTemplate);

        Template blackDuckMediaTypeDiscoveryTemplate = config.getTemplate("blackDuckMediaTypeDiscovery.ftl");
        mediaTypeMapGenerator.generateMediaTypeMap(discoveryDirectory.getAbsolutePath(), blackDuckMediaTypeDiscoveryTemplate);
    }

    private void generateClasses(FieldDefinition field, List<ClassGenerator> generators, String responseMediaType) throws Exception {
        for (ClassGenerator generator : generators) {
            if (generator.isApplicable(field)) {
                Template template = generator.getTemplate(config);
                generator.generateClass(field, responseMediaType, template);
            }
        }
        for (FieldDefinition subField : field.getSubFields()) {
            if (!field.getType().equals(subField.getType())) {
                generateClasses(subField, generators, responseMediaType);
            }
        }
    }

}
