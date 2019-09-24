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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.MediaTypes;
import com.synopsys.integration.create.apigen.definitions.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.LinkHelper;
import com.synopsys.integration.create.apigen.helper.LinksAndImportsHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionHelper;
import com.synopsys.integration.create.apigen.helper.RandomClassData;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.DirectoryWalker;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {
    // TODO - Evaluate the scope of these variables!
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String CORE_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.core.";
    public static final String MANUAL_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.manual.";

    public static final String GENERATED_ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.ENUMERATION;
    public static final String GENERATED_VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.VIEW;
    public static final String GENERATED_COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.COMPONENT;
    public static final String GENERATED_RESPONSE_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.RESPONSE;
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String RESPONSE_BASE_CLASS = "BlackDuckResponse";
    public static final String CLASS_NAME = "className";
    public static final String IMPORT_PATH = "importPath";
    public static final String PACKAGE_NAME = "packageName";
    public static final String PARENT_CLASS = "parentClass";
    public static final String MEDIA_TYPE = "mediaType";
    public static final String BLACKDUCK_COMMON_API_BASE_DIRECTORY = FreeMarkerHelper.getBaseDirectory().getAbsolutePath();
    public static final String ENUM_DIRECTORY_SUFFIX = "/enumeration";
    public static final String VIEW_DIRECTORY_SUFFIX = "/view";
    public static final String RESPONSE_DIRECTORY_SUFFIX = "/response";
    public static final String COMPONENT_DIRECTORY_SUFFIX = "/component";

    public static final Set<String> NON_LINK_CLASS_NAMES = new HashSet<>();
    public static final Set<String> LINK_CLASS_NAMES = new HashSet<>();
    public static final List<String> RANDOM_LINK_CLASS_NAMES = new ArrayList<>();
    public static final Map<String, String> NULL_LINK_RESULT_CLASSES = new HashMap<>();

    private static final Map<String, MediaVersionHelper> LATEST_VIEW_MEDIA_VERSIONS = new HashMap<>();
    private static final Map<String, MediaVersionHelper> LATEST_COMPONENT_MEDIA_VERSIONS = new HashMap<>();

    private static final ClassCategories classCategories = new ClassCategories();
    final ImportHelper importHelper = new ImportHelper(classCategories);
    private final MissingFieldsAndLinks MISSING_FIELDS_AND_LINKS = new MissingFieldsAndLinks();

    public static void main(final String[] args) throws Exception {
        final Generator Generator = new Generator();
        final FreeMarkerHelper freeMarkerHelper = new FreeMarkerHelper();
        final Configuration config = freeMarkerHelper.configureFreeMarker();

        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForResponses(false, false);

        for (final ResponseDefinition response : responses) {
            final String responseName = NameParser.getNonVersionedName(response.getName());
            final List<FieldDefinition> missingFields = Generator.MISSING_FIELDS_AND_LINKS.getMissingFields(responseName);
            if (missingFields.size() > 0) {
                response.addFields(missingFields);
            }
            final List<LinkDefinition> missingLinks = Generator.MISSING_FIELDS_AND_LINKS.getMissingLinks(responseName);
            if (missingLinks.size() > 0) {
                response.addLinks(missingLinks);
            }
        }

        final Template enumTemplate = config.getTemplate("enumTemplate.ftl");
        Generator.generateEnumFiles(responses, enumTemplate);

        final Template viewTemplate = config.getTemplate("viewTemplate.ftl");
        final Template randomTemplate = config.getTemplate("randomTemplate.ftl");
        Generator.generateViewFiles(responses, viewTemplate, randomTemplate);

        System.out.println("\n******************************\nThere are " + RANDOM_LINK_CLASS_NAMES.size() + " classes that are referenced but have no definition in the API specs: \n");
        for (final String randomClassName : RANDOM_LINK_CLASS_NAMES) {
            System.out.println(randomClassName);
        }

        System.out.println("\n******************************\nThere are " + NULL_LINK_RESULT_CLASSES.size() + " classes that are referenced as link results in API specs but we have no information about what Object they correspond to: \n");
        for (final Map.Entry nullLinkResultClass : NULL_LINK_RESULT_CLASSES.entrySet()) {
            System.out.println(nullLinkResultClass.getKey() + " - " + nullLinkResultClass.getValue());
        }
    }

    private void generateEnumFiles(final List<ResponseDefinition> responses, final Template template) throws Exception {
        for (final ResponseDefinition response : responses) {
            for (final FieldDefinition field : response.getFields()) {
                generateEnumFiles(field, response.getMediaType(), template);
            }
        }
    }

    private void generateEnumFiles(final FieldDefinition field, final String responseMediaType, final Template template) throws Exception {
        final String classType = field.getType().replace(UtilStrings.JAVA_LIST, "").replace(">", "");
        if (isEnum(classType) && !classCategories.isThrowaway(classType)) {
            final Map<String, Object> input = getEnumInputData(GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), responseMediaType);
            final String pathToEnumFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + ENUM_DIRECTORY_SUFFIX;
            FreeMarkerHelper.writeFile(classType, template, input, pathToEnumFiles);
        }
        for (final FieldDefinition subField : field.getSubFields()) {
            generateEnumFiles(subField, responseMediaType, template);
        }
    }

    public static boolean isEnum(final String type) {
        return type.contains(UtilStrings.ENUM) && !classCategories.isNonEnumClassEndingInType(type);
    }

    private void generateViewFiles(final List<ResponseDefinition> responses, final Template viewTemplate, final Template randomTemplate) throws Exception {
        final MediaTypes mediaTypes = new MediaTypes();
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        final String pathToViewFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + VIEW_DIRECTORY_SUFFIX;
        final String pathToComponentFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;
        for (final ResponseDefinition response : responses) {
            if (longMediaTypes.contains(response.getMediaType())) {
                Set<String> imports = new HashSet<>();
                importHelper.addFieldImports(imports, response.getFields());
                final LinksAndImportsHelper helper = importHelper.getLinkImports(imports, response, LINK_CLASS_NAMES, NULL_LINK_RESULT_CLASSES);
                imports = helper.getImports();
                final Set<LinkHelper> links = helper.getLinks();

                final Map<String, Object> input = getViewInputData(GENERATED_VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields(), links, response.getMediaType());
                final String viewName = response.getName();

                MediaVersionHelper.updateLatestMediaVersions(viewName, input, LATEST_VIEW_MEDIA_VERSIONS);
                FreeMarkerHelper.writeFile(viewName, viewTemplate, input, pathToViewFiles);

                for (final FieldDefinition field : response.getFields()) {
                    if (isNotCommonTypeOrEnum(field.getType())) {
                        generateComponentFile(field, viewTemplate, response.getMediaType());
                    }
                }

                NON_LINK_CLASS_NAMES.add(viewName);
                NON_LINK_CLASS_NAMES.add(NameParser.getNonVersionedName(viewName));
            }
        }

        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, pathToComponentFiles);

        generateDummyClassesForReferencedButUndefinedObjects(randomTemplate);
    }

    private boolean isNotCommonTypeOrEnum(final String fieldType) {
        return (!classCategories.isCommonType(fieldType) && !isEnum(fieldType));
    }

    private void generateComponentFile(final FieldDefinition field, final Template template, final String responseMediaType) throws Exception {
        final String fieldType = NameParser.stripListNotation(field.getType());
        final Set<String> imports = new HashSet<>();
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            importHelper.addFieldImports(imports, subField);
            if (isNotCommonTypeOrEnum(subField.getType())) {
                generateComponentFile(subField, template, responseMediaType);
            }
        }

        final Map<String, Object> input = getViewInputData(GENERATED_COMPONENT_PACKAGE, imports, fieldType, COMPONENT_BASE_CLASS, subFields, responseMediaType);

        final String pathToComponentFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;

        MediaVersionHelper.updateLatestMediaVersions(fieldType, input, LATEST_COMPONENT_MEDIA_VERSIONS);

        if (isNotCommonTypeOrEnum(fieldType)) {
            FreeMarkerHelper.writeFile(fieldType, template, input, pathToComponentFiles);
        }
        NON_LINK_CLASS_NAMES.add(fieldType);
        NON_LINK_CLASS_NAMES.add(NameParser.getNonVersionedName(fieldType));
    }

    private Map<String, Object> getEnumInputData(final String enumPackage, final String enumClassName, final List<String> enumValues, final String mediaType) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put(PACKAGE_NAME, enumPackage);
        inputData.put(MEDIA_TYPE, mediaType);
        inputData.put("enumClassName", enumClassName);
        inputData.put("enumValues", enumValues);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final Set<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final String mediaType) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put(PACKAGE_NAME, viewPackage);
        inputData.put(CLASS_NAME, className);
        inputData.put(MEDIA_TYPE, mediaType);
        inputData.put("imports", imports);
        inputData.put("baseClass", baseClass);

        for (final FieldDefinition classField : classFields) {
            final String oldType = classField.getType();
            final String newType = NameParser.getNonVersionedName(oldType);
            classField.setType(newType);
        }
        inputData.put("classFields", classFields);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final Set<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final Set<LinkHelper> links,
        final String mediaType) {
        final HashMap<String, Object> inputData = getViewInputData(viewPackage, imports, className, baseClass, classFields, mediaType);

        if (links != null && links.size() > 0) {
            inputData.put("hasLinksWithResults", true);
            inputData.put("hasLinks", true);
            inputData.put(UtilStrings.LINKS, links);
        }

        return inputData;
    }

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToViewFiles, final String pathToComponentFiles)
        throws Exception {
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, LATEST_VIEW_MEDIA_VERSIONS.values());
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToComponentFiles, LATEST_COMPONENT_MEDIA_VERSIONS.values());
    }

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToFiles, final Collection<MediaVersionHelper> latestMediaVersions) throws Exception {
        for (final MediaVersionHelper latestMediaVersion : latestMediaVersions) {
            final Map<String, Object> input = latestMediaVersion.getInput();
            final String className = latestMediaVersion.getNonVersionedClass();
            input.put(CLASS_NAME, className);
            input.put(PARENT_CLASS, latestMediaVersion.getVersionedClassName());
            final String importClass = getImportClass(className);
            final String importPath = CORE_CLASS_PATH_PREFIX + importClass;
            input.put(IMPORT_PATH, importPath);
            FreeMarkerHelper.writeFile(className, randomTemplate, input, pathToFiles);
            NON_LINK_CLASS_NAMES.add(className);
            NON_LINK_CLASS_NAMES.add(NameParser.getNonVersionedName(className));
        }
    }

    private String getImportClass(final String className) {
        String importClass = null;
        if (classCategories.isView(className)) {
            importClass = VIEW_BASE_CLASS;
        } else if (classCategories.isResponse(className)) {
            importClass = RESPONSE_BASE_CLASS;
        } else if (classCategories.isComponent(className)) {
            importClass = COMPONENT_BASE_CLASS;
        }
        return importClass;
    }

    private void generateDummyClassesForReferencedButUndefinedObjects(final Template randomTemplate) throws Exception {
        for (final String linkClassName : LINK_CLASS_NAMES) {
            if (!classCategories.isManual(linkClassName) && !classCategories.isThrowaway(linkClassName) && !classCategories.isCommonType(linkClassName)) {
                final Map<String, Object> randomInput = new HashMap<>();
                randomInput.put(CLASS_NAME, linkClassName);
                final RandomClassData randomClassData = new RandomClassData(linkClassName, classCategories);
                final String packageName = randomClassData.getPackageName();
                final String destinationSuffix = randomClassData.getDestinationSuffix();
                final String importPath = randomClassData.getImportPath();
                final String parentClass = randomClassData.getParentClass();
                randomInput.put(PARENT_CLASS, parentClass);
                randomInput.put(PACKAGE_NAME, packageName);
                randomInput.put(IMPORT_PATH, importPath);

                if (!NON_LINK_CLASS_NAMES.contains(linkClassName) && !RANDOM_LINK_CLASS_NAMES.contains(linkClassName)) {
                    FreeMarkerHelper.writeFile(linkClassName, randomTemplate, randomInput, BLACKDUCK_COMMON_API_BASE_DIRECTORY + destinationSuffix);
                    RANDOM_LINK_CLASS_NAMES.add(linkClassName);
                }
            }
        }

    }

}
