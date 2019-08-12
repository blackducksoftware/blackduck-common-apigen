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
import com.synopsys.integration.blackduck.api.manual.component.VersionBomLicenseView;
import com.synopsys.integration.blackduck.api.manual.enumeration.MatchedFileUsagesEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.VersionBomComponentDiffComponentStateEnum;
import com.synopsys.integration.blackduck.api.manual.view.VersionBomComponentView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomComponentDiffView extends BlackDuckResponse {
    private VersionBomComponentView component;
    private VersionBomComponentDiffComponentStateEnum componentState;
    private VersionBomComponentDiffComponentStateEnum componentVersionState;
    private java.util.List<VersionBomLicenseView> leftLicenses;
    private java.util.List<MatchedFileUsagesEnum> leftUsages;

    public VersionBomComponentView getComponent() {
        return component;
    }

    public void setComponent(final VersionBomComponentView component) {
        this.component = component;
    }

    public VersionBomComponentDiffComponentStateEnum getComponentState() {
        return componentState;
    }

    public void setComponentState(final VersionBomComponentDiffComponentStateEnum componentState) {
        this.componentState = componentState;
    }

    public VersionBomComponentDiffComponentStateEnum getComponentVersionState() {
        return componentVersionState;
    }

    public void setComponentVersionState(final VersionBomComponentDiffComponentStateEnum componentVersionState) {
        this.componentVersionState = componentVersionState;
    }

    public java.util.List<VersionBomLicenseView> getLeftLicenses() {
        return leftLicenses;
    }

    public void setLeftLicenses(final java.util.List<VersionBomLicenseView> leftLicenses) {
        this.leftLicenses = leftLicenses;
    }

    public java.util.List<MatchedFileUsagesEnum> getLeftUsages() {
        return leftUsages;
    }

    public void setLeftUsages(final java.util.List<MatchedFileUsagesEnum> leftUsages) {
        this.leftUsages = leftUsages;
    }

}