package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class AssignableUserView extends BlackDuckResponse {
    private Boolean active;
    private String email;
    private String firstName;
    private String lastName;
    private String name;
    private String user;

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

}
