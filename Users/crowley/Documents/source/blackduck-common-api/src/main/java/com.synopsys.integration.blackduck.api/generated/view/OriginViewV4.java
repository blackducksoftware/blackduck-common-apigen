package com.synopsys.integration.blackduck.api.generated.view;

import com.synopsys.integration.blackduck.api.generated.component.OriginLicenseView;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.core.LinkResponse;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.generated.enumeration.ComponentSourceType;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class OriginViewV4 extends BlackDuckView {
	public static final String mediaType = "application/vnd.blackducksoftware.component-detail-4+json";

    private OriginLicenseView license;
    private String originId;
    private ComponentSourceType source;
    private String originName;
    private String versionName;
    private java.util.Date releasedOn;

    public OriginLicenseView getLicense() {
	return license;
    }

    public void setLicense(OriginLicenseView license) {
	this.license = license;
    }

    public String getOriginId() {
	return originId;
    }

    public void setOriginId(String originId) {
	this.originId = originId;
    }

    public ComponentSourceType getSource() {
	return source;
    }

    public void setSource(ComponentSourceType source) {
	this.source = source;
    }

    public String getOriginName() {
	return originName;
    }

    public void setOriginName(String originName) {
	this.originName = originName;
    }

    public String getVersionName() {
	return versionName;
    }

    public void setVersionName(String versionName) {
	this.versionName = versionName;
    }

    public java.util.Date getReleasedOn() {
	return releasedOn;
    }

    public void setReleasedOn(java.util.Date releasedOn) {
	this.releasedOn = releasedOn;
    }


    public String getMediaType() {
	return mediaType;
    }

}
