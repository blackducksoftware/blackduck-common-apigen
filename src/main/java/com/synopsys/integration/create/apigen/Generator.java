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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassSourceEnum;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;
import com.synopsys.integration.create.apigen.definitions.MediaTypes;
import com.synopsys.integration.create.apigen.definitions.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.definitions.TypeTranslator;
import com.synopsys.integration.create.apigen.generators.ClassGenerator;
import com.synopsys.integration.create.apigen.generators.DiscoveryGenerator;
import com.synopsys.integration.create.apigen.generators.ViewGenerator;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionHelper;
import com.synopsys.integration.create.apigen.helper.RandomClassData;
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
    private final List<ClassGenerator> generators;
    private final Configuration config;

    @Autowired
    public Generator(final ClassCategories classCategories, final MissingFieldsAndLinks missingFieldsAndLinks, final Gson gson, final MediaTypes mediaTypes, final TypeTranslator typeTranslator,
        final FreeMarkerHelper freeMarkerHelper, final ImportHelper importHelper, final DataManager dataManager, final ViewGenerator viewGenerator, final DiscoveryGenerator discoveryGenerator, final List<ClassGenerator> generators,
        final Configuration config) {
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
        this.generators = generators;
        this.config = config;
    }

    @PostConstruct
    public void createGeneratedClasses() throws Exception {
        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson, mediaTypes, typeTranslator, dataManager);
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

        generateMostRecentViewAndComponentMediaVersions(randomTemplate, UtilStrings.PATH_TO_VIEW_FILES, UtilStrings.PATH_TO_COMPONENT_FILES);

        generateDummyClassesForReferencedButUndefinedObjects(randomTemplate);
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

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToViewFiles, final String pathToComponentFiles)
        throws Exception {
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, dataManager.getLatestViewMediaVersions().values());
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToComponentFiles, dataManager.getLatestComponentMediaVersions().values());

        final Set<MediaVersionHelper> latestMediaVersions = new HashSet<>();
        latestMediaVersions.addAll(dataManager.getLatestComponentMediaVersions().values());
        latestMediaVersions.addAll(dataManager.getLatestViewMediaVersions().values());
        generateMediaTypeMap(latestMediaVersions);
    }

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToFiles, final Collection<MediaVersionHelper> latestMediaVersions) throws Exception {
        for (final MediaVersionHelper latestMediaVersion : latestMediaVersions) {
            final Map<String, Object> input = latestMediaVersion.getInput();
            final String className = latestMediaVersion.getNonVersionedClassName();
            input.put(UtilStrings.CLASS_NAME, className);
            input.put(UtilStrings.PARENT_CLASS, latestMediaVersion.getVersionedClassName());
            final ClassTypeEnum classType = classCategories.computeType(className);
            final String importClass = classType.getImportClass().get();
            final String importPath = UtilStrings.CORE_CLASS_PATH_PREFIX + importClass;
            input.put(UtilStrings.IMPORT_PATH, importPath);
            freeMarkerHelper.writeFile(className, randomTemplate, input, pathToFiles);
            dataManager.addNonLinkClassName(className);
            dataManager.addNonLinkClassName(NameParser.getNonVersionedName(className));
        }
    }

    private void generateMediaTypeMap(final Collection<MediaVersionHelper> latestMediaVersions) throws Exception {
        final Map<String, Object> input = new HashMap<>();

        input.put("package", UtilStrings.GENERATED_DISCOVERY_PACKAGE);
        input.put("mostRecentClassVersions", latestMediaVersions);

        final Set<String> imports = new HashSet<>();
        final Set<String> classNames = new HashSet<>();
        for (final MediaVersionHelper helper : latestMediaVersions) {
            classNames.add(helper.getNonVersionedClassName());
        }
        for (final String className : classNames) {
            importHelper.addFieldImports(imports, className);
        }
        input.put("imports", imports);

        final File mediaTypeMapBaseDirectory = new File(FreeMarkerHelper.getBaseDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        freeMarkerHelper.writeFile("MediaTypeDiscovery", config.getTemplate("mediaTypeDiscovery.ftl"), input, mediaTypeMapBaseDirectory.getAbsolutePath());
    }

    private void generateDummyClassesForReferencedButUndefinedObjects(final Template randomTemplate) throws Exception {
        for (final String linkClassName : dataManager.getLinkClassNames()) {
            final ClassSourceEnum classSource = classCategories.computeSource(linkClassName);
            final ClassTypeEnum classType = classCategories.computeType(linkClassName);
            if (!classSource.equals(ClassSourceEnum.MANUAL) && !classSource.equals(ClassSourceEnum.THROWAWAY) && !classType.equals(ClassTypeEnum.COMMON)) {
                final Map<String, Object> randomInput = new HashMap<>();
                randomInput.put(UtilStrings.CLASS_NAME, linkClassName);
                final RandomClassData randomClassData = new RandomClassData(linkClassName, classCategories);
                final String packageName = randomClassData.getPackageName();
                final String destinationSuffix = randomClassData.getDestinationSuffix();
                final String importPath = randomClassData.getImportPath();
                final String parentClass = randomClassData.getParentClass();
                randomInput.put(UtilStrings.PARENT_CLASS, parentClass);
                randomInput.put(UtilStrings.PACKAGE_NAME, packageName);
                randomInput.put(UtilStrings.IMPORT_PATH, importPath);

                if (!dataManager.getNonLinkClassNames().contains(linkClassName) && !dataManager.getRandomLinkClassNames().contains(linkClassName)) {
                    freeMarkerHelper.writeFile(linkClassName, randomTemplate, randomInput, UtilStrings.BLACKDUCK_COMMON_API_BASE_DIRECTORY + destinationSuffix);
                    dataManager.addRandomLinkClassName(linkClassName);
                }
            }
        }

    }

}
