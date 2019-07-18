package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;

public class ProjectMappingView extends BlackDuckResponse {
    private String applicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

}
