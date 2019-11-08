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
package com.synopsys.integration.create.apigen.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

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

    private Map<String, List<FieldTranslation>> populateFieldTranslations() {
        final Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();

        // ComponentActivityRiskProfileView
        final List<FieldTranslation> carpvTranslations = new ArrayList<>();
        final FieldTranslation carpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        carpvTranslations.add(carpvCountsTranslation);
        fieldTranslations.put("ComponentActivityRiskProfileView", carpvTranslations);

        // ComponentVersionView
        final List<FieldTranslation> cvvTranslations = new ArrayList<>();
        final FieldTranslation cvvLicenseTranslation = new FieldTranslation("license", "ProjectVersionLicenseLicensesView", UtilStrings.OBJECT);
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
        final FieldTranslation pvcvMatchTypesTranslation = new FieldTranslation("matchTypes", "VersionBomComponentMatchType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvMatchTypesTranslation);
        final FieldTranslation pvcvUsagesTranslation = new FieldTranslation("usages", "LicenseFamilyLicenseFamilyRiskRulesUsageType", UtilStrings.ARRAY);
        pvcvTranslations.add(pvcvUsagesTranslation);
        final FieldTranslation pvcvReleasedOnTranslation = new FieldTranslation("releasedOn", UtilStrings.JAVA_DATE, UtilStrings.STRING);
        pvcvTranslations.add(pvcvReleasedOnTranslation);
        final FieldTranslation pvcvReviewStatusTranslation = new FieldTranslation("reviewStatus", "VersionBomComponentReviewStatusType", UtilStrings.STRING);
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
        final FieldTranslation pvvCreatedAtTranslation = new FieldTranslation("createdAt", UtilStrings.JAVA_DATE, UtilStrings.STRING);
        pvvTranslations.add(pvvCreatedAtTranslation);
        final FieldTranslation pvvReleasedOnTranslation = new FieldTranslation("releasedOn", UtilStrings.JAVA_DATE, UtilStrings.STRING);
        pvvTranslations.add(pvvReleasedOnTranslation);
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        // ReportView
        final List<FieldTranslation> rvTranslations = new ArrayList<>();
        final FieldTranslation rvFinishedAtTranslation = new FieldTranslation("finishedAt", UtilStrings.JAVA_DATE, UtilStrings.STRING);
        rvTranslations.add(rvFinishedAtTranslation);
        final FieldTranslation rvCreatedAtTranslation = new FieldTranslation("createdAt", UtilStrings.JAVA_DATE, UtilStrings.STRING);
        rvTranslations.add(rvCreatedAtTranslation);
        fieldTranslations.put("ReportView", rvTranslations);

        // RiskProfileView
        final List<FieldTranslation> rpvTranslations = new ArrayList<>();
        final FieldTranslation rpvCountsTranslation = new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", UtilStrings.ARRAY);
        rpvTranslations.add(rpvCountsTranslation);
        fieldTranslations.put("RiskProfileView", rpvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateSwaggerToApigenTranslations() {
        final Map<String, String> translations = new HashMap<>();

        // swaggerName, api_SpecsName
        translations.put("ActivityDataView", "ComponentActivityDataView");
        translations.put("ComplexLicenseView", "ProjectVersionLicenseLicensesView");
        translations.put("ComplexLicenseType", "ProjectVersionComponentLicensesLicenseTypeType");
        translations.put("ComponentVersionPolicyViolationDetails", "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView");
        translations.put("CustomFieldType", "CustomFieldTypeType");
        translations.put("CustomFieldType", "ComponentCustomFieldTypeType");
        translations.put("CustomFieldType", "ProjectCustomFieldTypeType");
        translations.put("CustomFieldType", "ProjectVersionCustomFieldTypeType");
        translations.put("CustomFieldType", "ProjectVersionComponentCustomFieldTypeType");
        translations.put("CustomFieldView", "ProjectVersionCustomFieldView");
        translations.put("CustomFieldView", "ProjectVersionComponentVersionCustomFieldView");
        translations.put("LicenseFamilySummaryView", "ProjectVersionLicenseLicenseLicenseFamilySummaryView");
        translations.put("MatchedFileUsagesType", "LicenseFamilyLicenseFamilyRiskRulesUsageType");
        translations.put("OriginSourceType", "ComponentSourceType");
        translations.put("PolicyRuleExpressionSetView", "PolicyRuleExpressionView");
        translations.put("PolicyRuleExpressionParameter", "PolicyRuleExpressionExpressionsParametersView");
        translations.put("PolicyRuleExpressionView", "PolicyRuleExpressionExpressionsView");
        translations.put("PolicySummaryStatusType", "PolicyStatusType");
        translations.put("PolicyStatusView", "ComponentPolicyStatusView");
        translations.put("ProjectVersionDistributionType", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType");
        translations.put("ReportFormatType", "ProjectVersionReportReportFormatType");
        translations.put("ReportFormatType", "ReportReportFormatType");
        translations.put("RemediationOptionsView", "ComponentVersionRemediatingView");
        translations.put("ReviewedDetails", "ComponentReviewedDetailsView");
        translations.put("RiskCountView", "ComponentVersionRiskProfileRiskDataCountsView");
        translations.put("RiskCountType", "ComponentVersionRiskProfileRiskDataCountsCountTypeType");
        translations.put("RoleAssignmentView", "UserRoleAssignmentView");
        translations.put("VersionBomLicenseView", "ComponentLicensesView");
        translations.put("VersionBomComponentView", "ProjectVersionComponentView");
        translations.put("VersionBomPolicyStatusView", "ProjectVersionPolicyStatusView");

        return translations;
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
        return swaggerToApigenTranslations.get(swaggerName);
    }

    public String getClassSwaggerName(final String apigenName) { return apigenToSwaggerTranslations.get(apigenName); }

    private Map<String, Set<String>> populateSimplifiedClassTypes() {
        final Map<String, Set<String>> simplifiedClassNames = new HashMap<>();

        // RiskProfileView
        final Set<String> riskProfileViewIdentifiers = new HashSet<>();
        riskProfileViewIdentifiers.add("RiskProfileView");
        simplifiedClassNames.put("RiskProfileView", riskProfileViewIdentifiers);

        // PolicyStatusType
        final Set<String> policyStatusTypeIdentifiers = new HashSet<>();
        policyStatusTypeIdentifiers.add("PolicyStatus");
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
