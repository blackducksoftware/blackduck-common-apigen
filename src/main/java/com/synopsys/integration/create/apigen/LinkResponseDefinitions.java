package com.synopsys.integration.create.apigen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinkResponseDefinitions {

    private Map<String, Map<String, LinkResponseDefinitionItem>> definitions = new HashMap<>();
    private final Set<String> resultClasses;

    public LinkResponseDefinitions() {
        this.resultClasses = new HashSet<>();
        this.definitions = populateDefinitions();
    }

    private Map<String, Map<String, LinkResponseDefinitionItem>> populateDefinitions() {
        final Map<String, Map<String, LinkResponseDefinitionItem>> definitions = new HashMap<>();

        // CodeLocationView
        final Map<String, LinkResponseDefinitionItem> codeLocationViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem clvScanDefinition = new LinkResponseDefinitionItem(false, "String");
        codeLocationViewDefinitions.put("scans", clvScanDefinition);
        definitions.put("CodeLocation4View", codeLocationViewDefinitions);

        // ComponentVersionView
        final Map<String, LinkResponseDefinitionItem> componentVersionViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem cvvReferencesDefinition = new LinkResponseDefinitionItem(true, "VersionReferenceView");
        componentVersionViewDefinitions.put("references", cvvReferencesDefinition);
        final LinkResponseDefinitionItem cvvComponentDefinition = new LinkResponseDefinitionItem(false, "ComponentView");
        componentVersionViewDefinitions.put("component", cvvComponentDefinition);
        final LinkResponseDefinitionItem cvvOriginsDefinition = new LinkResponseDefinitionItem(true, "OriginView");
        componentVersionViewDefinitions.put("origins", cvvOriginsDefinition);
        final LinkResponseDefinitionItem cvvVulnerabilitiesDefinition = new LinkResponseDefinitionItem(true, "VulnerabilityView");
        componentVersionViewDefinitions.put("vulnerabilities", cvvVulnerabilitiesDefinition);
        final LinkResponseDefinitionItem cvvRiskProfileDefinition = new LinkResponseDefinitionItem(false, "VersionRiskView");
        componentVersionViewDefinitions.put("risk-profile", cvvRiskProfileDefinition);
        definitions.put("ComponentVersionView", componentVersionViewDefinitions);

        // ComponentView
        final Map<String, LinkResponseDefinitionItem> componentViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem cvVersionsDefinition = new LinkResponseDefinitionItem(true, "ComponentVersionView");
        componentViewDefinitions.put("versions", cvVersionsDefinition);
        final LinkResponseDefinitionItem cvHomepageDefinition = new LinkResponseDefinitionItem(true, "HomepageView");
        componentViewDefinitions.put("homepage", cvHomepageDefinition);
        final LinkResponseDefinitionItem cvOpenHubDefinition = new LinkResponseDefinitionItem(true, "OpenHubView");
        componentViewDefinitions.put("openhub", cvOpenHubDefinition);
        final LinkResponseDefinitionItem cvVulnerabilitiesDefinition = new LinkResponseDefinitionItem(true, "VulnerabilityView");
        componentViewDefinitions.put("vulnerabilities", cvVulnerabilitiesDefinition);
        final LinkResponseDefinitionItem cvSmallLogoDefinition = new LinkResponseDefinitionItem(true, "LogoView");
        componentViewDefinitions.put("smallLogo", cvSmallLogoDefinition);
        final LinkResponseDefinitionItem cvMediumLogoDefinition = new LinkResponseDefinitionItem(true, "LogoView");
        componentViewDefinitions.put("mediumLogo", cvMediumLogoDefinition);
        final LinkResponseDefinitionItem cvTagsDefinition = new LinkResponseDefinitionItem(true, "TagView");
        componentViewDefinitions.put("tags", cvTagsDefinition);
        final LinkResponseDefinitionItem cvCustomFieldsDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        componentViewDefinitions.put("custom-fields", cvCustomFieldsDefinition);
        definitions.put("ComponentView", componentViewDefinitions);

        // ComponentCustomFieldView
        final Map<String, LinkResponseDefinitionItem> componentCustomFieldViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem ccfvCustomFieldOptionListDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        componentCustomFieldViewDefinitions.put("custom-field-option-list", ccfvCustomFieldOptionListDefinition);
        definitions.put("ComponentCustomFieldView", componentCustomFieldViewDefinitions);

        // CustomFieldView
        final Map<String, LinkResponseDefinitionItem> customFieldViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem cfvCustomFieldOptionListDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        customFieldViewDefinitions.put("custom-field-option-list", cfvCustomFieldOptionListDefinition);
        definitions.put("CustomField4View", customFieldViewDefinitions);

        // ProjectView
        final Map<String, LinkResponseDefinitionItem> projectViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvVersionsDefinition = new LinkResponseDefinitionItem(true, "ProjectVersionView");
        projectViewDefinitions.put("versions", pvVersionsDefinition);
        final LinkResponseDefinitionItem pvCanonicalVersionDefinition = new LinkResponseDefinitionItem(false, "ProjectVersionView");
        projectViewDefinitions.put("canonicalVersion", pvCanonicalVersionDefinition);
        final LinkResponseDefinitionItem pvProjectMappingsDefinition = new LinkResponseDefinitionItem(true, "ProjectMappingView");
        projectViewDefinitions.put("project-mappings", pvProjectMappingsDefinition);
        final LinkResponseDefinitionItem pvTagsDefinition = new LinkResponseDefinitionItem(true, "TagView");
        projectViewDefinitions.put("tags", pvTagsDefinition);
        final LinkResponseDefinitionItem pvUsersDefinition = new LinkResponseDefinitionItem(true, "AssignedUserRequest");
        projectViewDefinitions.put("users", pvUsersDefinition);
        final LinkResponseDefinitionItem pvUserGroupsDefinition = new LinkResponseDefinitionItem(true, "AssignedUserGroupView");
        projectViewDefinitions.put("usergroups", pvUserGroupsDefinition);
        // These ProjectView links I added myself, based on API Specs data (not from definitions_with_links.txt)
        final LinkResponseDefinitionItem pvAssignableUsersDefinition = new LinkResponseDefinitionItem(true, "AssignableUserView");
        projectViewDefinitions.put("assignable-users", pvAssignableUsersDefinition);
        final LinkResponseDefinitionItem pvAssignableUserGroupsDefinition = new LinkResponseDefinitionItem(true, "AssignableUserGroupView");
        projectViewDefinitions.put("assignable-usergroups", pvAssignableUserGroupsDefinition);
        final LinkResponseDefinitionItem pvCustomFieldsDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        projectViewDefinitions.put("custom-fields", pvCustomFieldsDefinition);
        final LinkResponseDefinitionItem pvProjectJournalDefinition = new LinkResponseDefinitionItem(true, "ProjectJournalView");
        projectViewDefinitions.put("project-journal", pvProjectJournalDefinition);
        definitions.put("ProjectView", projectViewDefinitions);

        // ProjectVersionView
        final Map<String, LinkResponseDefinitionItem> projectVersionViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvvRiskProfileDefinition = new LinkResponseDefinitionItem(false, "VersionRiskProfileView");
        projectVersionViewDefinitions.put("riskProfile", pvvRiskProfileDefinition);
        final LinkResponseDefinitionItem pvvComponentsDefinition = new LinkResponseDefinitionItem(true, "VersionBomComponentView");
        projectVersionViewDefinitions.put("components", pvvComponentsDefinition);
        final LinkResponseDefinitionItem pvvVulnerableComponentsDefinition = new LinkResponseDefinitionItem(true, "VulnerableComponentView");
        projectVersionViewDefinitions.put("vulnerable-components", pvvVulnerableComponentsDefinition);
        final LinkResponseDefinitionItem pvvProjectDefinition = new LinkResponseDefinitionItem(false, "ProjectView");
        projectVersionViewDefinitions.put("project", pvvProjectDefinition);
        final LinkResponseDefinitionItem pvvPolicyStatusDefinition = new LinkResponseDefinitionItem(false, "VersionBomPolicyStatusView");
        projectVersionViewDefinitions.put("policy-status", pvvPolicyStatusDefinition);
        final LinkResponseDefinitionItem pvvCodeLocationsDefinition = new LinkResponseDefinitionItem(true, "CodeLocationView");
        projectVersionViewDefinitions.put("codelocations", pvvCodeLocationsDefinition);
        final LinkResponseDefinitionItem pvvVersionReportDefinition = new LinkResponseDefinitionItem(true, "ReportView");
        projectVersionViewDefinitions.put("versionReport", pvvVersionReportDefinition);
        final LinkResponseDefinitionItem pvvLicenseReportsDefinition = new LinkResponseDefinitionItem(true, "ReportView");
        projectVersionViewDefinitions.put("licenseReports", pvvLicenseReportsDefinition);
        // These ProjectVersionView links I added myself, based on API Specs data (not from definitions_with_links.txt)
        final LinkResponseDefinitionItem pvvHierarchicalComponentsDefinition = new LinkResponseDefinitionItem(true, "HierarchicalVersionBomComponentView");
        projectVersionViewDefinitions.put("hierarchical-components", pvvHierarchicalComponentsDefinition);
        final LinkResponseDefinitionItem pvvComparisonDefinition = new LinkResponseDefinitionItem(true, "VersionBomComponentDiffView");
        projectVersionViewDefinitions.put("comparison", pvvComparisonDefinition);
        final LinkResponseDefinitionItem pvvIssuesDefinition = new LinkResponseDefinitionItem(true, "IssueView");
        projectVersionViewDefinitions.put("issues", pvvIssuesDefinition);
        final LinkResponseDefinitionItem pvvCustomFieldsDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        projectVersionViewDefinitions.put("custom-fields", pvvCustomFieldsDefinition);
        final LinkResponseDefinitionItem pvvAttachmentsDefinition = new LinkResponseDefinitionItem(true, "VersionBomAttachmentView");
        projectVersionViewDefinitions.put("attachments", pvvAttachmentsDefinition);
        definitions.put("ProjectVersionView", projectVersionViewDefinitions);

        // ProjectCustomFieldView - not from definitions_with_links.txt
        final Map<String, LinkResponseDefinitionItem> projectCustomFieldViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pcfvCustomFieldOptionListDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        projectCustomFieldViewDefinitions.put("custom-field-option-list", pcfvCustomFieldOptionListDefinition);
        definitions.put("ProjectCustomFieldView", projectCustomFieldViewDefinitions);

        // ProjectVersionCustomFieldView
        final Map<String, LinkResponseDefinitionItem> projectVersionCustomFieldViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvcfvCustomFieldOptionListDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        projectVersionCustomFieldViewDefinitions.put("custom-field-option-list", pvcfvCustomFieldOptionListDefinition);
        definitions.put("ProjectVersionCustomFieldView", projectVersionCustomFieldViewDefinitions);

        // UserView
        final Map<String, LinkResponseDefinitionItem> userViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem uvRolesDefinition = new LinkResponseDefinitionItem(true, "RoleAssignmentView");
        userViewDefinitions.put("roles", uvRolesDefinition);
        final LinkResponseDefinitionItem uvNotificationsDefinition = new LinkResponseDefinitionItem(true, "NotificationUserView");
        userViewDefinitions.put("notifications", uvNotificationsDefinition);
        final LinkResponseDefinitionItem uvProjectsDefinition = new LinkResponseDefinitionItem(true, "AssignedProjectView");
        userViewDefinitions.put("projects", uvProjectsDefinition);
        final LinkResponseDefinitionItem uvInheritedRolesDefinition = new LinkResponseDefinitionItem(true, "RoleAssignmentView");
        userViewDefinitions.put("inherited-roles", uvInheritedRolesDefinition);
        definitions.put("UserView", userViewDefinitions);

        return definitions;
    }

    public Map<String, Map<String, LinkResponseDefinitionItem>> getDefinitions() {
        return definitions;
    }

    public Set<String> getResultClasses() { return this.resultClasses; }

    public class LinkResponseDefinitionItem {
        private final boolean hasMultipleResults;
        private final String resultClass;

        public LinkResponseDefinitionItem(final boolean hasMultipleResults, final String resultClass) {
            this.hasMultipleResults = hasMultipleResults;
            this.resultClass = resultClass;
            resultClasses.add(resultClass);
        }

        public boolean hasMultipleResults() {
            return hasMultipleResults;
        }

        public String getResultClass() {
            return resultClass;
        }

    }

}
