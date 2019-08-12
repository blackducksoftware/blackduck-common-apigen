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
package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss3TemporalMetricsExploitabilityEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss3TemporalMetricsRemediationLevelEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss3TemporalMetricsReportConfidenceEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class Cvss3TemporalMetricsView extends BlackDuckComponent {
    private Cvss3TemporalMetricsExploitabilityEnum exploitability;
    private Cvss3TemporalMetricsRemediationLevelEnum remediationLevel;
    private Cvss3TemporalMetricsReportConfidenceEnum reportConfidence;
    private java.math.BigDecimal score;

    public Cvss3TemporalMetricsExploitabilityEnum getExploitability() {
        return exploitability;
    }

    public void setExploitability(final Cvss3TemporalMetricsExploitabilityEnum exploitability) {
        this.exploitability = exploitability;
    }

    public Cvss3TemporalMetricsRemediationLevelEnum getRemediationLevel() {
        return remediationLevel;
    }

    public void setRemediationLevel(final Cvss3TemporalMetricsRemediationLevelEnum remediationLevel) {
        this.remediationLevel = remediationLevel;
    }

    public Cvss3TemporalMetricsReportConfidenceEnum getReportConfidence() {
        return reportConfidence;
    }

    public void setReportConfidence(final Cvss3TemporalMetricsReportConfidenceEnum reportConfidence) {
        this.reportConfidence = reportConfidence;
    }

    public java.math.BigDecimal getScore() {
        return score;
    }

    public void setScore(final java.math.BigDecimal score) {
        this.score = score;
    }

}
