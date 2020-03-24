package com.synopsys.integration.blackduck.api.generated.component;

import java.util.List;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectVersionComparisonItemsComponentMatchTypesType;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ProjectVersionComparisonItemsComponentView extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private java.util.List<ProjectVersionComparisonItemsComponentMatchTypesType> matchTypes;

    public java.util.List<ProjectVersionComparisonItemsComponentMatchTypesType> getMatchTypes() {
	return matchTypes;
    }

    public void setMatchTypes(java.util.List<ProjectVersionComparisonItemsComponentMatchTypesType> matchTypes) {
	this.matchTypes = matchTypes;
    }


    public String getMediaType() {
	return mediaType;
    }

}
