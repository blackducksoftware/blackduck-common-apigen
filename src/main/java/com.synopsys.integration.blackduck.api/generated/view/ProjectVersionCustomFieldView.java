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
package com.synopsys.integration.blackduck.api.generated.view;

    import java.util.HashMap;
    import java.util.Map;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import java.math.BigDecimal;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectVersionCustomFieldTypeEnum;
    import com.synopsys.integration.blackduck.api.core.LinkResponse;
    import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
    import com.synopsys.integration.blackduck.api.generated.view.CustomFieldView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ProjectVersionCustomFieldView extends BlackDuckView {
    public static final Map
    <String, LinkResponse> links = new HashMap<>();

        public static final String CUSTOM_FIELD_OPTION_LIST_LINK = "custom-field-option-list";

            public static final LinkMultipleResponses<CustomFieldView> CUSTOM_FIELD_OPTION_LIST_LINK_RESPONSE = new LinkMultipleResponses<CustomFieldView>(CUSTOM_FIELD_OPTION_LIST_LINK, CustomFieldView.class);

    static {
            links.put(CUSTOM_FIELD_OPTION_LIST_LINK, CUSTOM_FIELD_OPTION_LIST_LINK_RESPONSE);
    }

    private java.util.List<String> values;
    private String description;
    private Boolean active;
    private Object _meta;
    private BigDecimal position;
    private String label;
    private ProjectVersionCustomFieldTypeEnum type;

    public java.util.List<String> getValues() {
    return values;
    }

    public void setValues(java.util.List<String> values) {
    this.values = values;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    public Boolean getActive() {
    return active;
    }

    public void setActive(Boolean active) {
    this.active = active;
    }

    public Object get_meta() {
    return _meta;
    }

    public void set_meta(Object _meta) {
    this._meta = _meta;
    }

    public BigDecimal getPosition() {
    return position;
    }

    public void setPosition(BigDecimal position) {
    this.position = position;
    }

    public String getLabel() {
    return label;
    }

    public void setLabel(String label) {
    this.label = label;
    }

    public ProjectVersionCustomFieldTypeEnum getType() {
    return type;
    }

    public void setType(ProjectVersionCustomFieldTypeEnum type) {
    this.type = type;
    }

}
