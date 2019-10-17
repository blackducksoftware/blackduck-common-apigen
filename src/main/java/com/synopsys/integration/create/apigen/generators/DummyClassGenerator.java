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
package com.synopsys.integration.create.apigen.generators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassSourceEnum;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;
import com.synopsys.integration.create.apigen.definitions.UtilStrings;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.RandomClassData;

import freemarker.template.Template;

@Component
public class DummyClassGenerator {

    private final DataManager dataManager;
    private final ClassCategories classCategories;
    private final FreeMarkerHelper freeMarkerHelper;

    public DummyClassGenerator(final DataManager dataManager, final ClassCategories classCategories, final FreeMarkerHelper freeMarkerHelper) {
        this.dataManager = dataManager;
        this.classCategories = classCategories;
        this.freeMarkerHelper = freeMarkerHelper;
    }

    public void generateDummyClassesForReferencedButUndefinedObjects(final Template randomTemplate) throws Exception {
        for (final String linkClassName : dataManager.getLinkClassNames()) {
            final ClassSourceEnum classSource = classCategories.computeSource(linkClassName);
            final ClassTypeEnum classType = classCategories.computeType(linkClassName);
            if (!classSource.equals(ClassSourceEnum.MANUAL) && !classSource.equals(ClassSourceEnum.THROWAWAY) && !classType.equals(ClassTypeEnum.COMMON)) {
                final Map<String, Object> randomInput = new HashMap<>();
                randomInput.put(UtilStrings.CLASS_NAME, linkClassName);
                final RandomClassData randomClassData = new RandomClassData(linkClassName, classCategories);
                final String packageName = randomClassData.getPackageName();
                final String destinationSuffix = randomClassData.getDestinationSuffix();
                final String importPath = randomClassData.getImportPath();
                final String parentClass = randomClassData.getParentClass();
                randomInput.put(UtilStrings.PARENT_CLASS, parentClass);
                randomInput.put(UtilStrings.PACKAGE_NAME, packageName);
                randomInput.put(UtilStrings.IMPORT_PATH, importPath);

                if (!dataManager.getNonLinkClassNames().contains(linkClassName) && !dataManager.getRandomLinkClassNames().contains(linkClassName)) {
                    freeMarkerHelper.writeFile(linkClassName, randomTemplate, randomInput, UtilStrings.BLACKDUCK_COMMON_API_BASE_DIRECTORY + destinationSuffix);
                    dataManager.addRandomLinkClassName(linkClassName);
                }
            }
        }

    }
}
