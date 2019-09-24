package com.synopsys.integration.create.apigen.helper;

import static com.synopsys.integration.create.apigen.Generator.COMPONENT_BASE_CLASS;
import static com.synopsys.integration.create.apigen.Generator.COMPONENT_DIRECTORY_SUFFIX;
import static com.synopsys.integration.create.apigen.Generator.CORE_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.Generator.GENERATED_COMPONENT_PACKAGE;
import static com.synopsys.integration.create.apigen.Generator.GENERATED_RESPONSE_PACKAGE;
import static com.synopsys.integration.create.apigen.Generator.GENERATED_VIEW_PACKAGE;
import static com.synopsys.integration.create.apigen.Generator.RESPONSE_BASE_CLASS;
import static com.synopsys.integration.create.apigen.Generator.RESPONSE_DIRECTORY_SUFFIX;
import static com.synopsys.integration.create.apigen.Generator.VIEW_BASE_CLASS;
import static com.synopsys.integration.create.apigen.Generator.VIEW_DIRECTORY_SUFFIX;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;

public class RandomClassData {

    final String packageName;
    final String destinationSuffix;
    final String importPath;
    final String parentClass;

    public RandomClassData(String linkClassName, ClassCategories classCategories) {
        if (classCategories.isView(linkClassName)) {
            packageName = GENERATED_VIEW_PACKAGE;
            destinationSuffix = VIEW_DIRECTORY_SUFFIX;
            parentClass = VIEW_BASE_CLASS;
        } else if (classCategories.isResponse(linkClassName)) {
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
