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
package com.synopsys.integration.create.apigen.model;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.parser.NameParser;

public class FieldData {
    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private static final Set<String> javaKeyWords = UtilStrings.getJavaKeyWords();
    private static final Set<String> dateSuffixes = UtilStrings.getDateSuffixes();

    private final String fieldDefinitionName;
    private String path;
    private final String type;
    private final boolean hasSubFields;
    private final boolean isArray;

    @Autowired
    public FieldData(final String path, final String type, final String fieldDefinitionName, final boolean hasSubFields, final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager) {
        this.fieldDefinitionName = fieldDefinitionName;
        this.path = path;
        this.type = type;
        this.hasSubFields = hasSubFields;
        this.isArray = type.equals(UtilStrings.ARRAY);
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getProcessedPath() {
        if (javaKeyWords.contains(path)) {
            path = path + "_";
        }
        path = typeTranslator.getSimplifiedClassName(path);
        return path.replace("[]", "");
    }

    public String getProcessedType() {
        final String mediaVersion = NameParser.getMediaVersion(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = NameParser.getNonVersionedName(fieldDefinitionName);

        // Deal with fields of type 'Number'
        if (type.equals(UtilStrings.NUMBER)) {
            return UtilStrings.BIG_DECIMAL;
        }

        // Deal with Date fields
        for (String dateSuffix : dateSuffixes) {
            if (path.endsWith(dateSuffix)) {
                return UtilStrings.JAVA_DATE;
            }
        }

        // Deal with special Swaggerhub - Apigen naming convention conflicts
        final String swaggerName = typeTranslator.getFieldSwaggerName(nonVersionedFieldDefinitionName, path, type);
        if (swaggerName != null) {
            return swaggerName;
        }

        if ((type.equals(UtilStrings.OBJECT) || type.equals(UtilStrings.ARRAY)) && hasSubFields) {
            // append subclass to create new field data type
            String processedType = NameParser.reorderViewInName(nonVersionedFieldDefinitionName + StringUtils.capitalize(getProcessedPath()));
            processedType = typeTranslator.getSimplifiedClassName(processedType);

            if (mediaVersion != null) {
                return processedType + "V" + mediaVersion;
            }
            return processedType;
        }
        return typeTranslator.getSimplifiedClassName(type);
    }

    public String getNonVersionedFieldDefinitionName() {
        return NameParser.getNonVersionedName(fieldDefinitionName);
    }

    public boolean isArray() {
        return isArray;
    }

}
