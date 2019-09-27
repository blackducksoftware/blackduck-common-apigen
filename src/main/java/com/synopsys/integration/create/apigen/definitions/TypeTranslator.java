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
package com.synopsys.integration.create.apigen.definitions;

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

        // ProjectVersionView
        final List<FieldTranslation> pvvTranslations = new ArrayList<>();
        final FieldTranslation pvvDistributionTranslation = new FieldTranslation("distribution", "ProjectVersionDistributionType", "String");
        pvvTranslations.add(pvvDistributionTranslation);
        final FieldTranslation pvvPhaseTranslation = new FieldTranslation("phase", "ProjectVersionPhaseType", "String");
        pvvTranslations.add(pvvPhaseTranslation);
        final FieldTranslation pvvCreatedAtTranslation = new FieldTranslation("createdAt", "java.util.Date", "String");
        pvvTranslations.add(pvvCreatedAtTranslation);
        final FieldTranslation pvvReleasedOnTranslation = new FieldTranslation("releasedOn", "java.util.Date", "String");
        pvvTranslations.add(pvvReleasedOnTranslation);
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateClassTranslations() {
        final Map<String, String> classTranslations = new HashMap<>();

        // swaggerName, api_genName
        classTranslations.put("ActivityDataView", "ProjectVersionComponentViewActivityData");
        classTranslations.put("ComplexLicenseView", "ProjectVersionViewLicenseLicense");
        classTranslations.put("CustomFieldType", "CustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ComponentCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectVersionCustomFieldTypeType");
        classTranslations.put("CustomFieldType", "ProjectVersionComponentCustomFieldTypeType");
        classTranslations.put("CustomFieldView", "ProjectVersionCustomFieldView");
        classTranslations.put("CustomFieldView", "ProjectVersionComponentVersionCustomFieldView");
        classTranslations.put("LicenseFamilySummaryView", "ProjectVersionViewLicenseLicenseLicenseFamilySummary");
        classTranslations.put("OriginSourceType", "ComponentSourceType");
        classTranslations.put("PolicyRuleExpressionSetView", "PolicyRuleViewExpression");
        classTranslations.put("PolicyRuleExpressionParameter", "PolicyRuleViewExpressionExpressionParameters");
        classTranslations.put("PolicyRuleExpressionView", "PolicyRuleViewExpressionExpression");
        classTranslations.put("ReportFormatType", "ProjectVersionReportReportFormatType");
        classTranslations.put("ReportFormatType", "ReportReportFormatType");
        classTranslations.put("RiskProfileView", "ProjectVersionComponentViewActivityRiskProfile");
        classTranslations.put("RiskProfileView", "ProjectVersionComponentViewLicenseRiskProfile");
        classTranslations.put("RiskProfileView", "ProjectVersionComponentViewOperationalRiskProfile");
        classTranslations.put("ReviewedDetails", "ProjectVersionComponentViewReviewedDetails");
        classTranslations.put("RoleAssignmentView", "UserRoleAssignmentView");
        classTranslations.put("VersionBomLicenseView", "ProjectVersionComponentViewLicenses");
        classTranslations.put("VersionBomComponentView", "ProjectVersionComponentView");

        return classTranslations;
    }

    public String getFieldSwaggerName(final String className, final String api_genPath, final String api_genName) {
        final List<FieldTranslation> typeTranslations = fieldTranslations.get(className);
        if (typeTranslations != null) {
            for (final FieldTranslation typeTranslation : typeTranslations) {
                final String apiGenName = typeTranslation.getApiGenName();
                final String apiGenPath = typeTranslation.getPath();
                if (apiGenName.equals(api_genName) && apiGenPath.equals(api_genPath)) {
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
