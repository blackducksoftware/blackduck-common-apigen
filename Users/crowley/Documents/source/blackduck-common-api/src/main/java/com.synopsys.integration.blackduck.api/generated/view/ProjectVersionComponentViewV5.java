package com.synopsys.integration.blackduck.api.generated.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.generated.enumeration.LicenseFamilyLicenseFamilyRiskRulesUsageType;
import com.synopsys.integration.blackduck.api.generated.view.RiskProfileView;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.core.LinkResponse;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.view.OriginView;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.enumeration.VersionBomComponentMatchType;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.component.VersionBomOriginView;
import java.math.BigDecimal;
import java.util.List;
import com.synopsys.integration.blackduck.api.generated.component.ComponentVersionRiskProfileActivityDataView;
import com.synopsys.integration.blackduck.api.generated.component.ProjectVersionComponentReviewedDetailsView;
import com.synopsys.integration.blackduck.api.generated.enumeration.PolicyStatusType;
import com.synopsys.integration.blackduck.api.generated.component.ProjectVersionComponentLicensesView;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.enumeration.VersionBomComponentReviewStatusType;
import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
import com.synopsys.integration.blackduck.api.manual.throwaway.generated.view.MatchedFileView;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ProjectVersionComponentViewV5 extends BlackDuckView {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-5+json";

	public static final Map<String, LinkResponse> links = new HashMap<>();
        public static final String ORIGINS_LINK = "origins";
        public static final String MATCHED_FILES_LINK = "matched-files";
        public static final String POLICY_RULES_LINK = "policy-rules";

	public static final LinkMultipleResponses<OriginView> ORIGINS_LINK_RESPONSE = new LinkMultipleResponses<OriginView>(ORIGINS_LINK, OriginView.class);
	public static final LinkMultipleResponses<MatchedFileView> MATCHED_FILES_LINK_RESPONSE = new LinkMultipleResponses<MatchedFileView>(MATCHED_FILES_LINK, MatchedFileView.class);
	public static final LinkMultipleResponses<ComponentPolicyRulesView> POLICY_RULES_LINK_RESPONSE = new LinkMultipleResponses<ComponentPolicyRulesView>(POLICY_RULES_LINK, ComponentPolicyRulesView.class);

    static {
	links.put(ORIGINS_LINK, ORIGINS_LINK_RESPONSE);
	links.put(MATCHED_FILES_LINK, MATCHED_FILES_LINK_RESPONSE);
	links.put(POLICY_RULES_LINK, POLICY_RULES_LINK_RESPONSE);
    }

    private java.util.List<VersionBomOriginView> origins;
    private String component;
    private Boolean ignored;
    private ComponentVersionRiskProfileActivityDataView activityData;
    private ProjectVersionComponentReviewedDetailsView reviewedDetails;
    private String componentVersionName;
    private Boolean manuallyAdjusted;
    private RiskProfileView activityRiskProfile;
    private Boolean componentModified;
    private RiskProfileView licenseRiskProfile;
    private java.util.List<VersionBomComponentMatchType> matchTypes;
    private String componentPurpose;
    private String policyStatus;
    private RiskProfileView securityRiskProfile;
    private PolicyStatusType approvalStatus;
    private BigDecimal totalFileMatchCount;
    private String componentVersion;
    private String componentModification;
    private RiskProfileView operationalRiskProfile;
    private Boolean inAttributionReport;
    private String componentName;
    private RiskProfileView versionRiskProfile;
    private VersionBomComponentReviewStatusType reviewStatus;
    private java.util.List<ProjectVersionComponentLicensesView> licenses;
    private java.util.Date releasedOn;
    private java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> usages;
    private String attributionStatement;

    public java.util.List<VersionBomOriginView> getOrigins() {
	return origins;
    }

    public void setOrigins(java.util.List<VersionBomOriginView> origins) {
	this.origins = origins;
    }

    public String getComponent() {
	return component;
    }

    public void setComponent(String component) {
	this.component = component;
    }

    public Boolean getIgnored() {
	return ignored;
    }

    public void setIgnored(Boolean ignored) {
	this.ignored = ignored;
    }

    public ComponentVersionRiskProfileActivityDataView getActivityData() {
	return activityData;
    }

    public void setActivityData(ComponentVersionRiskProfileActivityDataView activityData) {
	this.activityData = activityData;
    }

    public ProjectVersionComponentReviewedDetailsView getReviewedDetails() {
	return reviewedDetails;
    }

    public void setReviewedDetails(ProjectVersionComponentReviewedDetailsView reviewedDetails) {
	this.reviewedDetails = reviewedDetails;
    }

    public String getComponentVersionName() {
	return componentVersionName;
    }

    public void setComponentVersionName(String componentVersionName) {
	this.componentVersionName = componentVersionName;
    }

    public Boolean getManuallyAdjusted() {
	return manuallyAdjusted;
    }

    public void setManuallyAdjusted(Boolean manuallyAdjusted) {
	this.manuallyAdjusted = manuallyAdjusted;
    }

    public RiskProfileView getActivityRiskProfile() {
	return activityRiskProfile;
    }

    public void setActivityRiskProfile(RiskProfileView activityRiskProfile) {
	this.activityRiskProfile = activityRiskProfile;
    }

    public Boolean getComponentModified() {
	return componentModified;
    }

    public void setComponentModified(Boolean componentModified) {
	this.componentModified = componentModified;
    }

    public RiskProfileView getLicenseRiskProfile() {
	return licenseRiskProfile;
    }

    public void setLicenseRiskProfile(RiskProfileView licenseRiskProfile) {
	this.licenseRiskProfile = licenseRiskProfile;
    }

    public java.util.List<VersionBomComponentMatchType> getMatchTypes() {
	return matchTypes;
    }

    public void setMatchTypes(java.util.List<VersionBomComponentMatchType> matchTypes) {
	this.matchTypes = matchTypes;
    }

    public String getComponentPurpose() {
	return componentPurpose;
    }

    public void setComponentPurpose(String componentPurpose) {
	this.componentPurpose = componentPurpose;
    }

    public String getPolicyStatus() {
	return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
	this.policyStatus = policyStatus;
    }

    public RiskProfileView getSecurityRiskProfile() {
	return securityRiskProfile;
    }

    public void setSecurityRiskProfile(RiskProfileView securityRiskProfile) {
	this.securityRiskProfile = securityRiskProfile;
    }

    public PolicyStatusType getApprovalStatus() {
	return approvalStatus;
    }

    public void setApprovalStatus(PolicyStatusType approvalStatus) {
	this.approvalStatus = approvalStatus;
    }

    public BigDecimal getTotalFileMatchCount() {
	return totalFileMatchCount;
    }

    public void setTotalFileMatchCount(BigDecimal totalFileMatchCount) {
	this.totalFileMatchCount = totalFileMatchCount;
    }

    public String getComponentVersion() {
	return componentVersion;
    }

    public void setComponentVersion(String componentVersion) {
	this.componentVersion = componentVersion;
    }

    public String getComponentModification() {
	return componentModification;
    }

    public void setComponentModification(String componentModification) {
	this.componentModification = componentModification;
    }

    public RiskProfileView getOperationalRiskProfile() {
	return operationalRiskProfile;
    }

    public void setOperationalRiskProfile(RiskProfileView operationalRiskProfile) {
	this.operationalRiskProfile = operationalRiskProfile;
    }

    public Boolean getInAttributionReport() {
	return inAttributionReport;
    }

    public void setInAttributionReport(Boolean inAttributionReport) {
	this.inAttributionReport = inAttributionReport;
    }

    public String getComponentName() {
	return componentName;
    }

    public void setComponentName(String componentName) {
	this.componentName = componentName;
    }

    public RiskProfileView getVersionRiskProfile() {
	return versionRiskProfile;
    }

    public void setVersionRiskProfile(RiskProfileView versionRiskProfile) {
	this.versionRiskProfile = versionRiskProfile;
    }

    public VersionBomComponentReviewStatusType getReviewStatus() {
	return reviewStatus;
    }

    public void setReviewStatus(VersionBomComponentReviewStatusType reviewStatus) {
	this.reviewStatus = reviewStatus;
    }

    public java.util.List<ProjectVersionComponentLicensesView> getLicenses() {
	return licenses;
    }

    public void setLicenses(java.util.List<ProjectVersionComponentLicensesView> licenses) {
	this.licenses = licenses;
    }

    public java.util.Date getReleasedOn() {
	return releasedOn;
    }

    public void setReleasedOn(java.util.Date releasedOn) {
	this.releasedOn = releasedOn;
    }

    public java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> getUsages() {
	return usages;
    }

    public void setUsages(java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> usages) {
	this.usages = usages;
    }

    public String getAttributionStatement() {
	return attributionStatement;
    }

    public void setAttributionStatement(String attributionStatement) {
	this.attributionStatement = attributionStatement;
    }


    public String getMediaType() {
	return mediaType;
    }

}
