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
package com.synopsys.integration.create.apigen.generation.generators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.DeprecatedClassData;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.parser.NameParser;

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
        newInput.put("newName", NameParser.getNonVersionedName(apigenName));
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
