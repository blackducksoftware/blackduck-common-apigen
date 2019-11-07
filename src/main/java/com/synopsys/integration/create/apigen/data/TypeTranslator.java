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
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class TypeTranslator {

    private final Map<String, List<FieldTranslation>> fieldTranslations;
    private final Map<String, String> classTranslations;

    public TypeTranslator() {
        this.fieldTranslations = populateFieldTranslations();
        this.classTranslations = populateClassTranslations();
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

    private Map<String, String> populateClassTranslations() {
        final Map<String, String> classTranslations = new HashMap<>();

        // swaggerName, api_SpecsName
        classTranslations.put("ActivityDataView", "ComponentActivityDataView");
        classTranslations.put("ComplexLicenseView", "ProjectVersionLicenseLicensesView");
        classTranslations.put("ComplexLicenseType", "ProjectVersionComponentLicensesLicenseTypeType");
        classTranslations.put("ComponentVersionPolicyViolationDetails", "ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView");
        classTranslations.put("CustomFieldType", "CustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ComponentCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectVersionCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectVersionComponentCustomFieldTypeType");
        classTranslations.put("CustomFieldView", "ProjectVersionCustomFieldView");
        classTranslations.put("CustomFieldView", "ProjectVersionComponentVersionCustomFieldView");
        classTranslations.put("LicenseFamilySummaryView", "ProjectVersionLicenseLicenseLicenseFamilySummaryView");
        classTranslations.put("MatchedFileUsagesType", "LicenseFamilyLicenseFamilyRiskRulesUsageType");
        classTranslations.put("OriginSourceType", "ComponentSourceType");
        classTranslations.put("PolicyRuleExpressionSetView", "PolicyRuleExpressionView");
        classTranslations.put("PolicyRuleExpressionParameter", "PolicyRuleExpressionExpressionsParametersView");
        classTranslations.put("PolicyRuleExpressionView", "PolicyRuleExpressionExpressionsView");
        classTranslations.put("PolicySummaryStatusType", "PolicyStatusType");
        classTranslations.put("PolicyStatusView", "ComponentPolicyStatusView");
        classTranslations.put("ProjectVersionDistributionType", "LicenseFamilyLicenseFamilyRiskRulesReleaseDistributionType");
        classTranslations.put("ReportFormatType", "ProjectVersionReportReportFormatType");
        classTranslations.put("ReportFormatType", "ReportReportFormatType");
        classTranslations.put("RemediationOptionsView", "ComponentVersionRemediatingView");
        classTranslations.put("ReviewedDetails", "ComponentReviewedDetailsView");
        classTranslations.put("RiskCountView", "ComponentVersionRiskProfileRiskDataCountsView");
        classTranslations.put("RiskCountType", "ComponentVersionRiskProfileRiskDataCountsCountTypeType");
        classTranslations.put("RoleAssignmentView", "UserRoleAssignmentView");
        classTranslations.put("VersionBomLicenseView", "ComponentLicensesView");
        classTranslations.put("VersionBomComponentView", "ProjectVersionComponentView");
        classTranslations.put("VersionBomPolicyStatusView", "ProjectVersionPolicyStatusView");

        return classTranslations;
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
        return classTranslations.get(swaggerName);
    }

}
