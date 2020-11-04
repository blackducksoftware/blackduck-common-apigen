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
package com.synopsys.integration.create.apigen.generation.finder;

import static com.synopsys.integration.create.apigen.data.UtilStrings.COMPONENT_BASE_CLASS;
import static com.synopsys.integration.create.apigen.data.UtilStrings.CORE_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.MANUAL_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.RESPONSE_BASE_CLASS;
import static com.synopsys.integration.create.apigen.data.UtilStrings.TEMPORARY_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.VIEW_BASE_CLASS;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassCategoryData;
import com.synopsys.integration.create.apigen.data.ClassSourceEnum;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkData;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.LinksAndImportsData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.model.ResultClassData;
import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class ImportFinder {
    private final ClassCategories classCategories;
    private final LinkResponseDefinitions linkResponseDefinitions;
    private final NameAndPathManager nameAndPathManager;
    private final ClassNameManager classNameManager;

    @Autowired
    public ImportFinder(final ClassCategories classCategories, final LinkResponseDefinitions linkResponseDefinitions, final NameAndPathManager nameAndPathManager, ClassNameManager classNameManager) {
        this.classCategories = classCategories;
        this.linkResponseDefinitions = linkResponseDefinitions;
        this.nameAndPathManager = nameAndPathManager;
        this.classNameManager = classNameManager;
    }

    public Set<String> getFieldImports(final Set<FieldDefinition> fields) {
        Set<String> fieldImports = new HashSet<>();
        for (final FieldDefinition field : fields) {
            String fieldType = field.getType();
            fieldImports.addAll(getFieldImports(fieldType, field.isOptional()));
        }
        return fieldImports;
    }

    public Set<String> getFieldImports(String fieldType, final boolean isOptional) {
        Set<String> fieldImports = new HashSet<>();
        fieldType = NameParser.stripListAndOptionalNotation(fieldType);
        fieldType = NameParser.getNonVersionedName(fieldType);

        final ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        ClassTypeEnum classType = classCategoryData.getType();

        if (isOptional) {
            // If fields that have optional=true in the specs are actually optional, we could wrap them in Optionals
            //imports.add("java.util.Optional");
        }

        if (fieldType.equals(UtilStrings.BIG_DECIMAL)) {
            fieldImports.add(UtilStrings.JAVA_BIG_DECIMAL);
        }

        String importPathPrefix;
        if (classSource.isTemporary()) {
            importPathPrefix = TEMPORARY_CLASS_PATH_PREFIX;
        } else if (classSource.isManual()) {
            importPathPrefix = MANUAL_CLASS_PATH_PREFIX;
        } else {
            importPathPrefix = GENERATED_CLASS_PATH_PREFIX;
        }

        String baseClass = null;
        String classCategory = null;
        if (classType.isEnum()) {
            classCategory = UtilStrings.ENUMERATION;
        } else if (classType.isResponse()) {
            classCategory = UtilStrings.RESPONSE;
            baseClass = RESPONSE_BASE_CLASS;
        } else if (classType.isView()) {
            classCategory = UtilStrings.VIEW;
            baseClass = VIEW_BASE_CLASS;
        } else if (!classType.isCommon()) {
            classCategory = UtilStrings.COMPONENT;
            baseClass = COMPONENT_BASE_CLASS;
        }

        if (classCategory != null) {
            fieldImports.add(String.format("%s%s.%s", importPathPrefix, classCategory, fieldType));
        }
        if (baseClass != null ) {
            fieldImports.add(CORE_CLASS_PATH_PREFIX + baseClass);
        }

        return fieldImports;
    }

    public LinksAndImportsData getLinkImports(final ResponseDefinition response) {
        final Set<LinkDefinition> rawLinks = response.getLinks();
        final Set<LinkData> links = new HashSet<>();
        final Set<String> linkImports = new HashSet<>();
        final String responseName = response.getName();

        if (!rawLinks.isEmpty()) {
            linkImports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.LINK_RESPONSE));
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkData link = new LinkData(rawLink.getRel(), response, linkResponseDefinitions);
            try {
                final String linkType = link.linkType();
                if (null == linkType) {
                    nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
                    continue;
                }
                final String linkImport = classNameManager.getFullyQualifiedClassName(linkType);
                linkImports.add(linkImport);

                final String resultClass = link.resultClass();
                if (resultClass != null) {
                    nameAndPathManager.addLinkClassName(resultClass);
                    final ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
                    final String resultImportPath = resultClassData.getResultImportPath();
                    final String resultImportType = resultClassData.getResultImportType();
                    final boolean shouldAddImport = resultClassData.shouldAddImport();

                    if (shouldAddImport) {
                        linkImports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    nameAndPathManager.addNullLinkResultClass(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsData(links, linkImports);
    }

}
