package com.synopsys.integration.blackduck.api.generated.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import java.util.Optional;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class UserGroupProjectsViewV4 extends BlackDuckResponse {
	public static final String mediaType = "application/vnd.blackducksoftware.project-detail-4+json";

    private String project;
    private String assignment;
    private String name;

    public String getProject() {
	return project;
    }

    public void setProject(String project) {
	this.project = project;
    }

    public String getAssignment() {
	return assignment;
    }

    public void setAssignment(String assignment) {
	this.assignment = assignment;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }


    public String getMediaType() {
	return mediaType;
    }

}
