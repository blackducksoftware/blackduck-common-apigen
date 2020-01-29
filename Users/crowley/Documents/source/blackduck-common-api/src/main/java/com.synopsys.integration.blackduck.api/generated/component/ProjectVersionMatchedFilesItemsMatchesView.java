package com.synopsys.integration.blackduck.api.generated.component;

import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectVersionMatchedFilesItemsMatchesMatchTypeType;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import java.util.Optional;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ProjectVersionMatchedFilesItemsMatchesView extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private ProjectVersionMatchedFilesItemsMatchesMatchTypeType matchType;
    private String component;
    private String snippet;

    public ProjectVersionMatchedFilesItemsMatchesMatchTypeType getMatchType() {
	return matchType;
    }

    public void setMatchType(ProjectVersionMatchedFilesItemsMatchesMatchTypeType matchType) {
	this.matchType = matchType;
    }

    public String getComponent() {
	return component;
    }

    public void setComponent(String component) {
	this.component = component;
    }

    public String getSnippet() {
	return snippet;
    }

    public void setSnippet(String snippet) {
	this.snippet = snippet;
    }


    public String getMediaType() {
	return mediaType;
    }

}
