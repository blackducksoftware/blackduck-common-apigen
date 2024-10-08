/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen;

import com.blackduck.integration.create.apigen.data.DuplicateOverrides;
import com.blackduck.integration.create.apigen.data.MissingFieldsAndLinks;
import com.blackduck.integration.create.apigen.data.NameAndPathManager;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.data.mediatype.MediaTypePathManager;
import com.blackduck.integration.create.apigen.data.mediatype.MediaTypes;
import com.blackduck.integration.create.apigen.exception.NullMediaTypeException;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import com.blackduck.integration.create.apigen.generation.finder.FilePathUtil;
import com.blackduck.integration.create.apigen.generation.generators.*;
import com.blackduck.integration.create.apigen.model.ApiSpecification;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.LinkDefinition;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import com.blackduck.integration.create.apigen.parser.ApiGeneratorParser;
import com.blackduck.integration.create.apigen.parser.ApiParser;
import com.blackduck.integration.create.apigen.parser.ApiPathDataPopulator;
import com.blackduck.integration.create.apigen.parser.DuplicateTypeOverrider;
import com.google.gson.Gson;
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
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final Gson gson;
    private final NameAndPathManager nameAndPathManager;
    private final ViewGenerator viewGenerator;
    private final ApiDiscoveryGenerator apiDiscoveryGenerator;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final DeprecatedClassGenerator deprecatedClassGenerator;
    private final List<ClassGenerator> generators;
    private final Configuration config;
    private final GeneratorConfig generatorConfig;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final MediaTypePathManager mediaTypePathManager;
    private final ObjectFactory<ApiGeneratorParser> parserFactory;
    private final DuplicateOverrides duplicateOverrides;
    private final DuplicateTypeOverrider duplicateTypeOverrider;

    @Autowired
    public GeneratorRunner(MissingFieldsAndLinks missingFieldsAndLinks, Gson gson, NameAndPathManager nameAndPathManager, ViewGenerator viewGenerator, ApiDiscoveryGenerator apiDiscoveryGenerator,
                           MediaTypeMapGenerator mediaTypeMapGenerator, DeprecatedClassGenerator deprecatedClassGenerator, List<ClassGenerator> generators,
                           Configuration config, GeneratorConfig generatorConfig, FilePathUtil filePathUtil, GeneratorDataManager generatorDataManager, MediaTypePathManager mediaTypePathManager,
                           ObjectFactory<ApiGeneratorParser> parserFactory, DuplicateOverrides duplicateOverrides, DuplicateTypeOverrider duplicateTypeOverrider) {
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.gson = gson;
        this.nameAndPathManager = nameAndPathManager;
        this.viewGenerator = viewGenerator;
        this.apiDiscoveryGenerator = apiDiscoveryGenerator;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
        this.generators = generators;
        this.config = config;
        this.generatorConfig = generatorConfig;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.mediaTypePathManager = mediaTypePathManager;
        this.parserFactory = parserFactory;
        this.duplicateOverrides = duplicateOverrides;
        this.duplicateTypeOverrider = duplicateTypeOverrider;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        generatorConfig.logConfig();
        File inputDirectory = generatorConfig.getInputDirectory();
        generateFiles(inputDirectory);
    }

    private void generateFiles(File apiSpecificationFile) throws Exception {
        ApiSpecification apiSpecification = new ApiSpecification(apiSpecificationFile);
        MediaTypes mediaTypes = apiSpecification.getMediaTypesFile();
        List<ResponseDefinition> responses = parseResponseDefinitionsFromApiSpecifications(apiSpecificationFile, mediaTypes);
        accumulateMediaTypes(responses);
        accumulateGeneratedResponseClassData(responses, mediaTypes);
        accumulateApiDiscoveryClassData(responses);
        generateDiscoveryClasses();
        deprecatedClassGenerator.generateDeprecatedClasses();
        generatorDataManager.writeFiles();
    }

    private List<ResponseDefinition> parseResponseDefinitionsFromApiSpecifications(File apiSpecification, MediaTypes mediaTypes) {
        ApiParser apiParser = parserFactory.getObject();
        List<ResponseDefinition> responses = apiParser.parseApi(apiSpecification, mediaTypes);
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

    private void accumulateGeneratedResponseClassData(List<ResponseDefinition> responses, MediaTypes mediaTypes) throws Exception {
        Template template = viewGenerator.getTemplate(config);
        for (ResponseDefinition response : responses) {
            if (mediaTypes.getLongNames().contains(response.getMediaType())) {
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
