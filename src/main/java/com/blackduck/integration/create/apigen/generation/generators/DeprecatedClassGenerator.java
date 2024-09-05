/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.blackduck.integration.create.apigen.generation.FileGenerationData;
import com.blackduck.integration.create.apigen.generation.GeneratorDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackduck.integration.create.apigen.data.DeprecatedClassData;
import com.blackduck.integration.create.apigen.data.UtilStrings;

import freemarker.template.Template;

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
