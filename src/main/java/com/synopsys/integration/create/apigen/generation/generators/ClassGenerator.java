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
package com.synopsys.integration.create.apigen.generation.generators;

import java.io.IOException;

import com.synopsys.integration.create.apigen.model.FieldDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class ClassGenerator {

    public abstract boolean isApplicable(FieldDefinition field);

    public abstract void generateClass(FieldDefinition field, String responseMediaType, Template template) throws Exception;

    public abstract Template getTemplate(Configuration config) throws IOException;
}
