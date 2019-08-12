package com.synopsys.integration.create.apigen.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ResponseNameParserTest {
    @Test
    public void testSomething() {
        final ResponseNameParser responseNameParser = new ResponseNameParser();
        final String responseName = responseNameParser.getResponseName(
            "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/policy-rules/policyRuleId/GET/bds_policy_4_json/response-specification.json", true);
        System.out.println(responseName);
        assertEquals("PolicyRuleViewV4", responseName);
        final String responseName2 = responseNameParser.getResponseName(
            "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/policy-rules/policyRuleId/GET/bds_policy_4_json/response-specification.json", false);
        assertEquals("PolicyRuleView", responseName2);
    }
}
