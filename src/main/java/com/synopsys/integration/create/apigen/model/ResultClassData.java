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

import static com.synopsys.integration.create.apigen.data.UtilStrings.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.MANUAL_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.data.UtilStrings.TEMPORARY_CLASS_PATH_PREFIX;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassCategoryData;
import com.synopsys.integration.create.apigen.data.ClassSourceEnum;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.UtilStrings;

public class ResultClassData {

    private final String resultClass;
    private final ClassCategories classCategories;
    private final ClassTypeEnum classType;
    private final ClassSourceEnum classSource;

    public ResultClassData(final String resultClass, final ClassCategories classCategories) {
        this.resultClass = resultClass;
        this.classCategories = classCategories;
        final ClassCategoryData classCategoryData = classCategories.computeData(resultClass);
        this.classSource = classCategoryData.getSource();
        this.classType = classCategoryData.getType();
    }

    public String getResultClass() {
        return resultClass;
    }

    public String getResultImportType() {
        String resultImportType = null;
        if (resultClass != null) {
            // result classes must extend BlackDuckResponse
            if (classType.isView()) {
                resultImportType = UtilStrings.VIEW;
            } else if (classType.isResponse()) {
                resultImportType = UtilStrings.RESPONSE;
            }
        }
        return resultImportType;
    }

    public String getResultImportPath() {
        String resultImportPath = null;
        if (resultClass != null) {
            if (classSource.isManual()) {
                resultImportPath = MANUAL_CLASS_PATH_PREFIX;
            } else if (classSource.isTemporary()) {
                resultImportPath = TEMPORARY_CLASS_PATH_PREFIX;
            } else {
                resultImportPath = GENERATED_CLASS_PATH_PREFIX;
            }
        }
        return resultImportPath;
    }

    public boolean shouldAddImport() {
        if (resultClass != null && (getResultImportPath() == null || getResultImportType() == null)) {
            return false;
        }
        return true;
    }

}
