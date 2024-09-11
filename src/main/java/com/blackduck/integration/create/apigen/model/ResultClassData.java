/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import static com.blackduck.integration.create.apigen.data.UtilStrings.GENERATED_CLASS_PATH_PREFIX;
import static com.blackduck.integration.create.apigen.data.UtilStrings.MANUAL_CLASS_PATH_PREFIX;
import static com.blackduck.integration.create.apigen.data.UtilStrings.TEMPORARY_CLASS_PATH_PREFIX;

import com.blackduck.integration.create.apigen.data.ClassCategories;
import com.blackduck.integration.create.apigen.data.ClassCategoryData;
import com.blackduck.integration.create.apigen.data.ClassSourceEnum;
import com.blackduck.integration.create.apigen.data.ClassTypeEnum;
import com.blackduck.integration.create.apigen.data.UtilStrings;

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
