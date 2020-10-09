package com.synopsys.integrations.apigen.maintenance.model;

public class BlackDuckGitHubRepo {

    private String urlPrefix = "https://github.com/blackducksoftware/";
    private String projectName;
    private String projectUrl;

    public BlackDuckGitHubRepo(String projectName) {
        this.projectName = projectName;
        this.projectUrl = urlPrefix + projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

}
