/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.blackduck.integration.create.apigen.model.FieldDefinition;
import com.blackduck.integration.create.apigen.model.LinkDefinition;
import org.springframework.stereotype.Component;

@Component
public class MissingFieldsAndLinks {
    // FIXME - this whole file is a patch-job.  Requests should be made to the Black Duck API team to add these fields and links to the spec

    private final Map<String, MissingFieldAndLinkHelper> missingFieldAndLinkMap;

    public MissingFieldsAndLinks() {
        this.missingFieldAndLinkMap = populateMissingFieldsAndLinks();
    }

    private Map<String, MissingFieldAndLinkHelper> populateMissingFieldsAndLinks() {
        final Map<String, MissingFieldAndLinkHelper> missingFieldAndLinkMap = new HashMap<>();

        // ComponentPolicyStatusView
        final MissingFieldAndLinkHelper cpsvFieldsAndLinks = new MissingFieldAndLinkHelper();
        cpsvFieldsAndLinks.addLink(new LinkDefinition("comment"));
        missingFieldAndLinkMap.put("ComponentPolicyStatusView", cpsvFieldsAndLinks);

        // LicenseView
        final MissingFieldAndLinkHelper lvFieldsAndLinks = new MissingFieldAndLinkHelper();
        lvFieldsAndLinks.addLink(new LinkDefinition("text"));
        missingFieldAndLinkMap.put("LicenseView", lvFieldsAndLinks);

        // UserView
        final MissingFieldAndLinkHelper uvFieldsAndLinks = new MissingFieldAndLinkHelper();
        uvFieldsAndLinks.addLink(createRequired("notifications"));
        uvFieldsAndLinks.addLink(createRequired("projects"));
        uvFieldsAndLinks.addLink(createRequired("roles"));
        uvFieldsAndLinks.addLink(createRequired("inherited-roles"));
        missingFieldAndLinkMap.put("UserView", uvFieldsAndLinks);

        // UserGroupView
        final MissingFieldAndLinkHelper ugvFieldsAndLinks = new MissingFieldAndLinkHelper();
        ugvFieldsAndLinks.addLink(new LinkDefinition("users"));
        missingFieldAndLinkMap.put("UserGroupView", ugvFieldsAndLinks);

        // ReportView
        final MissingFieldAndLinkHelper rvFieldsAndLinks = new MissingFieldAndLinkHelper();
        rvFieldsAndLinks.addLink(new LinkDefinition("content"));
        missingFieldAndLinkMap.put("ReportView", rvFieldsAndLinks);

        // RoleAssignmentView
        final MissingFieldAndLinkHelper ravFieldsAndLinks = new MissingFieldAndLinkHelper();
        ravFieldsAndLinks.addLink(new LinkDefinition("user"));
        missingFieldAndLinkMap.put("RoleAssignmentView", ravFieldsAndLinks);

        // ProjectVersionVulnerableBomComponentsView
        final MissingFieldAndLinkHelper pvvbcvFieldsAndLinks = new MissingFieldAndLinkHelper();
        pvvbcvFieldsAndLinks.addLink(new LinkDefinition("matched-files"));
        pvvbcvFieldsAndLinks.addLink(new LinkDefinition("vulnerabilities"));
        missingFieldAndLinkMap.put("ProjectVersionVulnerableBomComponentsView", pvvbcvFieldsAndLinks);

        return missingFieldAndLinkMap;
    }

    private LinkDefinition createRequired(String label) {
        return new LinkDefinition(label);
    }

    public Set<FieldDefinition> getMissingFields(final String className) {
        return missingFieldAndLinkMap.getOrDefault(className, new MissingFieldAndLinkHelper()).getMissingFields();
    }

    public Set<LinkDefinition> getMissingLinks(final String className) {
        return missingFieldAndLinkMap.getOrDefault(className, new MissingFieldAndLinkHelper()).getMissingLinks();
    }

}
