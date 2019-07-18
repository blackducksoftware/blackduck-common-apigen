package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.ComplexLicenseEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomLicenseView extends BlackDuckComponent {
    private String license;
    private String licenseDisplay;
    private ComplexLicenseEnum licenseType;
    private java.util.List<VersionBomLicenseView> licenses;

    public String getLicense() {
        return license;
    }

    public void setLicense(final String license) {
        this.license = license;
    }

    public String getLicenseDisplay() {
        return licenseDisplay;
    }

    public void setLicenseDisplay(final String licenseDisplay) {
        this.licenseDisplay = licenseDisplay;
    }

    public ComplexLicenseEnum getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(final ComplexLicenseEnum licenseType) {
        this.licenseType = licenseType;
    }

    public java.util.List<VersionBomLicenseView> getLicenses() {
        return licenses;
    }

    public void setLicenses(final java.util.List<VersionBomLicenseView> licenses) {
        this.licenses = licenses;
    }

}
