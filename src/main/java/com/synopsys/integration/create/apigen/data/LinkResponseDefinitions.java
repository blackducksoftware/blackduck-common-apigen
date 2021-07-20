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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LinkResponseDefinitions {

    private final Map<String, Map<String, LinkResponseDefinitionItem>> definitions;
    private final Set<String> linkLabels;

    @Autowired
    public LinkResponseDefinitions() {
        this.linkLabels = new HashSet<>();
        this.definitions = populateDefinitions();
    }

    private Map<String, Map<String, LinkResponseDefinitionItem>> populateDefinitions() {
        final Map<String, Map<String, LinkResponseDefinitionItem>> definitions = new HashMap<>();

        // CodeLocationView
        final Map<String, LinkResponseDefinitionItem> codeLocationViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem clvScanDefinition = new LinkResponseDefinitionItem(false, "String");
        codeLocationViewDefinitions.put("scans", clvScanDefinition);
        definitions.put("CodeLocationView", codeLocationViewDefinitions);

        // ComponentVersionView
        final Map<String, LinkResponseDefinitionItem> componentVersionViewDefinitions = new HashMap<>();
        //final LinkResponseDefinitionItem cvvReferencesDefinition = new LinkResponseDefinitionItem(true, "VersionReferenceView");
        //componentVersionViewDefinitions.put("references", cvvReferencesDefinition);
        final LinkResponseDefinitionItem cvvComponentDefinition = new LinkResponseDefinitionItem(false, "ComponentView");
        componentVersionViewDefinitions.put("component", cvvComponentDefinition);
        final LinkResponseDefinitionItem cvvOriginsDefinition = new LinkResponseDefinitionItem(true, "OriginView");
        componentVersionViewDefinitions.put("origins", cvvOriginsDefinition);
        final LinkResponseDefinitionItem cvvVulnerabilitiesDefinition = new LinkResponseDefinitionItem(true, "VulnerabilityView");
        componentVersionViewDefinitions.put("vulnerabilities", cvvVulnerabilitiesDefinition);
        final LinkResponseDefinitionItem cvvUpgradeGuidanceDefinition = new LinkResponseDefinitionItem(false, "ComponentVersionUpgradeGuidanceView");
        componentVersionViewDefinitions.put("upgrade-guidance", cvvUpgradeGuidanceDefinition);
        //final LinkResponseDefinitionItem cvvRiskProfileDefinition = new LinkResponseDefinitionItem(false, "VersionRiskView");
        //componentVersionViewDefinitions.put("risk-profile", cvvRiskProfileDefinition);
        definitions.put("ComponentVersionView", componentVersionViewDefinitions);

        // ComponentView
        final Map<String, LinkResponseDefinitionItem> componentViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem cvVersionsDefinition = new LinkResponseDefinitionItem(true, "ComponentVersionView");
        componentViewDefinitions.put("versions", cvVersionsDefinition);
        final LinkResponseDefinitionItem cvVulnerabilitiesDefinition = new LinkResponseDefinitionItem(true, "VulnerabilityView");
        componentViewDefinitions.put("vulnerabilities", cvVulnerabilitiesDefinition);
        final LinkResponseDefinitionItem cvTagsDefinition = new LinkResponseDefinitionItem(true, "TagView");
        componentViewDefinitions.put("tags", cvTagsDefinition);
        final LinkResponseDefinitionItem cvCustomFieldsDefinition = new LinkResponseDefinitionItem(true, "CustomFieldView");
        componentViewDefinitions.put("custom-fields", cvCustomFieldsDefinition);
        definitions.put("ComponentView", componentViewDefinitions);

        // LicenseView
        final Map<String, LinkResponseDefinitionItem> licenseViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem lvTextDefinition = new LinkResponseDefinitionItem(false, "String");
        licenseViewDefinitions.put("text", lvTextDefinition);
        definitions.put("LicenseView", licenseViewDefinitions);

        // ProjectView
        final Map<String, LinkResponseDefinitionItem> projectViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvVersionsDefinition = new LinkResponseDefinitionItem(true, "ProjectVersionView");
        projectViewDefinitions.put("versions", pvVersionsDefinition);
        final LinkResponseDefinitionItem pvCanonicalVersionDefinition = new LinkResponseDefinitionItem(false, "ProjectVersionView");
        projectViewDefinitions.put("canonicalVersion", pvCanonicalVersionDefinition);
        final LinkResponseDefinitionItem pvProjectMappingsDefinition = new LinkResponseDefinitionItem(true, "ProjectMappingView"); // *
        projectViewDefinitions.put("project-mappings", pvProjectMappingsDefinition);
        final LinkResponseDefinitionItem pvTagsDefinition = new LinkResponseDefinitionItem(true, "TagView"); // *
        projectViewDefinitions.put("tags", pvTagsDefinition);
        final LinkResponseDefinitionItem pvUsersDefinition = new LinkResponseDefinitionItem(true, "AssignedUserView");
        projectViewDefinitions.put("users", pvUsersDefinition);
        final LinkResponseDefinitionItem pvUserGroupsDefinition = new LinkResponseDefinitionItem(true, "AssignedUserGroupView"); // ****
        projectViewDefinitions.put("usergroups", pvUserGroupsDefinition);
        definitions.put("ProjectView", projectViewDefinitions);

        // ProjectVersionView
        final Map<String, LinkResponseDefinitionItem> projectVersionViewDefinitions = new HashMap<>();
        //final LinkResponseDefinitionItem pvvRiskProfileDefinition = new LinkResponseDefinitionItem(false, "VersionRiskProfileView"); *
        //projectVersionViewDefinitions.put("riskProfile", pvvRiskProfileDefinition);
        final LinkResponseDefinitionItem pvvComponentsDefinition = new LinkResponseDefinitionItem(true, "ProjectVersionComponentView");
        projectVersionViewDefinitions.put("components", pvvComponentsDefinition);
        final LinkResponseDefinitionItem pvvVulnerableComponentsDefinition = new LinkResponseDefinitionItem(true, "ProjectVersionVulnerableBomComponentsView");
        projectVersionViewDefinitions.put("vulnerable-components", pvvVulnerableComponentsDefinition);
        final LinkResponseDefinitionItem pvvProjectDefinition = new LinkResponseDefinitionItem(false, "ProjectView");
        projectVersionViewDefinitions.put("project", pvvProjectDefinition);
        final LinkResponseDefinitionItem pvvPolicyStatusDefinition = new LinkResponseDefinitionItem(false, "ProjectVersionPolicyStatusView");
        projectVersionViewDefinitions.put("policy-status", pvvPolicyStatusDefinition);
        final LinkResponseDefinitionItem pvvCodeLocationsDefinition = new LinkResponseDefinitionItem(true, "CodeLocationView");
        projectVersionViewDefinitions.put("codelocations", pvvCodeLocationsDefinition);
        //final LinkResponseDefinitionItem pvvVersionReportDefinition = new LinkResponseDefinitionItem(true, "ReportView"); *
        //projectVersionViewDefinitions.put("versionReport", pvvVersionReportDefinition);
        final LinkResponseDefinitionItem pvvLicenseReportsDefinition = new LinkResponseDefinitionItem(true, "ReportView"); // *
        projectVersionViewDefinitions.put("licenseReports", pvvLicenseReportsDefinition);
        final LinkResponseDefinitionItem pvvIssuesDefinition = new LinkResponseDefinitionItem(true, "IssueView");
        projectVersionViewDefinitions.put("issues", pvvIssuesDefinition);
        definitions.put("ProjectVersionView", projectVersionViewDefinitions);

        // ProjectVersionComponentView
        final Map<String, LinkResponseDefinitionItem> projectVersionComponentViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvcvOriginsDefinition = new LinkResponseDefinitionItem(true, "OriginView");
        projectVersionComponentViewDefinitions.put("origins", pvcvOriginsDefinition);
        final LinkResponseDefinitionItem pvcvMatchedFilesDefinition = new LinkResponseDefinitionItem(true, "ComponentMatchedFilesView");
        projectVersionComponentViewDefinitions.put("matched-files", pvcvMatchedFilesDefinition);
        final LinkResponseDefinitionItem pvcvPolicyRulesDefinition = new LinkResponseDefinitionItem(true, "ComponentPolicyRulesView");
        projectVersionComponentViewDefinitions.put("policy-rules", pvcvPolicyRulesDefinition);
        definitions.put("ProjectVersionComponentView", projectVersionComponentViewDefinitions);

        // ReportView
        final Map<String, LinkResponseDefinitionItem> reportViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem rvContentDefinition = new LinkResponseDefinitionItem(false, "String");
        reportViewDefinitions.put("content", rvContentDefinition);
        definitions.put("ReportView", reportViewDefinitions);

        // RoleAssignmentView
        final Map<String, LinkResponseDefinitionItem> roleAssignmentViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem ravUserDefinition = new LinkResponseDefinitionItem(false, "UserView");
        roleAssignmentViewDefinitions.put("user", ravUserDefinition);
        definitions.put("RoleAssignmentView", roleAssignmentViewDefinitions);

        // UserView
        final Map<String, LinkResponseDefinitionItem> userViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem uvRolesDefinition = new LinkResponseDefinitionItem(true, "RoleAssignmentView");
        userViewDefinitions.put("roles", uvRolesDefinition);
        final LinkResponseDefinitionItem uvNotificationsDefinition = new LinkResponseDefinitionItem(true, "NotificationUserView"); // *
        userViewDefinitions.put("notifications", uvNotificationsDefinition);
        final LinkResponseDefinitionItem uvProjectsDefinition = new LinkResponseDefinitionItem(true, "AssignedProjectView"); // ****
        userViewDefinitions.put("projects", uvProjectsDefinition);
        final LinkResponseDefinitionItem uvInheritedRolesDefinition = new LinkResponseDefinitionItem(true, "RoleAssignmentView"); // ****
        userViewDefinitions.put("inherited-roles", uvInheritedRolesDefinition);
        definitions.put("UserView", userViewDefinitions);

        // UserGroupView
        final Map<String, LinkResponseDefinitionItem> userGroupViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem ugvRolesDefinition = new LinkResponseDefinitionItem(true, "UserView");
        userGroupViewDefinitions.put("users", ugvRolesDefinition);
        definitions.put("UserGroupView", userGroupViewDefinitions);

        // ProjectVersionVulnerableBomComponentsView
        final Map<String, LinkResponseDefinitionItem> projectVersionVulnerableBomComponentsViewDefinitions = new HashMap<>();
        final LinkResponseDefinitionItem pvvbcvMatchedFilesDefinition = new LinkResponseDefinitionItem(true, "ComponentMatchedFilesView");
        projectVersionVulnerableBomComponentsViewDefinitions.put("matched-files", pvvbcvMatchedFilesDefinition);
        final LinkResponseDefinitionItem pvvbcvVulnerabilitiesDefinition = new LinkResponseDefinitionItem(true, "VulnerabilityView");
        projectVersionVulnerableBomComponentsViewDefinitions.put("vulnerabilities", pvvbcvVulnerabilitiesDefinition);
        definitions.put("ProjectVersionVulnerableBomComponentsView", projectVersionVulnerableBomComponentsViewDefinitions);

        return definitions;
    }

    public Map<String, Map<String, LinkResponseDefinitionItem>> getDefinitions() {
        return definitions;
    }

    public Set<String> getLinkLabels() { return linkLabels; }

    public void addLinkLabel(final String label) { linkLabels.add(label); }

    public class LinkResponseDefinitionItem {
        private final boolean hasMultipleResults;
        private final String resultClass;

        public LinkResponseDefinitionItem(final boolean hasMultipleResults, final String resultClass) {
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
