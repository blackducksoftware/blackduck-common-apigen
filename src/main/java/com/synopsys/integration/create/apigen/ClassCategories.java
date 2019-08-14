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
        views.add("ComponentCustomFieldView");
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
        views.add("ProjectCustomFieldView");
        views.add("ProjectDashboardRiskAmalgamation");
        views.add("ProjectVersionComponentCustomFieldView");
        views.add("ProjectVersionComponentVersionCustomFieldView");
        views.add("ProjectVersionComponentView");
        views.add("ProjectVersionCustomFieldView");
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
        views.add("UserRoleAssignmentView");
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
        components.add("PolicyRuleViewExpression"); //
        components.add("PolicyRuleViewExpressionExpression"); //
        components.add("PolicyRuleViewExpressionExpressionParameters"); //
        components.add("PolicyRuleExpressionSetView");
        components.add("PolicyRuleExpressionView");
        components.add("ProjectVersionViewLicenseLicenseLicenseFamilySummary"); //
        components.add("ProjectVersionComponentViewLicenses"); //
        components.add("ProjectVersionViewLicense"); //
        components.add("ProjectVersionViewLicenseLicense"); //
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
