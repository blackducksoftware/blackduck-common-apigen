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

public class UtilStrings {

    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String CORE_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.core.";
    public static final String MANUAL_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.manual.";

    public static final String GENERATED_ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.ENUMERATION;
    public static final String GENERATED_VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.VIEW;
    public static final String GENERATED_COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.COMPONENT;
    public static final String GENERATED_RESPONSE_PACKAGE = GENERATED_CLASS_PATH_PREFIX + UtilStrings.RESPONSE;
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String RESPONSE_BASE_CLASS = "BlackDuckResponse";
    public static final String CLASS_NAME = "className";
    public static final String IMPORT_PATH = "importPath";
    public static final String PACKAGE_NAME = "packageName";
    public static final String PARENT_CLASS = "parentClass";
    public static final String MEDIA_TYPE = "mediaType";
    public static final String BLACKDUCK_COMMON_API_BASE_DIRECTORY = FreeMarkerHelper.getBaseDirectory().getAbsolutePath();
    public static final String ENUM_DIRECTORY_SUFFIX = "/enumeration";
    public static final String VIEW_DIRECTORY_SUFFIX = "/view";
    public static final String RESPONSE_DIRECTORY_SUFFIX = "/response";
    public static final String COMPONENT_DIRECTORY_SUFFIX = "/component";
    public static final String PATH_TO_VIEW_FILES = BLACKDUCK_COMMON_API_BASE_DIRECTORY + VIEW_DIRECTORY_SUFFIX;
    public static final String PATH_TO_COMPONENT_FILES = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;
    public static final String PATH_TO_ENUM_FILES = BLACKDUCK_COMMON_API_BASE_DIRECTORY + ENUM_DIRECTORY_SUFFIX;

    public static final String BIG_DECIMAL = "BigDecimal";
    public static final String JAVA_BIG_DECIMAL = "java.math." + BIG_DECIMAL;
    public static final String JAVA_LIST = "java.util.List<";
    public static final String LIST = "List<";
    public static final String ARRAY = "Array";
    public static final String STRING = "String";
    public static final String NUMBER = "Number";
    public static final String DATA = "data";
    public static final String META = "_meta";
    public static final String OBJECT = "Object";
    public static final String INTEGER = "Integer";

    public static final String LINKS = "links";
    public static final String FIELDS = "fields";

    public static final String VIEW = "view";
    public static final String COMPONENT = "component";
    public static final String RESPONSE = "response";
    public static final String ENUMERATION = "enumeration";
    public static final String ENUM = "Type";

}
