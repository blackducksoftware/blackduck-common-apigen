package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class AssignableUserGroupView extends BlackDuckResponse {
    private Boolean active;
    private String name;
    private String usergroup;

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUsergroup() {
        return usergroup;
    }

    public void setUsergroup(final String usergroup) {
        this.usergroup = usergroup;
    }

}
