/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.finder;

import com.blackduck.integration.create.apigen.data.*;
import com.blackduck.integration.create.apigen.model.*;
import com.blackduck.integration.create.apigen.parser.NameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.blackduck.integration.create.apigen.data.UtilStrings.*;

@Component
public class ImportFinder {
    private final ClassCategories classCategories;
    private final LinkResponseDefinitions linkResponseDefinitions;
    private final NameAndPathManager nameAndPathManager;
    private final ClassNameManager classNameManager;

    @Autowired
    public ImportFinder(ClassCategories classCategories, LinkResponseDefinitions linkResponseDefinitions, NameAndPathManager nameAndPathManager, ClassNameManager classNameManager) {
        this.classCategories = classCategories;
        this.linkResponseDefinitions = linkResponseDefinitions;
        this.nameAndPathManager = nameAndPathManager;
        this.classNameManager = classNameManager;
    }

    public Set<String> findFieldImports(Set<FieldDefinition> fields) {
        Set<String> fieldImports = new HashSet<>();
        for (FieldDefinition field : fields) {
            String fieldType = field.getType();
            fieldImports.addAll(findFieldImports(fieldType));
        }
        return fieldImports;
    }

    public Set<String> findFieldImports(String fieldType) {
        Set<String> fieldImports = new HashSet<>();
        fieldType = NameParser.stripListAndOptionalNotation(fieldType);

        if (fieldType.equals(UtilStrings.JSON_OBJECT)) {
            return fieldImports;
        }

        ClassCategoryData classCategoryData = classCategories.computeData(fieldType);
        ClassSourceEnum classSource = classCategoryData.getSource();
        ClassTypeEnum classType = classCategoryData.getType();

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
        if (baseClass != null) {
            fieldImports.add(CORE_CLASS_PATH_PREFIX + baseClass);
        }

        return fieldImports;
    }

    public LinksAndImportsData findLinkAndImportsData(ResponseDefinition response) {
        Set<LinkDefinition> rawLinks = response.getLinks();
        Set<LinkData> links = new HashSet<>();
        Set<String> linkImports = new HashSet<>();
        String responseName = response.getName();

        if (!rawLinks.isEmpty()) {
            linkImports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.LINK_BLACKDUCK_RESPONSE));
            linkImports.add("java.util.Optional");
        }

        for (LinkDefinition rawLink : rawLinks) {
            LinkData link = new LinkData(rawLink.getRel(), response, linkResponseDefinitions);
            try {
                String linkType = link.linkType();
                if (null == linkType) {
                    nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
                    continue;
                }
                String linkImport = classNameManager.getFullyQualifiedClassName(linkType);
                linkImports.add(linkImport);
                String urlResponseImport = classNameManager.getFullyQualifiedClassName(link.urlResponseType);
                linkImports.add(urlResponseImport);

                String resultClass = link.resultClass();
                if (resultClass != null) {
                    nameAndPathManager.addLinkClassName(resultClass);
                    ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
                    String resultImportPath = resultClassData.getResultImportPath();
                    String resultImportType = resultClassData.getResultImportType();
                    boolean shouldAddImport = resultClassData.shouldAddImport();

                    if (shouldAddImport) {
                        linkImports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    nameAndPathManager.addNullLinkResultClass(responseName, linkType);
                }
            } catch (NullPointerException e) {
                nameAndPathManager.addNullLinkResultClass(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsData(links, linkImports);
    }

}
