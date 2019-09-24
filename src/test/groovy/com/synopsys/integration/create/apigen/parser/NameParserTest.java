package com.synopsys.integration.create.apigen.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameParserTest {
    @Test
    public void testSomething() {
        final NameParser nameParser = new NameParser();
        final String responseName = nameParser.getResponseName(
            "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/policy-rules/policyRuleId/GET/bds_policy_4_json/response-specification.json");
        System.out.println(responseName);
        assertEquals("PolicyRuleViewV4", responseName);
        final String responseName2 = nameParser.getResponseName(
            "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/policy-rules/policyRuleId/GET/bds_policy_4_json/response-specification.json");
        assertEquals("PolicyRuleView", responseName2);
    }
}
