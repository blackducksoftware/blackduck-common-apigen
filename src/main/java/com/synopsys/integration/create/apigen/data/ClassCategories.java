/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

import static com.synopsys.integration.create.apigen.data.ClassSourceEnum.DEPRECATED;
import static com.synopsys.integration.create.apigen.data.ClassSourceEnum.GENERATED;
import static com.synopsys.integration.create.apigen.data.ClassSourceEnum.MANUAL;
import static com.synopsys.integration.create.apigen.data.ClassSourceEnum.TEMPORARY;
import static com.synopsys.integration.create.apigen.data.ClassTypeEnum.COMMON;
import static com.synopsys.integration.create.apigen.data.ClassTypeEnum.COMPONENT;
import static com.synopsys.integration.create.apigen.data.ClassTypeEnum.RESPONSE;
import static com.synopsys.integration.create.apigen.data.ClassTypeEnum.VIEW;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.generation.finder.ClassNameManager;

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

        populate(COMMON, ClassSourceEnum.NULL, "Array");
        populate(COMMON, ClassSourceEnum.NULL, "String");
        populate(COMMON, ClassSourceEnum.NULL, "Object");
        populate(COMMON, ClassSourceEnum.NULL, "BigDecimal");
        populate(COMMON, ClassSourceEnum.NULL, "Integer");
        populate(COMMON, ClassSourceEnum.NULL, "Boolean");
        populate(COMMON, ClassSourceEnum.NULL, "java.util.Date");
        populate(COMMON, ClassSourceEnum.NULL, "Number");
        populate(COMMON, ClassSourceEnum.NULL, "null");
        populate(COMMON, ClassSourceEnum.NULL, "");

        populate(VIEW, GENERATED, "AnnouncementBannerView");
        populate(VIEW, GENERATED, "CodeLocationView");
        populate(VIEW, GENERATED, "ComponentMatchedFilesView");
        populate(VIEW, GENERATED, "ComponentMigrationsView");
        populate(VIEW, GENERATED, "ComponentPolicyRulesView");
        populate(VIEW, GENERATED, "ComponentVersionLicenseView");
        populate(VIEW, GENERATED, "ComponentVersionLicenseLicensesView");
        populate(VIEW, GENERATED, "ComponentVersionPolicyStatusView");
        populate(VIEW, GENERATED, "ComponentVersionRiskProfileRiskDataView");
        populate(VIEW, GENERATED, "ComponentVersionView");
        populate(VIEW, GENERATED, "ComponentView");
        populate(VIEW, GENERATED, "CpesView");
        populate(VIEW, GENERATED, "CurrentUserView");
        populate(VIEW, GENERATED, "CustomFieldObjectView");
        populate(VIEW, GENERATED, "CustomFieldView");
        populate(VIEW, GENERATED, "DeveloperScansScanView");
        populate(VIEW, GENERATED, "ExternalConfigDetectUriView");
        populate(VIEW, GENERATED, "FileSourceContentsSha1View");
        populate(VIEW, GENERATED, "GraphComponentImportEventsView");
        populate(VIEW, GENERATED, "HealthChecksLivenessView");
        populate(VIEW, GENERATED, "IssueView");
        populate(VIEW, GENERATED, "JobView");
        populate(VIEW, GENERATED, "LicenseDashboardView");
        populate(VIEW, GENERATED, "LicenseFamilyView");
        populate(VIEW, GENERATED, "LicenseReportsReportView");
        populate(VIEW, GENERATED, "LicenseTermAssociationView");
        populate(VIEW, GENERATED, "LicenseTermCategoryView");
        populate(VIEW, GENERATED, "LicenseTermView");
        populate(VIEW, GENERATED, "LicenseTextView");
        populate(VIEW, GENERATED, "LicenseView");
        populate(VIEW, GENERATED, "ManageAnnouncementBannerView");
        populate(VIEW, GENERATED, "ManagementSettingsView");
        populate(VIEW, GENERATED, "OriginView");
        populate(VIEW, GENERATED, "OriginDependencyPathsView");
        populate(VIEW, GENERATED, "PolicyRuleView");
        populate(VIEW, GENERATED, "ProjectCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentVersionCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentView");
        populate(VIEW, GENERATED, "ProjectVersionComponentVersionView");
        populate(VIEW, GENERATED, "ProjectVersionCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionIssuesView");
        populate(VIEW, GENERATED, "ProjectVersionLicenseLicensesView");
        populate(VIEW, GENERATED, "ProjectVersionLicenseView");
        populate(VIEW, GENERATED, "ProjectVersionPolicyStatusView");
        populate(VIEW, GENERATED, "ProjectVersionView");
        populate(VIEW, GENERATED, "ProjectVersionVulnerableBomComponentsView");
        populate(VIEW, GENERATED, "ProjectVersionVulnerableBomComponentsItemsVulnerabilityWithRemediationView");
        populate(VIEW, GENERATED, "ProjectView");
        populate(VIEW, GENERATED, "ProjectGroupsView");
        populate(VIEW, GENERATED, "RegistrationView");
        populate(VIEW, GENERATED, "ReportContentsView");
        populate(VIEW, GENERATED, "RoleAssignmentView");
        populate(VIEW, GENERATED, "RoleView");
        populate(VIEW, GENERATED, "ScanBomEntriesView");
        populate(VIEW, GENERATED, "SettingsAnalysisView");
        populate(VIEW, GENERATED, "ScanView");
        populate(VIEW, GENERATED, "ScanReadinessView");
        populate(VIEW, GENERATED, "SsoConfigurationView");
        populate(VIEW, GENERATED, "SystemOauthClientView");
        populate(VIEW, GENERATED, "TagView");
        populate(VIEW, GENERATED, "UserGroupView");
        populate(VIEW, GENERATED, "UserView");
        populate(VIEW, GENERATED, "VulnerabilityReportsReportView");
        populate(VIEW, GENERATED, "VulnerabilityView");

        populate(VIEW, MANUAL, "NotificationView");
        populate(VIEW, MANUAL, "NotificationUserView");
        populate(VIEW, MANUAL, "ProjectMappingView");

        populate(VIEW, DEPRECATED, "ComplexLicenseView");
        populate(VIEW, DEPRECATED, "MatchedFileView");
        populate(VIEW, DEPRECATED, "PolicyStatusView");
        populate(VIEW, DEPRECATED, "RiskProfileView");
        populate(VIEW, DEPRECATED, "VersionBomComponentView");
        populate(VIEW, DEPRECATED, "VersionBomPolicyRuleView");
        populate(VIEW, DEPRECATED, "VersionBomPolicyStatusView");
        populate(VIEW, DEPRECATED, "VulnerableComponentView");

        populate(VIEW, TEMPORARY, "AssignedUserView");
        populate(VIEW, TEMPORARY, "ComponentDetailsView");
        populate(VIEW, TEMPORARY, "ExternalExtensionConfigValueView");
        populate(VIEW, TEMPORARY, "ExternalExtensionUserView");
        populate(VIEW, TEMPORARY, "VulnerabilityWithRemediationView");

        populate(VIEW, ClassSourceEnum.NULL, "ComponentCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentOriginMatchedFilesView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentPolicyStatusView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentVersionMatchedFilesView");
        populate(VIEW, ClassSourceEnum.NULL, "CustomFieldsCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "ExternalExtensionView");
        populate(VIEW, ClassSourceEnum.NULL, "FieldsCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "FilterView");
        populate(VIEW, ClassSourceEnum.NULL, "LicenseTermsLicenseTermView");
        populate(VIEW, ClassSourceEnum.NULL, "LicensesLicenseView");
        populate(VIEW, ClassSourceEnum.NULL, "ProjectDashboardRiskAmalgamation");
        populate(VIEW, ClassSourceEnum.NULL, "ProjectVersionReportView");
        populate(VIEW, ClassSourceEnum.NULL, "RegistrationAttributesInternalView");
        populate(VIEW, ClassSourceEnum.NULL, "RegistrationSummaryInternalView");
        populate(VIEW, ClassSourceEnum.NULL, "ReportView");
        populate(VIEW, ClassSourceEnum.NULL, "VulnerabilityAffectedProjectsView");

        populate(RESPONSE, GENERATED, "AssignedProjectView");
        populate(RESPONSE, GENERATED, "CommentView");
        populate(RESPONSE, GENERATED, "ComponentVersionRemediatingView");
        populate(RESPONSE, GENERATED, "ComponentVersionUpgradeGuidanceView");
        populate(RESPONSE, GENERATED, "ComponentsView");
        populate(RESPONSE, GENERATED, "ComponentVersionUpgradeGuidanceView");
        populate(RESPONSE, GENERATED, "CurrentVersionView");
        populate(RESPONSE, GENERATED, "CweView");
        populate(RESPONSE, GENERATED, "HealthChecksLivenessView");
        populate(RESPONSE, GENERATED, "ProjectJournalView");
        populate(RESPONSE, GENERATED, "TypesView");

        populate(RESPONSE, DEPRECATED, "ComponentSearchResultView");
        populate(RESPONSE, DEPRECATED, "CustomFieldTypeView");
        populate(RESPONSE, DEPRECATED, "HealthCheckStatusView");
        populate(RESPONSE, DEPRECATED, "RemediationOptionsView");

        populate(RESPONSE, TEMPORARY, "AssignedUserGroupView");
        populate(RESPONSE, TEMPORARY, "ComponentVersionReferenceView");
        populate(RESPONSE, TEMPORARY, "ComponentVersionRiskView");
        populate(RESPONSE, TEMPORARY, "DashboardSummaryView");
        populate(RESPONSE, TEMPORARY, "EndUserLicenseAgreementView");
        populate(RESPONSE, TEMPORARY, "JobStatisticsView");
        populate(RESPONSE, TEMPORARY, "PolicySummaryView");
        populate(RESPONSE, TEMPORARY, "UserCommentView");
        populate(RESPONSE, TEMPORARY, "VersionRiskProfileView");

        populate(RESPONSE, ClassSourceEnum.NULL, "ApiTokenView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignableUserGroupView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignableUserView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignedUserRequest");
        populate(RESPONSE, ClassSourceEnum.NULL, "ComponentVersionRemediatingView");
        populate(RESPONSE, ClassSourceEnum.NULL, "HierarchicalVersionBomComponentView");
        populate(RESPONSE, ClassSourceEnum.NULL, "HomepageView");
        populate(RESPONSE, ClassSourceEnum.NULL, "LegacyFilterView");
        populate(RESPONSE, ClassSourceEnum.NULL, "LogoView");
        populate(RESPONSE, ClassSourceEnum.NULL, "OpenHubView");
        populate(RESPONSE, ClassSourceEnum.NULL, "UserGroupProjectsView");
        populate(RESPONSE, ClassSourceEnum.NULL, "VersionBomAttachmentView");

        populate(COMPONENT, GENERATED, "ComponentVersionRiskProfileView");
        populate(COMPONENT, GENERATED, "CweCommonConsequencesView");
        populate(COMPONENT, GENERATED, "LicenseTermCategorySummaryView");
        populate(COMPONENT, GENERATED, "NotificationSubscriptionsSubscriptionView");
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsParametersView");
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsView");
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionView");
        populate(COMPONENT, GENERATED, "ProjectVersionComponentLicensesView");
        populate(COMPONENT, GENERATED, "ProjectVersionComponentReviewedDetailsReviewingUserView");
        populate(COMPONENT, GENERATED, "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        populate(COMPONENT, GENERATED, "RegistrationFeaturesView");
        populate(COMPONENT, GENERATED, "VulnerabilityCvss2TemporalMetricsView");
        populate(COMPONENT, GENERATED, "VulnerabilityCvss3TemporalMetricsView");

        populate(COMPONENT, DEPRECATED, "ActivityDataView");
        populate(COMPONENT, DEPRECATED, "ComponentVersionPolicyViolationDetails");
        populate(COMPONENT, DEPRECATED, "CompositePathWithArchiveContext");
        populate(COMPONENT, DEPRECATED, "LicenseFamilyRiskRuleView");
        populate(COMPONENT, DEPRECATED, "LicenseFamilySummaryView");
        populate(COMPONENT, DEPRECATED, "PolicyRuleExpressionParameter");
        populate(COMPONENT, DEPRECATED, "PolicyRuleExpressionSetView");
        populate(COMPONENT, DEPRECATED, "RemediatingVersionView");
        populate(COMPONENT, DEPRECATED, "ReviewedDetails");
        populate(COMPONENT, DEPRECATED, "RiskCountView");
        populate(COMPONENT, DEPRECATED, "VersionBomLicenseView");
        populate(COMPONENT, DEPRECATED, "VersionBomComponentDiffView");

        populate(COMPONENT, TEMPORARY, "AssignedUserGroup");
        populate(COMPONENT, TEMPORARY, "AuditEventCount");
        populate(COMPONENT, TEMPORARY, "BomComponentReleaseUsageRiskProfile");
        populate(COMPONENT, TEMPORARY, "ChannelRelease");
        populate(COMPONENT, TEMPORARY, "CodeLocationProgress");
        populate(COMPONENT, TEMPORARY, "CommentUserData");
        populate(COMPONENT, TEMPORARY, "ComplexLicenseRequest");
        populate(COMPONENT, TEMPORARY, "ComponentHit");
        populate(COMPONENT, TEMPORARY, "ConfigOptionView");
        populate(COMPONENT, TEMPORARY, "CustomComponentVersionRequest");
        populate(COMPONENT, TEMPORARY, "CustomFieldOptionRequest");
        populate(COMPONENT, TEMPORARY, "DeclaredComponentPath");
        populate(COMPONENT, TEMPORARY, "Duration");
        populate(COMPONENT, TEMPORARY, "EntityKey");
        populate(COMPONENT, TEMPORARY, "Facet");
        populate(COMPONENT, TEMPORARY, "FacetType");
        populate(COMPONENT, TEMPORARY, "FacetValue");
        populate(COMPONENT, TEMPORARY, "FileBomComponent");
        populate(COMPONENT, TEMPORARY, "FileSnippetBomComponent");
        populate(COMPONENT, TEMPORARY, "JournalObjectView");
        populate(COMPONENT, TEMPORARY, "JournalTriggerView");
        populate(COMPONENT, TEMPORARY, "LegacyAppliedFilterView");
        populate(COMPONENT, TEMPORARY, "LegacyFilterValueView");
        populate(COMPONENT, TEMPORARY, "LicenseDefinition");
        populate(COMPONENT, TEMPORARY, "NameValuePairView");
        populate(COMPONENT, TEMPORARY, "PolicyStatusSummary");
        populate(COMPONENT, TEMPORARY, "ProjectData");
        populate(COMPONENT, TEMPORARY, "ProjectRiskProfile");
        populate(COMPONENT, TEMPORARY, "ProjectVersionRequest");
        populate(COMPONENT, TEMPORARY, "ReleaseData");
        populate(COMPONENT, TEMPORARY, "ReportFileContent");
        populate(COMPONENT, TEMPORARY, "ResourceLink");
        populate(COMPONENT, TEMPORARY, "ResourceMetadata");
        populate(COMPONENT, TEMPORARY, "RiskProfile");
        populate(COMPONENT, TEMPORARY, "SearchResultSpec");
        populate(COMPONENT, TEMPORARY, "SearchResultStatistics");
        populate(COMPONENT, TEMPORARY, "SignaturePair");
        populate(COMPONENT, TEMPORARY, "TemporalUnit");
        populate(COMPONENT, TEMPORARY, "TextByteOffsetView");
        populate(COMPONENT, TEMPORARY, "UserData");
        populate(COMPONENT, TEMPORARY, "UserDataView");
        populate(COMPONENT, TEMPORARY, "UserSummary");
        populate(COMPONENT, TEMPORARY, "Version");
        populate(COMPONENT, TEMPORARY, "VersionBomComponentOriginUpdateRequest");
        populate(COMPONENT, TEMPORARY, "VersionBomOriginView");
        populate(COMPONENT, TEMPORARY, "VersionDataView");

        populate(COMPONENT, ClassSourceEnum.NULL, "CommentUserView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentActivityDataView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentActivityRiskProfileView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentLicenseRiskProfileView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentLicensesRiskView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentLicensesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentMatchedFilesItemsFilePathView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentOperationalRiskProfileView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentReviewedDetailsReviewingUserView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentReviewedDetailsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentSecurityRiskProfileView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingLatestAfterCurrentView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRemediatingNoVulnerabilitiesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentVersionRiskProfileRiskDataCountsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ComponentViewSecurityRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "CustomFieldOptionUpdateRequest");
        populate(COMPONENT, ClassSourceEnum.NULL, "CustomFieldOptionsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "CustomFieldValueBulkRequestEntry");
        populate(COMPONENT, ClassSourceEnum.NULL, "Cvss2TemporalMetricsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "Cvss3TemporalMetricsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseCreatedByView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseFamilyLicenseFamilyRiskRulesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseLicenseFamilyView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseStatusUpdatedByView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseUpdatedByView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicenseViewLicenseFamily");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseUpdatedByView");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseViewCreatedBy");
        populate(COMPONENT, ClassSourceEnum.NULL, "LicensesLicenseViewStatusUpdatedBy");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpression");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionParameters");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionsParameters");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRulesPolicyruleViewExpression");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComparisonView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewActivityData");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewActivityRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewLicenseRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewOperationalRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewReviewedDetails");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewReviewedDetailsReviewingUser");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewSecurityRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentViewVersionRiskProfile");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionLicenseLicenseLicenseFamilySummaryView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionMatchedFilesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsSeverityLevelsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionPolicyStatusComponentVersionStatusCountsView");
        populate(COMPONENT, ClassSourceEnum.NULL, "RegistrationAttributesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "RegistrationMessagesView");
        populate(COMPONENT, ClassSourceEnum.NULL, "RegistrationViewFeatures");
        populate(COMPONENT, ClassSourceEnum.NULL, "ReportContentsReportContentView");
        populate(COMPONENT, ClassSourceEnum.NULL, "UserSummaryView");
        populate(COMPONENT, ClassSourceEnum.NULL, "VersionBomCustomFieldView");
        populate(COMPONENT, ClassSourceEnum.NULL, "VersionReferenceView");
        populate(COMPONENT, ClassSourceEnum.NULL, "VersionRiskView");
        populate(COMPONENT, ClassSourceEnum.NULL, "VulnerabilityClassificationView");
        populate(COMPONENT, ClassSourceEnum.NULL, "VulnerabilityCvss2View");
        populate(COMPONENT, ClassSourceEnum.NULL, "VulnerabilityCvss3View");

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

        populate(RESPONSE, MANUAL, "BlackDuckStringResponse");
    }

    private void populate(ClassTypeEnum type, ClassSourceEnum source, String className) {
        classNameData.put(className, new ClassCategoryData(className, type, source));
    }

    public ClassCategoryData computeData(String className) {
        ClassCategoryData classCategoryData = classNameData.get(className);
        if (null == classCategoryData) {
            // default unknown case to a generated component
            ClassTypeEnum type = COMPONENT;
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
