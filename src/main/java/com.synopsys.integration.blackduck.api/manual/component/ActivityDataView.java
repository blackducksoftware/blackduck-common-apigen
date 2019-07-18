package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.ActivityDataTrendingEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ActivityDataView extends BlackDuckComponent {
    private Long commitCount12Month;
    private Long contributorCount12Month;
    private java.util.Date lastCommitDate;
    private ActivityDataTrendingEnum trending;

    public Long getCommitCount12Month() {
        return commitCount12Month;
    }

    public void setCommitCount12Month(final Long commitCount12Month) {
        this.commitCount12Month = commitCount12Month;
    }

    public Long getContributorCount12Month() {
        return contributorCount12Month;
    }

    public void setContributorCount12Month(final Long contributorCount12Month) {
        this.contributorCount12Month = contributorCount12Month;
    }

    public java.util.Date getLastCommitDate() {
        return lastCommitDate;
    }

    public void setLastCommitDate(final java.util.Date lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }

    public ActivityDataTrendingEnum getTrending() {
        return trending;
    }

    public void setTrending(final ActivityDataTrendingEnum trending) {
        this.trending = trending;
    }

}
