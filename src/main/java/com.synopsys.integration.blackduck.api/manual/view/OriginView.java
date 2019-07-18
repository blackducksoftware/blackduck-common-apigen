package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.enumeration.OriginSourceEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class OriginView extends BlackDuckView {
    private ComplexLicenseView license;
    private String originId;
    private String originName;
    private java.util.Date releasedOn;
    private OriginSourceEnum source;
    private String versionName;

    public ComplexLicenseView getLicense() {
        return license;
    }

    public void setLicense(final ComplexLicenseView license) {
        this.license = license;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(final String originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(final String originName) {
        this.originName = originName;
    }

    public java.util.Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(final java.util.Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    public OriginSourceEnum getSource() {
        return source;
    }

    public void setSource(final OriginSourceEnum source) {
        this.source = source;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(final String versionName) {
        this.versionName = versionName;
    }

}