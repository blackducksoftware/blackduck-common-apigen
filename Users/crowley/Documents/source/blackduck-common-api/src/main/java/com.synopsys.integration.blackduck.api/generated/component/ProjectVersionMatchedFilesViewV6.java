package com.synopsys.integration.blackduck.api.generated.component;

import java.util.List;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.generated.component.ProjectVersionMatchedFilesItemsMatchesView;
import java.util.Optional;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ProjectVersionMatchedFilesViewV6 extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private String uri;
    private String sha1;
    private java.util.List<ProjectVersionMatchedFilesItemsMatchesView> matches;

    public String getUri() {
	return uri;
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getSha1() {
	return sha1;
    }

    public void setSha1(String sha1) {
	this.sha1 = sha1;
    }

    public java.util.List<ProjectVersionMatchedFilesItemsMatchesView> getMatches() {
	return matches;
    }

    public void setMatches(java.util.List<ProjectVersionMatchedFilesItemsMatchesView> matches) {
	this.matches = matches;
    }


    public String getMediaType() {
	return mediaType;
    }

}
