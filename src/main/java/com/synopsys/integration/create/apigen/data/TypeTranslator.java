/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

import static com.synopsys.integration.create.apigen.data.UtilStrings.ARRAY;
import static com.synopsys.integration.create.apigen.data.UtilStrings.OBJECT;
import static com.synopsys.integration.create.apigen.data.UtilStrings.STRING;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.FieldDefinition;

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
        translations.put("ActivityDataTrendingType", "ProjectVersionComponentActivityDataTrendingType");
        translations.put("ActivityDataView", "ComponentVersionRiskProfileActivityDataView");
        translations.put("AssignableProjectView", "ProjectTagView");
        translations.put("BomComponentIssueView", "ProjectVersionIssuesView");
        translations.put("CommentUserData", "CommentUserView");
        translations.put("ComplexLicenseView", "ComponentVersionLicenseView");
        translations.put("ComplexLicenseType", "ProjectVersionLicenseType"); // * its name is ProjectVersionLicenseType
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
        translations.put("LicenseFamilyRiskRuleView", "LicenseFamilyRiskRulesView");
        translations.put("LicenseFamilySummaryView", "ComponentVersionLicenseLicensesLicenseFamilySummaryView");
        translations.put("MatchedFileView", "ComponentMatchedFilesView");
        translations.put("MatchedFileUsagesType", "UsageType");
        translations.put("NotificationSubscriptionView", "NotificationSubscriptionsSubscriptionView");
        translations.put("OriginFileLevelCopyrightDataView", "OriginFileCopyrightsView");
        translations.put("OriginFuzzyFileLevelDataView", "OriginFileLicensesFuzzyView");
        translations.put("OriginLicenseFileLevelDataView", "FileLicensesLicenseView");
        translations.put("PolicyRuleExpressionSetOperatorType", "PolicyRuleExpressionOperatorType");
        translations.put("PolicyRuleExpressionParameter", "PolicyRuleExpressionExpressionsParametersView");
        translations.put("PolicyRuleExpressionSetView", "PolicyRuleExpressionView");
        translations.put("PolicyRuleExpressionView", "PolicyRuleExpressionExpressionsView");
        translations.put("PolicySummaryStatusType", "ProjectVersionComponentPolicyStatusType");
        translations.put("PolicyStatusView", "ComponentPolicyStatusView");
        translations.put("RegistrationAttributeType", "RegistrationAttributesAttributeType");
        translations.put("RegistrationAttributeView", "RegistrationAttributesView");
        translations.put("RegistrationFeatureView", "RegistrationFeaturesView");
        translations.put("RegistrationFeatureType", "RegistrationFeaturesFeatureType");
        translations.put("RegistrationMessageCodeType", "RegistrationMessagesMessageCodeType");
        translations.put("RegistrationMessageView", "RegistrationMessagesView");
        translations.put("RemediationOptionsView", "ComponentVersionRemediatingView");
        translations.put("RemediatingVersionView", "ComponentVersionRemediatingFixesPreviousVulnerabilitiesView");
        translations.put("ReportType", "LicenseReportsReportReportType");
        translations.put("ReportContent", "ReportContentsView");
        translations.put("ReportFileContent", "ReportContentsReportContentView");
        translations.put("ReviewedDetails", "ProjectVersionComponentReviewedDetailsView");
        translations.put("RiskCountView", "RiskProfileCountsView");
        translations.put("RiskCountType", "RiskPriorityType");
        translations.put("TextByteOffsetView", "FileLicensesLicenseItemsOffsetsView");
        translations.put("UserCommentView", "CommentView");
        translations.put("UserSummaryView", "LicenseFamilyUpdatedByView");
        translations.put("VersionBomLicenseView", "ProjectVersionComponentLicensesView");
        translations.put("VersionBomComponentDiffComponentStateType", "ProjectVersionComparisonItemsComponentStateType");
        translations.put("VersionBomComponentDiffView", "ProjectVersionComparisonView");
        translations.put("VersionBomComponentMatchType", "MatchType");
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
        // If a translation is created to override a field's type to the type of another generated class, set FieldTranslation.typeWasOverrided = true (true is the default)
        // If a translation is created to modify a field's verbose or unexpressive name, set typeWasOverrided = false
        Map<String, List<FieldTranslation>> fieldTranslations = new HashMap<>();

        // ComponentActivityComponentVersionRiskDataView
        List<FieldTranslation> carpvTranslations = Arrays.asList(
            new FieldTranslation("counts", "ComponentVersionRiskProfileRiskDataCountsView", ARRAY)
        );
        fieldTranslations.put("ComponentActivityComponentVersionRiskDataView", carpvTranslations);

        // ComponentPolicyRulesView
        List<FieldTranslation> cprvTranslations = Arrays.asList(
            new FieldTranslation("expression", "PolicyRuleExpressionView", OBJECT)
        );
        fieldTranslations.put("ComponentPolicyRulesItemsView", cprvTranslations); // At the time of parsing, the parent definition name is ComponentPolicyRulesItemsView

        // ComponentVersionRemediatingView
        List<FieldTranslation> cvrvTranslations = Arrays.asList(
            new FieldTranslation("latestAfterCurrent", "RemediatingVersionView", OBJECT, false),
            new FieldTranslation("noVulnerabilities", "RemediatingVersionView", OBJECT),
            new FieldTranslation("fixesPreviousVulnerabilities", "RemediatingVersionView", OBJECT)
        );
        fieldTranslations.put("ComponentVersionRemediatingView", cvrvTranslations);

        // ComponentVersionRiskProfileView
        List<FieldTranslation> cvrpvTranslations = Arrays.asList(
            new FieldTranslation("riskData", "RiskProfileView", OBJECT, false)
        );
        fieldTranslations.put("ComponentVersionRiskProfileView", cvrpvTranslations);

        // LicenseFamilyRiskRulesView
        List<FieldTranslation> lfrrvTranslations = Arrays.asList(
            new FieldTranslation("usage", "UsageType", STRING),
            new FieldTranslation("riskPriority", "RiskPriorityType", STRING, false)
        );
        fieldTranslations.put("LicenseFamilyRiskRulesView", lfrrvTranslations);

        // LicenseFamilyView
        List<FieldTranslation> lfvTranslations = Arrays.asList(
            new FieldTranslation("licenseFamilyRiskRules", "LicenseFamilyRiskRulesView", ARRAY, false)
        );
        fieldTranslations.put("LicenseFamilyView", lfvTranslations);

        // LicenseTermCategoryView
        List<FieldTranslation> ltcvTranslations = Arrays.asList(
            new FieldTranslation("updatedBy", "LicenseFamilyUpdatedByView", OBJECT),
            new FieldTranslation("createdBy", "LicenseFamilyUpdatedByView", OBJECT)
        );
        fieldTranslations.put("LicenseTermCategoryView", ltcvTranslations);

        // LicenseTermView
        List<FieldTranslation> ltvTranslations = Arrays.asList(
            new FieldTranslation("category", "LicenseTermCategorySummaryView", OBJECT, false)
        );
        fieldTranslations.put("LicenseTermView", ltvTranslations);

        // OriginLicenseView
        List<FieldTranslation> olvTranslations = Arrays.asList(
            new FieldTranslation("type", "LicenseType", STRING, false)
        );
        fieldTranslations.put("OriginLicenseView", olvTranslations);

        // ProjectVersionComparisonItemsComponentView
        List<FieldTranslation> pvcicvTranslations = Arrays.asList(
            new FieldTranslation("matchTypes", "ComparisonMatchType", ARRAY, false)
        );
        fieldTranslations.put("ProjectVersionComparisonItemsComponentView", pvcicvTranslations);

        // ProjectVersionComponentLicensesView
        List<FieldTranslation> pvclvTranslations = Arrays.asList(
            new FieldTranslation("licenses", "ProjectVersionComponentLicensesView", ARRAY)
        );
        fieldTranslations.put("ProjectVersionComponentLicensesView", pvclvTranslations);

        // ProjectVersionComponentVersionLicensesView
        List<FieldTranslation> pvcvlvTranslations = Arrays.asList(
            new FieldTranslation("licenses", "ProjectVersionComponentVersionLicensesView", ARRAY)
        );
        fieldTranslations.put("ProjectVersionComponentVersionLicensesView", pvcvlvTranslations);

        // ProjectVersionComponentVersionView
        List<FieldTranslation> pvcvvTranslations = Arrays.asList(
            new FieldTranslation("securityRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("versionRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("licenseRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("activityRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("operationalRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("matchTypes", "MatchType", ARRAY),
            new FieldTranslation("approvalStatus", "ProjectVersionComponentPolicyStatusType", STRING),
            new FieldTranslation("usages", "UsageType", ARRAY),
            new FieldTranslation("origins", "VersionBomOriginView", ARRAY)
        );
        fieldTranslations.put("ProjectVersionComponentVersionView", pvcvvTranslations);

        // ProjectVersionComponentView
        List<FieldTranslation> pvcvTranslations = Arrays.asList(
            new FieldTranslation("origins", "VersionBomOriginView", ARRAY),
            new FieldTranslation("usages", "UsageType", ARRAY, false),
            new FieldTranslation("approvalStatus", "ProjectVersionComponentPolicyStatusType", STRING),
            new FieldTranslation("operationalRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("securityRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("versionRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("licenseRiskProfile", "RiskProfileView", OBJECT),
            new FieldTranslation("activityRiskProfile", "RiskProfileView", OBJECT)
        );
        fieldTranslations.put("ProjectVersionComponentView", pvcvTranslations);

        // ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView
        List<FieldTranslation> pvpscvpvdvTranslations = Arrays.asList(
            new FieldTranslation("severityLevels", "NameValuePairView", ARRAY)
        );
        fieldTranslations.put("ProjectVersionPolicyStatusComponentVersionPolicyViolationDetailsView", pvpscvpvdvTranslations);

        // ProjectVersionPolicyStatusView
        List<FieldTranslation> pvpsvTranslations = Arrays.asList(
            new FieldTranslation("componentVersionStatusCounts", "NameValuePairView", ARRAY),
            new FieldTranslation("overallStatus", "ProjectVersionComponentPolicyStatusType", STRING)
        );
        fieldTranslations.put("ProjectVersionPolicyStatusView", pvpsvTranslations);

        // ProjectVersionView
        List<FieldTranslation> pvvTranslations = Arrays.asList(
            new FieldTranslation("phase", "ProjectVersionPhaseType", STRING)
        );
        fieldTranslations.put("ProjectVersionView", pvvTranslations);

        return fieldTranslations;
    }

    private Map<String, String> populateNewTypesToOldTypes() {
        Map<String, String> translations = new HashMap<>();
        for (Map.Entry<String, String> entry : populateOldTypesToNewTypes().entrySet()) {
            translations.put(entry.getValue(), entry.getKey());
        }
        return translations;
    }

    public FieldTranslation getTrueFieldType(String className, String path, String type) {
        List<FieldTranslation> typeTranslations = fieldTranslations.get(className);
        if (typeTranslations != null) {
            for (FieldTranslation typeTranslation : typeTranslations) {
                if (typeTranslation.getApiSpecsType().equals(type) && typeTranslation.getPath().equals(path.replace("[]", ""))) {
                    return typeTranslation;
                }
            }
        }
        return null;
    }

    public String getNewName(String oldName) {
        return oldTypesToNewTypes.get(oldName);
    }

    public String getNameOfDeprecatedEquivalent(String apigenName) { return newTypesToOldTypes.get(apigenName); }

}
