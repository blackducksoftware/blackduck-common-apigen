package com.synopsys.integration.blackduck.api.generated.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class CurrentVersionViewV4 extends BlackDuckResponse {
	public static final String mediaType = "application/vnd.blackducksoftware.status-4+json";

    private String version;

    public String getVersion() {
	return version;
    }

    public void setVersion(String version) {
	this.version = version;
    }


    public String getMediaType() {
	return mediaType;
    }

}
