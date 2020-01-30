package com.synopsys.integration.blackduck.api.generated.enumeration;

import com.synopsys.integration.util.EnumUtils;

@Deprecated
/**
* PolicyRuleExpressionSetOperatorType is now called PolicyRuleExpressionOperatorType
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public enum PolicyRuleExpressionSetOperatorType {
	AND,
	OR;

	private String mediaType = "application/vnd.blackducksoftware.policy-4+json";

	public String getMediaType() {
	return mediaType;
	}

	public String prettyPrint() {
	return EnumUtils.prettyPrint(this);
	}

} 