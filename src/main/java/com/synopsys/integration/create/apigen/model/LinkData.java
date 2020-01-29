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
package com.synopsys.integration.create.apigen.model;

import java.util.Map;

import com.synopsys.integration.create.apigen.data.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.parser.NameParser;
import com.synopsys.integration.util.Stringable;

public class LinkData extends Stringable {
    public final String javaConstant;
    public final String label;
    private boolean hasMultipleResults;
    public String resultClass;
    public String linkType;
    private final Map<String, Map<String, LinkResponseDefinitions.LinkResponseDefinitionItem>> linkResponseDefinitionsList;

    public LinkData(final String label, final ResponseDefinition response, final LinkResponseDefinitions linkResponseDefinitions) {
        this.linkResponseDefinitionsList = linkResponseDefinitions.getDefinitions();
        this.label = label;
        this.javaConstant = label.toUpperCase().replace('-', '_') + "_LINK";
        try {
            final String nonVersionedResponseName = NameParser.getNonVersionedName(response.getName());
            final Map<String, LinkResponseDefinitions.LinkResponseDefinitionItem> linkResponseDefinitionsMap = linkResponseDefinitionsList.get(nonVersionedResponseName);
            final LinkResponseDefinitions.LinkResponseDefinitionItem linkResponseDefinitionItem = linkResponseDefinitionsMap.get(this.label);

            this.hasMultipleResults = linkResponseDefinitionItem.hasMultipleResults();

            final String result_class = linkResponseDefinitionItem.getResultClass();
            this.resultClass = result_class;

            if (result_class.equals(UtilStrings.STRING)) {
                this.linkType = "LinkStringResponse";
            } else {
                this.linkType = this.hasMultipleResults ? "LinkMultipleResponses<" + this.resultClass + ">" : "LinkSingleResponse<" + this.resultClass + ">";
            }
        } catch (final NullPointerException e) {
            this.hasMultipleResults = false;
            this.resultClass = null;
            this.linkType = null;
        }
    }

    public String javaConstant() { return this.javaConstant; }

    public String getLabel() { return this.label; }

    public String resultClass() { return this.resultClass; }

    public String linkType() { return this.linkType; }

    public boolean hasMultipleResults() { return this.hasMultipleResults; }

}

