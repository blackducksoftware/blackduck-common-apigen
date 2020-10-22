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
    private String type;
    private final Set<String> allowedValues;
    private boolean optional;

    private final MissingFieldsAndLinks missingFieldsAndLinks;

    public FieldDefinitionBuilder(final FieldData fieldData, final Set<String> allowedValues, final MissingFieldsAndLinks missingFieldsAndLinks) {
        this.path = fieldData.getProcessedPath();
        this.type = fieldData.getProcessedType();
        this.optional = fieldData.isOptional();
        this.allowedValues = allowedValues;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
    }

    public FieldDefinition build() {

        final FieldDefinition fieldDefinition;
        if (allowedValues == null) {
            fieldDefinition = new FieldDefinition(path, type, optional);
        } else {
            fieldDefinition = new FieldDefinition(path, type, optional, allowedValues);
        }

        final Set<FieldDefinition> missingFields = missingFieldsAndLinks.getMissingFields(NameParser.getNonVersionedName(type));
        fieldDefinition.addSubFields(missingFields);

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

    public Set<String> getAllowedValues() {
        return allowedValues;
    }
}
