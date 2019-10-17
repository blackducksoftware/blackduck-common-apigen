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

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.MediaTypes;
import com.synopsys.integration.create.apigen.definitions.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.definitions.TypeTranslator;
import com.synopsys.integration.create.apigen.generators.ClassGenerator;
import com.synopsys.integration.create.apigen.generators.DiscoveryGenerator;
import com.synopsys.integration.create.apigen.generators.DummyClassGenerator;
import com.synopsys.integration.create.apigen.generators.MediaTypeMapGenerator;
import com.synopsys.integration.create.apigen.generators.MediaVersionGenerator;
import com.synopsys.integration.create.apigen.generators.ViewGenerator;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersions;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ApiPathDataPopulator;
import com.synopsys.integration.create.apigen.parser.DirectoryWalker;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class Generator {
    private final ClassCategories classCategories;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final Gson gson;
    private final MediaTypes mediaTypes;
    private final TypeTranslator typeTranslator;
    private final FreeMarkerHelper freeMarkerHelper;
    private final ImportHelper importHelper;
    private final DataManager dataManager;
    private final ViewGenerator viewGenerator;
    private final DiscoveryGenerator discoveryGenerator;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final DummyClassGenerator dummyClassGenerator;
    private final MediaVersionGenerator mediaVersionGenerator;
    private final List<ClassGenerator> generators;
    private final Configuration config;
    private final MediaVersions mediaVersions;

    @Autowired
    public Generator(final ClassCategories classCategories, final MissingFieldsAndLinks missingFieldsAndLinks, final Gson gson, final MediaTypes mediaTypes, final TypeTranslator typeTranslator,
        final FreeMarkerHelper freeMarkerHelper, final ImportHelper importHelper, final DataManager dataManager, final ViewGenerator viewGenerator, final DiscoveryGenerator discoveryGenerator,
        final MediaTypeMapGenerator mediaTypeMapGenerator, final DummyClassGenerator dummyClassGenerator, final MediaVersionGenerator mediaVersionGenerator, final List<ClassGenerator> generators,
        final Configuration config, final MediaVersions mediaVersions) {
        this.classCategories = classCategories;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.gson = gson;
        this.mediaTypes = mediaTypes;
        this.typeTranslator = typeTranslator;
        this.freeMarkerHelper = freeMarkerHelper;
        this.importHelper = importHelper;
        this.dataManager = dataManager;
        this.viewGenerator = viewGenerator;
        this.discoveryGenerator = discoveryGenerator;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.dummyClassGenerator = dummyClassGenerator;
        this.mediaVersionGenerator = mediaVersionGenerator;
        this.generators = generators;
        this.config = config;
        this.mediaVersions = mediaVersions;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson, mediaTypes, typeTranslator, dataManager);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForResponses(false, false);

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

        System.out.println("\n******************************\nThere are " + dataManager.getRandomLinkClassNames().size() + " classes that are referenced but have no definition in the API specs: \n");
        for (final String randomClassName : dataManager.getRandomLinkClassNames()) {
            System.out.println(randomClassName);
        }

        System.out.println(
            "\n******************************\nThere are " + dataManager.getNullLinkResultClasses().size() + " classes that are referenced as link results in API specs but we have no information about what Object they correspond to: \n");
        for (final Map.Entry nullLinkResultClass : dataManager.getNullLinkResultClasses().entrySet()) {
            System.out.println(nullLinkResultClass.getKey() + " - " + nullLinkResultClass.getValue());
        }
    }

    private void generateFiles(final List<ResponseDefinition> responses, final Template randomTemplate) throws Exception {
        for (final ResponseDefinition response : responses) {
            if (viewGenerator.isApplicable(response)) {
                final Template template = viewGenerator.getTemplate(config);
                viewGenerator.generateClasses(response, template);
            } else {
                System.out.println("Non-applicable response!");
            }
            for (final FieldDefinition field : response.getFields()) {
                generateClasses(field, generators, response.getMediaType());
            }
        }
        final ApiPathDataPopulator apiPathDataPopulator = new ApiPathDataPopulator(dataManager);
        apiPathDataPopulator.populateApiPathData(responses);

        final File discoveryBaseDirectory = new File(FreeMarkerHelper.getBaseDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        final Template discoveryTemplate = config.getTemplate("discoveryTemplate.ftl");
        discoveryGenerator.createDiscoveryFile(discoveryBaseDirectory, discoveryTemplate);

        mediaVersionGenerator.generateMostRecentViewAndComponentMediaVersions(randomTemplate, UtilStrings.PATH_TO_VIEW_FILES, UtilStrings.PATH_TO_COMPONENT_FILES);

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
            generateClasses(subField, generators, responseMediaType);
        }
    }

}
