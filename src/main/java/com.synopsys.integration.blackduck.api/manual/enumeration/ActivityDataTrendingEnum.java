package com.synopsys.integration.blackduck.api.manual.enumeration;

import com.synopsys.integration.util.EnumUtils;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public enum ActivityDataTrendingEnum {
    DECREASING,
    INCREASING,
    STABLE,
    UNKNOWN;

    public String prettyPrint() {
        return EnumUtils.prettyPrint(this);
    }

}