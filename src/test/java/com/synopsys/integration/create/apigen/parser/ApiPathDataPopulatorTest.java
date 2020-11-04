package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.ApiPathData;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ApiPathDataPopulatorTest {

    private NameAndPathManager nameAndPathManager = new NameAndPathManager();
    private ApiPathDataPopulator apiPathDataPopulator = new ApiPathDataPopulator(nameAndPathManager);

    @Test
    public void test() {
        assertTrue(nameAndPathManager.getApiDiscoveryData().size() == 0);
        List<ResponseDefinition> mockResponses = getMockResponses();
        apiPathDataPopulator.populateApiPathData(mockResponses);
        Set<ApiPathData> resultApiDiscoveryData = nameAndPathManager.getApiDiscoveryData();

        assertFalse(resultApiDiscoveryData.size() > 5);
        for (ApiPathData apiPathData : resultApiDiscoveryData) {
            if (apiPathData.getPath().equals("/api/components")) {
                assertTrue(apiPathData.getResultClass().equals("ComponentSearchResultView"));
            }
            if (apiPathData.getPath().equals("/api/vulnerabilities")) {
                assertTrue(apiPathData.hasMultipleResults() == false);
            }
        }
    }

    private List<ResponseDefinition> getMockResponses() {
        List<ResponseDefinition> responses = new ArrayList<>();

        responses.add(new ResponseDefinition("components/path/path/path", "ComponentView", "", true));
        responses.add(new ResponseDefinition("users/path", "UserView", "", true));
        responses.add(new ResponseDefinition("scan-summaries/path/path/path", "ScanView", "", false));
        responses.add(new ResponseDefinition("vulnerabilities/path/path/", "VulnerabilityView", "", false));
        responses.add(new ResponseDefinition("projects/path", "ProjectView", "", true));
        ResponseDefinition arrayResponse = new ResponseDefinition("arrays/path", "ArrayView", "", true);
        arrayResponse.addField(new FieldDefinition(UtilStrings.ITEMS, UtilStrings.ARRAY, false, false));
        arrayResponse.addField(new FieldDefinition(UtilStrings.META, UtilStrings.OBJECT, false, false));
        arrayResponse.addField(new FieldDefinition(UtilStrings.TOTAL_COUNT, UtilStrings.NUMBER, false, false));

        return responses;
    }
}
