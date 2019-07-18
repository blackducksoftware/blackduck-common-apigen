package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.component.NameValuePairView;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicySummaryStatusEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentVersionPolicyViolationDetails extends BlackDuckComponent {
    private PolicySummaryStatusEnum name;
    private java.util.List<NameValuePairView> severityLevels;

    public PolicySummaryStatusEnum getName() {
        return name;
    }

    public void setName(final PolicySummaryStatusEnum name) {
        this.name = name;
    }

    public java.util.List<NameValuePairView> getSeverityLevels() {
        return severityLevels;
    }

    public void setSeverityLevels(final java.util.List<NameValuePairView> severityLevels) {
        this.severityLevels = severityLevels;
    }

}
