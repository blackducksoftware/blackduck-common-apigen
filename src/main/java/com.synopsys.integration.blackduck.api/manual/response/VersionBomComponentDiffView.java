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