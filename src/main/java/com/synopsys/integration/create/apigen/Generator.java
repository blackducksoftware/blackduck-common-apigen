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
import com.synopsys.integration.create.apigen.helper.LinkHelper;
import com.synopsys.integration.create.apigen.helper.LinksAndImportsHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionHelper;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.DirectoryWalker;
import com.synopsys.integration.create.apigen.parser.ResponseNameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generator {
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String CORE_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.core.";
    public static final String MANUAL_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.manual.";
    public static final String ENUMERATION = "enumeration";
    public static final String VIEW = "view";
    public static final String ENUM = "Type";
    public static final String COMPONENT = "component";
    public static final String RESPONSE = "response";
    public static final String GENERATED_ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String GENERATED_VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + VIEW;
    public static final String GENERATED_COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + COMPONENT;
    public static final String GENERATED_RESPONSE_PACKAGE = GENERATED_CLASS_PATH_PREFIX + RESPONSE;
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String RESPONSE_BASE_CLASS = "BlackDuckResponse";
    public static final String JAVA_LIST = "java.util.List<";
    public static final String LIST = "List<";
    public static final String CLASS_NAME = "className";
    public static String BLACKDUCK_COMMON_API_BASE_DIRECTORY;
    public static final String ENUM_DIRECTORY_SUFFIX = "/enumeration";
    public static final String VIEW_DIRECTORY_SUFFIX = "/view";
    public static final String RESPONSE_DIRECTORY_SUFFIX = "/response";
    public static final String COMPONENT_DIRECTORY_SUFFIX = "/component";

    public static Set<String> NON_LINK_CLASS_NAMES = new HashSet<>();
    public static Set<String> LINK_CLASS_NAMES = new HashSet<>();
    public static List<String> RANDOM_LINK_CLASS_NAMES = new ArrayList<>();
    public static Map<String, String> NULL_LINK_RESULT_CLASSES = new HashMap<>();

    private static final Map<String, MediaVersionHelper> LATEST_VIEW_MEDIA_VERSIONS = new HashMap<>();
    private static final Map<String, MediaVersionHelper> LATEST_COMPONENT_MEDIA_VERSIONS = new HashMap<>();

    private final ClassCategories CLASS_CATEGORIES = new ClassCategories();
    private final MissingFieldsAndLinks MISSING_FIELDS_AND_LINKS = new MissingFieldsAndLinks();

    public static void main(final String[] args) throws Exception {
        final Generator Generator = new Generator();
        final FreeMarkerHelper freeMarkerHelper = new FreeMarkerHelper();
        BLACKDUCK_COMMON_API_BASE_DIRECTORY = freeMarkerHelper.getBaseDirectory().getAbsolutePath();
        final Configuration config = freeMarkerHelper.configureFreeMarker();

        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForResponses(false);

        // ********* Where does this go??? **********
        for (final ResponseDefinition response : responses) {
            final String responseName = ResponseNameParser.getNonVersionedName(response.getName());
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
                final String classType = field.getType().replace(JAVA_LIST, "").replace(">", "");
                if (classType.contains(ENUM) && !CLASS_CATEGORIES.isNonEnumClassEndingInType(classType) && !CLASS_CATEGORIES.isThrowaway(classType)) {
                    final Map<String, Object> input = getEnumInputData(GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), response.getMediaType());
                    final String pathToEnumFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + ENUM_DIRECTORY_SUFFIX;
                    FreeMarkerHelper.writeFile(classType, template, input, pathToEnumFiles);
                }
                if (classType.contains(ENUM)) {
                    System.out.println("** " + classType);
                }
            }
        }
    }

    private void generateViewFiles(final List<ResponseDefinition> responses, final Template viewTemplate, final Template randomTemplate) throws Exception {
        final MediaTypes mediaTypes = new MediaTypes();
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        final String pathToViewFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + VIEW_DIRECTORY_SUFFIX;
        final String pathToComponentFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;
        for (final ResponseDefinition response : responses) {
            if (longMediaTypes.contains(response.getMediaType())) {
                List<String> imports = new ArrayList<>();
                imports = getFieldImports(imports, response, viewTemplate);
                final LinksAndImportsHelper helper = getLinkImports(imports, response);
                imports = helper.getImports();
                final List<LinkHelper> links = helper.getLinks();

                final Map<String, Object> input = getViewInputData(GENERATED_VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields(), links, response.getMediaType());
                final String viewName = response.getName();

                MediaVersionHelper.updateLatestMediaVersions(viewName, input, LATEST_VIEW_MEDIA_VERSIONS);
                FreeMarkerHelper.writeFile(viewName, viewTemplate, input, pathToViewFiles);
                NON_LINK_CLASS_NAMES.add(viewName);
                NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(viewName));
            }
        }

        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, pathToComponentFiles);

        generateDummyClassesForReferencedButUndefinedObjects(randomTemplate);
    }

    private void generateComponentFile(final FieldDefinition field, final Template template, final String responseMediaType) throws Exception {
        final String fieldType = field.getType().replace(JAVA_LIST, "").replace(LIST, "").replace(">", "");
        final List<String> imports = new ArrayList<>();
        imports.add(CORE_CLASS_PATH_PREFIX + COMPONENT_BASE_CLASS);
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            String subFieldType = subField.getType().replace(JAVA_LIST, "").replace(">", "");
            if (subFieldType.contains(LIST)) {
                subFieldType = subFieldType.replace(LIST, "");
                imports.add(JAVA_LIST.replace("<", ""));
            }
            subFieldType = ResponseNameParser.getNonVersionedName(subFieldType);
            if (subFieldType.contains(ENUM) && !CLASS_CATEGORIES.isNonEnumClassEndingInType(subFieldType)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + subFieldType);
            }
            if (!CLASS_CATEGORIES.isCommonType(subFieldType)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + subFieldType);
                generateComponentFile(subField, template, responseMediaType);
            }
        }
        final Map<String, Object> input = getViewInputData(GENERATED_COMPONENT_PACKAGE, imports, fieldType, COMPONENT_BASE_CLASS, subFields, responseMediaType);

        final String pathToComponentFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;

        MediaVersionHelper.updateLatestMediaVersions(fieldType, input, LATEST_COMPONENT_MEDIA_VERSIONS);

        FreeMarkerHelper.writeFile(fieldType, template, input, pathToComponentFiles);
        NON_LINK_CLASS_NAMES.add(fieldType);
        NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(fieldType));
    }

    private Map<String, Object> getEnumInputData(final String enumPackage, final String enumClassName, final List<String> enumValues, final String mediaType) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put("packageName", enumPackage);
        inputData.put("enumClassName", enumClassName);
        inputData.put("enumValues", enumValues);
        inputData.put("mediaType", mediaType);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final String mediaType) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put("packageName", viewPackage);
        inputData.put("imports", imports);
        inputData.put("className", className);
        inputData.put("baseClass", baseClass);
        inputData.put("mediaType", mediaType);

        for (final FieldDefinition classField : classFields) {
            final String oldType = classField.getType();
            final String newType = ResponseNameParser.getNonVersionedName(oldType);
            classField.setType(newType);
        }
        inputData.put("classFields", classFields);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final List<LinkHelper> links,
        final String mediaType) {
        final HashMap<String, Object> inputData = getViewInputData(viewPackage, imports, className, baseClass, classFields, mediaType);

        if (links != null && links.size() > 0) {
            inputData.put("hasLinksWithResults", true);
            inputData.put("hasLinks", true);
            inputData.put("links", links);
        }

        return inputData;
    }

    private List<String> getFieldImports(final List<String> imports, final ResponseDefinition response, final Template template) throws Exception {
        imports.add(CORE_CLASS_PATH_PREFIX + VIEW_BASE_CLASS);

        for (final FieldDefinition field : response.getFields()) {
            String fieldType = field.getType().replace(JAVA_LIST, "").replace(LIST, "").replace(">", "");
            fieldType = ResponseNameParser.getNonVersionedName(fieldType);
            final String importPathPrefix = CLASS_CATEGORIES.isThrowaway(fieldType) ? GENERATED_CLASS_PATH_PREFIX.replace("generated", "manual.throwaway.generated") : GENERATED_CLASS_PATH_PREFIX;
            if (fieldType.contains(ENUM) && !CLASS_CATEGORIES.isNonEnumClassEndingInType(fieldType)) {
                imports.add(importPathPrefix + ENUMERATION + "." + fieldType);
            } else if (!CLASS_CATEGORIES.isCommonType(fieldType)) {
                imports.add(importPathPrefix + COMPONENT + "." + fieldType);
                generateComponentFile(field, template, response.getMediaType());
            } else if (fieldType.equals("BigDecimal")) {
                imports.add("java.math.BigDecimal");
            }
        }
        return imports;
    }

    private LinksAndImportsHelper getLinkImports(final List<String> imports, final ResponseDefinition response) {
        final List<LinkDefinition> rawLinks = response.getLinks();
        final List<LinkHelper> links = new ArrayList<>();
        final String responseName = response.getName();

        if (rawLinks.size() > 0) {
            imports.add(CORE_CLASS_PATH_PREFIX + "LinkResponse");
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkHelper link = new LinkHelper(rawLink.getRel(), responseName);
            try {
                final String resultClass = link.resultClass();
                if (resultClass != null) {
                    LINK_CLASS_NAMES.add(resultClass);
                }
                final String linkType = link.linkType();
                final String linkImportType;
                String resultImportType = null;
                String resultImportPath = null;

                if (linkType.contains("LinkMultipleResponses")) {
                    linkImportType = "LinkMultipleResponses";
                } else if (linkType.contains("LinkSingleResponse")) {
                    linkImportType = "LinkSingleResponse";
                } else {
                    linkImportType = "LinkStringResponse";
                }
                final String linkImport = CORE_CLASS_PATH_PREFIX + linkImportType;
                if (!imports.contains(linkImport)) {
                    imports.add(linkImport);
                }

                boolean shouldAddImport = true;
                if (resultClass != null) {
                    if (CLASS_CATEGORIES.isView(resultClass)) {
                        resultImportType = VIEW;
                    } else if (CLASS_CATEGORIES.isResponse(resultClass)) {
                        resultImportType = RESPONSE;
                    } else if (CLASS_CATEGORIES.isComponent(resultClass)) {
                        resultImportType = COMPONENT;
                    } else {
                        shouldAddImport = false;
                    }

                    if (CLASS_CATEGORIES.isManual(resultClass)) {
                        resultImportPath = MANUAL_CLASS_PATH_PREFIX;
                    } else if (CLASS_CATEGORIES.isThrowaway(resultClass)) {
                        resultImportPath = MANUAL_CLASS_PATH_PREFIX + "throwaway.generated.";
                    } else if (CLASS_CATEGORIES.isGenerated(resultClass)) {
                        resultImportPath = GENERATED_CLASS_PATH_PREFIX;
                    } else {
                        shouldAddImport = false;
                    }

                    if (shouldAddImport) {
                        imports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    NULL_LINK_RESULT_CLASSES.put(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                NULL_LINK_RESULT_CLASSES.put(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsHelper(links, imports);
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
            input.put("parentClass", latestMediaVersion.getVersionedClassName());
            String importClass = null;
            if (CLASS_CATEGORIES.isView(className)) {
                importClass = VIEW_BASE_CLASS;
            } else if (CLASS_CATEGORIES.isResponse(className)) {
                importClass = RESPONSE_BASE_CLASS;
            } else if (CLASS_CATEGORIES.isComponent(className)) {
                importClass = COMPONENT_BASE_CLASS;
            }
            final String importPath = CORE_CLASS_PATH_PREFIX + importClass;
            input.put("importPath", importPath);
            FreeMarkerHelper.writeFile(className, randomTemplate, input, pathToFiles);
            NON_LINK_CLASS_NAMES.add(className);
            NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(className));
        }
    }

    private void generateDummyClassesForReferencedButUndefinedObjects(final Template randomTemplate) throws Exception {
        for (final String linkClassName : LINK_CLASS_NAMES) {
            if (!CLASS_CATEGORIES.isManual(linkClassName) && !CLASS_CATEGORIES.isThrowaway(linkClassName) && !CLASS_CATEGORIES.isCommonType(linkClassName)) {
                final Map<String, Object> randomInput = new HashMap<>();
                randomInput.put(CLASS_NAME, linkClassName);
                final String packageName;
                final String destinationSuffix;
                final String importPath;
                final String parentClass;
                if (CLASS_CATEGORIES.isView(linkClassName)) {
                    packageName = GENERATED_VIEW_PACKAGE;
                    destinationSuffix = VIEW_DIRECTORY_SUFFIX;
                    parentClass = VIEW_BASE_CLASS;
                } else if (CLASS_CATEGORIES.isResponse(linkClassName)) {
                    packageName = GENERATED_RESPONSE_PACKAGE;
                    destinationSuffix = RESPONSE_DIRECTORY_SUFFIX;
                    parentClass = RESPONSE_BASE_CLASS;
                } else {
                    packageName = GENERATED_COMPONENT_PACKAGE;
                    destinationSuffix = COMPONENT_DIRECTORY_SUFFIX;
                    parentClass = COMPONENT_BASE_CLASS;
                }
                importPath = CORE_CLASS_PATH_PREFIX + parentClass;
                randomInput.put("parentClass", parentClass);
                randomInput.put("packageName", packageName);
                randomInput.put("importPath", importPath);

                if (!NON_LINK_CLASS_NAMES.contains(linkClassName) && !RANDOM_LINK_CLASS_NAMES.contains(linkClassName)) {
                    FreeMarkerHelper.writeFile(linkClassName, randomTemplate, randomInput, BLACKDUCK_COMMON_API_BASE_DIRECTORY + destinationSuffix);
                    RANDOM_LINK_CLASS_NAMES.add(linkClassName);
                }
            }
        }
    }

}
