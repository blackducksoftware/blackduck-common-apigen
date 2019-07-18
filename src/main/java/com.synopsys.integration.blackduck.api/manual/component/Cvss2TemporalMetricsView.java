package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss2TemporalMetricsExploitabilityEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss2TemporalMetricsReportConfidenceEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.Cvss3TemporalMetricsRemediationLevelEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class Cvss2TemporalMetricsView extends BlackDuckComponent {
    private Cvss2TemporalMetricsExploitabilityEnum exploitability;
    private Cvss3TemporalMetricsRemediationLevelEnum remediationLevel;
    private Cvss2TemporalMetricsReportConfidenceEnum reportConfidence;
    private java.math.BigDecimal score;

    public Cvss2TemporalMetricsExploitabilityEnum getExploitability() {
        return exploitability;
    }

    public void setExploitability(final Cvss2TemporalMetricsExploitabilityEnum exploitability) {
        this.exploitability = exploitability;
    }

    public Cvss3TemporalMetricsRemediationLevelEnum getRemediationLevel() {
        return remediationLevel;
    }

    public void setRemediationLevel(final Cvss3TemporalMetricsRemediationLevelEnum remediationLevel) {
        this.remediationLevel = remediationLevel;
    }

    public Cvss2TemporalMetricsReportConfidenceEnum getReportConfidence() {
        return reportConfidence;
    }

    public void setReportConfidence(final Cvss2TemporalMetricsReportConfidenceEnum reportConfidence) {
        this.reportConfidence = reportConfidence;
    }

    public java.math.BigDecimal getScore() {
        return score;
    }

    public void setScore(final java.math.BigDecimal score) {
        this.score = score;
    }

}