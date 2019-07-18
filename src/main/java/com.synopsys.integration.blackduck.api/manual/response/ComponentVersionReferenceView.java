package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.manual.enumeration.ProjectVersionDistributionEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.ProjectVersionPhaseEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentVersionReferenceView extends BlackDuckResponse {
    private ProjectVersionDistributionEnum distribution;
    private ProjectVersionPhaseEnum phase;
    private String projectName;
    private Integer projectTier;
    private String projectUrl;
    private String projectVersionUrl;
    private java.util.Date releasedOn;
    private String versionName;

    public ProjectVersionDistributionEnum getDistribution() {
        return distribution;
    }

    public void setDistribution(final ProjectVersionDistributionEnum distribution) {
        this.distribution = distribution;
    }

    public ProjectVersionPhaseEnum getPhase() {
        return phase;
    }

    public void setPhase(final ProjectVersionPhaseEnum phase) {
        this.phase = phase;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectTier() {
        return projectTier;
    }

    public void setProjectTier(final Integer projectTier) {
        this.projectTier = projectTier;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(final String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String getProjectVersionUrl() {
        return projectVersionUrl;
    }

    public void setProjectVersionUrl(final String projectVersionUrl) {
        this.projectVersionUrl = projectVersionUrl;
    }

    public java.util.Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(final java.util.Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(final String versionName) {
        this.versionName = versionName;
    }

}