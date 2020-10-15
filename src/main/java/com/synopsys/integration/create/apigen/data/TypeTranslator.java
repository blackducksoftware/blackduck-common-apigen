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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class TypeTranslator {

    private Map<String, List<FieldTranslation>> fieldTranslations;
    private Map<String, String> swaggerToApigenTranslations;
    private Map<String, String> apigenToSwaggerTranslations;
    private Map<String, Set<String>> simplifiedClassTypes;

    public TypeTranslator() {
        this.fieldTranslations = populateFieldTranslations();
        this.swaggerToApigenTranslations = populateSwaggerToApigenTranslations();
        this.apigenToSwaggerTranslations = populateApigenToSwaggerTranslations();
        this.simplifiedClassTypes = populateSimplifiedClassTypes();
    }

    private Map<String, String> populateSwaggerToApigenTranslations() {
        Map<String, String> translations = new HashMap<>();

        // swaggerName, api_SpecsName
        translations.put("ActivityDataTrendingType", "ComponentVersionRiskProfileActivityDataTrendingType");
        translations.put("ActivityDataView", "ComponentVersionRiskProfileActivityDataView");
        translations.put("AssignableProjectView", "ProjectTagView");
        translations.put("AssignedProjectView", "UserProjectsView");
        translations.put("BomComponentIssueView", "ProjectVersionIssuesView");
        translations.put("CommentUserData", "CommentUserView");
        translations.put("ComplexLicenseView", "ProjectVersionLicenseView");
        translations.put("ComplexLicenseType", "ProjectVersionLicenseTypeType"); // * its name is ProjectVersionLicenseType
        translations.put("ComponentSearchResultView", "ComponentsView");
        translations.put("ComponentVersionPolicyViolationDetails", "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView");
        translations.put("ComponentVersionRiskView", "ComponentVersionRiskProfileView");
        translations.put("ComponentType", "ComponentVersionType");
        translations.put("CompositePathWithArchiveContext", "ComponentMatchedFilesItemsFilePathView");
        translations.put("CustomFieldOptionView", "CustomFieldOptionsView");
        translations.put("CustomFieldType", "CustomFieldTypeType");
        translations.put("CustomFieldTypeView", "TypesView");
        translations.put("CustomLicenseRequestCodeSharingType", "LicenseCodeSharingType");
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
        translations.put("LicenseFamilySummaryView", "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        translations.put("LicenseTermCategorySummaryView", "ProjectVersionLicenseLicensesLicenseFamilySummaryView");
        translations.put("LicenseOwnershipType", "ProjectVersionLicenseLicensesOwnershipType");
        translations.put("LicenseSourceType", "LicenseLicenseSourceType");
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
        translations.put("PolicySummaryStatusType", "PolicyStatusType");
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
        translations.put("VulnerabilityCvss2IntegrityImpactType", "VulnerabilityCvss2ConfidentialityImpactType");
        translations.put("VulnerabilityCvss3IntegrityImpactType", "VulnerabilityCvss3ConfidentialityImpactType");
        translations.put("VulnerabilityWithRemediationSeverityType", "VulnerabilityRemediationCvss3SeverityType");
        translations.put("VulnerabilitySourceType", "ProjectVersionVulnerableBomComponentsItemsVulnerabilityWithRemediationSourceType");
        translations.put("VulnerabilityWithRemediationStatusType", "VulnerabilityRemediationRemediationStatusType");
        translations.put("VulnerabilityWithRemediationView", "ProjectVersionVulnerableBomComponentsItemsVulnerabilityWithRemediationView");
        translations.put("VulnerableComponentView", "ProjectVersionVulnerableBomComponentsView");

        return translations;
    }

    private Map<String, List<FieldTranslation>> populateFieldTranslations() {
        // FIXME - this Map is a patch-job– although only way, given format of specs data (as of 1/13/20)
        Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();

        // ComponentActivityRiskProfileView
        List<FieldTranslation> carpvTranslations = new ArrayList<>();
        FieldTranslation carpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        carpvTranslations.add(carpvCountsTranslation);
        fieldTranslations.put("ComponentActivityRiskProfileView", carpvTranslations);

        // ComponentVersionRemediatingNoVulnerabilitiesView
        List<FieldTranslation> cvrnvvTranslations = new ArrayList<>();
        fieldTranslations.put("ComponentVersionRemediatingNoVulnerabilitiesView", cvrnvvTranslations);

        // ComponentVersionView
        List<FieldTranslation> cvvTranslations = new ArrayList<>();
        // As of 2020.8.0 - the specs do not reflect this, but REST responses from blackduck do
        FieldTranslation cvvLicenseTranslation = new FieldTranslation("license", "ProjectVersionLicenseView", UtilStrings.OBJECT);
        cvvTranslations.add(cvvLicenseTranslation);
        fieldTranslations.put("ComponentVersionView", cvvTranslations);

        // ComponentVersionLicenseLicensesView
        List<FieldTranslation> cvllvTranslations = new ArrayList<>();
        FieldTranslation cvllvOwnershipTranslation = new FieldTranslation("ownership", "ProjectVersionLicenseLicensesOwnershipType", UtilStrings.STRING);
        cvllvTranslations.add(cvllvOwnershipTranslation);
        fieldTranslations.put("ComponentVersionLicenseLicensesView", cvllvTranslations);
        
        // LicenseTermCategoryView
        List<FieldTranslation> ltcvTranslations = new ArrayList<>();
        FieldTranslation ltcvUpdatedByTranslation = new FieldTranslation("updatedBy", "LicenseFamilyUpdatedByView", UtilStrings.OBJECT);
        ltcvTranslations.add(ltcvUpdatedByTranslation);
        FieldTranslation ltcvCreatedByTranslation = new FieldTranslation("createdBy", "LicenseFamilyUpdatedByView", UtilStrings.OBJECT);
        ltcvTranslations.add(ltcvCreatedByTranslation);
        fieldTranslations.put("LicenseTermCategoryView", ltcvTranslations);

        // ProjectVersionComponentLicensesView
        List<FieldTranslation> pvclvTranslations = new ArrayList<>();
        FieldTranslation pvclvLicensesTranslation = new FieldTranslation("licenses", "ProjectVersionComponentLicensesView", UtilStrings.ARRAY);
        pvclvTranslations.add(pvclvLicensesTranslation);
        fieldTranslations.put("ProjectVersionComponentLicensesView", pvclvTranslations);

        // ProjectVersionComponentView
        List<FieldTranslation> pvcvTranslations = new ArrayList<>();
        FieldTranslation pvcvOriginsTranslation = new FieldTranslation("origins", "VersionBomOriginView", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvOriginsTranslation);
        FieldTranslation pvcvMatchTypesTranslation = new FieldTranslation("matchTypes", "ProjectVersionMatchedFilesItemsMatchesMatchTypeType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvMatchTypesTranslation);
        FieldTranslation pvcvUsagesTranslation = new FieldTranslation("usages", "LicenseFamilyLicenseFamilyRiskRulesUsageType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvUsagesTranslation);
        FieldTranslation pvcvReviewStatusTranslation = new FieldTranslation("reviewStatus", "ProjectVersionComponentReviewStatusType", UtilStrings.STRING);
        pvcvTranslations.add(pvcvReviewStatusTranslation);
        FieldTranslation pvcvApprovalStatusTranslation = new FieldTranslation("approvalStatus", "PolicyStatusType", UtilStrings.STRING);
        pvcvTranslations.add(pvcvApprovalStatusTranslation);
        fieldTranslations.put("ProjectVersionComponentView", pvcvTranslations);

        // ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView
        List<FieldTranslation> pvpscvpvdvTranslations = new ArrayList<>();
        FieldTranslation pvpscvpvdvSeverityLevelsTranslation = new FieldTranslation("severityLevels", "NameValuePairView", UtilStrings.ARRAY);
        pvpscvpvdvTranslations.add(pvpscvpvdvSeverityLevelsTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView", pvpscvpvdvTranslations);

        // ProjectVersionPolicyStatusView
        List<FieldTranslation> pvpsvTranslations = new ArrayList<>();
        FieldTranslation pvpsvComponentVersionStatusCountsTranslation = new FieldTranslation("componentVersionStatusCounts", "NameValuePairView", UtilStrings.ARRAY);
        pvpsvTranslations.add(pvpsvComponentVersionStatusCountsTranslation);
        FieldTranslation pvpsvOverallStatusTranslation = new FieldTranslation("overallStatus", "PolicyStatusType", UtilStrings.STRING);
        pvpsvTranslations.add(pvpsvOverallStatusTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusView", pvpsvTranslations);

        // ProjectVersionLicenseView
        List<FieldTranslation> pvlvTranslations = new ArrayList<>();
        FieldTranslation pvlvTypeTranslation = new FieldTranslation("type", "ProjectVersionLicenseTypeType", UtilStrings.STRING);
        pvlvTranslations.add(pvlvTypeTranslation);
        fieldTranslations.put("ProjectVersionLicenseView", pvlvTranslations);

        // ProjectVersionLicenseLicensesView
        List<FieldTranslation> pvllvTranslations = new ArrayList<>();
        FieldTranslation pvllvLicensesTranslation = new FieldTranslation("licenses", "ProjectVersionLicenseLicensesView", UtilStrings.ARRAY);
        pvllvTranslations.add(pvllvLicensesTranslation);
        fieldTranslations.put("ProjectVersionLicenseLicensesView", pvllvTranslations);

        // ProjectVersionView
        List<FieldTranslation> pvvTranslations = new ArrayList<>();
        FieldTranslation pvvDistributionTranslation = new FieldTranslation("distribution", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType", UtilStrings.STRING);
        pvvTranslations.add(pvvDistributionTranslation);
        FieldTranslation pvvPhaseTranslation = new FieldTranslation("phase", "ProjectVersionPhaseType", UtilStrings.STRING);
        pvvTranslations.add(pvvPhaseTranslation);
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        // RiskProfileView
        List<FieldTranslation> rpvTranslations = new ArrayList<>();
        FieldTranslation rpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        rpvTranslations.add(rpvCountsTranslation);
        fieldTranslations.put("RiskProfileView", rpvTranslations);

        // ProjectVersionVulnerableBomComponentsView
        List<FieldTranslation> pvvbcvTranslations = new ArrayList<>();
        FieldTranslation pvvbcvLicenseTranslation = new FieldTranslation("license", "ProjectVersionLicenseView", UtilStrings.OBJECT);
        pvvbcvTranslations.add(pvvbcvLicenseTranslation);
        fieldTranslations.put("ProjectVersionVulnerableBomComponentsItemsView", pvvbcvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateApigenToSwaggerTranslations() {
        Map<String, String> translations = new HashMap<>();
        for (Map.Entry<String, String> entry : populateSwaggerToApigenTranslations().entrySet()) {
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

    public String getApiGenClassName(String swaggerName) {
        return swaggerToApigenTranslations.get(NameParser.getNonVersionedName(swaggerName));
    }

    public String getClassSwaggerName(String apigenName) { return apigenToSwaggerTranslations.get(NameParser.getNonVersionedName(apigenName)); }

    private Map<String, Set<String>> populateSimplifiedClassTypes() {
        Map<String, Set<String>> simplifiedClassNames = new HashMap<>();

        // RiskProfileView
        Set<String> riskProfileViewIdentifiers = new HashSet<>();
        riskProfileViewIdentifiers.add("RiskProfileView");
        simplifiedClassNames.put("RiskProfileView", riskProfileViewIdentifiers);

        // PolicyStatusType
        Set<String> policyStatusTypeIdentifiers = new HashSet<>();
        policyStatusTypeIdentifiers.add("Policy");
        policyStatusTypeIdentifiers.add("StatusType");
        simplifiedClassNames.put("PolicyStatusType", policyStatusTypeIdentifiers);

        return simplifiedClassNames;
    }

    public String getSimplifiedClassName(String classType) {
        for (Map.Entry<String, Set<String>> simplifiedClassTypeIdentifiers : simplifiedClassTypes.entrySet()) {
            boolean hasAllIdentifiers = true;
            for (String simplifiedClassTypeIdentifier : simplifiedClassTypeIdentifiers.getValue()) {
                if (!classType.contains(simplifiedClassTypeIdentifier)) {
                    hasAllIdentifiers = false;
                }
            }
            if (hasAllIdentifiers) {
                return simplifiedClassTypeIdentifiers.getKey();
            }
        }
        return classType;
    }

}
