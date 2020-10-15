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
    private final TypeTranslator typeTranslator;
    private final ClassNameManager classNameManager;

    @Autowired
    public ImportFinder(final ClassCategories classCategories, final LinkResponseDefinitions linkResponseDefinitions, final NameAndPathManager nameAndPathManager, TypeTranslator typeTranslator, ClassNameManager classNameManager) {
        this.classCategories = classCategories;
        this.linkResponseDefinitions = linkResponseDefinitions;
        this.nameAndPathManager = nameAndPathManager;
        this.typeTranslator = typeTranslator;
        this.classNameManager = classNameManager;
    }

    public void addFieldImports(final Set<String> imports, final Set<FieldDefinition> fields) {
        imports.add(CORE_CLASS_PATH_PREFIX + VIEW_BASE_CLASS);

        for (final FieldDefinition field : fields) {
            final String fieldType = typeTranslator.getSimplifiedClassName(field.getType());
            addFieldImports(imports, fieldType, field.isOptional());
        }
    }

    public void addFieldImports(final Set<String> imports, String fieldType, final boolean isOptional) {
        fieldType = NameParser.stripListAndOptionalNotation(fieldType);
        fieldType = NameParser.getNonVersionedName(fieldType);

        final ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        ClassTypeEnum classType = classCategoryData.getType();

        String baseClass = null;
        if ((classType).isView()) {
            baseClass = VIEW_BASE_CLASS;
        } else if (classType.isResponse()) {
            baseClass = RESPONSE_BASE_CLASS;
        } else if (!classType.isEnum() && !classType.isCommon()) {
            baseClass = COMPONENT_BASE_CLASS;
        }

        if (baseClass != null ) {
            imports.add(CORE_CLASS_PATH_PREFIX + baseClass);
        }

        if (isOptional) {
            // If fields that have optional=true in the specs are actually optional, we could wrap them in Optionals
            //imports.add("java.util.Optional");
        }

        final String importPathPrefix = classSource.isTemporary() ? TEMPORARY_CLASS_PATH_PREFIX : GENERATED_CLASS_PATH_PREFIX;

        if (fieldType.equals(UtilStrings.BIG_DECIMAL)) {
            imports.add(UtilStrings.JAVA_BIG_DECIMAL);
        }

        if (classType.isEnum()) {
            imports.add(importPathPrefix + UtilStrings.ENUMERATION + "." + fieldType);
        } else if (classType.isResponse()) {
            imports.add(importPathPrefix + UtilStrings.RESPONSE + "." + fieldType);
        } else if (classType.isView()) {
            imports.add(importPathPrefix + UtilStrings.VIEW + "." + fieldType);
        } else if (!classType.isCommon()) {
            imports.add(importPathPrefix + UtilStrings.COMPONENT + "." + fieldType);
        }
    }

    public LinksAndImportsData getLinkImports(final Set<String> imports, final ResponseDefinition response) {
        final Set<LinkDefinition> rawLinks = response.getLinks();
        final Set<LinkData> links = new HashSet<>();
        final String responseName = response.getName();

        if (!rawLinks.isEmpty()) {
            imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.LINK_RESPONSE));
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkData link = new LinkData(rawLink.getRel(), response, linkResponseDefinitions);
            try {
                final String resultClass = link.resultClass();
                final String linkType = link.linkType();
                if (null == linkType) {
                    nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
                    continue;
                }

                final String linkImport = classNameManager.getFullyQualifiedClassName(linkType);
                imports.add(linkImport);

                if (resultClass != null) {
                    nameAndPathManager.addLinkClassName(resultClass);

                    final ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
                    final String resultImportPath = resultClassData.getResultImportPath();
                    final String resultImportType = resultClassData.getResultImportType();
                    final boolean shouldAddImport = resultClassData.shouldAddImport();

                    if (shouldAddImport) {
                        imports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    nameAndPathManager.addNullLinkResultClass(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsData(links, imports);
    }

}
