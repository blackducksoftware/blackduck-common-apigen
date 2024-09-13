/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.finder;

import com.blackduck.integration.create.apigen.data.ImportComparator;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.model.ClassTypeData;
import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.LinkData;
import com.blackduck.integration.create.apigen.parser.NameParser;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InputDataFinder {
    public static final String IMPORT_HASHMAP = "java.util.HashMap";
    public static final String IMPORT_MAP = "java.util.Map";

    private static final List<String> MAP_IMPORTS = Arrays.asList(IMPORT_HASHMAP, IMPORT_MAP);

    public Map<String, Object> getEnumInputData(final String packageName, final String className, final Set<String> enumValues, final String mediaType) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put(UtilStrings.PACKAGE_NAME, packageName);
        inputData.put(UtilStrings.MEDIA_TYPE, mediaType);
        inputData.put(UtilStrings.CLASS_NAME, NameParser.stripListAndOptionalNotation(className));

        List<String> sortedEnumValues = enumValues.stream()
                                            .sorted(Comparator.naturalOrder())
                                            .collect(Collectors.toList());
        inputData.put("enumValues", sortedEnumValues);

        return inputData;
    }

    public HashMap<String, Object> getInputData(final ClassTypeData classTypeData, final Set<String> imports, final String className, final Set<FieldDefinition> classFields, final String mediaType) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put(UtilStrings.PACKAGE_NAME, classTypeData.getPackageName());
        inputData.put(UtilStrings.CLASS_NAME, NameParser.stripListAndOptionalNotation(className));
        inputData.put(UtilStrings.MEDIA_TYPE, mediaType);

        imports.add(classTypeData.getBaseClassImportPath());

        List sortedImports = imports.stream()
                                 .sorted(ImportComparator.of())
                                 .collect(Collectors.toList());
        inputData.put("imports", sortedImports);

        inputData.put(UtilStrings.BASE_CLASS, classTypeData.getBaseClass());

        List sortedClassFields = classFields.stream()
                                     .sorted(Comparator.comparing(FieldDefinition::getPath))
                                     .collect(Collectors.toList());
        inputData.put(UtilStrings.CLASS_FIELDS, sortedClassFields);

        return inputData;
    }

    public HashMap<String, Object> getInputData(final ClassTypeData classTypeData, final Set<String> imports, final String className, final Set<FieldDefinition> classFields, final Set<LinkData> links,
        final String mediaType) {
        boolean hasLinks = links != null && links.size() > 0;

        if (hasLinks) {
            imports.addAll(MAP_IMPORTS);
        }

        final HashMap<String, Object> inputData = getInputData(classTypeData, imports, className, classFields, mediaType);
        if (hasLinks) {
            inputData.put("hasLinksWithResults", true);
            inputData.put("hasLinks", true);

            List<LinkData> sortedLinks = links.stream()
                                             .sorted(Comparator.comparing(LinkData::javaConstant))
                                             .collect(Collectors.toList());
            inputData.put(UtilStrings.LINKS, sortedLinks);
        }
        return inputData;
    }

}
