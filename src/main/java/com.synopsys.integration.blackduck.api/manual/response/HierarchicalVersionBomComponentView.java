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
package com.synopsys.integration.blackduck.api.manual.response;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.manual.component.ReviewedDetails;
import com.synopsys.integration.blackduck.api.manual.component.VersionBomLicenseView;
import com.synopsys.integration.blackduck.api.manual.component.VersionBomOriginView;
import com.synopsys.integration.blackduck.api.manual.enumeration.MatchedFileUsagesEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicySummaryStatusEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.VersionBomComponentMatchEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.VersionBomComponentReviewStatusEnum;
import com.synopsys.integration.blackduck.api.manual.view.RiskProfileView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class HierarchicalVersionBomComponentView extends BlackDuckResponse {
    private RiskProfileView aggregateChildLicenseRiskProfile;
    private RiskProfileView aggregateChildOperationalRiskProfile;
    private PolicySummaryStatusEnum aggregateChildPolicyStatus;
    private RiskProfileView aggregateChildSecurityRiskProfile;
    private RiskProfileView aggregateLicenseRiskProfile;
    private RiskProfileView aggregateOperationalRiskProfile;
    private PolicySummaryStatusEnum aggregatePolicyStatus;
    private RiskProfileView aggregateSecurityRiskProfile;
    private String component;
    private String componentName;
    private String componentVersion;
    private String componentVersionName;
    private RiskProfileView licenseRiskProfile;
    private java.util.List<VersionBomLicenseView> licenses;
    private java.util.List<VersionBomComponentMatchEnum> matchTypes;
    private Integer numberOfMatches;
    private RiskProfileView operationalRiskProfile;
    private java.util.List<VersionBomOriginView> origins;
    private PolicySummaryStatusEnum policyStatus;
    private VersionBomComponentReviewStatusEnum reviewStatus;
    private ReviewedDetails reviewedDetails;
    private RiskProfileView securityRiskProfile;
    private java.util.List<MatchedFileUsagesEnum> usages;

    public RiskProfileView getAggregateChildLicenseRiskProfile() {
        return aggregateChildLicenseRiskProfile;
    }

    public void setAggregateChildLicenseRiskProfile(final RiskProfileView aggregateChildLicenseRiskProfile) {
        this.aggregateChildLicenseRiskProfile = aggregateChildLicenseRiskProfile;
    }

    public RiskProfileView getAggregateChildOperationalRiskProfile() {
        return aggregateChildOperationalRiskProfile;
    }

    public void setAggregateChildOperationalRiskProfile(final RiskProfileView aggregateChildOperationalRiskProfile) {
        this.aggregateChildOperationalRiskProfile = aggregateChildOperationalRiskProfile;
    }

    public PolicySummaryStatusEnum getAggregateChildPolicyStatus() {
        return aggregateChildPolicyStatus;
    }

    public void setAggregateChildPolicyStatus(final PolicySummaryStatusEnum aggregateChildPolicyStatus) {
        this.aggregateChildPolicyStatus = aggregateChildPolicyStatus;
    }

    public RiskProfileView getAggregateChildSecurityRiskProfile() {
        return aggregateChildSecurityRiskProfile;
    }

    public void setAggregateChildSecurityRiskProfile(final RiskProfileView aggregateChildSecurityRiskProfile) {
        this.aggregateChildSecurityRiskProfile = aggregateChildSecurityRiskProfile;
    }

    public RiskProfileView getAggregateLicenseRiskProfile() {
        return aggregateLicenseRiskProfile;
    }

    public void setAggregateLicenseRiskProfile(final RiskProfileView aggregateLicenseRiskProfile) {
        this.aggregateLicenseRiskProfile = aggregateLicenseRiskProfile;
    }

    public RiskProfileView getAggregateOperationalRiskProfile() {
        return aggregateOperationalRiskProfile;
    }

    public void setAggregateOperationalRiskProfile(final RiskProfileView aggregateOperationalRiskProfile) {
        this.aggregateOperationalRiskProfile = aggregateOperationalRiskProfile;
    }

    public PolicySummaryStatusEnum getAggregatePolicyStatus() {
        return aggregatePolicyStatus;
    }

    public void setAggregatePolicyStatus(final PolicySummaryStatusEnum aggregatePolicyStatus) {
        this.aggregatePolicyStatus = aggregatePolicyStatus;
    }

    public RiskProfileView getAggregateSecurityRiskProfile() {
        return aggregateSecurityRiskProfile;
    }

    public void setAggregateSecurityRiskProfile(final RiskProfileView aggregateSecurityRiskProfile) {
        this.aggregateSecurityRiskProfile = aggregateSecurityRiskProfile;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        this.component = component;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(final String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getComponentVersionName() {
        return componentVersionName;
    }

    public void setComponentVersionName(final String componentVersionName) {
        this.componentVersionName = componentVersionName;
    }

    public RiskProfileView getLicenseRiskProfile() {
        return licenseRiskProfile;
    }

    public void setLicenseRiskProfile(final RiskProfileView licenseRiskProfile) {
        this.licenseRiskProfile = licenseRiskProfile;
    }

    public java.util.List<VersionBomLicenseView> getLicenses() {
        return licenses;
    }

    public void setLicenses(final java.util.List<VersionBomLicenseView> licenses) {
        this.licenses = licenses;
    }

    public java.util.List<VersionBomComponentMatchEnum> getMatchTypes() {
        return matchTypes;
    }

    public void setMatchTypes(final java.util.List<VersionBomComponentMatchEnum> matchTypes) {
        this.matchTypes = matchTypes;
    }

    public Integer getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(final Integer numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public RiskProfileView getOperationalRiskProfile() {
        return operationalRiskProfile;
    }

    public void setOperationalRiskProfile(final RiskProfileView operationalRiskProfile) {
        this.operationalRiskProfile = operationalRiskProfile;
    }

    public java.util.List<VersionBomOriginView> getOrigins() {
        return origins;
    }

    public void setOrigins(final java.util.List<VersionBomOriginView> origins) {
        this.origins = origins;
    }

    public PolicySummaryStatusEnum getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(final PolicySummaryStatusEnum policyStatus) {
        this.policyStatus = policyStatus;
    }

    public VersionBomComponentReviewStatusEnum getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(final VersionBomComponentReviewStatusEnum reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public ReviewedDetails getReviewedDetails() {
        return reviewedDetails;
    }

    public void setReviewedDetails(final ReviewedDetails reviewedDetails) {
        this.reviewedDetails = reviewedDetails;
    }

    public RiskProfileView getSecurityRiskProfile() {
        return securityRiskProfile;
    }

    public void setSecurityRiskProfile(final RiskProfileView securityRiskProfile) {
        this.securityRiskProfile = securityRiskProfile;
    }

    public java.util.List<MatchedFileUsagesEnum> getUsages() {
        return usages;
    }

    public void setUsages(final java.util.List<MatchedFileUsagesEnum> usages) {
        this.usages = usages;
    }

}
