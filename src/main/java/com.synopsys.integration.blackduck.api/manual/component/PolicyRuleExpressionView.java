package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class PolicyRuleExpressionView extends BlackDuckComponent {
    private String displayName;
    private String name;
    private String operation;
    private PolicyRuleExpressionParameter parameters;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public PolicyRuleExpressionParameter getParameters() {
        return parameters;
    }

    public void setParameters(final PolicyRuleExpressionParameter parameters) {
        this.parameters = parameters;
    }

}
