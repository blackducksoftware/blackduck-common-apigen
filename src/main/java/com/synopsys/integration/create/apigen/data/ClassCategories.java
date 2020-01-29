/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class ClassCategories {

    private final Set<String> views;
    private final Set<String> responses;
    private final Set<String> components;
    private final Set<String> generated;
    private final Set<String> manual;
    private final Set<String> throwaway;
    private final Set<String> commonTypes;
    private final Set<String> nonEnumClassesContainingType;
    private final Set<DeprecatedClassData> deprecatedClasses;

    @Autowired
    public ClassCategories() {
        this.views = populateViews();
        this.responses = populateResponses();
        this.components = populateComponents();
        this.generated = populateGenerated();
        this.manual = populateManual();
        this.throwaway = populateThrowaway();
        this.commonTypes = populateCommonTypes();
        this.nonEnumClassesContainingType = populateNonEnumClassesContainingType();
        this.deprecatedClasses = new HashSet<>();
    }

    private Set<String> populateViews() {
        final Set<String> views = new HashSet<>();

        views.add("AssignedUserView");
        views.add("CodeLocationView");
        views.add("CommentView");
        views.add("ComplexLicenseView");
        views.add("ComponentCustomFieldView");
        views.add("ComponentDetailsView");
        views.add("ComponentMatchedFilesView");
        views.add("ComponentOriginMatchedFilesView");
        views.add("ComponentPolicyStatusView");
        views.add("ComponentPolicyRulesView");
        views.add("ComponentVersionMatchedFilesView");
        views.add("ComponentVersionPolicyStatusView");
        views.add("ComponentVersionRiskProfileView");
        views.add("ComponentVersionView");
        views.add("ComponentView");
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
        views.add("LicenseReportsReportView");  //
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
        views.add("PolicyStatusView");
        views.add("ProjectCustomFieldView");
        views.add("ProjectDashboardRiskAmalgamation");
        views.add("ProjectMappingView");
        views.add("ProjectVersionComponentCustomFieldView");
        views.add("ProjectVersionComponentVersionCustomFieldView");
        views.add("ProjectVersionComponentView");
        views.add("ProjectVersionCustomFieldView");
        views.add("ProjectVersionLicenseView");
        views.add("ProjectVersionLicenseLicensesView");
        views.add("ProjectVersionPolicyStatusView");
        views.add("ProjectVersionReportView");
        views.add("ProjectVersionView");
        views.add("ProjectVersionVulnerableBomComponentsView");
        views.add("ProjectView");
        views.add("RegistrationAttributesInternalView");
        views.add("RegistrationSummaryInternalView");
        views.add("RegistrationView");
        views.add("ReportView");
        views.add("ReportContentsView");
        views.add("RiskProfileView");
        views.add("RoleView");
        views.add("RoleAssignmentView");
        views.add("ScanView");
        views.add("TagView");
        views.add("UserGroupView");
        views.add("UserView");
        views.add("VersionBomComponentView");
        views.add("VersionBomPolicyRuleView");
        views.add("VersionBomPolicyStatusView");
        views.add("VulnerabilityAffectedProjectsView");
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
        responses.add("ComponentSearchResultView");
        responses.add("ComponentsView");
        responses.add("ComponentVersionReferenceView");
        responses.add("ComponentVersionRemediatingView");
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
        responses.add("TypesView");
        responses.add("UserCommentView");
        responses.add("UserProjectsView");
        responses.add("UserGroupProjectsView");
        responses.add("VersionBomAttachmentView");
        responses.add("VersionRiskProfileView");

        return responses;
    }

    private Set<String> populateComponents() {
        final Set<String> components = new HashSet<>();

        components.add("ActivityDataView");
        components.add("AssignedUserGroup");
        components.add("AuditEventCount");
        components.add("BomComponentReleaseUsageRiskProfile");
        components.add("ChannelRelease");
        components.add("CodeLocationProgress");
        components.add("CommentUserData");
        components.add("CommentUserView");
        components.add("ComplexLicenseRequest");
        components.add("ComponentHit");
        components.add("ComponentLicensesView");
        components.add("ComponentActivityDataView");
        components.add("ComponentActivityRiskProfileView");
        components.add("ComponentLicenseRiskProfileView");
        components.add("ComponentLicensesRiskView");
        components.add("ComponentMatchedFilesItemsFilePathView");
        components.add("ComponentOperationalRiskProfileView");
        components.add("ComponentSecurityRiskProfileView");
        components.add("ComponentReviewedDetailsView");
        components.add("ComponentReviewedDetailsReviewingUserView");
        components.add("ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        components.add("ComponentVersionRemediatingLatestAfterCurrentView");
        components.add("ComponentVersionRemediatingNoVulnerabilitiesView");
        components.add("ComponentVersionRiskProfileView");
        components.add("ComponentVersionRiskProfileRiskDataCountsView");
        components.add("ComponentVersionPolicyViolationDetails");
        components.add("CompositePathWithArchiveContext");
        components.add("ConfigOptionView");
        components.add("CustomFieldOptionRequest");
        components.add("CustomFieldOptionsView");
        components.add("CustomComponentVersionRequest");
        components.add("CustomFieldValueBulkRequestEntry");
        components.add("CustomFieldOptionUpdateRequest");
        components.add("Cvss2TemporalMetricsView");
        components.add("Cvss3TemporalMetricsView");
        components.add("CweCommonConsequencesView");
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
        components.add("LicenseFamilyLicenseFamilyRiskRulesView");
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
        components.add("NotificationSubscriptionsSubscriptionView");
        components.add("PolicyRuleExpressionExpressionsView"); //
        components.add("PolicyRuleExpressionView"); //
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
        components.add("ProjectVersionComparisonView");
        components.add("ProjectVersionComponentReviewedDetailsReviewingUserView");
        components.add("ProjectVersionLicenseLicenseLicenseFamilySummaryView"); //
        components.add("ProjectVersionLicenseLicensesLicenseFamilySummaryView"); //
        components.add("ProjectVersionComponentLicensesView"); //
        components.add("ProjectVersionComponentViewActivityData");
        components.add("ProjectVersionComponentViewActivityRiskProfile");
        components.add("ProjectVersionComponentViewLicenseRiskProfile");
        components.add("ProjectVersionComponentViewReviewedDetails");
        components.add("ProjectVersionComponentViewReviewedDetailsReviewingUser");
        components.add("ProjectVersionComponentViewOperationalRiskProfile");
        components.add("ProjectVersionComponentViewSecurityRiskProfile");
        components.add("ProjectVersionComponentViewVersionRiskProfile");
        components.add("ProjectVersionMatchedFilesView");
        components.add("ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsSeverityLevelsView");
        components.add("ProjectVersionPolicyStatusComponentVersionStatusCountsView");
        components.add("ProjectVersionRequest");
        components.add("RegistrationAttributesView");
        components.add("RegistrationFeaturesView");
        components.add("RegistrationMessagesView");
        components.add("RegistrationViewFeatures");
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
        components.add("VersionReferenceView");
        components.add("VersionRiskView");
        components.add("ComponentViewSecurityRiskProfile"); //
        components.add("VulnerabilityClassificationView");
        components.add("VulnerabilityCvss2View");
        components.add("VulnerabilityCvss3View");
        components.add("VulnerabilityCvss2TemporalMetricsView");
        components.add("VulnerabilityCvss3TemporalMetricsView");

        return components;
    }

    private Set<String> populateGenerated() {
        final Set<String> generatedClasses = new HashSet<>();

        // component
        generatedClasses.add("PolicyRuleExpressionView");
        generatedClasses.add("PolicyRuleExpressionExpressionsView");
        generatedClasses.add("PolicyRuleExpressionExpressionsParametersView");
        generatedClasses.add("ProjectVersionComponentReviewedDetailsReviewingUserView");
        generatedClasses.add("ProjectVersionComponentViewLicenses");
        generatedClasses.add("ProjectVersionLicenseView");
        generatedClasses.add("ProjectVersionLicenseLicensesView");
        generatedClasses.add("ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        generatedClasses.add("VulnerabilityCvss2TemporalMetricsView");
        generatedClasses.add("VulnerabilityCvss3TemporalMetricsView");

        // enumeration
        generatedClasses.add("ComponentApprovalStatusType");
        generatedClasses.add("ComponentCustomFieldTypeType");
        generatedClasses.add("ComponentSourceType");
        generatedClasses.add("CustomFieldTypeType");
        generatedClasses.add("PolicyRuleSeverityType");
        generatedClasses.add("PolicyStatusType");
        generatedClasses.add("ProjectCloneCategoriesType");
        generatedClasses.add("ProjectCustomFieldTypeType");
        generatedClasses.add("ProjectVersionComponentCustomFieldTypeType");
        generatedClasses.add("ProjectVersionComponentVersionCustomFieldTypeType");
        generatedClasses.add("ProjectVersionCustomFieldTypeType");

        // responses
        generatedClasses.add("ComponentsView");
        generatedClasses.add("ComponentSearchResultView");
        generatedClasses.add("CweView");
        generatedClasses.add("CustomFieldTypeView");
        generatedClasses.add("RemediationOptionsView");

        // views
        generatedClasses.add("CodeLocationView");
        generatedClasses.add("CommentView");
        generatedClasses.add("CommentsView");
        generatedClasses.add("ComponentVersionPolicyStatusView");
        generatedClasses.add("ComponentVersionRiskProfileView");
        generatedClasses.add("ComponentView");
        generatedClasses.add("ComponentVersionView");
        generatedClasses.add("CustomFieldView");
        generatedClasses.add("CustomFieldObjectView");
        generatedClasses.add("CurrentUserView");
        generatedClasses.add("JobView");
        generatedClasses.add("LicenseFamiliesLicenseFamilyView");
        generatedClasses.add("LicenseReportsReportView");
        generatedClasses.add("LicenseTermView");
        generatedClasses.add("LicenseTextView");
        generatedClasses.add("LicenseView");
        generatedClasses.add("PolicyRuleView");
        generatedClasses.add("ProjectCustomFieldView");
        generatedClasses.add("ProjectJournalView");
        generatedClasses.add("ProjectVersionComponentCustomFieldView");
        generatedClasses.add("ProjectVersionComponentVersionCustomFieldView");
        generatedClasses.add("ProjectView");
        generatedClasses.add("ProjectVersionView");
        generatedClasses.add("ProjectVersionComponentView");
        generatedClasses.add("ProjectVersionCustomFieldView");
        generatedClasses.add("ProjectVersionPolicyStatusView");
        generatedClasses.add("RiskProfileView");
        generatedClasses.add("ReportContentsView");
        generatedClasses.add("RegistrationView");
        generatedClasses.add("RoleView");
        generatedClasses.add("ScanView");
        generatedClasses.add("UserView");
        generatedClasses.add("UserGroupView");
        generatedClasses.add("RoleAssignmentView");
        generatedClasses.add("VulnerabilityReportsReportView");
        generatedClasses.add("VulnerabilityView");

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
        throwawayClasses.add("ComponentDetailsView");
        throwawayClasses.add("ComponentVersionView");
        throwawayClasses.add("ExternalExtensionConfigValueView");
        throwawayClasses.add("ExternalExtensionUserView");
        throwawayClasses.add("IssueView");
        throwawayClasses.add("LicenseFamilyView");
        throwawayClasses.add("LicenseTermAssociationView");
        throwawayClasses.add("LicenseTermCategoryView");
        throwawayClasses.add("OriginView");
        throwawayClasses.add("NotificationUserView");
        throwawayClasses.add("MatchedFileView");
        throwawayClasses.add("ProjectMappingView");
        throwawayClasses.add("VersionBomOriginView");
        throwawayClasses.add("VulnerabilityWithRemediationView");
        throwawayClasses.add("VulnerableComponentView");

        // Responses
        throwawayClasses.add("AssignedProjectView");
        throwawayClasses.add("ComponentVersionReferenceView");
        throwawayClasses.add("ComponentVersionRiskView");
        throwawayClasses.add("CurrentVersionView");
        throwawayClasses.add("DashboardSummaryView");
        throwawayClasses.add("EndUserLicenseAgreementView");
        throwawayClasses.add("HealthCheckStatusView");
        throwawayClasses.add("JobStatisticsView");
        throwawayClasses.add("UserCommentView");
        throwawayClasses.add("VersionRiskProfileView");

        // Components
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
        throwawayClasses.add("VersionBomComponentMatchType");
        throwawayClasses.add("VersionBomComponentReviewStatusType");

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
        commonTypes.add("null");
        commonTypes.add("");

        return commonTypes;
    }

    private Set<String> populateNonEnumClassesContainingType() {
        final Set<String> nonEnumClassesContainingType = new HashSet<>();

        nonEnumClassesContainingType.add("FacetType");
        nonEnumClassesContainingType.add("TypesView");
        nonEnumClassesContainingType.add("CustomFieldTypeView");

        return nonEnumClassesContainingType;
    }

    public Set<DeprecatedClassData> getDeprecatedClasses() {
        return deprecatedClasses;
    }

    public void addDeprecatedClass(final String swaggerName, final String apigenName, final Template template, final Map<String, Object> input, final String destination) {
        Map<String, Object> newInput = new HashMap<>();
        for (Map.Entry<String, Object> entry : input.entrySet()) {
            newInput.put(entry.getKey(), entry.getValue());
        }
        newInput.put(UtilStrings.CLASS_NAME, swaggerName);
        newInput.put("hasNewName", true);
        newInput.put("newName", NameParser.getNonVersionedName(apigenName));
        newInput.put("isDeprecated", true);
        for (DeprecatedClassData deprecatedClass : deprecatedClasses) {
            if (deprecatedClass.getSwaggerName().equals(swaggerName)) {
                return;
            }
        }
        deprecatedClasses.add(new DeprecatedClassData(swaggerName, apigenName, template, newInput, destination));
    }

    private boolean isView(final String className) {
        return this.views.contains(className);
    }

    private boolean isResponse(final String className) {
        return this.responses.contains(className);
    }

    private boolean isComponent(final String className) { return this.components.contains(className); }

    private boolean isGenerated(final String className) { return this.generated.contains(className); }

    private boolean isManual(final String className) { return this.manual.contains(className); }

    private boolean isThrowaway(final String className) { return this.throwaway.contains(className); }

    private boolean isCommonType(final String className) { return this.commonTypes.contains(className); }

    private boolean isNonEnumClassContainingType(final String className) { return this.nonEnumClassesContainingType.contains(className); }

    public ClassTypeEnum computeType(String className) {
        className = NameParser.getNonVersionedName(className);
        if (isView(className)) {
            return ClassTypeEnum.VIEW;
        }
        if (isComponent(className)) {
            return ClassTypeEnum.COMPONENT;
        }
        if (className.contains(UtilStrings.ENUM) && !isNonEnumClassContainingType(className)) {
            return ClassTypeEnum.ENUM;
        }
        if (isResponse(className)) {
            return ClassTypeEnum.RESPONSE;
        }
        if (isCommonType(className)) {
            return ClassTypeEnum.COMMON;
        }
        if (isNonEnumClassContainingType(className)) {
            return ClassTypeEnum.NON_ENUM_CONTAINING_TYPE;
        }
        return ClassTypeEnum.NULL;
    }

    public ClassSourceEnum computeSource(String className) {
        className = NameParser.getNonVersionedName(className);
        if (isGenerated(className)) {
            return ClassSourceEnum.GENERATED;
        }
        if (isManual(className)) {
            return ClassSourceEnum.MANUAL;
        }
        if (isThrowaway(className)) {
            return ClassSourceEnum.THROWAWAY;
        }
        return ClassSourceEnum.NULL;
    }

}
