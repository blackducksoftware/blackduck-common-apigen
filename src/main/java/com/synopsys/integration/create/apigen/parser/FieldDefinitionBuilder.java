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
package com.synopsys.integration.create.apigen.parser;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class FieldDefinitionBuilder {
    private final String path;
    private final String type;
    private final Set<String> allowedValues;
    private final String nonVersionedFieldDefinitionName;
    private boolean optional;
    private final boolean isArray;
    private Set<FieldDefinition> subFields = Collections.emptySet();

    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final TypeTranslator typeTranslator;

    public FieldDefinitionBuilder(final FieldData fieldData, final Set<String> allowedValues, final MissingFieldsAndLinks missingFieldsAndLinks, final TypeTranslator typeTranslator) {
        this.path = fieldData.getProcessedPath();
        this.type = fieldData.getProcessedType();
        this.nonVersionedFieldDefinitionName = fieldData.getNonVersionedFieldDefinitionName();
        this.allowedValues = allowedValues;
        this.isArray = fieldData.isArray();
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.typeTranslator = typeTranslator;
    }

    public FieldDefinition build() {

        final FieldDefinition fieldDefinition;

        String nameOfEnum = nonVersionedFieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + UtilStrings.ENUM;
        nameOfEnum = typeTranslator.getSimplifiedClassName(nameOfEnum);

        //debug
        if (nameOfEnum.contains("ProjectVersionLicenseLicensesType")) {
            System.out.println("");
        }

        // For fields with type 'Array', change type to a Java List<E>
        if (isArray) {
            final String coreType = type.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : type;
            fieldDefinition = allowedValues == null ? new FieldDefinition(path, "java.util.List<" + coreType + ">", optional) : new FieldDefinition(path, "java.util.List<" + nameOfEnum + ">", optional, allowedValues);
        } else {
            if (allowedValues == null) {
                fieldDefinition = new FieldDefinition(path, type, optional);
            } else if (NumberUtils.isCreatable(allowedValues.iterator().next())) {
                fieldDefinition = new FieldDefinition(path, UtilStrings.INTEGER, optional);
            } else {
                fieldDefinition = new FieldDefinition(path, nameOfEnum, optional, allowedValues);
            }
        }
        fieldDefinition.addSubFields(subFields);
        final Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(NameParser.getNonVersionedName(type));
        if (missingFields.size() > 0) {
            fieldDefinition.addSubFields(missingFields);
        }
        return fieldDefinition;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(final boolean optional) {
        this.optional = optional;
    }

    public Set<FieldDefinition> getSubFields() {
        return subFields;
    }

    public void setSubFields(final Set<FieldDefinition> subFields) {
        this.subFields = subFields;
    }

    public Set<String> getAllowedValues() {
        return allowedValues;
    }

    public String getNonVersionedFieldDefinitionName() {
        return nonVersionedFieldDefinitionName;
    }
}
