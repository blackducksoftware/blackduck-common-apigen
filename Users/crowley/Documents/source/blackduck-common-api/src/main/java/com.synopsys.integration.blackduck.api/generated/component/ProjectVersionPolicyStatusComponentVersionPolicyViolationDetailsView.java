package com.synopsys.integration.blackduck.api.generated.component;

import java.util.List;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.component.NameValuePairView;
import com.synopsys.integration.blackduck.api.generated.enumeration.PolicyStatusType;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import java.util.Optional;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private PolicyStatusType name;
    private java.util.List<NameValuePairView> severityLevels;

    public PolicyStatusType getName() {
	return name;
    }

    public void setName(PolicyStatusType name) {
	this.name = name;
    }

    public java.util.List<NameValuePairView> getSeverityLevels() {
	return severityLevels;
    }

    public void setSeverityLevels(java.util.List<NameValuePairView> severityLevels) {
	this.severityLevels = severityLevels;
    }


    public String getMediaType() {
	return mediaType;
    }

}
