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
