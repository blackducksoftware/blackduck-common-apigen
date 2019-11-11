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
package com.synopsys.integration.create.apigen;

import static java.lang.System.exit;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.DeprecatedClassGenerator;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MediaVersionDataManager;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.ClassGenerator;
import com.synopsys.integration.create.apigen.generation.DiscoveryGenerator;
import com.synopsys.integration.create.apigen.generation.DummyClassGenerator;
import com.synopsys.integration.create.apigen.generation.GeneratedClassWriter;
import com.synopsys.integration.create.apigen.generation.MediaTypeMapGenerator;
import com.synopsys.integration.create.apigen.generation.MediaVersionGenerator;
import com.synopsys.integration.create.apigen.generation.ViewGenerator;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ApiPathDataPopulator;
import com.synopsys.integration.create.apigen.parser.DirectoryWalker;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class GeneratorRunner {
    private final ClassCategories classCategories;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final Gson gson;
    private final MediaTypes mediaTypes;
    private final TypeTranslator typeTranslator;
    private final GeneratedClassWriter generatedClassWriter;
    private final ImportFinder importFinder;
    private final NameAndPathManager nameAndPathManager;
    private final ViewGenerator viewGenerator;
    private final DiscoveryGenerator discoveryGenerator;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final DummyClassGenerator dummyClassGenerator;
    private final MediaVersionGenerator mediaVersionGenerator;
    private final DeprecatedClassGenerator deprecatedClassGenerator;
    private final List<ClassGenerator> generators;
    private final Configuration config;
    private final MediaVersionDataManager mediaVersionDataManager;
    private final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);

    public static int classesGenerated = 0;

    public static void incrementClassesGenerated() { classesGenerated++; }

    @Autowired
    public GeneratorRunner(final ClassCategories classCategories, final MissingFieldsAndLinks missingFieldsAndLinks, final Gson gson, final MediaTypes mediaTypes, final TypeTranslator typeTranslator,
        final GeneratedClassWriter generatedClassWriter, final ImportFinder importFinder, final NameAndPathManager nameAndPathManager, final ViewGenerator viewGenerator, final DiscoveryGenerator discoveryGenerator,
        final MediaTypeMapGenerator mediaTypeMapGenerator, final DummyClassGenerator dummyClassGenerator, final MediaVersionGenerator mediaVersionGenerator, final DeprecatedClassGenerator deprecatedClassGenerator, final List<ClassGenerator> generators,
        final Configuration config, final MediaVersionDataManager mediaVersionDataManager) {
        this.classCategories = classCategories;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.gson = gson;
        this.mediaTypes = mediaTypes;
        this.typeTranslator = typeTranslator;
        this.generatedClassWriter = generatedClassWriter;
        this.importFinder = importFinder;
        this.nameAndPathManager = nameAndPathManager;
        this.viewGenerator = viewGenerator;
        this.discoveryGenerator = discoveryGenerator;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
        this.dummyClassGenerator = dummyClassGenerator;
        this.mediaVersionGenerator = mediaVersionGenerator;
        this.generators = generators;
        this.config = config;
        this.mediaVersionDataManager = mediaVersionDataManager;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        final URL rootDirectory = GeneratorRunner.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson, mediaTypes, typeTranslator, nameAndPathManager, missingFieldsAndLinks);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForResponses(false, false);

        //exit(0);

        for (final ResponseDefinition response : responses) {
            final String responseName = NameParser.getNonVersionedName(response.getName());
            final List<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(responseName);
            if (missingFields.size() > 0) {
                response.addFields(missingFields);
            }
            final List<LinkDefinition> missingLinks = missingFieldsAndLinks.getMissingLinks(responseName);
            if (missingLinks.size() > 0) {
                response.addLinks(missingLinks);
            }
        }
        final Template randomTemplate = config.getTemplate("randomTemplate.ftl");

        generateFiles(responses, randomTemplate);

        logger.info("\n******************************\nThere are " + nameAndPathManager.getRandomLinkClassNames().size() + " classes that are referenced but have no data in the API specs: \n");
        for (final String randomClassName : nameAndPathManager.getRandomLinkClassNames()) {
            logger.info(randomClassName);
        }

        logger.info(
            "\n******************************\nThere are " + nameAndPathManager.getNullLinkResultClasses().size()
                + " classes that are referenced as link results in API specs but we have no information about what Object they correspond to: \n");
        for (final Map.Entry nullLinkResultClass : nameAndPathManager.getNullLinkResultClasses().entrySet()) {
            logger.info(nullLinkResultClass.getKey() + " - " + nullLinkResultClass.getValue());
        }

        logger.info("Classes Generated: " + classesGenerated);
    }

    private void generateFiles(final List<ResponseDefinition> responses, final Template randomTemplate) throws Exception {
        for (final ResponseDefinition response : responses) {
            if (viewGenerator.isApplicable(response)) {
                final Template template = viewGenerator.getTemplate(config);
                viewGenerator.generateClasses(response, template);
            } else {
                logger.info("Non-applicable response!");
            }
            for (final FieldDefinition field : response.getFields()) {
                generateClasses(field, generators, response.getMediaType());
            }
        }
        final ApiPathDataPopulator apiPathDataPopulator = new ApiPathDataPopulator(nameAndPathManager);
        apiPathDataPopulator.populateApiPathData(responses);

        final File discoveryBaseDirectory = new File(GeneratedClassWriter.getBaseDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        final Template discoveryTemplate = config.getTemplate("discoveryTemplate.ftl");
        discoveryGenerator.createDiscoveryFile(discoveryBaseDirectory, discoveryTemplate);

        mediaVersionGenerator.generateMostRecentViewAndComponentMediaVersions(randomTemplate, UtilStrings.PATH_TO_VIEW_FILES, UtilStrings.PATH_TO_COMPONENT_FILES);

        deprecatedClassGenerator.generateDeprecatedClasses();

        dummyClassGenerator.generateDummyClassesForReferencedButUndefinedObjects(randomTemplate);
    }

    private void generateClasses(final FieldDefinition field, final List<ClassGenerator> generators, final String responseMediaType) throws Exception {
        for (final ClassGenerator generator : generators) {
            if (generator.isApplicable(field)) {
                final Template template = generator.getTemplate(config);
                generator.generateClass(field, responseMediaType, template);
            }
        }
        for (final FieldDefinition subField : field.getSubFields()) {
            if (!field.getType().equals(subField.getType())) {
                generateClasses(subField, generators, responseMediaType);
            }
        }
    }

}
