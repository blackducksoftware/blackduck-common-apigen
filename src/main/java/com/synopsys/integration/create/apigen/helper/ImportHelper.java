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
package com.synopsys.integration.create.apigen.helper;

import static com.synopsys.integration.create.apigen.helper.UtilStrings.COMPONENT_BASE_CLASS;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.CORE_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.VIEW_BASE_CLASS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassCategoryData;
import com.synopsys.integration.create.apigen.definitions.ClassSourceEnum;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;
import com.synopsys.integration.create.apigen.definitions.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class ImportHelper {

    public static final String LINK_RESPONSE = "LinkResponse";
    public static final String LINK_MULTIPLE_RESPONSES = "LinkMultipleResponses";
    public static final String LINK_SINGLE_RESPONSE = "LinkSingleResponse";
    public static final String LINK_STRING_RESPONSE = "LinkStringResponse";

    private final ClassCategories classCategories;
    private final LinkResponseDefinitions linkResponseDefinitions;
    private final DataManager dataManager;

    @Autowired
    public ImportHelper(final ClassCategories classCategories, final LinkResponseDefinitions linkResponseDefinitions, final DataManager dataManager) {
        this.classCategories = classCategories;
        this.linkResponseDefinitions = linkResponseDefinitions;
        this.dataManager = dataManager;
    }

    public void addFieldImports(final Set<String> imports, final List<FieldDefinition> fields) {
        imports.add(CORE_CLASS_PATH_PREFIX + VIEW_BASE_CLASS);

        for (final FieldDefinition field : fields) {
            addFieldImports(imports, field);
        }
    }

    public void addFieldImports(final Set<String> imports, final FieldDefinition field) {
        imports.add(CORE_CLASS_PATH_PREFIX + COMPONENT_BASE_CLASS);

        String fieldType = field.getType();
        if (fieldType.contains(UtilStrings.LIST)) {
            imports.add(UtilStrings.JAVA_LIST.replace("<", ""));
        }
        fieldType = NameParser.stripListNotation(fieldType);
        fieldType = NameParser.getNonVersionedName(fieldType);
        final ClassCategoryData classCategoryData = new ClassCategoryData(fieldType, classCategories);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        final ClassTypeEnum classType = classCategoryData.getType();
        final String importPathPrefix = classSource.isThrowaway() ? GENERATED_CLASS_PATH_PREFIX.replace("generated", "manual.throwaway.generated") : GENERATED_CLASS_PATH_PREFIX;

        if (fieldType.equals(UtilStrings.BIG_DECIMAL)) {
            imports.add(UtilStrings.JAVA_BIG_DECIMAL);
        }

        if (classType.isEnum()) {
            imports.add(importPathPrefix + UtilStrings.ENUMERATION + "." + fieldType);
        } else if (classType.isResponse()) {
            imports.add(importPathPrefix + UtilStrings.RESPONSE + "." + fieldType);
        } else if (!classType.isCommon()) {
            imports.add(importPathPrefix + UtilStrings.COMPONENT + "." + fieldType);
        }
    }

    public LinksAndImportsHelper getLinkImports(final Set<String> imports, final ResponseDefinition response) {
        final List<LinkDefinition> rawLinks = response.getLinks();
        final Set<LinkHelper> links = new HashSet<>();
        final String responseName = response.getName();

        if (!rawLinks.isEmpty()) {
            imports.add(CORE_CLASS_PATH_PREFIX + LINK_RESPONSE);
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkHelper link = new LinkHelper(rawLink.getRel(), responseName, linkResponseDefinitions);
            try {
                final String resultClass = link.resultClass();
                final String linkType = link.linkType();
                final String linkImportType = getLinkImportType(linkType);

                final String linkImport = CORE_CLASS_PATH_PREFIX + linkImportType;
                imports.add(linkImport);

                if (resultClass != null) {
                    dataManager.addLinkClassName(resultClass);

                    final ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
                    final String resultImportPath = resultClassData.getResultImportPath();
                    final String resultImportType = resultClassData.getResultImportType();
                    final boolean shouldAddImport = resultClassData.shouldAddImport();

                    if (shouldAddImport) {
                        imports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    dataManager.addNullLinkResultClass(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                dataManager.addNullLinkResultClass(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsHelper(links, imports);
    }

    private String getLinkImportType(final String linkType) {
        if (linkType.contains(LINK_MULTIPLE_RESPONSES)) {
            return LINK_MULTIPLE_RESPONSES;
        } else if (linkType.contains(LINK_SINGLE_RESPONSE)) {
            return LINK_SINGLE_RESPONSE;
        } else {
            return LINK_STRING_RESPONSE;
        }
    }
}
