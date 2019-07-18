package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.component.PolicyRuleExpressionSetView;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicySummaryStatusEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomPolicyRuleView extends BlackDuckView {
    private java.util.Date createdAt;
    private String createdBy;
    private String createdByUser;
    private String description;
    private Boolean enabled;
    private PolicyRuleExpressionSetView expression;
    private String name;
    private Boolean overridable;
    private PolicySummaryStatusEnum policyApprovalStatus;
    private String severity;
    private java.util.Date updatedAt;
    private String updatedBy;
    private String updatedByUser;

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(final String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public PolicyRuleExpressionSetView getExpression() {
        return expression;
    }

    public void setExpression(final PolicyRuleExpressionSetView expression) {
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getOverridable() {
        return overridable;
    }

    public void setOverridable(final Boolean overridable) {
        this.overridable = overridable;
    }

    public PolicySummaryStatusEnum getPolicyApprovalStatus() {
        return policyApprovalStatus;
    }

    public void setPolicyApprovalStatus(final PolicySummaryStatusEnum policyApprovalStatus) {
        this.policyApprovalStatus = policyApprovalStatus;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(final String severity) {
        this.severity = severity;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(final String updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

}