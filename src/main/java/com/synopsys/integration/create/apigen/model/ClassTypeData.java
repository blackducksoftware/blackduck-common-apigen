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
package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;

public class ClassTypeData {

    private String packageName;
    private String baseClass;
    private String baseClassImportPath;
    private String pathToOutputDirectory;

    public ClassTypeData(ClassTypeEnum classType, FilePathUtil filePathUtil) {
        if (classType.isView()) {
            this.packageName = UtilStrings.GENERATED_VIEW_PACKAGE;
            this.baseClass = UtilStrings.VIEW_BASE_CLASS;
            this.baseClassImportPath = UtilStrings.CORE_CLASS_PATH_PREFIX + baseClass;
            this.pathToOutputDirectory = filePathUtil.getOutputPathToViewFiles();
        } else if (classType.isResponse()) {
            this.packageName = UtilStrings.GENERATED_RESPONSE_PACKAGE;
            this.baseClass = UtilStrings.RESPONSE_BASE_CLASS;
            this.baseClassImportPath = UtilStrings.CORE_CLASS_PATH_PREFIX + baseClass;
            this.pathToOutputDirectory = filePathUtil.getOutputPathToResponseFiles();
        } else {
            this.packageName = UtilStrings.GENERATED_COMPONENT_PACKAGE;
            this.baseClass = UtilStrings.COMPONENT_BASE_CLASS;
            this.baseClassImportPath = UtilStrings.CORE_CLASS_PATH_PREFIX + baseClass;
            this.pathToOutputDirectory = filePathUtil.getOutputPathToComponentFiles();
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public String getBaseClass() {
        return baseClass;
    }

    public String getBaseClassImportPath() {
        return baseClassImportPath;
    }

    public String getPathToOutputDirectory() {
        return pathToOutputDirectory;
    }
}
