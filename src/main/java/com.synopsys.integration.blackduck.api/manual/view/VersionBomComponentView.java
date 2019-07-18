package com.synopsys.integration.blackduck.api.manual.view;

import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
import com.synopsys.integration.blackduck.api.core.LinkResponse;
import com.synopsys.integration.blackduck.api.core.LinkSingleResponse;
import com.synopsys.integration.blackduck.api.manual.component.ActivityDataView;
import com.synopsys.integration.blackduck.api.manual.component.ReviewedDetails;
import com.synopsys.integration.blackduck.api.manual.component.VersionBomLicenseView;
import com.synopsys.integration.blackduck.api.manual.component.VersionBomOriginView;
import com.synopsys.integration.blackduck.api.manual.enumeration.MatchedFileUsagesEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicySummaryStatusEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.VersionBomComponentMatchEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.VersionBomComponentReviewStatusEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomComponentView extends BlackDuckView {
    public static final Map<String, LinkResponse> links = new HashMap<>();

    public static final String POLICY_RULES_LINK = "policy-rules";
    public static final String MATCHED_FILES_LINK = "matched-files";
    public static final String COMMENTS_LINK = "comments";
    public static final String COMPONENT_ISSUES_LINK = "component-issues";
    public static final String VULNERABILITIES_LINK = "vulnerabilities";
    public static final String VULNERABLE_COMPONENTS_LINK = "vulnerable-components";

    public static final LinkMultipleResponses<VersionBomPolicyRuleView> POLICY_RULES_LINK_RESPONSE = new LinkMultipleResponses<VersionBomPolicyRuleView>(POLICY_RULES_LINK, VersionBomPolicyRuleView.class);
    public static final LinkMultipleResponses<MatchedFileView> MATCHED_FILES_LINK_RESPONSE = new LinkMultipleResponses<MatchedFileView>(MATCHED_FILES_LINK, MatchedFileView.class);
    public static final LinkMultipleResponses<UserCommentView> COMMENTS_LINK_RESPONSE = new LinkMultipleResponses<UserCommentView>(COMMENTS_LINK, UserCommentView.class);
    public static final LinkSingleResponse<IssueView> COMPONENT_ISSUES_LINK_RESPONSE = new LinkSingleResponse<IssueView>(COMPONENT_ISSUES_LINK, IssueView.class);
    public static final LinkMultipleResponses<VulnerabilityView> VULNERABILITIES_LINK_RESPONSE = new LinkMultipleResponses<VulnerabilityView>(VULNERABILITIES_LINK, VulnerabilityView.class);
    public static final LinkMultipleResponses<VulnerableComponentView> VULNERABLE_COMPONENTS_LINK_RESPONSE = new LinkMultipleResponses<VulnerableComponentView>(VULNERABLE_COMPONENTS_LINK, VulnerableComponentView.class);

    static {
        links.put(POLICY_RULES_LINK, POLICY_RULES_LINK_RESPONSE);
        links.put(MATCHED_FILES_LINK, MATCHED_FILES_LINK_RESPONSE);
        links.put(COMMENTS_LINK, COMMENTS_LINK_RESPONSE);
        links.put(COMPONENT_ISSUES_LINK, COMPONENT_ISSUES_LINK_RESPONSE);
        links.put(VULNERABILITIES_LINK, VULNERABILITIES_LINK_RESPONSE);
        links.put(VULNERABLE_COMPONENTS_LINK, VULNERABLE_COMPONENTS_LINK_RESPONSE);
    }

    private ActivityDataView activityData;
    private RiskProfileView activityRiskProfile;
    private PolicySummaryStatusEnum approvalStatus;
    private String component;
    private String componentModification;
    private Boolean componentModified;
    private String componentName;
    private String componentPurpose;
    private String componentVersion;
    private String componentVersionName;
    private RiskProfileView licenseRiskProfile;
    private java.util.List<VersionBomLicenseView> licenses;
    private java.util.List<VersionBomComponentMatchEnum> matchTypes;
    private RiskProfileView operationalRiskProfile;
    private java.util.List<VersionBomOriginView> origins;
    private PolicySummaryStatusEnum policyStatus;
    private java.util.Date releasedOn;
    private VersionBomComponentReviewStatusEnum reviewStatus;
    private ReviewedDetails reviewedDetails;
    private RiskProfileView securityRiskProfile;
    private Long totalFileMatchCount;
    private java.util.List<MatchedFileUsagesEnum> usages;
    private RiskProfileView versionRiskProfile;

    public ActivityDataView getActivityData() {
        return activityData;
    }

    public void setActivityData(final ActivityDataView activityData) {
        this.activityData = activityData;
    }

    public RiskProfileView getActivityRiskProfile() {
        return activityRiskProfile;
    }

    public void setActivityRiskProfile(final RiskProfileView activityRiskProfile) {
        this.activityRiskProfile = activityRiskProfile;
    }

    public PolicySummaryStatusEnum getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(final PolicySummaryStatusEnum approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        this.component = component;
    }

    public String getComponentModification() {
        return componentModification;
    }

    public void setComponentModification(final String componentModification) {
        this.componentModification = componentModification;
    }

    public Boolean getComponentModified() {
        return componentModified;
    }

    public void setComponentModified(final Boolean componentModified) {
        this.componentModified = componentModified;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }

    public String getComponentPurpose() {
        return componentPurpose;
    }

    public void setComponentPurpose(final String componentPurpose) {
        this.componentPurpose = componentPurpose;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(final String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getComponentVersionName() {
        return componentVersionName;
    }

    public void setComponentVersionName(final String componentVersionName) {
        this.componentVersionName = componentVersionName;
    }

    public RiskProfileView getLicenseRiskProfile() {
        return licenseRiskProfile;
    }

    public void setLicenseRiskProfile(final RiskProfileView licenseRiskProfile) {
        this.licenseRiskProfile = licenseRiskProfile;
    }

    public java.util.List<VersionBomLicenseView> getLicenses() {
        return licenses;
    }

    public void setLicenses(final java.util.List<VersionBomLicenseView> licenses) {
        this.licenses = licenses;
    }

    public java.util.List<VersionBomComponentMatchEnum> getMatchTypes() {
        return matchTypes;
    }

    public void setMatchTypes(final java.util.List<VersionBomComponentMatchEnum> matchTypes) {
        this.matchTypes = matchTypes;
    }

    public RiskProfileView getOperationalRiskProfile() {
        return operationalRiskProfile;
    }

    public void setOperationalRiskProfile(final RiskProfileView operationalRiskProfile) {
        this.operationalRiskProfile = operationalRiskProfile;
    }

    public java.util.List<VersionBomOriginView> getOrigins() {
        return origins;
    }

    public void setOrigins(final java.util.List<VersionBomOriginView> origins) {
        this.origins = origins;
    }

    public PolicySummaryStatusEnum getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(final PolicySummaryStatusEnum policyStatus) {
        this.policyStatus = policyStatus;
    }

    public java.util.Date getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(final java.util.Date releasedOn) {
        this.releasedOn = releasedOn;
    }

    public VersionBomComponentReviewStatusEnum getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(final VersionBomComponentReviewStatusEnum reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public ReviewedDetails getReviewedDetails() {
        return reviewedDetails;
    }

    public void setReviewedDetails(final ReviewedDetails reviewedDetails) {
        this.reviewedDetails = reviewedDetails;
    }

    public RiskProfileView getSecurityRiskProfile() {
        return securityRiskProfile;
    }

    public void setSecurityRiskProfile(final RiskProfileView securityRiskProfile) {
        this.securityRiskProfile = securityRiskProfile;
    }

    public Long getTotalFileMatchCount() {
        return totalFileMatchCount;
    }

    public void setTotalFileMatchCount(final Long totalFileMatchCount) {
        this.totalFileMatchCount = totalFileMatchCount;
    }

    public java.util.List<MatchedFileUsagesEnum> getUsages() {
        return usages;
    }

    public void setUsages(final java.util.List<MatchedFileUsagesEnum> usages) {
        this.usages = usages;
    }

    public RiskProfileView getVersionRiskProfile() {
        return versionRiskProfile;
    }

    public void setVersionRiskProfile(final RiskProfileView versionRiskProfile) {
        this.versionRiskProfile = versionRiskProfile;
    }

}
