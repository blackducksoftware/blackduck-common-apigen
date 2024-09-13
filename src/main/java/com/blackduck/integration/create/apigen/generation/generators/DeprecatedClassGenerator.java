/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import com.blackduck.integration.create.apigen.data.DeprecatedClassData;
import com.blackduck.integration.create.apigen.data.UtilStrings;
import com.blackduck.integration.create.apigen.generation.FileGenerationData;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class DeprecatedClassGenerator {
    private final Set<DeprecatedClassData> deprecatedClasses;
    private final GeneratorDataManager generatorDataManager;

    @Autowired
    public DeprecatedClassGenerator(GeneratorDataManager generatorDataManager) {
        this.deprecatedClasses = new HashSet<>();
        this.generatorDataManager = generatorDataManager;
    }

    public void addDeprecatedClass(String swaggerName, String apigenName, Template template, Map<String, Object> input, String destination, String packageOverride) {
        Map<String, Object> newInput = new HashMap<>();
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            newInput.put(entry.getKey(), entry.getValue());
        }
        newInput.put(UtilStrings.CLASS_NAME, swaggerName);
        newInput.put("hasNewName", true);
        newInput.put("newName", apigenName);
        newInput.put("isDeprecated", true);
        newInput.put(UtilStrings.PACKAGE_NAME, packageOverride);
        for (DeprecatedClassData deprecatedClass : deprecatedClasses) {
            if (deprecatedClass.getOldName().equals(swaggerName)) {
                return;
            }
        }
        deprecatedClasses.add(new DeprecatedClassData(swaggerName, apigenName, template, newInput, destination));
    }

    public void generateDeprecatedClasses() {
        for (DeprecatedClassData deprecatedClassData : deprecatedClasses) {
            generatorDataManager.addFileData(new FileGenerationData(deprecatedClassData.getOldName(), deprecatedClassData.getTemplate(), deprecatedClassData.getInput(), deprecatedClassData.getDestination()));
        }
    }
}
