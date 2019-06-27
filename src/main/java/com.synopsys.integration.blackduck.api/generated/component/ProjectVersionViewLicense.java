package com.synopsys.integration.blackduck.api.generated.component;

    import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
    import com.synopsys.integration.blackduck.api.generated.component.ProjectVersionViewLicenseLicense;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ProjectVersionViewLicense extends BlackDuckComponent {
    private String type;
    private ProjectVersionViewLicenseLicense license;
    private String licenseDisplay;

    public String getType() {
    return type;
    }

    public void setType(String type) {
    this.type = type;
    }

    public ProjectVersionViewLicenseLicense getLicense() {
    return license;
    }

    public void setLicense(ProjectVersionViewLicenseLicense license) {
    this.license = license;
    }

    public String getLicenseDisplay() {
    return licenseDisplay;
    }

    public void setLicenseDisplay(String licenseDisplay) {
    this.licenseDisplay = licenseDisplay;
    }

}
