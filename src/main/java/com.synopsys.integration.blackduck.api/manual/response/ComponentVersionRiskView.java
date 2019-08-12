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
package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.manual.component.ActivityDataView;
import com.synopsys.integration.blackduck.api.manual.component.VersionDataView;
import com.synopsys.integration.blackduck.api.manual.view.RiskProfileView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentVersionRiskView extends BlackDuckResponse {
    private ActivityDataView activityData;
    private RiskProfileView riskData;
    private VersionDataView versionData;

    public ActivityDataView getActivityData() {
        return activityData;
    }

    public void setActivityData(final ActivityDataView activityData) {
        this.activityData = activityData;
    }

    public RiskProfileView getRiskData() {
        return riskData;
    }

    public void setRiskData(final RiskProfileView riskData) {
        this.riskData = riskData;
    }

    public VersionDataView getVersionData() {
        return versionData;
    }

    public void setVersionData(final VersionDataView versionData) {
        this.versionData = versionData;
    }

}
