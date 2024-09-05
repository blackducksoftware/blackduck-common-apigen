/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.model;

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
