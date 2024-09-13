/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import com.blackduck.integration.create.apigen.generation.finder.ClassNameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.blackduck.integration.create.apigen.data.ClassSourceEnum.*;

@Component
public class ClassCategories {
    private final ClassNameManager classNameManager;
    private final Map<String, ClassCategoryData> classNameData = new HashMap<>();

    @Autowired
    public ClassCategories(ClassNameManager classNameManager) {
        this.classNameManager = classNameManager;
        populateData();
    }

    private void populateData() {
        classNameManager.getClassNames()
            .forEach(className -> populate(ClassTypeEnum.NULL, MANUAL, className));

        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "Array");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "String");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "Object");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "BigDecimal");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "Integer");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "Boolean");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "java.util.Date");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "Number");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "null");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "");
        populate(ClassTypeEnum.COMMON, ClassSourceEnum.NULL, "com.google.gson.JsonObject");

        populate(ClassTypeEnum.VIEW, GENERATED, "AnnouncementBannerView");
        populate(ClassTypeEnum.VIEW, GENERATED, "BomStatusScanView");
        populate(ClassTypeEnum.VIEW, GENERATED, "CodeLocationView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentMatchedFilesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentMigrationsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentPolicyRulesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVersionLicenseView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVersionLicenseLicensesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVersionPolicyStatusView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVersionRiskProfileRiskDataView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVersionView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentVulnerabilityBomView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ComponentView");
        populate(ClassTypeEnum.VIEW, GENERATED, "CpesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "CurrentUserView");
        populate(ClassTypeEnum.VIEW, GENERATED, "CustomFieldObjectView");
        populate(ClassTypeEnum.VIEW, GENERATED, "CustomFieldView");
        populate(ClassTypeEnum.VIEW, GENERATED, "DeveloperScansScanView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ExternalConfigDetectUriView");
        populate(ClassTypeEnum.VIEW, GENERATED, "FileSourceContentsSha1View");
        populate(ClassTypeEnum.VIEW, GENERATED, "GraphComponentImportEventsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "HealthChecksLivenessView");
        populate(ClassTypeEnum.VIEW, GENERATED, "HistoriesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "IntegrationGithubView");
        populate(ClassTypeEnum.VIEW, GENERATED, "IssueView");
        populate(ClassTypeEnum.VIEW, GENERATED, "JobView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseDashboardView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseFamilyView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseReportsReportView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseTermAssociationView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseTermCategoryView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseTermView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseTextView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LicenseView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LiteDashboardView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LiteProjectsProjectView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LiteVersionsProjectVersionView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LwBomProjectVersionsProjectVersionView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LwBomProjectsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "LwBomsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ManageAnnouncementBannerView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ManagementSettingsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "OriginView");
        populate(ClassTypeEnum.VIEW, GENERATED, "OriginDependencyPathsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "PolicyRuleView");
        populate(ClassTypeEnum.VIEW, GENERATED, "PolicySummaryView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectCustomFieldView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionBomStatusView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionComponentCustomFieldView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionComponentVersionCustomFieldView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionComponentView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionComponentVersionView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionCustomFieldView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionIssuesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionLicenseLicensesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionLicenseView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionPolicyStatusView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionVulnerableBomComponentsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectVersionVulnerableBomComponentsItemsVulnerabilityWithRemediationView");
        
        populate(ClassTypeEnum.VIEW, MANUAL, "ProjectView");
        
        populate(ClassTypeEnum.VIEW, GENERATED, "ProjectGroupsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "PurgeTokensView");
        populate(ClassTypeEnum.VIEW, GENERATED, "RegistrationView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ReportContentsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "RoleAssignmentView");
        populate(ClassTypeEnum.VIEW, GENERATED, "RoleView");
        populate(ClassTypeEnum.VIEW, GENERATED, "RuntimesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "SbomFieldsScopesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ScanBomEntriesView");
        populate(ClassTypeEnum.VIEW, GENERATED, "SearchKbPurlComponentView");
        populate(ClassTypeEnum.VIEW, GENERATED, "SettingsAnalysisView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ScanMonitorView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ScanView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ScanReadinessView");
        populate(ClassTypeEnum.VIEW, GENERATED, "SsoConfigurationView");
        populate(ClassTypeEnum.VIEW, GENERATED, "SystemOauthClientView");
        populate(ClassTypeEnum.VIEW, GENERATED, "TagView");
        populate(ClassTypeEnum.VIEW, GENERATED, "TokensView");
        populate(ClassTypeEnum.VIEW, GENERATED, "ToolsView");
        populate(ClassTypeEnum.VIEW, GENERATED, "UserGroupView");
        populate(ClassTypeEnum.VIEW, GENERATED, "UserView");
        populate(ClassTypeEnum.VIEW, GENERATED, "VulnerabilityReportsReportView");
        populate(ClassTypeEnum.VIEW, GENERATED, "VulnerabilityView");

        populate(ClassTypeEnum.VIEW, MANUAL, "NotificationView");
        populate(ClassTypeEnum.VIEW, MANUAL, "NotificationUserView");
        populate(ClassTypeEnum.VIEW, MANUAL, "ProjectMappingView");

        populate(ClassTypeEnum.VIEW, DEPRECATED, "ComplexLicenseView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "MatchedFileView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "PolicyStatusView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "RiskProfileView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "ProjectVersionPolicyRulesView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "VersionBomComponentView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "VersionBomPolicyRuleView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "VersionBomPolicyStatusView");
        populate(ClassTypeEnum.VIEW, DEPRECATED, "VulnerableComponentView");

        populate(ClassTypeEnum.VIEW, TEMPORARY, "AssignedUserView");
        populate(ClassTypeEnum.VIEW, TEMPORARY, "ComponentDetailsView");
        populate(ClassTypeEnum.VIEW, TEMPORARY, "ExternalExtensionConfigValueView");
        populate(ClassTypeEnum.VIEW, TEMPORARY, "ExternalExtensionUserView");
        populate(ClassTypeEnum.VIEW, TEMPORARY, "VulnerabilityWithRemediationView");

        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ComponentCustomFieldView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ComponentOriginMatchedFilesView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ComponentPolicyStatusView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ComponentVersionMatchedFilesView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "CustomFieldsCustomFieldView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ExternalExtensionView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "FieldsCustomFieldView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "FilterView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "LicenseTermsLicenseTermView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "LicensesLicenseView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ProjectDashboardRiskAmalgamation");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ProjectVersionReportView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "RegistrationAttributesInternalView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "RegistrationSummaryInternalView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "ReportView");
        populate(ClassTypeEnum.VIEW, ClassSourceEnum.NULL, "VulnerabilityAffectedProjectsView");

        populate(ClassTypeEnum.RESPONSE, GENERATED, "AssignedProjectView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "CommentView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "ComponentVersionRemediatingView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "ComponentVersionUpgradeGuidanceView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "ComponentsView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "ComponentVersionUpgradeGuidanceView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "CurrentVersionView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "CweView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "HealthChecksLivenessView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "ProjectJournalView");
        populate(ClassTypeEnum.RESPONSE, GENERATED, "TypesView");

        populate(ClassTypeEnum.RESPONSE, DEPRECATED, "ComponentSearchResultView");
        populate(ClassTypeEnum.RESPONSE, DEPRECATED, "CustomFieldTypeView");
        populate(ClassTypeEnum.RESPONSE, DEPRECATED, "HealthCheckStatusView");
        populate(ClassTypeEnum.RESPONSE, DEPRECATED, "RemediationOptionsView");

        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "AssignedUserGroupView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "ComponentVersionReferenceView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "ComponentVersionRiskView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "DashboardSummaryView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "EndUserLicenseAgreementView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "JobStatisticsView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "UserCommentView");
        populate(ClassTypeEnum.RESPONSE, TEMPORARY, "VersionRiskProfileView");

        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "ApiTokenView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "AssignableUserGroupView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "AssignableUserView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "AssignedUserRequest");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "ComponentVersionRemediatingView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "HierarchicalVersionBomComponentView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "HomepageView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "LegacyFilterView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "LogoView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "OpenHubView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "UserGroupProjectsView");
        populate(ClassTypeEnum.RESPONSE, ClassSourceEnum.NULL, "VersionBomAttachmentView");

        populate(ClassTypeEnum.COMPONENT, GENERATED, "ComponentVersionRiskProfileView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "CweCommonConsequencesView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "LicenseTermCategorySummaryView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "NotificationSubscriptionsSubscriptionView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsParametersView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "PolicyRuleExpressionView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "ProjectVersionComponentLicensesView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "ProjectVersionComponentReviewedDetailsReviewingUserView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "RegistrationFeaturesView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "VulnerabilityCvss2TemporalMetricsView");
        populate(ClassTypeEnum.COMPONENT, GENERATED, "VulnerabilityCvss3TemporalMetricsView");

        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "ActivityDataView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "ComponentVersionPolicyViolationDetails");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "CompositePathWithArchiveContext");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "LicenseFamilyRiskRuleView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "LicenseFamilySummaryView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "PolicyRuleExpressionParameter");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "PolicyRuleExpressionSetView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "RemediatingVersionView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "ReviewedDetails");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "RiskCountView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "VersionBomLicenseView");
        populate(ClassTypeEnum.COMPONENT, DEPRECATED, "VersionBomComponentDiffView");

        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "AssignedUserGroup");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "AuditEventCount");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "BomComponentReleaseUsageRiskProfile");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ChannelRelease");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "CodeLocationProgress");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "CommentUserData");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ComplexLicenseRequest");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ComponentHit");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ConfigOptionView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "CustomComponentVersionRequest");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "CustomFieldOptionRequest");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "DeclaredComponentPath");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "Duration");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "EntityKey");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "Facet");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "FacetType");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "FacetValue");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "FileBomComponent");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "FileSnippetBomComponent");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "JournalObjectView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "JournalTriggerView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "LegacyAppliedFilterView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "LegacyFilterValueView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "LicenseDefinition");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "NameValuePairView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "PolicyStatusSummary");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ProjectData");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ProjectRiskProfile");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ProjectVersionRequest");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ReleaseData");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ReportFileContent");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ResourceLink");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "ResourceMetadata");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "RiskProfile");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "SearchResultSpec");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "SearchResultStatistics");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "SignaturePair");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "TemporalUnit");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "TextByteOffsetView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "UserData");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "UserDataView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "UserSummary");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "Version");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "VersionBomComponentOriginUpdateRequest");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "VersionBomOriginView");
        populate(ClassTypeEnum.COMPONENT, TEMPORARY, "VersionDataView");

        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "CommentUserView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentActivityDataView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentActivityRiskProfileView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentLicenseRiskProfileView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentLicensesRiskView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentLicensesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentMatchedFilesItemsFilePathView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentOperationalRiskProfileView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentReviewedDetailsReviewingUserView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentReviewedDetailsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentSecurityRiskProfileView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingLatestAfterCurrentView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingNoVulnerabilitiesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRiskProfileRiskDataCountsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ComponentViewSecurityRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "CustomFieldOptionUpdateRequest");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "CustomFieldOptionsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "CustomFieldValueBulkRequestEntry");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "Cvss2TemporalMetricsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "Cvss3TemporalMetricsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseCreatedByView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseFamilyLicenseFamilyRiskRulesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseLicenseFamilyView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseStatusUpdatedByView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseUpdatedByView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicenseViewLicenseFamily");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseUpdatedByView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseViewCreatedBy");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseViewStatusUpdatedBy");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpression");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionParameters");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionsParameters");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "PolicyRulesPolicyruleViewExpression");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComparisonView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewActivityData");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewActivityRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewLicenseRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewOperationalRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewReviewedDetails");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewReviewedDetailsReviewingUser");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewSecurityRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewVersionRiskProfile");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionLicenseLicenseLicenseFamilySummaryView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionMatchedFilesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsSeverityLevelsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ProjectVersionPolicyStatusComponentVersionStatusCountsView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "RegistrationAttributesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "RegistrationMessagesView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "RegistrationViewFeatures");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "ReportContentsReportContentView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "UserSummaryView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VersionBomCustomFieldView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VersionReferenceView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VersionRiskView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VulnerabilityClassificationView");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VulnerabilityCvss2View");
        populate(ClassTypeEnum.COMPONENT, ClassSourceEnum.NULL, "VulnerabilityCvss3View");

        populate(ClassTypeEnum.ENUM, DEPRECATED, "ProjectVersionDistributionType");
        populate(ClassTypeEnum.ENUM, DEPRECATED, "LicenseOwnershipType");
        populate(ClassTypeEnum.ENUM, DEPRECATED, "OriginSourceType");
        populate(ClassTypeEnum.ENUM, TEMPORARY, "ProjectVersionPhaseType");
        populate(ClassTypeEnum.ENUM, TEMPORARY, "VersionBomComponentMatchType");
        populate(ClassTypeEnum.ENUM, TEMPORARY, "VersionBomComponentReviewStatusType");

        populate(ClassTypeEnum.NULL, GENERATED, "CommentsView");
        populate(ClassTypeEnum.NULL, GENERATED, "ProjectVersionComponentViewLicenses");

        populate(ClassTypeEnum.ENUM, MANUAL, "LicenseLimitType");
        populate(ClassTypeEnum.ENUM, MANUAL, "OperationType");
        populate(ClassTypeEnum.ENUM, MANUAL, "ScanSummaryStatusType");

        populate(ClassTypeEnum.NULL, MANUAL, "AffectedProjectVersion");
        populate(ClassTypeEnum.NULL, MANUAL, "BomEditNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "BomEditNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "BomEditNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "ComponentVersionStatus");
        populate(ClassTypeEnum.NULL, MANUAL, "LicenseLimitNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "LicenseLimitNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "LicenseLimitNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "NotificationContentComponent");
        populate(ClassTypeEnum.NULL, MANUAL, "NotificationContentData");
        populate(ClassTypeEnum.NULL, MANUAL, "NotificationViewData");
        populate(ClassTypeEnum.NULL, MANUAL, "PolicyInfo");
        populate(ClassTypeEnum.NULL, MANUAL, "PolicyOverrideNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "PolicyOverrideNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "PolicyOverrideNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectVersionNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectVersionNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "ProjectVersionNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationClearedNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationClearedNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationClearedNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "RuleViolationNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "ScanSummaryView");
        populate(ClassTypeEnum.NULL, MANUAL, "VersionBomCodeLocationBomComputedNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "VersionBomCodeLocationBomComputedNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "VersionBomCodeLocationBomComputedNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "VulnerabilityNotificationContent");
        populate(ClassTypeEnum.NULL, MANUAL, "VulnerabilityNotificationUserView");
        populate(ClassTypeEnum.NULL, MANUAL, "VulnerabilityNotificationView");
        populate(ClassTypeEnum.NULL, MANUAL, "VulnerabilitySourceQualifiedId");

        populate(ClassTypeEnum.NULL, TEMPORARY, "CweCommonConsequenceView");
        populate(ClassTypeEnum.NULL, TEMPORARY, "RegistrationAttributeView");
        populate(ClassTypeEnum.NULL, TEMPORARY, "RegistrationFeatureView");
        populate(ClassTypeEnum.NULL, TEMPORARY, "RegistrationMessageView");

        populate(ClassTypeEnum.RESPONSE, MANUAL, "BlackDuckStringResponse");
    }

    private void populate(ClassTypeEnum type, ClassSourceEnum source, String className) {
        classNameData.put(className, new ClassCategoryData(className, type, source));
    }

    public ClassCategoryData computeData(String className) {
        ClassCategoryData classCategoryData = classNameData.get(className);
        if (null == classCategoryData) {
            // default unknown case to a generated component
            ClassTypeEnum type = ClassTypeEnum.COMPONENT;
            ClassSourceEnum source = GENERATED;
            if (className.contains(UtilStrings.ENUM)) {
                type = ClassTypeEnum.ENUM;
            }
            classCategoryData = new ClassCategoryData(className, type, source);
            classNameData.put(className, classCategoryData);
        }

        return classCategoryData;
    }

}
