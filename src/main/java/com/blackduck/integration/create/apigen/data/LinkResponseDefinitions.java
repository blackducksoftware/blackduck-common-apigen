/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LinkResponseDefinitions {
    private final Map<String, Map<String, LinkResponseDefinitionItem>> definitions = new HashMap<>();

    public LinkResponseDefinitions() {
        addToDefinitions("CodeLocationView", Arrays.asList(
            new LinkResponseDefinition("scans", false, "BlackDuckStringResponse")
        ));

        addToDefinitions("ComponentVersionView", Arrays.asList(
            new LinkResponseDefinition("component", false, "ComponentView")
            , new LinkResponseDefinition("origins", true, "OriginView")
            , new LinkResponseDefinition("vulnerabilities", true, "VulnerabilityView")
            , new LinkResponseDefinition("upgrade-guidance", false, "ComponentVersionUpgradeGuidanceView")
        ));

        addToDefinitions("CodeLocationView", Arrays.asList(
            new LinkResponseDefinition("scans", false, "BlackDuckStringResponse")
        ));

        addToDefinitions("ComponentView", Arrays.asList(
            new LinkResponseDefinition("versions", true, "ComponentVersionView")
            , new LinkResponseDefinition("vulnerabilities", true, "VulnerabilityView")
            , new LinkResponseDefinition("tags", true, "TagView")
            , new LinkResponseDefinition("custom-fields", true, "CustomFieldView")
        ));

        addToDefinitions("LicenseView", Arrays.asList(
            new LinkResponseDefinition("text", false, "BlackDuckStringResponse")
        ));

        addToDefinitions("ProjectView", Arrays.asList(
            new LinkResponseDefinition("versions", true, "ProjectVersionView")
            , new LinkResponseDefinition("canonicalVersion", false, "ProjectVersionView")
            , new LinkResponseDefinition("project-mappings", true, "ProjectMappingView")
            , new LinkResponseDefinition("tags", true, "TagView")
            , new LinkResponseDefinition("users", true, "AssignedUserView")
            , new LinkResponseDefinition("usergroups", true, "AssignedUserGroupView")
        ));

        addToDefinitions("ProjectVersionView", Arrays.asList(
            new LinkResponseDefinition("riskProfile", false, "VersionRiskProfileView")
            , new LinkResponseDefinition("components", true, "ProjectVersionComponentVersionView")
            , new LinkResponseDefinition("vulnerable-components", true, "ProjectVersionVulnerableBomComponentsView")
            , new LinkResponseDefinition("project", false, "ProjectView")
            , new LinkResponseDefinition("policy-status", false, "ProjectVersionPolicyStatusView")
            , new LinkResponseDefinition("codelocations", true, "CodeLocationView")
            , new LinkResponseDefinition("reports", true, "ReportView")
            , new LinkResponseDefinition("versionReport", true, "ProjectVersionReportView")
            , new LinkResponseDefinition("licenseReports", true, "ProjectVersionReportView")
            , new LinkResponseDefinition("issues", true, "ProjectVersionIssuesView")
            , new LinkResponseDefinition("active-policy-rules", true, "ProjectVersionPolicyRulesView")
            , new LinkResponseDefinition("bom-status", true, "ProjectVersionBomStatusView")
        ));

        addToDefinitions("ProjectVersionComponentView", Arrays.asList(
            new LinkResponseDefinition("component-issues", true, "IssueView")
            , new LinkResponseDefinition("origins", true, "OriginView")
            , new LinkResponseDefinition("matched-files", true, "ComponentMatchedFilesView")
            , new LinkResponseDefinition("policy-rules", true, "ComponentPolicyRulesView")
        ));

        addToDefinitions("ProjectVersionComponentVersionView", Arrays.asList(
            new LinkResponseDefinition("component-issues", true, "IssueView"),
            new LinkResponseDefinition("matched-files", true, "ComponentMatchedFilesView"),
            new LinkResponseDefinition("origins", true, "OriginView"),
            new LinkResponseDefinition("policy-rules", true, "ComponentPolicyRulesView")
        ));

        addToDefinitions("ReportView", Arrays.asList(
            new LinkResponseDefinition("content", false, "BlackDuckStringResponse")
        ));

        addToDefinitions("RoleAssignmentView", Arrays.asList(
            new LinkResponseDefinition("user", false, "UserView")
        ));

        addToDefinitions("UserView", Arrays.asList(
            new LinkResponseDefinition("roles", true, "RoleAssignmentView")
            , new LinkResponseDefinition("notifications", true, "NotificationUserView")
            , new LinkResponseDefinition("projects", true, "AssignedProjectView")
            , new LinkResponseDefinition("inherited-roles", true, "RoleAssignmentView")
            , new LinkResponseDefinition("usergroups", true, "UserGroupView")
        ));

        addToDefinitions("UserGroupView", Arrays.asList(
            new LinkResponseDefinition("users", true, "UserView")
        ));

        addToDefinitions("ProjectVersionVulnerableBomComponentsView", Arrays.asList(
            new LinkResponseDefinition("matched-files", true, "ComponentMatchedFilesView")
            , new LinkResponseDefinition("vulnerabilities", true, "VulnerabilityView")
        ));
    }

    private void addToDefinitions(String viewName, List<LinkResponseDefinition> responseDefinitions) {
        Map<String, LinkResponseDefinitionItem> viewDefinitions = new HashMap<>();
        for (LinkResponseDefinition linkResponseDefinition : responseDefinitions) {
            viewDefinitions.put(linkResponseDefinition.link, new LinkResponseDefinitionItem(linkResponseDefinition.hasMultipleResults, linkResponseDefinition.resultClass));
        }
        definitions.put(viewName, viewDefinitions);
    }

    public Map<String, Map<String, LinkResponseDefinitionItem>> getDefinitions() {
        return definitions;
    }

    private class LinkResponseDefinition {
        public final String link;
        public final boolean hasMultipleResults;
        public final String resultClass;

        public LinkResponseDefinition(String link, boolean hasMultipleResults, String resultClass) {
            this.link = link;
            this.hasMultipleResults = hasMultipleResults;
            this.resultClass = resultClass;
        }
    }

    public class LinkResponseDefinitionItem {
        private final boolean hasMultipleResults;
        private final String resultClass;

        public LinkResponseDefinitionItem(boolean hasMultipleResults, String resultClass) {
            this.hasMultipleResults = hasMultipleResults;
            this.resultClass = resultClass;
        }

        public boolean hasMultipleResults() {
            return hasMultipleResults;
        }

        public String getResultClass() {
            return resultClass;
        }

    }

}
