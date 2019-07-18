package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;

public class AssignedUserRequest extends BlackDuckResponse {
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }
}
