/**
 * blackduck-common-apigen
 * <p>
 * Copyright (c) 2020 Synopsys, Inc.
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.data;

import com.synopsys.integration.create.apigen.generation.finder.ClassNameManager;
import com.synopsys.integration.create.apigen.parser.NameParser;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.synopsys.integration.create.apigen.data.ClassSourceEnum.*;
import static com.synopsys.integration.create.apigen.data.ClassTypeEnum.*;

@Component
public class ClassCategories {
    private ClassNameManager classNameManager;
    private final Set<DeprecatedClassData> deprecatedClasses;

    private final Map<String, ClassCategoryData> classNameData = new HashMap<>();

    @Autowired
    public ClassCategories(ClassNameManager classNameManager) {
        this.classNameManager = classNameManager;
        this.deprecatedClasses = new HashSet<>();

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

        populate(VIEW, GENERATED, "CodeLocationView");
        populate(VIEW, GENERATED, "CommentView");
        populate(VIEW, GENERATED, "ComponentVersionPolicyStatusView");
        populate(VIEW, GENERATED, "ComponentView");
        populate(VIEW, GENERATED, "CurrentUserView");
        populate(VIEW, GENERATED, "CustomFieldObjectView");
        populate(VIEW, GENERATED, "CustomFieldView");
        populate(VIEW, GENERATED, "JobView");
        populate(VIEW, GENERATED, "LicenseFamilyView");
        populate(VIEW, GENERATED, "LicenseReportsReportView");
        populate(VIEW, GENERATED, "LicenseTermView");
        populate(VIEW, GENERATED, "LicenseTextView");
        populate(VIEW, GENERATED, "LicenseView");
        populate(VIEW, GENERATED, "PolicyRuleView");
        populate(VIEW, GENERATED, "ProjectCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentVersionCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionComponentView");
        populate(VIEW, GENERATED, "ProjectVersionCustomFieldView");
        populate(VIEW, GENERATED, "ProjectVersionLicenseLicensesView");
        populate(VIEW, GENERATED, "ProjectVersionLicenseView");
        populate(VIEW, GENERATED, "ProjectVersionPolicyStatusView");
        populate(VIEW, GENERATED, "ProjectVersionView");
        populate(VIEW, GENERATED, "ProjectView");
        populate(VIEW, GENERATED, "RegistrationView");
        populate(VIEW, GENERATED, "ReportContentsView");
        populate(VIEW, GENERATED, "RiskProfileView");
        populate(VIEW, GENERATED, "RoleAssignmentView");
        populate(VIEW, GENERATED, "RoleView");
        populate(VIEW, GENERATED, "ScanView");
        populate(VIEW, GENERATED, "TagView");
        populate(VIEW, GENERATED, "UserGroupView");
        populate(VIEW, GENERATED, "UserView");
        populate(VIEW, GENERATED, "VulnerabilityReportsReportView");
        populate(VIEW, GENERATED, "VulnerabilityView");

        populate(VIEW, MANUAL, "NotificationView");
        populate(VIEW, MANUAL, "NotificationUserView");
        populate(VIEW, MANUAL, "ProjectMappingView");

        populate(VIEW, THROWAWAY, "AssignedUserView");
        populate(VIEW, THROWAWAY, "ComponentDetailsView");
        populate(VIEW, THROWAWAY, "ComponentVersionView");
        populate(VIEW, THROWAWAY, "ExternalExtensionConfigValueView");
        populate(VIEW, THROWAWAY, "ExternalExtensionUserView");
        populate(VIEW, THROWAWAY, "IssueView");
        populate(VIEW, THROWAWAY, "LicenseTermCategoryView");
        populate(VIEW, THROWAWAY, "OriginView");
        populate(VIEW, THROWAWAY, "VersionBomComponentView");
        populate(VIEW, THROWAWAY, "VulnerabilityWithRemediationView");
        populate(VIEW, THROWAWAY, "VulnerableComponentView");

        populate(VIEW, ClassSourceEnum.NULL, "ComplexLicenseView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentMatchedFilesView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentOriginMatchedFilesView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentPolicyRulesView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentPolicyStatusView");
        populate(VIEW, ClassSourceEnum.NULL, "ComponentVersionMatchedFilesView");
        populate(VIEW, ClassSourceEnum.NULL, "CustomFieldsCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "ExternalExtensionView");
        populate(VIEW, ClassSourceEnum.NULL, "FieldsCustomFieldView");
        populate(VIEW, ClassSourceEnum.NULL, "FilterView");
        populate(VIEW, ClassSourceEnum.NULL, "LicenseTermsLicenseTermView");
        populate(VIEW, ClassSourceEnum.NULL, "LicensesLicenseView");
        populate(VIEW, ClassSourceEnum.NULL, "MatchedFileView");
        populate(VIEW, ClassSourceEnum.NULL, "PolicyStatusView");
        populate(VIEW, ClassSourceEnum.NULL, "ProjectDashboardRiskAmalgamation");
        populate(VIEW, ClassSourceEnum.NULL, "ProjectVersionReportView");
        populate(VIEW, ClassSourceEnum.NULL, "ProjectVersionVulnerableBomComponentsView");
        populate(VIEW, ClassSourceEnum.NULL, "RegistrationAttributesInternalView");
        populate(VIEW, ClassSourceEnum.NULL, "RegistrationSummaryInternalView");
        populate(VIEW, ClassSourceEnum.NULL, "ReportView");
        populate(VIEW, ClassSourceEnum.NULL, "VersionBomPolicyRuleView");
        populate(VIEW, ClassSourceEnum.NULL, "VersionBomPolicyStatusView");
        populate(VIEW, ClassSourceEnum.NULL, "VulnerabilityAffectedProjectsView");

        populate(RESPONSE, GENERATED, "ComponentSearchResultView");
        populate(RESPONSE, GENERATED, "ComponentsView");
        populate(RESPONSE, GENERATED, "CurrentVersionView");
        populate(RESPONSE, GENERATED, "CustomFieldTypeView");
        populate(RESPONSE, GENERATED, "CweView");
        populate(RESPONSE, GENERATED, "ProjectJournalView");
        populate(RESPONSE, GENERATED, "RemediationOptionsView");
        populate(RESPONSE, GENERATED, "TypesView");
        populate(RESPONSE, GENERATED, "UserProjectsView");

        populate(RESPONSE, THROWAWAY, "AssignedUserGroupView");
        populate(RESPONSE, THROWAWAY, "ComponentVersionReferenceView");
        populate(RESPONSE, THROWAWAY, "ComponentVersionRiskView");
        populate(RESPONSE, THROWAWAY, "DashboardSummaryView");
        populate(RESPONSE, THROWAWAY, "EndUserLicenseAgreementView");
        populate(RESPONSE, THROWAWAY, "HealthCheckStatusView");
        populate(RESPONSE, THROWAWAY, "JobStatisticsView");
        populate(RESPONSE, THROWAWAY, "UserCommentView");
        populate(RESPONSE, THROWAWAY, "VersionRiskProfileView");

        populate(RESPONSE, ClassSourceEnum.NULL, "ApiTokenView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignableUserGroupView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignableUserView");
        populate(RESPONSE, ClassSourceEnum.NULL, "AssignedProjectView");
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
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsParametersView");
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionExpressionsView");
        populate(COMPONENT, GENERATED, "PolicyRuleExpressionView");
        populate(COMPONENT, GENERATED, "ProjectVersionComponentReviewedDetailsReviewingUserView");
        populate(COMPONENT, GENERATED, "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        populate(COMPONENT, GENERATED, "VulnerabilityCvss2TemporalMetricsView");
        populate(COMPONENT, GENERATED, "VulnerabilityCvss3TemporalMetricsView");

        populate(COMPONENT, THROWAWAY, "AssignedUserGroup");
        populate(COMPONENT, THROWAWAY, "AuditEventCount");
        populate(COMPONENT, THROWAWAY, "BomComponentReleaseUsageRiskProfile");
        populate(COMPONENT, THROWAWAY, "ChannelRelease");
        populate(COMPONENT, THROWAWAY, "CodeLocationProgress");
        populate(COMPONENT, THROWAWAY, "CommentUserData");
        populate(COMPONENT, THROWAWAY, "ComplexLicenseRequest");
        populate(COMPONENT, THROWAWAY, "ComponentHit");
        populate(COMPONENT, THROWAWAY, "ComponentVersionPolicyViolationDetails");
        populate(COMPONENT, THROWAWAY, "CompositePathWithArchiveContext");
        populate(COMPONENT, THROWAWAY, "ConfigOptionView");
        populate(COMPONENT, THROWAWAY, "CustomComponentVersionRequest");
        populate(COMPONENT, THROWAWAY, "CustomFieldOptionRequest");
        populate(COMPONENT, THROWAWAY, "DeclaredComponentPath");
        populate(COMPONENT, THROWAWAY, "Duration");
        populate(COMPONENT, THROWAWAY, "EntityKey");
        populate(COMPONENT, THROWAWAY, "Facet");
        populate(COMPONENT, THROWAWAY, "FacetType");
        populate(COMPONENT, THROWAWAY, "FacetValue");
        populate(COMPONENT, THROWAWAY, "FileBomComponent");
        populate(COMPONENT, THROWAWAY, "FileSnippetBomComponent");
        populate(COMPONENT, THROWAWAY, "JournalObjectView");
        populate(COMPONENT, THROWAWAY, "JournalTriggerView");
        populate(COMPONENT, THROWAWAY, "LegacyAppliedFilterView");
        populate(COMPONENT, THROWAWAY, "LegacyFilterValueView");
        populate(COMPONENT, THROWAWAY, "LicenseDefinition");
        populate(COMPONENT, THROWAWAY, "LicenseFamilyRiskRuleView");
        populate(COMPONENT, THROWAWAY, "LicenseFamilySummaryView");
        populate(COMPONENT, THROWAWAY, "LicenseTermCategorySummaryView");
        populate(COMPONENT, THROWAWAY, "NameValuePairView");
        populate(COMPONENT, THROWAWAY, "PolicyStatusSummary");
        populate(COMPONENT, THROWAWAY, "ProjectData");
        populate(COMPONENT, THROWAWAY, "ProjectRiskProfile");
        populate(COMPONENT, THROWAWAY, "ProjectVersionRequest");
        populate(COMPONENT, THROWAWAY, "ReleaseData");
        populate(COMPONENT, THROWAWAY, "RemediatingVersionView");
        populate(COMPONENT, THROWAWAY, "ReportFileContent");
        populate(COMPONENT, THROWAWAY, "ResourceLink");
        populate(COMPONENT, THROWAWAY, "ResourceMetadata");
        populate(COMPONENT, THROWAWAY, "ReviewedDetails");
        populate(COMPONENT, THROWAWAY, "RiskCountView");
        populate(COMPONENT, THROWAWAY, "RiskProfile");
        populate(COMPONENT, THROWAWAY, "SearchResultSpec");
        populate(COMPONENT, THROWAWAY, "SearchResultStatistics");
        populate(COMPONENT, THROWAWAY, "SignaturePair");
        populate(COMPONENT, THROWAWAY, "TemporalUnit");
        populate(COMPONENT, THROWAWAY, "TextByteOffsetView");
        populate(COMPONENT, THROWAWAY, "UserData");
        populate(COMPONENT, THROWAWAY, "UserDataView");
        populate(COMPONENT, THROWAWAY, "UserSummary");
        populate(COMPONENT, THROWAWAY, "Version");
        populate(COMPONENT, THROWAWAY, "VersionBomComponentDiffView");
        populate(COMPONENT, THROWAWAY, "VersionBomComponentOriginUpdateRequest");
        populate(COMPONENT, THROWAWAY, "VersionBomLicenseView");
        populate(COMPONENT, THROWAWAY, "VersionBomOriginView");
        populate(COMPONENT, THROWAWAY, "VersionDataView");

        populate(COMPONENT, ClassSourceEnum.NULL, "ActivityDataView");
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
        populate(COMPONENT, ClassSourceEnum.NULL, "NotificationSubscriptionsSubscriptionView");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleExpressionSetView");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpression");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionParameters");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionExpressionsParameters");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRuleViewExpressionParameter");
        populate(COMPONENT, ClassSourceEnum.NULL, "PolicyRulesPolicyruleViewExpression");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComparisonView");
        populate(COMPONENT, ClassSourceEnum.NULL, "ProjectVersionComponentLicensesView");
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
        populate(COMPONENT, ClassSourceEnum.NULL, "RegistrationFeaturesView");
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

        populate(ClassTypeEnum.ENUM, THROWAWAY, "ProjectVersionDistributionType");
        populate(ClassTypeEnum.ENUM, THROWAWAY, "ProjectVersionPhaseType");
        populate(ClassTypeEnum.ENUM, THROWAWAY, "VersionBomComponentMatchType");
        populate(ClassTypeEnum.ENUM, THROWAWAY, "VersionBomComponentReviewStatusType");

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

        populate(ClassTypeEnum.NULL, THROWAWAY, "CweCommonConsequenceView");
        populate(ClassTypeEnum.NULL, THROWAWAY, "LicenseTermAssociationView");
        populate(ClassTypeEnum.NULL, THROWAWAY, "RegistrationAttributeView");
        populate(ClassTypeEnum.NULL, THROWAWAY, "RegistrationFeatureView");
        populate(ClassTypeEnum.NULL, THROWAWAY, "RegistrationMessageView");
    }

    private void populate(ClassTypeEnum type, ClassSourceEnum source, String className) {
        classNameData.put(className, new ClassCategoryData(className, type, source));
    }

    public Set<DeprecatedClassData> getDeprecatedClasses() {
        return deprecatedClasses;
    }

    public void addDeprecatedClass(String swaggerName, String apigenName, Template template, Map<String, Object> input, String destination) {
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

    public ClassCategoryData computeData(String className) {
        className = NameParser.getNonVersionedName(className);

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
