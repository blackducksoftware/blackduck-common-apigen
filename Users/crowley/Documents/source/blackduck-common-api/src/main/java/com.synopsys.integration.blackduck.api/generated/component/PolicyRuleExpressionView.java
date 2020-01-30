package com.synopsys.integration.blackduck.api.generated.component;

import java.util.List;
import com.synopsys.integration.blackduck.api.generated.component.PolicyRuleExpressionExpressionsView;
import com.synopsys.integration.blackduck.api.generated.enumeration.PolicyRuleExpressionOperatorType;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

/**
* PolicyRuleExpressionView is now called PolicyRuleExpressionExpressionsView
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class PolicyRuleExpressionView extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private PolicyRuleExpressionOperatorType operator;
    private java.util.List<PolicyRuleExpressionExpressionsView> expressions;

    public PolicyRuleExpressionOperatorType getOperator() {
	return operator;
    }

    public void setOperator(PolicyRuleExpressionOperatorType operator) {
	this.operator = operator;
    }

    public java.util.List<PolicyRuleExpressionExpressionsView> getExpressions() {
	return expressions;
    }

    public void setExpressions(java.util.List<PolicyRuleExpressionExpressionsView> expressions) {
	this.expressions = expressions;
    }


    public String getMediaType() {
	return mediaType;
    }

}
