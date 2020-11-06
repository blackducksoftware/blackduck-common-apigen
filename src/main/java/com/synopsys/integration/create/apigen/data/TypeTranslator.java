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

import static com.synopsys.integration.create.apigen.data.UtilStrings.ARRAY;
import static com.synopsys.integration.create.apigen.data.UtilStrings.OBJECT;
import static com.synopsys.integration.create.apigen.data.UtilStrings.STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class TypeTranslator {

    private Map<String, List<FieldTranslation>> fieldTranslations;
    private Map<String, String> oldTypesToNewTypes;
    private Map<String, String> newTypesToOldTypes;

    public TypeTranslator() {
        this.fieldTranslations = populateFieldTranslations();
        this.oldTypesToNewTypes = populateOldTypesToNewTypes();
        this.newTypesToOldTypes = populateNewTypesToOldTypes();
    }

    private Map<String, String> populateOldTypesToNewTypes() {
        Map<String, String> translations = new HashMap<>();

        // swaggerName, api_SpecsName
        translations.put("ActivityDataTrendingType", "ComponentVersionRiskProfileActivityDataTrendingType");
        translations.put("ActivityDataView", "ComponentVersionRiskProfileActivityDataView");
        translations.put("AssignableProjectView", "ProjectTagView");
        translations.put("BomComponentIssueView", "ProjectVersionIssuesView");
        translations.put("CommentUserData", "CommentUserView");
        translations.put("ComplexLicenseView", "ComponentVersionLicenseView");
        translations.put("ComplexLicenseType", "ProjectVersionLicenseTypeType"); // * its name is ProjectVersionLicenseType
        translations.put("ComponentSearchResultView", "ComponentsView");
        translations.put("ComponentVersionPolicyViolationDetails", "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView");
        translations.put("ComponentVersionRiskView", "ComponentVersionComponentVersionRiskDataView");
        translations.put("ComponentType", "ComponentVersionType");
        translations.put("CompositePathWithArchiveContext", "ComponentMatchedFilesItemsFilePathView");
        translations.put("CustomFieldOptionView", "CustomFieldOptionsView");
        translations.put("CustomFieldTypeView", "TypesView");
        translations.put("CustomLicenseRequestCodeSharingType", "ComponentVersionLicenseLicensesLicenseCodeSharingType");
        translations.put("Cvss2TemporalMetricsView", "VulnerabilityCvss2TemporalMetricsView");
        translations.put("Cvss2TemporalMetricsExploitabilityType", "VulnerabilityCvss2TemporalMetricsExploitabilityType");
        translations.put("Cvss2TemporalMetricsReportConfidenceType", "VulnerabilityCvss2TemporalMetricsReportConfidenceType");
        translations.put("Cvss3TemporalMetricsView", "VulnerabilityCvss3TemporalMetricsView");
        translations.put("Cvss3TemporalMetricsExploitabilityType", "VulnerabilityCvss3TemporalMetricsExploitabilityType");
        translations.put("Cvss3TemporalMetricsReportConfidenceType", "VulnerabilityCvss3TemporalMetricsReportConfidenceType");
        translations.put("Cvss3TemporalMetricsRemediationLevelType", "VulnerabilityCvss3TemporalMetricsRemediationLevelType");
        translations.put("CweCommonConsequenceView", "CweCommonConsequencesView");
        translations.put("CweCommonConsequenceScopesType", "CweCommonConsequencesScopesType");
        translations.put("HealthCheckStatusView", "HealthChecksLivenessView");
        translations.put("JournalObjectType", "ProjectItemsObjectDataType");
        translations.put("JournalObjectView", "ProjectItemsObjectDataView");
        translations.put("JournalTriggerType", "ProjectItemsTriggerDataType");
        translations.put("JournalTriggerView", "ProjectItemsTriggerDataView");
        translations.put("LicenseFamilyRiskRuleView", "LicenseFamilyLicenseFamilyRiskRulesView");
        translations.put("LicenseFamilySummaryView", "ComponentVersionLicenseLicensesLicenseFamilySummaryView");
        translations.put("LicenseTermCategorySummaryView", "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        translations.put("LicenseOwnershipType", "ComponentVersionLicenseLicensesOwnershipType");
        translations.put("LicenseSourceType", "ComponentSourceType");
        translations.put("MatchedFileView", "ComponentMatchedFilesView");
        translations.put("MatchedFileUsagesType", "LicenseFamilyLicenseFamilyRiskRulesUsageType");
        translations.put("NotificationSubscriptionView", "NotificationSubscriptionsSubscriptionView");
        translations.put("OriginFileLevelCopyrightDataView", "OriginFileCopyrightsView");
        translations.put("OriginFuzzyFileLevelDataView", "OriginFileLicensesFuzzyView");
        translations.put("OriginLicenseFileLevelDataView", "FileLicensesLicenseView");
        translations.put("OriginSourceType", "ComponentSourceType");
        translations.put("PolicyRuleExpressionSetOperatorType", "PolicyRuleExpressionOperatorType");
        translations.put("PolicyRuleExpressionParameter", "PolicyRuleExpressionExpressionsParametersView");
        translations.put("PolicyRuleExpressionSetView", "PolicyRuleExpressionView");
        translations.put("PolicyRuleExpressionView", "PolicyRuleExpressionExpressionsView");
        translations.put("PolicySummaryStatusType", "ProjectVersionComponentPolicyStatusType");
        translations.put("PolicyStatusView", "ComponentPolicyStatusView");
        translations.put("ProjectVersionDistributionType", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType");
        translations.put("RegistrationAttributeType", "RegistrationAttributesAttributeType");
        translations.put("RegistrationAttributeView", "RegistrationAttributesView");
        translations.put("RegistrationFeatureView", "RegistrationFeaturesView");
        translations.put("RegistrationFeatureType", "RegistrationFeaturesFeatureType");
        translations.put("RegistrationMessageCodeType", "RegistrationMessagesMessageCodeType");
        translations.put("RegistrationMessageView", "RegistrationMessagesView");
        translations.put("ReportFormatType", "LicenseReportsReportReportFormatType");
        translations.put("RemediationOptionsView", "ComponentVersionRemediatingView");
        translations.put("RemediatingVersionView", "ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        translations.put("ReportType", "LicenseReportsReportReportType");
        translations.put("ReportContent", "ReportContentsView");
        translations.put("ReportFileContent", "ReportContentsReportContentView");
        translations.put("ReviewedDetails", "ProjectVersionComponentReviewedDetailsView");
        translations.put("RiskCountView", "ComponentVersionRiskProfileRiskDataCountsView");
        translations.put("RiskCountType", "ComponentVersionRiskProfileRiskDataCountsCountTypeType");
        translations.put("RiskProfileView", "ComponentVersionRiskProfileRiskDataView");
        translations.put("TextByteOffsetView", "FileLicensesLicenseItemsOffsetsView");
        translations.put("UserCommentView", "CommentView");
        translations.put("UserSummaryView", "LicenseFamilyUpdatedByView");
        translations.put("VersionBomLicenseView", "ProjectVersionComponentLicensesView");
        translations.put("VersionBomComponentDiffComponentStateType", "ProjectVersionComparisonItemsComponentVersionStateType");
        translations.put("VersionBomComponentDiffView", "ProjectVersionComparisonView");
        translations.put("VersionBomComponentMatchType", "ProjectVersionComponentMatchTypesType");
        translations.put("VersionBomComponentReviewStatusType", "ProjectVersionComponentReviewStatusType");
        translations.put("VersionBomComponentView", "ProjectVersionComponentView");
        translations.put("VersionBomPolicyRuleView", "ComponentPolicyRulesView");
        translations.put("VersionBomPolicyStatusView", "ProjectVersionPolicyStatusView");
        translations.put("VersionDataView", "ComponentVersionRiskProfileVersionDataView");
        translations.put("VulnerabilityWithRemediationSeverityType", "VulnerabilityRemediationCvss3SeverityType");
        translations.put("VulnerabilityWithRemediationStatusType", "VulnerabilityRemediationRemediationStatusType");
        translations.put("VulnerabilityWithRemediationView", "ProjectVersionVulnerableBomComponentsItemsVulnerabilityWithRemediationView");
        translations.put("VulnerableComponentView", "ProjectVersionVulnerableBomComponentsView");

        return translations;
    }

    private Map<String, List<FieldTranslation>> populateFieldTranslations() {
        // FIXME - this Map is a patch-job– although only way, given format of specs data (as of 1/13/20)
        Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();

        // ComponentActivityComponentVersionRiskDataView
        List<FieldTranslation> carpvTranslations = new ArrayList<>();
        FieldTranslation carpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", ARRAY);
        carpvTranslations.add(carpvCountsTranslation);
        fieldTranslations.put("ComponentActivityComponentVersionRiskDataView", carpvTranslations);
    
        // ComponentVersionLicenseLicensesView
        List<FieldTranslation> cvllvTranslations = new ArrayList<>();
        FieldTranslation cvllvLicenseTranslation = new FieldTranslation("license", "ComponentVersionLicenseLicensesLicenseView", STRING);
        cvllvTranslations.add(cvllvLicenseTranslation);
        FieldTranslation cvllvLicensesTranslation = new FieldTranslation("licenses", "ComponentVersionLicenseLicensesLicenseView", ARRAY);
        cvllvTranslations.add(cvllvLicensesTranslation);
        fieldTranslations.put("ComponentVersionLicenseLicensesView", cvllvTranslations);
        
        // ComponentVersionRemediatingNoVulnerabilitiesView
        List<FieldTranslation> cvrnvvTranslations = new ArrayList<>();
        fieldTranslations.put("ComponentVersionRemediatingNoVulnerabilitiesView", cvrnvvTranslations);
        
        // LicenseTermCategoryView
        List<FieldTranslation> ltcvTranslations = new ArrayList<>();
        FieldTranslation ltcvUpdatedByTranslation = new FieldTranslation("updatedBy", "LicenseFamilyUpdatedByView", OBJECT);
        ltcvTranslations.add(ltcvUpdatedByTranslation);
        FieldTranslation ltcvCreatedByTranslation = new FieldTranslation("createdBy", "LicenseFamilyUpdatedByView", OBJECT);
        ltcvTranslations.add(ltcvCreatedByTranslation);
        fieldTranslations.put("LicenseTermCategoryView", ltcvTranslations);

        // ProjectVersionComponentLicensesView
        List<FieldTranslation> pvclvTranslations = new ArrayList<>();
        FieldTranslation pvclvLicensesTranslation = new FieldTranslation("licenses", "ProjectVersionComponentLicensesView", ARRAY);
        pvclvTranslations.add(pvclvLicensesTranslation);
        fieldTranslations.put("ProjectVersionComponentLicensesView", pvclvTranslations);

        // ProjectVersionComponentVersionView
        List<FieldTranslation> pvcvvTranslations = new ArrayList<>();
        FieldTranslation pvcvvSecurityRiskProfileTranslation = new FieldTranslation("securityRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvvTranslations.add(pvcvvSecurityRiskProfileTranslation);
        FieldTranslation pvcvvVersionRiskProfileTranslation = new FieldTranslation("versionRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvvTranslations.add(pvcvvVersionRiskProfileTranslation);
        FieldTranslation pvcvvLicenseRiskProfileTranslation = new FieldTranslation("licenseRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvvTranslations.add(pvcvvLicenseRiskProfileTranslation);
        FieldTranslation pvcvvActivityRiskProfileTranslation = new FieldTranslation("activityRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvvTranslations.add(pvcvvActivityRiskProfileTranslation);
        fieldTranslations.put("ProjectVersionComponentVersionView", pvcvvTranslations);

        // ProjectVersionComponentView
        List<FieldTranslation> pvcvTranslations = new ArrayList<>();
        FieldTranslation pvcvOriginsTranslation = new FieldTranslation("origins", "VersionBomOriginView", ARRAY);
        pvcvTranslations.add(pvcvOriginsTranslation);
        FieldTranslation pvcvUsagesTranslation = new FieldTranslation("usages", "LicenseFamilyLicenseFamilyRiskRulesUsageType", ARRAY);
        pvcvTranslations.add(pvcvUsagesTranslation);
        FieldTranslation pvcvApprovalStatusTranslation = new FieldTranslation("approvalStatus", "ProjectVersionComponentPolicyStatusType", STRING);
        pvcvTranslations.add(pvcvApprovalStatusTranslation);
        FieldTranslation pvcvSecurityRiskProfileTranslation = new FieldTranslation("securityRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvTranslations.add(pvcvSecurityRiskProfileTranslation);
        FieldTranslation pvcvVersionRiskProfileTranslation = new FieldTranslation("versionRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvTranslations.add(pvcvVersionRiskProfileTranslation);
        FieldTranslation pvcvLicenseRiskProfileTranslation = new FieldTranslation("licenseRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvTranslations.add(pvcvLicenseRiskProfileTranslation);
        FieldTranslation pvcvActivityRiskProfileTranslation = new FieldTranslation("activityRiskProfile", "ComponentVersionRiskProfileRiskDataView", OBJECT);
        pvcvTranslations.add(pvcvActivityRiskProfileTranslation);
        fieldTranslations.put("ProjectVersionComponentView", pvcvTranslations);

        // ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView
        List<FieldTranslation> pvpscvpvdvTranslations = new ArrayList<>();
        FieldTranslation pvpscvpvdvSeverityLevelsTranslation = new FieldTranslation("severityLevels", "NameValuePairView", ARRAY);
        pvpscvpvdvTranslations.add(pvpscvpvdvSeverityLevelsTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView", pvpscvpvdvTranslations);

        // ProjectVersionPolicyStatusView
        List<FieldTranslation> pvpsvTranslations = new ArrayList<>();
        FieldTranslation pvpsvComponentVersionStatusCountsTranslation = new FieldTranslation("componentVersionStatusCounts", "NameValuePairView", ARRAY);
        pvpsvTranslations.add(pvpsvComponentVersionStatusCountsTranslation);
        FieldTranslation pvpsvOverallStatusTranslation = new FieldTranslation("overallStatus", "ProjectVersionComponentPolicyStatusType", STRING);
        pvpsvTranslations.add(pvpsvOverallStatusTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusView", pvpsvTranslations);

        // ProjectVersionView
        List<FieldTranslation> pvvTranslations = new ArrayList<>();
        FieldTranslation pvvPhaseTranslation = new FieldTranslation("phase", "ProjectVersionPhaseType", STRING);
        pvvTranslations.add(pvvPhaseTranslation);
        FieldTranslation pvvLicenseTranslation = new FieldTranslation("license", "ComponentVersionLicenseView", OBJECT);
        pvvTranslations.add(pvvLicenseTranslation);
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        // ProjectVersionVulnerableBomComponentsView
        List<FieldTranslation> pvvbcvTranslations = new ArrayList<>();
        FieldTranslation pvvbcvLicenseTranslation = new FieldTranslation("license", "ComponentVersionLicenseView", OBJECT);
        pvvbcvTranslations.add(pvvbcvLicenseTranslation);
        fieldTranslations.put("ProjectVersionVulnerableBomComponentsItemsView", pvvbcvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateNewTypesToOldTypes() {
        Map<String, String> translations = new HashMap<>();
        for (Map.Entry<String, String> entry : populateOldTypesToNewTypes().entrySet()) {
            translations.put(entry.getValue(), entry.getKey());
        }
        return translations;
    }

    public String getTrueFieldName(String className, String api_SpecsPath, String api_SpecsName) {
        List<FieldTranslation> typeTranslations = fieldTranslations.get(className);
        if (typeTranslations != null) {
            for (FieldTranslation typeTranslation : typeTranslations) {
                String apiSpecsName = typeTranslation.getApiSpecsName();
                String apiSpecsPath = typeTranslation.getPath();
                if (apiSpecsName.equals(api_SpecsName) && apiSpecsPath.equals(api_SpecsPath.replace("[]", ""))) {
                    return typeTranslation.getSwaggerName();
                }
            }
        }
        return null;
    }

    public String getNewName(String swaggerName) {
        return oldTypesToNewTypes.get(swaggerName);
    }

    public String getNameOfDeprecatedEquivalent(String apigenName) { return newTypesToOldTypes.get(apigenName); }

}
