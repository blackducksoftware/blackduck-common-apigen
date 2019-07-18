package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.component.LicenseFamilySummaryView;
import com.synopsys.integration.blackduck.api.manual.enumeration.ComplexLicenseEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComplexLicenseView extends BlackDuckView {
    private String license;
    private String licenseDisplay;
    private LicenseFamilySummaryView licenseFamilySummary;
    private java.util.List<ComplexLicenseView> licenses;
    private String name;
    private String ownership;
    private ComplexLicenseEnum type;

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

    public LicenseFamilySummaryView getLicenseFamilySummary() {
        return licenseFamilySummary;
    }

    public void setLicenseFamilySummary(final LicenseFamilySummaryView licenseFamilySummary) {
        this.licenseFamilySummary = licenseFamilySummary;
    }

    public java.util.List<ComplexLicenseView> getLicenses() {
        return licenses;
    }

    public void setLicenses(final java.util.List<ComplexLicenseView> licenses) {
        this.licenses = licenses;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(final String ownership) {
        this.ownership = ownership;
    }

    public ComplexLicenseEnum getType() {
        return type;
    }

    public void setType(final ComplexLicenseEnum type) {
        this.type = type;
    }

}
