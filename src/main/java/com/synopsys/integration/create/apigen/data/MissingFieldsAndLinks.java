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
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

@Component
public class MissingFieldsAndLinks {
    // FIXME - this whole file is a patch-job

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

        // ComponentVersionView
        //TODO remove for 2020.10+, this link was removed in 2020.10+
        final MissingFieldAndLinkHelper cvvFieldsAndLinks = new MissingFieldAndLinkHelper();
        cvvFieldsAndLinks.addLink(new LinkDefinition("remediating"));
        missingFieldAndLinkMap.put("ComponentVersionView", cvvFieldsAndLinks);

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
