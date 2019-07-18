package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.component.RiskCountView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class RiskProfileView extends BlackDuckView {
    private java.util.List<RiskCountView> counts;

    public java.util.List<RiskCountView> getCounts() {
        return counts;
    }

    public void setCounts(final java.util.List<RiskCountView> counts) {
        this.counts = counts;
    }

}
