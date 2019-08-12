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
package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class IssueView extends BlackDuckView {
    private String issueAssignee;
    private java.util.Date issueCreatedAt;
    private String issueDescription;
    private String issueId;
    private String issueLink;
    private String issueStatus;
    private java.util.Date issueUpdatedAt;

    public String getIssueAssignee() {
        return issueAssignee;
    }

    public void setIssueAssignee(final String issueAssignee) {
        this.issueAssignee = issueAssignee;
    }

    public java.util.Date getIssueCreatedAt() {
        return issueCreatedAt;
    }

    public void setIssueCreatedAt(final java.util.Date issueCreatedAt) {
        this.issueCreatedAt = issueCreatedAt;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(final String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(final String issueId) {
        this.issueId = issueId;
    }

    public String getIssueLink() {
        return issueLink;
    }

    public void setIssueLink(final String issueLink) {
        this.issueLink = issueLink;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(final String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public java.util.Date getIssueUpdatedAt() {
        return issueUpdatedAt;
    }

    public void setIssueUpdatedAt(final java.util.Date issueUpdatedAt) {
        this.issueUpdatedAt = issueUpdatedAt;
    }

}
