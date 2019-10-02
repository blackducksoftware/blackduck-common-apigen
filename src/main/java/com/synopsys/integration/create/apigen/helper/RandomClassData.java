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
import static com.synopsys.integration.create.apigen.helper.UtilStrings.COMPONENT_DIRECTORY_SUFFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.CORE_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.GENERATED_COMPONENT_PACKAGE;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.GENERATED_RESPONSE_PACKAGE;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.GENERATED_VIEW_PACKAGE;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.RESPONSE_BASE_CLASS;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.RESPONSE_DIRECTORY_SUFFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.VIEW_BASE_CLASS;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.VIEW_DIRECTORY_SUFFIX;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassCategoryData;
import com.synopsys.integration.create.apigen.definitions.ClassSourceEnum;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;

public class RandomClassData {

    final String packageName;
    final String destinationSuffix;
    final String importPath;
    final String parentClass;

    public RandomClassData(final String linkClassName, final ClassCategories classCategories) {
        final ClassCategoryData classCategoryData = new ClassCategoryData(linkClassName, classCategories);
        final ClassSourceEnum classSource = classCategoryData.getSource();
        final ClassTypeEnum classType = classCategoryData.getType();
        if (classType.isView()) {
            packageName = GENERATED_VIEW_PACKAGE;
            destinationSuffix = VIEW_DIRECTORY_SUFFIX;
            parentClass = VIEW_BASE_CLASS;
        } else if (classType.isResponse()) {
            packageName = GENERATED_RESPONSE_PACKAGE;
            destinationSuffix = RESPONSE_DIRECTORY_SUFFIX;
            parentClass = RESPONSE_BASE_CLASS;
        } else {
            packageName = GENERATED_COMPONENT_PACKAGE;
            destinationSuffix = COMPONENT_DIRECTORY_SUFFIX;
            parentClass = COMPONENT_BASE_CLASS;
        }
        importPath = CORE_CLASS_PATH_PREFIX + parentClass;
    }

    public String getDestinationSuffix() {
        return destinationSuffix;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getImportPath() {
        return importPath;
    }

    public String getParentClass() {
        return parentClass;
    }
}
