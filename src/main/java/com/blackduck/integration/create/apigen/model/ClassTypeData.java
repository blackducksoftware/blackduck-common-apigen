/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import com.blackduck.integration.create.apigen.generation.finder.FilePathUtil;
import com.blackduck.integration.create.apigen.data.ClassTypeEnum;
import com.blackduck.integration.create.apigen.data.UtilStrings;

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
