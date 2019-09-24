/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.definitions;

import java.util.HashSet;
import java.util.Set;

public class ClassCategories {

    private final Set<String> VIEWS;
    private final Set<String> RESPONSES;
    private final Set<String> COMPONENTS;
    private final Set<String> GENERATED;
    private final Set<String> MANUAL;
    private final Set<String> THROWAWAY;
    private final Set<String> COMMON_TYPES;
    private final Set<String> NON_ENUM_CLASSES_ENDING_IN_TYPE;

    public ClassCategories() {
        this.VIEWS = populateViews();
        this.RESPONSES = populateResponses();
        this.COMPONENTS = populateComponents();
        this.GENERATED = populateGenerated();
        this.MANUAL = populateManual();
        this.THROWAWAY = populateThrowaway();
        this.COMMON_TYPES = populateCommonTypes();
        this.NON_ENUM_CLASSES_ENDING_IN_TYPE = populateNonEnumClassesEndingInType();
    }

    private Set<String> populateViews() {
        final Set<String> views = new HashSet<>();

        views.add("AssignedUserView");
        views.add("CodeLocationView");
        views.add("CodelocationsCodelocationView"); //
        views.add("ComplexLicenseView");
        views.add("ComponentCustomFieldView");
        views.add("ComponentDetailsView");
        views.add("ComponentSearchResultView");
        views.add("ComponentVersionView");
        views.add("ComponentView");
        views.add("ComponentsComponentView"); //
        views.add("CurrentUserView");
        views.add("CustomFieldObjectView");
        views.add("CustomFieldView");
        views.add("FieldsCustomFieldView"); //
        views.add("CustomFieldsCustomFieldView"); //
        views.add("ExternalExtensionConfigValueView");
        views.add("ExternalExtensionUserView");
        views.add("ExternalExtensionView");
        views.add("FilterView");
        views.add("IssueView");
        views.add("JobView");
        views.add("JobsJobView"); //
        views.add("LicenseFamilyView");
        views.add("LicenseTermCategoryView");
        views.add("LicenseTermView");
        views.add("LicenseTermsLicenseTermView"); //
        views.add("LicenseTextView");
        views.add("LicenseView");
        views.add("LicensesLicenseView"); //
        views.add("MatchedFileView");
        views.add("NotificationView");
        views.add("NotificationUserView");
        views.add("OriginView");
        views.add("PolicyRuleView");
        views.add("PolicyRulesPolicyruleView"); //
        views.add("PolicyStatusView");
        views.add("ProjectCustomFieldView");
        views.add("ProjectDashboardRiskAmalgamation");
        views.add("ProjectMappingView");
        views.add("ProjectVersionComponentCustomFieldView");
        views.add("ProjectVersionComponentVersionCustomFieldView");
        views.add("ProjectVersionComponentView");
        views.add("ProjectVersionCustomFieldView");
        views.add("ProjectVersionReportView");
        views.add("ProjectVersionView");
        views.add("VersionsProjectversionView"); //
        views.add("ProjectView");
        views.add("ProjectsProjectView"); //
        views.add("RegistrationAttributesInternalView");
        views.add("RegistrationSummaryInternalView");
        views.add("RegistrationView");
        views.add("ReportView");
        views.add("ReportsReportView"); //
        views.add("LicenseReportsReportView");  //
        views.add("ReportContentsView"); //
        views.add("RiskProfileView");
        views.add("RoleView");
        views.add("RolesRoleView"); //
        views.add("RoleAssignmentView");
        views.add("RolesRoleassignmentView"); //
        views.add("ScanView");
        views.add("ScanSummariesScanView"); //
        views.add("TagView");
        views.add("UserGroupView");
        views.add("UsergroupsUsergroupView"); //
        views.add("UserRoleAssignmentView");
        views.add("UserView");
        views.add("UsersUserView"); //
        views.add("VersionBomComponentView");
        views.add("VersionBomPolicyRoleView");
        views.add("VersionBomPolicyStatusView");
        views.add("VulnerabilityView");
        views.add("VulnerabilityWithRemediationView");
        views.add("VulnerableComponentView");
        views.add("VulnerabilityReportsReportView"); //

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
        responses.add("UserCommentView");
        responses.add("VersionBomAttachmentView");
        responses.add("VersionRiskProfileView");

        return responses;
    }

    private Set<String> populateComponents() {
        final Set<String> components = new HashSet<>();

        components.add("ActivityDataView");
        components.add("AssignedUserGroup");
        components.add("AssignedUserGroupView");
        components.add("AuditEventCount");
        components.add("BomComponentReleaseUsageRiskProfile");
        components.add("ChannelRelease");
        components.add("CodeLocationProgress");
        components.add("CommentUserData");
        components.add("ComponentHit");
        components.add("ComponentLicensesView");
        components.add("ComponentActivityRiskProfileView"); //
        components.add("ComponentActivityDataView"); //
        components.add("ComponentLicenseRiskProfileView"); //
        components.add("ComponentOperationalRiskProfileView"); //
        components.add("ComponentSecurityRiskProfileView"); //
        components.add("ComponentReviewedDetailsView"); //
        components.add("ComponentReviewedDetailsReviewingUserView"); //
        components.add("ComponentVersionRiskProfileView"); //
        components.add("ComplexLicenseRequest");
        components.add("ComponentVersionPolicyViolationDetails");
        components.add("CompositePathWithArchiveContext");
        components.add("ConfigOptionView");
        components.add("CustomFieldOptionRequest");
        components.add("CustomComponentVersionRequest");
        components.add("CustomFieldValueBulkRequestEntry");
        components.add("CustomFieldOptionUpdateRequest");
        components.add("Cvss2TemporalMetricsView");
        components.add("Cvss3TemporalMetricsView");
        components.add("CweCommonConsequenceView");
        components.add("DeclaredComponentPath");
        components.add("EntityKey");
        components.add("Duration");
        components.add("Facet");
        components.add("FacetType");
        components.add("FacetValue");
        components.add("FileBomComponent");
        components.add("FileSnippetBomComponent");
        components.add("JournalTriggerView");
        components.add("JournalObjectView");
        components.add("LegacyAppliedFilterView");
        components.add("LegacyFilterValueView");
        components.add("LicenseDefinition");
        components.add("LicenseFamilySummaryView");
        components.add("LicenseFamilyRiskRuleView");
        components.add("LicenseTermCategorySummaryView");
        components.add("LicensesLicenseViewCreatedBy");
        components.add("LicenseCreatedByView");
        components.add("LicenseViewLicenseFamily");
        components.add("LicenseLicenseFamilyView"); //
        components.add("LicensesLicenseUpdatedByView");
        components.add("LicenseStatusUpdatedByView");
        components.add("LicensesLicenseViewStatusUpdatedBy"); //
        components.add("LicenseUpdatedByView");
        components.add("NameValuePairView");
        components.add("PolicyRulesPolicyruleViewExpressionExpressions"); //
        components.add("PolicyRuleViewExpression"); //
        components.add("PolicyRulesPolicyruleViewExpression"); //
        components.add("PolicyRuleViewExpressionParameter");
        components.add("PolicyRuleViewExpressionExpression"); //
        components.add("PolicyRuleExpressionExpressionsView");
        components.add("PolicyRuleViewExpressionExpressionParameters"); //
        components.add("PolicyRuleViewExpressionExpressionsParameters"); //
        components.add("PolicyRuleExpressionExpressionsParametersView"); //
        components.add("PolicyRuleExpressionSetView");
        components.add("PolicyRuleExpressionView");
        components.add("PolicyStatusSummary");
        components.add("ProjectData");
        components.add("ProjectRiskProfile");
        components.add("ProjectVersionLicenseLicenseLicenseFamilySummaryView"); //
        components.add("ProjectVersionLicenseLicensesLicenseFamilySummaryView"); //
        components.add("ProjectVersionComponentViewLicenses"); //
        components.add("ProjectVersionComponentViewActivityData");
        components.add("ProjectVersionComponentViewActivityRiskProfile");
        components.add("ProjectVersionComponentViewLicenseRiskProfile");
        components.add("ProjectVersionComponentViewReviewedDetails");
        components.add("ProjectVersionComponentViewReviewedDetailsReviewingUser");
        components.add("ProjectVersionComponentViewOperationalRiskProfile");
        components.add("ProjectVersionComponentViewSecurityRiskProfile");
        components.add("ProjectVersionComponentViewVersionRiskProfile");
        components.add("ProjectVersionLicenseView"); //
        components.add("ProjectVersionViewLicenseLicense"); //
        components.add("ProjectVersionLicenseLicensesView"); //
        components.add("ProjectVersionRequest");
        components.add("RegistrationAttributesView");
        components.add("RegistrationViewAttributes"); //
        components.add("RegistrationFeaturesView");
        components.add("RegistrationMessagesView");
        components.add("RegistrationViewFeatures");
        components.add("RegistrationViewMessages"); //
        components.add("ReleaseData");
        components.add("RemediatingVersionView");
        components.add("ReportFileContent");
        components.add("ReportContentsReportContentView"); //
        components.add("ResourceMetadata");
        components.add("ResourceLink");
        components.add("ReviewedDetails");
        components.add("RiskCountView");
        components.add("RiskProfile");
        components.add("SearchResultSpec");
        components.add("SearchResultStatistics");
        components.add("SignaturePair");
        components.add("TextByteOffsetView");
        components.add("TemporalUnit");
        components.add("UserData");
        components.add("UserDataView");
        components.add("UserSummary");
        components.add("UserSummaryView");
        components.add("Version");
        components.add("VersionBomComponentDiffView");
        components.add("VersionBomComponentOriginUpdateRequest");
        components.add("VersionBomCustomFieldView");
        components.add("VersionBomCustomFieldView");
        components.add("VersionBomLicenseView");
        components.add("VersionBomOriginView");
        components.add("VersionDataView");
        components.add("VersionsProjectversionViewLicense");
        components.add("VersionsProjectversionViewLicenseLicenses"); //
        components.add("VersionsProjectversionViewLicenseLicensesLicenseFamilySummary"); //
        components.add("ComponentsComponentViewSecurityRiskProfile"); //
        components.add("VulnerabilityClassificationView");
        components.add("VulnerabilityCvss2View");
        components.add("VulnerabilityCvss3View");

        return components;
    }

    private Set<String> populateGenerated() {
        final Set<String> generatedClasses = new HashSet<>();

        // component
        generatedClasses.add("PolicyRuleVExpressionView");
        generatedClasses.add("PolicyRuleExpressionExpressionView");
        generatedClasses.add("PolicyRuleExpressionParametersView");
        generatedClasses.add("ProjectVersionComponentViewLicenses");
        generatedClasses.add("ProjectVersionLicenseView");
        generatedClasses.add("ProjectVersionLicenseLicensesView");
        generatedClasses.add("ProjectVersionLicenseLicensesLicenseFamilySummaryView");

        // enumeration
        generatedClasses.add("ComponentApprovalStatusType");
        generatedClasses.add("ComponentCustomFieldTypeType");
        generatedClasses.add("ComponentSourceType");
        generatedClasses.add("CustomFieldTypeType");
        generatedClasses.add("PolicyRuleSeverityType");
        generatedClasses.add("ProjectCloneCategoriesType");
        generatedClasses.add("ProjectCustomFieldTypeType");
        generatedClasses.add("ProjectVersionComponentCustomFieldTypeType");
        generatedClasses.add("ProjectVersionComponentVersionCustomFieldTypeType");
        generatedClasses.add("ProjectVersionCustomFieldTypeType");

        // views
        generatedClasses.add("CodeLocationView");
        generatedClasses.add("CustomFieldView");
        generatedClasses.add("CustomFieldView");
        generatedClasses.add("PolicyRuleView");
        generatedClasses.add("ProjectCustomFieldView");
        generatedClasses.add("ProjectJournalView");
        generatedClasses.add("ProjectMappingView");
        generatedClasses.add("ProjectVersionComponentCustomFieldView");
        generatedClasses.add("ProjectVersionComponentVersionCustomFieldView");
        generatedClasses.add("ProjectView");
        generatedClasses.add("ProjectVersionView");
        generatedClasses.add("ProjectVersionComponentView");
        generatedClasses.add("RoleView");
        generatedClasses.add("UserView");
        generatedClasses.add("UserGroupView");
        generatedClasses.add("UserRoleAssignmentView");
        generatedClasses.add("VersionBomAttachmentView");

        return generatedClasses;
    }

    private Set<String> populateManual() {
        final Set<String> manualClasses = new HashSet<>();

        // core
        manualClasses.add("BlackDuckComponent");
        manualClasses.add("BlackDuckResponse");
        manualClasses.add("BlackDuckPath");
        manualClasses.add("BlackDuckPathMultipleResponses");
        manualClasses.add("BlackDuckPathSingleResponse");
        manualClasses.add("BlackDuckResponse");
        manualClasses.add("BlackDuckView");
        manualClasses.add("LinkMultipleResponses");
        manualClasses.add("LinkResponse");
        manualClasses.add("LinkStringResponse");
        manualClasses.add("LinkSingleResponse");

        // Component
        manualClasses.add("AffectedProjectVersion");
        manualClasses.add("BomEditNotificationContent");
        manualClasses.add("ComponentVersionStatus");
        manualClasses.add("LicenseLimitNotificationContent");
        manualClasses.add("NotificationContentComponent");
        manualClasses.add("PolicyInfo");
        manualClasses.add("PolicyOverrideNotificationContent");
        manualClasses.add("ProjectNotificationContent");
        manualClasses.add("ProjectVersionNotificationContent");
        manualClasses.add("RuleViolationClearedNotificationContent");
        manualClasses.add("RuleViolationNotificationContent");
        manualClasses.add("VersionBomCodeLocationBomComputedNotificationContent");
        manualClasses.add("VulnerabilityNotificationContent");
        manualClasses.add("VulnerabilitySourceQualifiedId");
        // Contract
        manualClasses.add("NotificationContentData");
        manualClasses.add("NotificationViewData");

        // discovery
        manualClasses.add("ApiDiscovery");

        // Enumeration
        manualClasses.add("LicenseLimitType");
        manualClasses.add("OperationType");
        manualClasses.add("ScanSummaryStatusType");
        // View
        manualClasses.add("BomEditNotificationUserView");
        manualClasses.add("BomEditNotificationView");
        manualClasses.add("LicenseLimitNotificationUserView");
        manualClasses.add("LicenseLimitNotificationView");
        manualClasses.add("NotificationUserView");
        manualClasses.add("NotificationView");
        manualClasses.add("PolicyOverrideNotificationUserView");
        manualClasses.add("PolicyOverrideNotificationView");
        manualClasses.add("ProjectNotificationView");
        manualClasses.add("ProjectNotificationUserView");
        manualClasses.add("ProjectVersionNotificationView");
        manualClasses.add("ProjectVersionNotificationUserView");
        manualClasses.add("RuleViolationClearedNotificationUserView");
        manualClasses.add("RuleViolationClearedNotificationView");
        manualClasses.add("RuleViolationNotificationUserView");
        manualClasses.add("RuleViolationNotificationView");
        manualClasses.add("ScanSummaryView");
        manualClasses.add("VersionBomCodeLocationBomComputedNotificationUserView");
        manualClasses.add("VersionBomCodeLocationBomComputedNotificationView");
        manualClasses.add("VulnerabilityNotificationUserView");
        manualClasses.add("VulnerabilityNotificationView");

        return manualClasses;
    }

    private Set<String> populateThrowaway() {
        final Set<String> throwawayClasses = new HashSet<>();

        // Views
        throwawayClasses.add("AssignedUserView");
        throwawayClasses.add("ComplexLicenseView");
        throwawayClasses.add("ComponentDetailsView");
        throwawayClasses.add("ComponentSearchResultView");
        throwawayClasses.add("ComponentVersionView");
        throwawayClasses.add("CustomFieldObjectView");
        throwawayClasses.add("ExternalExtensionConfigValueView");
        throwawayClasses.add("ExternalExtensionUserView");
        throwawayClasses.add("IssueView");
        throwawayClasses.add("LicenseFamilyView");
        throwawayClasses.add("LicenseTermAssociationView");
        throwawayClasses.add("LicenseTermCategoryView");
        throwawayClasses.add("LicenseTermView");
        throwawayClasses.add("OriginView");
        throwawayClasses.add("NotificationUserView");
        throwawayClasses.add("MatchedFileView");
        throwawayClasses.add("ProjectMappingView");
        throwawayClasses.add("RegistrationView");
        throwawayClasses.add("ReportView");
        throwawayClasses.add("RiskProfileView");
        throwawayClasses.add("RoleAssignmentView");
        throwawayClasses.add("VersionBomComponentView");
        throwawayClasses.add("VersionBomPolicyRuleView");
        throwawayClasses.add("VersionBomPolicyStatusView");
        throwawayClasses.add("VulnerabilityView");
        throwawayClasses.add("VulnerabilityWithRemediationView");
        throwawayClasses.add("VulnerableComponentView");

        // Responses
        throwawayClasses.add("AssignedProjectView");
        throwawayClasses.add("ComponentSearchResult");
        throwawayClasses.add("ComponentVersionReferenceView");
        throwawayClasses.add("ComponentVersionRiskView");
        throwawayClasses.add("CustomFieldTypeView");
        throwawayClasses.add("CurrentVersionView");
        throwawayClasses.add("DashboardSummaryView");
        throwawayClasses.add("EndUserLicenseAgreementView");
        throwawayClasses.add("HealthCheckStatusView");
        throwawayClasses.add("JobStatisticsView");
        throwawayClasses.add("UserCommentView");
        throwawayClasses.add("VersionRiskProfileView");

        // Components
        throwawayClasses.add("ActivityDataView");
        throwawayClasses.add("AssignedUserGroup");
        throwawayClasses.add("AssignedUserGroupView");
        throwawayClasses.add("AuditEventCount");
        throwawayClasses.add("CommentUserData");
        throwawayClasses.add("BomComponentReleaseUsageRiskProfile");
        throwawayClasses.add("CodeLocationProgress");
        throwawayClasses.add("ChannelRelease");
        throwawayClasses.add("CommentUserData");
        throwawayClasses.add("ComplexLicenseRequest");
        throwawayClasses.add("ComponentHit");
        throwawayClasses.add("ComponentVersionPolicyViolationDetails");
        throwawayClasses.add("CompositePathWithArchiveContext");
        throwawayClasses.add("ConfigOptionView");
        throwawayClasses.add("CustomComponentVersionRequest");
        throwawayClasses.add("CustomFieldOptionRequest");
        throwawayClasses.add("CustomFieldOptionUpdateRequest");
        throwawayClasses.add("Cvss2TemporalMetricsView");
        throwawayClasses.add("Cvss3TemporalMetricsView");
        throwawayClasses.add("CweCommonConsequenceView");
        throwawayClasses.add("DeclaredComponentPath");
        throwawayClasses.add("Duration");
        throwawayClasses.add("EntityKey");
        throwawayClasses.add("Facet");
        throwawayClasses.add("FacetType");
        throwawayClasses.add("FacetValue");
        throwawayClasses.add("FileBomComponent");
        throwawayClasses.add("FileSnippetBomComponent");
        throwawayClasses.add("JournalObjectView");
        throwawayClasses.add("JournalTriggerView");
        throwawayClasses.add("LegacyAppliedFilterView");
        throwawayClasses.add("LegacyFilterValueView");
        throwawayClasses.add("LicenseDefinition");
        throwawayClasses.add("LicenseFamilyRiskRuleView");
        throwawayClasses.add("LicenseFamilySummaryView");
        throwawayClasses.add("LicenseTermCategorySummaryView");
        throwawayClasses.add("NameValuePairView");
        throwawayClasses.add("PolicyRuleExpressionParameter");
        throwawayClasses.add("PolicyRuleExpressionSetView");
        throwawayClasses.add("PolicyRuleExpressionView");
        throwawayClasses.add("PolicyStatusSummary");
        throwawayClasses.add("ProjectData");
        throwawayClasses.add("ProjectRiskProfile");
        throwawayClasses.add("ProjectVersionRequest");
        throwawayClasses.add("RegistrationAttributeView");
        throwawayClasses.add("RegistrationFeatureView");
        throwawayClasses.add("RegistrationMessageView");
        throwawayClasses.add("ReleaseData");
        throwawayClasses.add("RemediatingVersionView");
        throwawayClasses.add("ReportFileContent");
        throwawayClasses.add("ResourceLink");
        throwawayClasses.add("ResourceMetadata");
        throwawayClasses.add("ReviewedDetails");
        throwawayClasses.add("RiskCountView");
        throwawayClasses.add("RiskProfile");
        throwawayClasses.add("SearchResultSpec");
        throwawayClasses.add("SearchResultStatistics");
        throwawayClasses.add("SignaturePair");
        throwawayClasses.add("TagView");
        throwawayClasses.add("TemporalUnit");
        throwawayClasses.add("TextByteOffsetView");
        throwawayClasses.add("UserData");
        throwawayClasses.add("UserDataView");
        throwawayClasses.add("UserSummary");
        throwawayClasses.add("Version");
        throwawayClasses.add("VersionBomComponentView");
        throwawayClasses.add("VersionBomComponentDiffView");
        throwawayClasses.add("VersionBomComponentOriginUpdateRequest");
        throwawayClasses.add("VersionBomLicenseView");
        throwawayClasses.add("VersionBomOriginView");
        throwawayClasses.add("VersionDataView");
        throwawayClasses.add("VulnerableComponentView");

        // Enums
        throwawayClasses.add("ProjectVersionDistributionType");
        throwawayClasses.add("ProjectVersionPhaseType");

        return throwawayClasses;
    }

    private Set<String> populateCommonTypes() {
        final Set<String> commonTypes = new HashSet<>();

        commonTypes.add("Array");
        commonTypes.add("String");
        commonTypes.add("Object");
        commonTypes.add("BigDecimal");
        commonTypes.add("Integer");
        commonTypes.add("Boolean");
        commonTypes.add("java.util.Date");
        commonTypes.add("Number");

        return commonTypes;
    }

    private Set<String> populateNonEnumClassesEndingInType() {
        final Set<String> nonEnumClassesEndingInType = new HashSet<>();

        nonEnumClassesEndingInType.add("FacetType");

        return nonEnumClassesEndingInType;
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

    public boolean isThrowaway(final String className) { return this.THROWAWAY.contains(className); }

    public boolean isCommonType(final String className) { return this.COMMON_TYPES.contains(className); }

    public boolean isNonEnumClassEndingInType(final String className) { return this.NON_ENUM_CLASSES_ENDING_IN_TYPE.contains(className); }
}
