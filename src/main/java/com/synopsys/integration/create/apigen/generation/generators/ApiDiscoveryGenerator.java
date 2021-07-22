/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.generation.generators;

import com.synopsys.integration.create.apigen.GeneratorRunner;
import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ImportComparator;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.ClassNameManager;
import com.synopsys.integration.create.apigen.model.ApiPathData;
import com.synopsys.integration.create.apigen.model.ResultClassData;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ApiDiscoveryGenerator {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorRunner.class);
    private final ClassCategories classCategories;
    private final NameAndPathManager nameAndPathManager;
    private final GeneratorDataManager generatorDataManager;
    private final ClassNameManager classNameManager;

    @Autowired
    public ApiDiscoveryGenerator(ClassCategories classCategories, NameAndPathManager nameAndPathManager, GeneratorDataManager generatorDataManager, ClassNameManager classNameManager) {
        this.classCategories = classCategories;
        this.nameAndPathManager = nameAndPathManager;
        this.generatorDataManager = generatorDataManager;
        this.classNameManager = classNameManager;
    }

    public void createDiscoveryFile(String discoveryDirectoryPath, Template template) {
        Map<String, Object> model = new HashMap<>();
        model.put("discoveryPackage", UtilStrings.GENERATED_DISCOVERY_PACKAGE);

        Set<String> imports = new HashSet<>();
        Set<ApiPathData> unsortedApiData = nameAndPathManager.getApiDiscoveryData();
        List<ApiPathData> sortedApiData = new ArrayList<>(unsortedApiData);
        Collections.sort(sortedApiData, Comparator.comparing(ApiPathData::getPath));

        model.put("apiPathData", sortedApiData);
        for (ApiPathData data : sortedApiData) {
            String resultClass = data.getResultClass();
            ResultClassData resultClassData = new ResultClassData(resultClass, classCategories);
            String resultImportPath = resultClassData.getResultImportPath();
            String resultImportType = resultClassData.getResultImportType();
            String importPackage = resultImportPath + resultImportType + "." + resultClass;
            if (null == importPackage || null == resultImportType) {
                logger.info("couldn't find package for: " + resultClass);
            } else {
                imports.add(importPackage);
                imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.HTTP_URL));
                imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.BLACKDUCK_RESPONSE));
                imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.URL_SINGLE_RESPONSE));
                imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.URL_MULTIPLE_RESPONSES));
                imports.add(classNameManager.getFullyQualifiedClassName(ClassNameManager.BLACKDUCK_PATH));
            }
        }
        List sortedImports = imports.stream()
                .sorted(ImportComparator.of())
                .collect(Collectors.toList());
        model.put("imports", sortedImports);

        generatorDataManager.addFileData(new FileGenerationData("ApiDiscovery", template, model, discoveryDirectoryPath));
    }

}
