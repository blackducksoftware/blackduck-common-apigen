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

    private final Map<String, List<FieldTranslation>> fieldTranslations;
    private final Map<String, String> swaggerToApigenTranslations;
    private final Map<String, String> apigenToSwaggerTranslations;
    private final Map<String, Set<String>> simplifiedClassTypes;

    public TypeTranslator() {
        this.fieldTranslations = populateFieldTranslations();
        this.swaggerToApigenTranslations = populateSwaggerToApigenTranslations();
        this.apigenToSwaggerTranslations = populateApigenToSwaggerTranslations();
        this.simplifiedClassTypes = populateSimplifiedClassTypes();
    }

    private Map<String, String> populateSwaggerToApigenTranslations() {
        final Map<String, String> translations = new HashMap<>();

        // swaggerName, api_SpecsName
        translations.put("ActivityDataView", "ComponentVersionRiskProfileActivityDataView");
        translations.put("AssignedProjectView", "UserProjectsView");
        translations.put("ComplexLicenseView", "ProjectVersionLicenseView");
        translations.put("ComplexLicenseType", "ProjectVersionLicenseTypeType"); // * its final name is ProjectVersionLicenseType
        translations.put("ComponentSearchResultView", "ComponentsView");
        translations.put("ComponentVersionPolicyViolationDetails", "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView");
        translations.put("CompositePathWithArchiveContext", "ComponentMatchedFilesItemsFilePathView");
        translations.put("CustomFieldType", "CustomFieldTypeType");
        translations.put("CustomFieldTypeView", "TypesView");
        translations.put("HealthCheckStatusView", "HealthChecksLivenessView");
        translations.put("LicenseFamilySummaryView", "ProjectVersionLicenseLicenseLicenseFamilySummaryView");
        translations.put("MatchedFileView", "ComponentMatchedFilesView");
        translations.put("MatchedFileUsagesType", "LicenseFamilyLicenseFamilyRiskRulesUsageType");
        translations.put("NotificationSubscriptionView", "NotificationSubscriptionsSubscriptionView");
        translations.put("OriginSourceType", "ComponentSourceType");
        translations.put("PolicyRuleExpressionSetOperatorType", "PolicyRuleExpressionOperatorType");
        translations.put("PolicyRuleExpressionParameter", "PolicyRuleExpressionExpressionsParametersView");
        translations.put("PolicyRuleExpressionSetView", "PolicyRuleExpressionView");
        translations.put("PolicyRuleExpressionView", "PolicyRuleExpressionExpressionsView");
        translations.put("PolicySummaryStatusType", "PolicyStatusType");
        translations.put("PolicyStatusView", "ComponentPolicyStatusView");
        translations.put("ProjectVersionDistributionType", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType");
        translations.put("ReportFormatType", "LicenseReportsReportReportFormatType");
        translations.put("RemediationOptionsView", "ComponentVersionRemediatingView");
        translations.put("RemediatingVersionView", "ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        translations.put("ReviewedDetails", "ProjectVersionComponentReviewedDetailsView");
        translations.put("RiskCountView", "ComponentVersionRiskProfileRiskDataCountsView");
        translations.put("RiskCountType", "ComponentVersionRiskProfileRiskDataCountsCountTypeType");
        translations.put("VersionBomLicenseView", "ProjectVersionComponentLicensesView");
        translations.put("VersionBomComponentDiffView", "ProjectVersionComparisonView");
        translations.put("VersionBomComponentMatchType", "ProjectVersionMatchedFilesItemsMatchesMatchTypeType");
        translations.put("VersionBomComponentReviewStatusType", "ProjectVersionComponentReviewStatusType");
        translations.put("VersionBomComponentView", "ProjectVersionComponentView");
        translations.put("VersionBomPolicyRuleView", "ComponentPolicyRulesView");
        translations.put("VersionBomPolicyStatusView", "ProjectVersionPolicyStatusView");
        translations.put("VulnerableComponentView", "ProjectVersionVulnerableBomComponentsView");

        return translations;
    }

    private Map<String, List<FieldTranslation>> populateFieldTranslations() {
        // FIXME - this Map is a patch-job– although only way, given format of specs data (as of 1/13/20)
        final Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();

        // ComponentActivityRiskProfileView
        final List<FieldTranslation> carpvTranslations = new ArrayList<>();
        final FieldTranslation carpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        carpvTranslations.add(carpvCountsTranslation);
        fieldTranslations.put("ComponentActivityRiskProfileView", carpvTranslations);

        // ComponentVersionRemediatingNoVulnerabilitiesView
        final List<FieldTranslation> cvrnvvTranslations = new ArrayList<>();
        fieldTranslations.put("ComponentVersionRemediatingNoVulnerabilitiesView", cvrnvvTranslations);

        // ComponentVersionView
        final List<FieldTranslation> cvvTranslations = new ArrayList<>();
        // Experiment
        final FieldTranslation cvvLicenseTranslation = new FieldTranslation("license", "ProjectVersionLicenseView", UtilStrings.OBJECT);
        cvvTranslations.add(cvvLicenseTranslation);
        fieldTranslations.put("ComponentVersionView", cvvTranslations);

        // ProjectVersionComponentLicensesView
        final List<FieldTranslation> pvclvTranslations = new ArrayList<>();
        final FieldTranslation pvclvLicensesTranslation = new FieldTranslation("licenses", "ProjectVersionComponentLicensesView", UtilStrings.ARRAY);
        pvclvTranslations.add(pvclvLicensesTranslation);
        fieldTranslations.put("ProjectVersionComponentLicensesView", pvclvTranslations);

        // ProjectVersionComponentView
        final List<FieldTranslation> pvcvTranslations = new ArrayList<>();
        final FieldTranslation pvcvOriginsTranslation = new FieldTranslation("origins", "VersionBomOriginView", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvOriginsTranslation);
        final FieldTranslation pvcvMatchTypesTranslation = new FieldTranslation("matchTypes", "ProjectVersionMatchedFilesItemsMatchesMatchTypeType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvMatchTypesTranslation);
        final FieldTranslation pvcvUsagesTranslation = new FieldTranslation("usages", "LicenseFamilyLicenseFamilyRiskRulesUsageType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvUsagesTranslation);
        final FieldTranslation pvcvReviewStatusTranslation = new FieldTranslation("reviewStatus", "ProjectVersionComponentReviewStatusType", UtilStrings.STRING);
        pvcvTranslations.add(pvcvReviewStatusTranslation);
        final FieldTranslation pvcvApprovalStatusTranslation = new FieldTranslation("approvalStatus", "PolicyStatusType", UtilStrings.STRING);
        pvcvTranslations.add(pvcvApprovalStatusTranslation);
        fieldTranslations.put("ProjectVersionComponentView", pvcvTranslations);

        // ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView
        final List<FieldTranslation> pvpscvpvdvTranslations = new ArrayList<>();
        final FieldTranslation pvpscvpvdvSeverityLevelsTranslation = new FieldTranslation("severityLevels", "NameValuePairView", UtilStrings.ARRAY);
        pvpscvpvdvTranslations.add(pvpscvpvdvSeverityLevelsTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView", pvpscvpvdvTranslations);

        // ProjectVersionPolicyStatusView
        final List<FieldTranslation> pvpsvTranslations = new ArrayList<>();
        final FieldTranslation pvpsvComponentVersionStatusCountsTranslation = new FieldTranslation("componentVersionStatusCounts", "NameValuePairView", UtilStrings.ARRAY);
        pvpsvTranslations.add(pvpsvComponentVersionStatusCountsTranslation);
        final FieldTranslation pvpsvOverallStatusTranslation = new FieldTranslation("overallStatus", "PolicyStatusType", UtilStrings.STRING);
        pvpsvTranslations.add(pvpsvOverallStatusTranslation);
        fieldTranslations.put("ProjectVersionPolicyStatusView", pvpsvTranslations);

        // ProjectVersionLicenseView
        List<FieldTranslation> pvlvTranslations = new ArrayList<>();
        final FieldTranslation pvlvTypeTranslation = new FieldTranslation("type", "ProjectVersionLicenseTypeType", UtilStrings.STRING);
        pvlvTranslations.add(pvlvTypeTranslation);
        fieldTranslations.put("ProjectVersionLicenseView", pvlvTranslations);

        // ProjectVersionLicenseLicensesView
        final List<FieldTranslation> pvllvTranslations = new ArrayList<>();
        final FieldTranslation pvllvLicensesTranslation = new FieldTranslation("licenses", "ProjectVersionLicenseLicensesView", UtilStrings.ARRAY);
        pvllvTranslations.add(pvllvLicensesTranslation);
        fieldTranslations.put("ProjectVersionLicenseLicensesView", pvllvTranslations);

        // ProjectVersionView
        final List<FieldTranslation> pvvTranslations = new ArrayList<>();
        final FieldTranslation pvvDistributionTranslation = new FieldTranslation("distribution", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType", UtilStrings.STRING);
        pvvTranslations.add(pvvDistributionTranslation);
        final FieldTranslation pvvPhaseTranslation = new FieldTranslation("phase", "ProjectVersionPhaseType", UtilStrings.STRING);
        pvvTranslations.add(pvvPhaseTranslation);
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        // RiskProfileView
        final List<FieldTranslation> rpvTranslations = new ArrayList<>();
        final FieldTranslation rpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        rpvTranslations.add(rpvCountsTranslation);
        fieldTranslations.put("RiskProfileView", rpvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateApigenToSwaggerTranslations() {
        Map<String, String> translations = new HashMap<>();
        for (Map.Entry<String, String> entry : populateSwaggerToApigenTranslations().entrySet()) {
            translations.put(entry.getValue(), entry.getKey());
        }
        return translations;
    }

    public String getFieldSwaggerName(final String className, final String api_SpecsPath, final String api_SpecsName) {
        final List<FieldTranslation> typeTranslations = fieldTranslations.get(className);
        if (typeTranslations != null) {
            for (final FieldTranslation typeTranslation : typeTranslations) {
                final String apiSpecsName = typeTranslation.getApiSpecsName();
                final String apiSpecsPath = typeTranslation.getPath();
                if (apiSpecsName.equals(api_SpecsName) && apiSpecsPath.equals(api_SpecsPath.replace("[]", ""))) {
                    return typeTranslation.getSwaggerName();
                }
            }
        }
        return null;
    }

    public String getApiGenClassName(final String swaggerName) {
        return swaggerToApigenTranslations.get(NameParser.getNonVersionedName(swaggerName));
    }

    public String getClassSwaggerName(final String apigenName) { return apigenToSwaggerTranslations.get(NameParser.getNonVersionedName(apigenName)); }

    private Map<String, Set<String>> populateSimplifiedClassTypes() {
        final Map<String, Set<String>> simplifiedClassNames = new HashMap<>();

        // RiskProfileView
        final Set<String> riskProfileViewIdentifiers = new HashSet<>();
        riskProfileViewIdentifiers.add("RiskProfileView");
        simplifiedClassNames.put("RiskProfileView", riskProfileViewIdentifiers);

        // PolicyStatusType
        final Set<String> policyStatusTypeIdentifiers = new HashSet<>();
        policyStatusTypeIdentifiers.add("Policy");
        policyStatusTypeIdentifiers.add("StatusType");
        simplifiedClassNames.put("PolicyStatusType", policyStatusTypeIdentifiers);

        return simplifiedClassNames;
    }

    public String getSimplifiedClassName(final String classType) {
        for (final Map.Entry<String, Set<String>> simplifiedClassTypeIdentifiers : simplifiedClassTypes.entrySet()) {
            boolean hasAllIdentifiers = true;
            for (final String simplifiedClassTypeIdentifier : simplifiedClassTypeIdentifiers.getValue()) {
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
