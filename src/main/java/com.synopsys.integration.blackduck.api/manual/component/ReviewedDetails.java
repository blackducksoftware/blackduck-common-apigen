package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ReviewedDetails extends BlackDuckComponent {
    private java.util.Date reviewedAt;
    private String reviewedBy;

    public java.util.Date getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(final java.util.Date reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(final String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

}
