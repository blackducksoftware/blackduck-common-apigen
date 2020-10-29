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

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.data.*;
import com.synopsys.integration.create.apigen.exception.NullMediaTypeException;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class GeneratorRunner {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final Gson gson;
    private final NameAndPathManager nameAndPathManager;
    private final ViewGenerator viewGenerator;
    private final DiscoveryGenerator discoveryGenerator;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final MediaVersionGenerator mediaVersionGenerator;
    private final DeprecatedClassGenerator deprecatedClassGenerator;
    private final List<ClassGenerator> generators;
    private final Configuration config;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final GeneratorConfig generatorConfig;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final MediaTypePathManager mediaTypePathManager;
    private final ObjectFactory<ApiGeneratorParser> parserFactory;

    @Autowired
    public GeneratorRunner(MissingFieldsAndLinks missingFieldsAndLinks, Gson gson, NameAndPathManager nameAndPathManager, ViewGenerator viewGenerator, DiscoveryGenerator discoveryGenerator,
                           MediaTypeMapGenerator mediaTypeMapGenerator, MediaVersionGenerator mediaVersionGenerator, DeprecatedClassGenerator deprecatedClassGenerator, List<ClassGenerator> generators,
                           Configuration config, MediaVersionDataManager mediaVersionDataManager, GeneratorConfig generatorConfig, FilePathUtil filePathUtil, GeneratorDataManager generatorDataManager, MediaTypePathManager mediaTypePathManager,
                           ObjectFactory<ApiGeneratorParser> parserFactory) {
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.gson = gson;
        this.nameAndPathManager = nameAndPathManager;
        this.viewGenerator = viewGenerator;
        this.discoveryGenerator = discoveryGenerator;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
        this.mediaVersionGenerator = mediaVersionGenerator;
        this.generators = generators;
        this.config = config;
        this.mediaVersionDataManager = mediaVersionDataManager;
        this.generatorConfig = generatorConfig;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.mediaTypePathManager = mediaTypePathManager;
        this.parserFactory = parserFactory;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        generatorConfig.logConfig();
        String inputPath = generatorConfig.getInputPath();
        File inputDirectory = new File(inputPath);
        if (!inputDirectory.exists()) {
            logger.info(generatorConfig.getInputPath() + " not found");
            System.exit(0);
        }
        ApiParser apiParser = parserFactory.getObject();
        DirectoryWalker directoryWalker = new DirectoryWalker(gson, apiParser);
        List<ResponseDefinition> responses = directoryWalker.parseDirectoryForResponses(generatorConfig.getShowOutput(), generatorConfig.getControlRun(), inputDirectory);
        for (ResponseDefinition response : responses) {
            String responseName = NameParser.getNonVersionedName(response.getName());
            Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(responseName);
            response.addFields(missingFields);

            Set<LinkDefinition> missingLinks = missingFieldsAndLinks.getMissingLinks(responseName);
            response.addLinks(missingLinks);
        }

        generateFiles(responses);

        logger.info(
                "\n******************************\nThere are " + nameAndPathManager.getNullLinkResultClasses().size()
                        + " classes that are referenced as link results in API specs but we have no information about what Object they correspond to: \n");
        for (Map.Entry nullLinkResultClass : nameAndPathManager.getNullLinkResultClasses().entrySet()) {
            logger.info(nullLinkResultClass.getKey() + " - " + nullLinkResultClass.getValue());
        }
    }

    private void generateFiles(List<ResponseDefinition> responses) throws Exception {
        accumulateMediaTypes(responses);
        accumulateGeneratedResponseClassData(responses);
        accumulateApiDiscoveryClassData(responses);
        accumulateMediaTypeDiscoveryClassData();
        accumulateLatestViewAndComponentClassData();
        deprecatedClassGenerator.generateDeprecatedClasses();
        generatorDataManager.writeFiles();
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

    private void accumulateMediaTypeDiscoveryClassData() throws Exception {
        File discoveryBaseDirectory = new File(generatorConfig.getOutputDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        Template discoveryTemplate = config.getTemplate("discoveryTemplate.ftl");
        discoveryGenerator.createDiscoveryFile(discoveryBaseDirectory, discoveryTemplate);
    }

    private void accumulateLatestViewAndComponentClassData() throws Exception {
        Template viewTemplate = config.getTemplate("viewTemplate.ftl");
        Template enumTemplate = config.getTemplate("enumTemplate.ftl");
        mediaVersionGenerator.generateMostRecentViewAndComponentMediaVersions(viewTemplate, enumTemplate, filePathUtil.getOutputPathToViewFiles(), filePathUtil.getOutputPathToResponseFiles(), filePathUtil.getOutputPathToComponentFiles(), filePathUtil.getOutputPathToEnumFiles());
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
