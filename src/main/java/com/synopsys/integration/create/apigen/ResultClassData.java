package com.synopsys.integration.create.apigen;

import static com.synopsys.integration.create.apigen.Generator.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.Generator.MANUAL_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.MissingDependencyIdentifier.CLASS_CATEGORIES;

import com.synopsys.integration.create.apigen.helper.UtilStrings;

public class ResultClassData {

    private final String resultClass;

    public ResultClassData(final String resultClass) {
        this.resultClass = resultClass;
    }

    public String getResultClass() {
        return resultClass;
    }

    public String getResultImportType() {
        String resultImportType = null;
        if (resultClass != null) {
            if (CLASS_CATEGORIES.isView(resultClass)) {
                resultImportType = UtilStrings.VIEW;
            } else if (CLASS_CATEGORIES.isResponse(resultClass)) {
                resultImportType = UtilStrings.RESPONSE;
            } else if (CLASS_CATEGORIES.isComponent(resultClass)) {
                resultImportType = UtilStrings.COMPONENT;
            }
        }
        return resultImportType;
    }

    public String getResultImportPath() {
        String resultImportPath = null;
        if (resultClass != null) {
            if (CLASS_CATEGORIES.isManual(resultClass)) {
                resultImportPath = MANUAL_CLASS_PATH_PREFIX;
            } else if (CLASS_CATEGORIES.isThrowaway(resultClass)) {
                resultImportPath = MANUAL_CLASS_PATH_PREFIX + "throwaway.generated.";
            } else if (CLASS_CATEGORIES.isGenerated(resultClass)) {
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
