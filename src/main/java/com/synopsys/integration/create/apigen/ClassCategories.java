package com.synopsys.integration.create.apigen;

import java.util.HashSet;
import java.util.Set;

public class ClassCategories {

    private final Set<String> VIEWS;
    private final Set<String> RESPONSES;
    private final Set<String> COMPONENTS;
    private final Set<String> GENERATED;
    private final Set<String> MANUAL;
    private final Set<String> COMMON_TYPES;

    public ClassCategories() {
        this.VIEWS = populateViews();
        this.RESPONSES = populateResponses();
        this.COMPONENTS = populateComponents();
        this.GENERATED = populateGenerated();
        this.MANUAL = populateManual();
        this.COMMON_TYPES = populateCommonTypes();
    }

    private Set<String> populateViews() {
        final Set<String> views = new HashSet<>();

        views.add("AssignedUserView");
        views.add("CodeLocationView");
        views.add("CodeLocation4View");
        views.add("ComplexLicenseView");
        views.add("ComponentDetailsView");
        views.add("ComponentSearchResultView");
        views.add("ComponentVersionView");
        views.add("ComponentView");
        views.add("CustomFieldObjectView");
        views.add("CustomFieldView");
        views.add("ExternalExtensionConfigValueView");
        views.add("ExternalExtensionUserView");
        views.add("ExternalExtensionView");
        views.add("FilterView");
        views.add("IssueView");
        views.add("LicenseFamilyView");
        views.add("LicenseTextView");
        views.add("LicenseView");
        views.add("MatchedFileView");
        views.add("NotificationView");
        views.add("NotificationUserView");
        views.add("OriginView");
        views.add("PolicyRuleView");
        views.add("PolicyStatusView");
        views.add("ProjectDashboardRiskAmalgamation");
        views.add("ProjectVersionView");
        views.add("ProjectView");
        views.add("RegistrationAttributesInternalView");
        views.add("RegistrationSummaryInternalView");
        views.add("RegistrationView");
        views.add("ReportView");
        views.add("RiskProfileView");
        views.add("RoleView");
        views.add("RoleAssignmentView");
        views.add("TagView");
        views.add("UserGroupView");
        views.add("UserView");
        views.add("VersionBomComponentView");
        views.add("VersionBomPolicyStatusView");
        views.add("VulnerabilityView");
        views.add("VulnerabilityWithRemediationView");
        views.add("VulnerableComponentView");

        return views;
    }

    private Set<String> populateResponses() {
        final Set<String> responses = new HashSet<>();

        responses.add("ApiTokenView");
        responses.add("AssignableUserGroupView");
        responses.add("AssignableUserView");
        responses.add("AssignedProjectView");
        responses.add("AssignedUserGroupView");
        responses.add("AssignedUserRequest");
        responses.add("ComponentSearchResult");
        responses.add("ComponentVersionReferenceView");
        responses.add("ComponentVersionRiskView");
        responses.add("CurrentVersionView");
        responses.add("CustomFieldTypeView");
        responses.add("CweView");
        responses.add("DashboardSummaryView");
        responses.add("EndUserLicenseAgreementView");
        responses.add("HealthCheckStatusView");
        responses.add("HomepageView");
        responses.add("HierarchicalVersionBomComponentView");
        responses.add("JobStatisticsView");
        responses.add("LogoView");
        responses.add("OpenHubView");
        responses.add("ProjectJournalView");
        responses.add("ProjectMappingView");
        responses.add("LegacyFilterView");
        responses.add("RemediationOptionsView");
        responses.add("VersionBomAttachmentView");
        responses.add("VersionBomComponentDiffView");
        responses.add("VersionRiskProfileView");

        return responses;
    }

    private Set<String> populateComponents() {
        final Set<String> components = new HashSet<>();

        components.add("ActivityDataView");
        components.add("CommentUserData");
        components.add("ComponentVersionPolicyViolationDetails");
        components.add("CompositePathWithArchiveContext");
        components.add("Cvss2TemporalMetricsView");
        components.add("Cvss3TemporalMetricsView");
        components.add("CweCommonConsequenceView");
        components.add("LicenseFamilySummaryView");
        components.add("NameValuePairView");
        components.add("PolicyRuleExpressionSetView");
        components.add("PolicyRuleExpressionView");
        components.add("ResourceLink");
        components.add("ResourceMetadata");
        components.add("ReviewedDetails");
        components.add("RiskCountView");
        components.add("VersionBomCustomFieldView");
        components.add("VersionBomLicenseView");
        components.add("VersionBomOriginView");
        components.add("VersionDataView");
        components.add("VulnerabilityClassificationView");
        components.add("VulnerabilityCvss2View");
        components.add("VulnerabilityCvss3View");

        return components;
    }

    private Set<String> populateGenerated() {
        final Set<String> generatedClasses = new HashSet<>();

        generatedClasses.add("AssignedUserGroupView");
        generatedClasses.add("CodeLocationView");
        generatedClasses.add("CodeLocation4View");
        generatedClasses.add("CustomFieldView");
        generatedClasses.add("CustomField4View");
        generatedClasses.add("HomepageView");
        generatedClasses.add("LogoView");
        generatedClasses.add("OpenHubView");
        generatedClasses.add("ProjectJournalView");
        generatedClasses.add("ProjectMappingView");
        generatedClasses.add("ProjectView");
        generatedClasses.add("ProjectVersionView");
        generatedClasses.add("ReportView");
        generatedClasses.add("TagView");
        generatedClasses.add("VersionBomAttachmentView");

        return generatedClasses;
    }

    private Set<String> populateManual() {
        final Set<String> manualClasses = new HashSet<>();

        manualClasses.add("AssignableUserGroupView");
        manualClasses.add("AssignableUserView");
        manualClasses.add("AssignedUserRequest");
        manualClasses.add("AssignedUserGroupRequest");
        manualClasses.add("ComplexLicenseView");
        manualClasses.add("ComponentVersionReferenceView");
        manualClasses.add("ComponentVersionRiskView");
        manualClasses.add("ComponentVersionView");
        manualClasses.add("CweView");
        manualClasses.add("HierarchicalVersionBomComponentView");
        manualClasses.add("IssueView");
        manualClasses.add("MatchedFileView");
        manualClasses.add("OriginView");
        manualClasses.add("ProjectMappingView");
        manualClasses.add("ReportView");
        manualClasses.add("RiskProfileView");
        manualClasses.add("TagView");
        manualClasses.add("UserCommentView");
        manualClasses.add("VersionBomComponentDiffView");
        manualClasses.add("VersionBomComponentView");
        manualClasses.add("VersionBomPolicyRuleView");
        manualClasses.add("VersionBomPolicyStatusView");
        manualClasses.add("VersionRiskProfileView");
        manualClasses.add("VulnerabilityView");
        manualClasses.add("VulnerabilityWithRemediationView");
        manualClasses.add("VulnerableComponentView");

        return manualClasses;
    }

    private Set<String> populateCommonTypes() {
        final Set<String> commonTypes = new HashSet<>();

        commonTypes.add("Array");
        commonTypes.add("String");
        commonTypes.add("Object");
        commonTypes.add("BigDecimal");
        commonTypes.add("Integer");
        commonTypes.add("Boolean");

        return commonTypes;
    }

    public boolean isView(final String className) {
        return this.VIEWS.contains(className);
    }

    public boolean isResponse(final String className) {
        return this.RESPONSES.contains(className);
    }

    public boolean isComponent(final String className) { return this.COMPONENTS.contains(className); }

    public boolean isGenerated(final String className) { return this.GENERATED.contains(className); }

    public boolean isManual(final String className) { return this.MANUAL.contains(className); }

    public boolean isCommonType(final String className) { return this.COMMON_TYPES.contains(className); }
}
