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

import java.util.Map;

import freemarker.template.Template;

public class DeprecatedClassData {

    private final String oldName;
    private final String apigenName;
    private final Template template;
    private final Map<String, Object> input;
    private final String destination;

    public DeprecatedClassData(final String oldName, final String apigenName, final Template template, final Map<String, Object> input, final String destination) {
        this.oldName = oldName;
        this.apigenName = apigenName;
        this.template = template;
        this.input = input;
        this.destination = destination;
    }

    public String getOldName() {
        return oldName;
    }

    public String getApigenName() {
        return apigenName;
    }

    public Template getTemplate() {
        return template;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public String getDestination() {
        return destination;
    }
}
