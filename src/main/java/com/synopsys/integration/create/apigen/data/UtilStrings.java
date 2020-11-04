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
package com.synopsys.integration.create.apigen.data;

import java.util.HashSet;
import java.util.Set;

public class UtilStrings {

    public static final String GENERATED = "generated";
    public static final String MANUAL = "manual";
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String CORE_CLASS_PATH_PREFIX = GENERATED_CLASS_PATH_PREFIX.replace(GENERATED, "core");
    public static final String MANUAL_CLASS_PATH_PREFIX = GENERATED_CLASS_PATH_PREFIX.replace(GENERATED, MANUAL);
    public static final String DEPRECATED_CLASS_PATH_PREFIX = GENERATED_CLASS_PATH_PREFIX.replace(GENERATED, "generated.deprecated");
    public static final String TEMPORARY_CLASS_PATH_PREFIX = MANUAL_CLASS_PATH_PREFIX.replace(MANUAL, "manual.temporary");

    public static final String VIEW = "view";
    public static final String COMPONENT = "component";
    public static final String RESPONSE = "response";
    public static final String ENUMERATION = "enumeration";
    public static final String ENUM = "Type";

    public static final String GENERATED_ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String GENERATED_VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + VIEW;
    public static final String GENERATED_COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + COMPONENT;
    public static final String GENERATED_RESPONSE_PACKAGE = GENERATED_CLASS_PATH_PREFIX + RESPONSE;
    public static final String DEPRECATED_ENUM_PACKAGE = DEPRECATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String DEPRECATED_VIEW_PACKAGE = DEPRECATED_CLASS_PATH_PREFIX + VIEW;
    public static final String DEPRECATED_COMPONENT_PACKAGE = DEPRECATED_CLASS_PATH_PREFIX + COMPONENT;
    public static final String DEPRECATED_RESPONSE_PACKAGE = DEPRECATED_CLASS_PATH_PREFIX + RESPONSE;
    public static final String GENERATED_DISCOVERY_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "discovery";
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String RESPONSE_BASE_CLASS = "BlackDuckResponse";
    public static final String CLASS_NAME = "className";
    public static final String IMPORT_PATH = "importPath";
    public static final String PACKAGE_NAME = "packageName";
    public static final String BASE_CLASS = "baseClass";
    public static final String MEDIA_TYPE = "mediaType";
    public static final String MEDIA_TYPE_SUFFIX = "_json";
    public static final String DISCOVERY_DIRECTORY_SUFFIX = "/discovery";

    public static final String BIG_DECIMAL = "BigDecimal";
    public static final String JAVA_BIG_DECIMAL = "java.math." + BIG_DECIMAL;
    public static final String JAVA_LIST = "java.util.List<";
    public static final String LIST = "List<";
    public static final String OPTIONAL_WRAPPER = "Optional<";
    public static final String ARRAY = "Array";
    public static final String STRING = "String";
    public static final String NUMBER = "Number";
    public static final String DATA = "data";
    public static final String META = "_meta";
    public static final String OBJECT = "Object";
    public static final String INTEGER = "Integer";
    public static final String JAVA_DATE = "java.util.Date";

    public static final String LINKS = "links";
    public static final String FIELDS = "fields";
    public static final String CLASS_FIELDS = "classFields";

    public static final String RESPONSE_SPECIFICATION_JSON = "response-specification.json";
    public static final String REQUEST_SPECIFICATION_JSON = "request-specification.json";
    public static final String API = "api";
    public static final String GET = "GET";

    public static final String NEW_NAME = "newName";
    public static final String HAS_NEW_NAME = "hasNewName";

    public static final String ITEMS = "items";
    public static final String TOTAL_COUNT = "totalCount";

    public static final String[] DIGIT_STRINGS = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static Set<String> getJavaKeyWords() {
        final Set<String> javaKeyWords = new HashSet<>();

        javaKeyWords.add("default");

        return javaKeyWords;
    }

    public static Set<String> getDateSuffixes() {
        final Set<String> dateSuffixes = new HashSet<>();

        dateSuffixes.add("edOn");
        dateSuffixes.add("edAt");
        dateSuffixes.add("Date");

        return dateSuffixes;
    }

}
