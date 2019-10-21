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

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

public class MissingFieldAndLinkHelper {
    private final List<FieldDefinition> missingFields;
    private final List<LinkDefinition> missingLinks;

    public MissingFieldAndLinkHelper() {
        this.missingFields = new ArrayList<>();
        this.missingLinks = new ArrayList<>();
    }

    public void addField(final FieldDefinition field) {
        missingFields.add(field);
    }

    public void addLink(final LinkDefinition link) {
        missingLinks.add(link);
    }

    public List<FieldDefinition> getMissingFields() {
        return this.missingFields;
    }

    public List<LinkDefinition> getMissingLinks() {
        return this.missingLinks;
    }
}
