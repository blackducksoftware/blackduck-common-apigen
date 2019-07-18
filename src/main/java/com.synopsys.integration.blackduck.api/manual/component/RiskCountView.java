package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.RiskCountEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class RiskCountView extends BlackDuckComponent {
    private Integer count;
    private RiskCountEnum countType;

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public RiskCountEnum getCountType() {
        return countType;
    }

    public void setCountType(final RiskCountEnum countType) {
        this.countType = countType;
    }

}
