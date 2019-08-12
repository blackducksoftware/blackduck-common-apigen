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
package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.component.ComponentVersionPolicyViolationDetails;
import com.synopsys.integration.blackduck.api.manual.component.NameValuePairView;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicySummaryStatusEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomPolicyStatusView extends BlackDuckView {
    private ComponentVersionPolicyViolationDetails componentVersionPolicyViolationDetails;
    private java.util.List<NameValuePairView> componentVersionStatusCounts;
    private PolicySummaryStatusEnum overallStatus;
    private java.util.Date updatedAt;

    public ComponentVersionPolicyViolationDetails getComponentVersionPolicyViolationDetails() {
        return componentVersionPolicyViolationDetails;
    }

    public void setComponentVersionPolicyViolationDetails(final ComponentVersionPolicyViolationDetails componentVersionPolicyViolationDetails) {
        this.componentVersionPolicyViolationDetails = componentVersionPolicyViolationDetails;
    }

    public java.util.List<NameValuePairView> getComponentVersionStatusCounts() {
        return componentVersionStatusCounts;
    }

    public void setComponentVersionStatusCounts(final java.util.List<NameValuePairView> componentVersionStatusCounts) {
        this.componentVersionStatusCounts = componentVersionStatusCounts;
    }

    public PolicySummaryStatusEnum getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(final PolicySummaryStatusEnum overallStatus) {
        this.overallStatus = overallStatus;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
