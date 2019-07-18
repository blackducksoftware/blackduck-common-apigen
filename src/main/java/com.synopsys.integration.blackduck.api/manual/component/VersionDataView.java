package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionDataView extends BlackDuckComponent {
    private Integer newerReleasesCount;
    private java.util.Date releasedOn;

    public Integer getNewerReleasesCount() {
        return newerReleasesCount;
    }

    public void setNewerReleasesCount(final Integer newerReleasesCount) {
        this.newerReleasesCount = newerReleasesCount;
    }

    public java.util.Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(final java.util.Date releasedOn) {
        this.releasedOn = releasedOn;
    }

}
