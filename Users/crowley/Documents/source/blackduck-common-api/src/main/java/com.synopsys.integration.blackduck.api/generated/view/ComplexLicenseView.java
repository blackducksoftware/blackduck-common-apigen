package com.synopsys.integration.blackduck.api.generated.view;

import java.util.List;
import com.synopsys.integration.blackduck.api.generated.view.ProjectVersionLicenseLicensesView;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectVersionLicenseTypeType;

@Deprecated
/**
* ComplexLicenseView is now called ProjectVersionLicenseView
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ComplexLicenseView extends BlackDuckView {
	public static final String mediaType = "application/vnd.blackducksoftware.component-detail-4+json";

    private String licenseDisplay;
    private java.util.List<ProjectVersionLicenseLicensesView> licenses;
    private ProjectVersionLicenseTypeType type;

    public String getLicenseDisplay() {
	return licenseDisplay;
    }

    public void setLicenseDisplay(String licenseDisplay) {
	this.licenseDisplay = licenseDisplay;
    }

    public java.util.List<ProjectVersionLicenseLicensesView> getLicenses() {
	return licenses;
    }

    public void setLicenses(java.util.List<ProjectVersionLicenseLicensesView> licenses) {
	this.licenses = licenses;
    }

    public ProjectVersionLicenseTypeType getType() {
	return type;
    }

    public void setType(ProjectVersionLicenseTypeType type) {
	this.type = type;
    }


    public String getMediaType() {
	return mediaType;
    }

}
