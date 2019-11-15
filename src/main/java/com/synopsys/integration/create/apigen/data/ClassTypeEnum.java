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
package com.synopsys.integration.create.apigen.data;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public enum ClassTypeEnum {
    VIEW(UtilStrings.VIEW_BASE_CLASS),
    RESPONSE(UtilStrings.RESPONSE_BASE_CLASS),
    COMPONENT(UtilStrings.COMPONENT_BASE_CLASS),
    ENUM(null),
    COMMON(null),
    NON_ENUM_CONTAINING_TYPE(null),
    NULL(null);

    private final String importClass;

    private ClassTypeEnum(final String importClass) {
        this.importClass = importClass;
    }

    public Optional<String> getImportClass() {
        return Optional.ofNullable(importClass);
    }

    public String getFormattedValue() {
        return StringUtils.capitalize(this.toString().toLowerCase());
    }

    public boolean isView() {
        return this.equals(VIEW);
    }

    public boolean isResponse() {
        return this.equals(RESPONSE);
    }

    public boolean isComponent() {
        return this.equals(COMPONENT);
    }

    public boolean isEnum() {
        return this.equals(ENUM);
    }

    public boolean isCommon() {
        return this.equals(COMMON);
    }

    public boolean isNotCommonTypeOrEnum() {
        return (!this.isCommon() && !this.isEnum());
    }
}