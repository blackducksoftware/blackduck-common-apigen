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

import static com.synopsys.integration.create.apigen.helper.UtilStrings.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.helper.UtilStrings.MANUAL_CLASS_PATH_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;

public class ResultClassData {

    private final String resultClass;
    private final ClassCategories classCategories;

    @Autowired
    public ResultClassData(final String resultClass, final ClassCategories classCategories) {
        this.resultClass = resultClass;
        this.classCategories = classCategories;
    }

    public String getResultClass() {
        return resultClass;
    }

    public String getResultImportType() {
        String resultImportType = null;
        if (resultClass != null) {
            if (classCategories.isView(resultClass)) {
                resultImportType = UtilStrings.VIEW;
            } else if (classCategories.isResponse(resultClass)) {
                resultImportType = UtilStrings.RESPONSE;
            } else if (classCategories.isComponent(resultClass)) {
                resultImportType = UtilStrings.COMPONENT;
            }
        }
        return resultImportType;
    }

    public String getResultImportPath() {
        String resultImportPath = null;
        if (resultClass != null) {
            if (classCategories.isManual(resultClass)) {
                resultImportPath = MANUAL_CLASS_PATH_PREFIX;
            } else if (classCategories.isThrowaway(resultClass)) {
                resultImportPath = MANUAL_CLASS_PATH_PREFIX + "throwaway.generated.";
            } else if (classCategories.isGenerated(resultClass)) {
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
