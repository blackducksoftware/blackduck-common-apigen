package com.synopsys.integration.create.apigen.definitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeTranslator {

    private Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();
    private Map<String, String> classTranslations = new HashMap<>();

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

    private class FieldTranslation {
        private String path;
        private String swaggerType;
        private String api_genType;

        public FieldTranslation(final String path, final String swaggerType, final String api_genType) {
            this.path = path;
            this.swaggerType = swaggerType;
            this.api_genType = api_genType;
        }

        public String getPath() {
            return path;
        }

        public void setPath(final String path) {
            this.path = path;
        }

        public String getSwaggerName() {
            return swaggerType;
        }

        public void setSwaggerName(final String swaggerType) {
            this.swaggerType = swaggerType;
        }

        public String getApiGenName() {
            return api_genType;
        }

        public void setApiGenName(final String api_genType) {
            this.api_genType = api_genType;
        }

    }

    private class ClassTranslation {
        private String swaggerType;
        private String api_genType;

        public ClassTranslation(final String swaggerType, final String api_genType) {
            this.swaggerType = swaggerType;
            this.api_genType = api_genType;
        }

        public String getSwaggerName() {
            return swaggerType;
        }

        public void setSwaggerName(final String swaggerType) {
            this.swaggerType = swaggerType;
        }

        public String getApiGenName() {
            return api_genType;
        }

        public void setApiGenName(final String api_genType) {
            this.api_genType = api_genType;
        }

    }

}
