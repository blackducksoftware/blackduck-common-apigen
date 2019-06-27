package com.synopsys.integration.blackduck.api.generated.component;

    import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
    import com.synopsys.integration.blackduck.api.generated.component.PolicyRuleViewExpressionExpression;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class PolicyRuleViewExpression extends BlackDuckComponent {
    private String operator;
    private PolicyRuleViewExpressionExpression expression;

    public String getOperator() {
    return operator;
    }

    public void setOperator(String operator) {
    this.operator = operator;
    }

    public PolicyRuleViewExpressionExpression getExpression() {
    return expression;
    }

    public void setExpression(PolicyRuleViewExpressionExpression expression) {
    this.expression = expression;
    }

}
