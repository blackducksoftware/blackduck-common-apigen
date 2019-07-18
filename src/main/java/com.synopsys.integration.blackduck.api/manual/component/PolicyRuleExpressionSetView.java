package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.PolicyRuleExpressionSetOperatorEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class PolicyRuleExpressionSetView extends BlackDuckComponent {
    private java.util.List<PolicyRuleExpressionView> expressions;
    private PolicyRuleExpressionSetOperatorEnum operator;

    public java.util.List<PolicyRuleExpressionView> getExpressions() {
        return expressions;
    }

    public void setExpressions(final java.util.List<PolicyRuleExpressionView> expressions) {
        this.expressions = expressions;
    }

    public PolicyRuleExpressionSetOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(final PolicyRuleExpressionSetOperatorEnum operator) {
        this.operator = operator;
    }

}
