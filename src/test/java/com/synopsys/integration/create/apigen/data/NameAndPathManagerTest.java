package com.synopsys.integration.create.apigen.data;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.model.ApiPathData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NameAndPathManagerTest {
    private NameAndPathManager nameAndPathManager;

    @BeforeEach
    void init(){
        nameAndPathManager = new NameAndPathManager();
    }

    @Test
    void ApiDiscoveryDataTest(){
        ApiPathData testDup = new ApiPathData("pathTest", "resultClassTest", true);
        nameAndPathManager.addApiDiscoveryData(testDup);
        nameAndPathManager.addApiDiscoveryData(testDup);
        Set<ApiPathData> apiDiscoveryData = nameAndPathManager.getApiDiscoveryData();
        assertEquals(1, apiDiscoveryData.size());
    }

    @Test
    void isRepeatApiDiscoveryTest(){
        nameAndPathManager.addApiDiscoveryPath("pathDup");
        boolean expectedTrue = nameAndPathManager.isRepeatApiDiscoveryPath("pathDup");
        boolean expectedFalse = nameAndPathManager.isRepeatApiDiscoveryPath("pathNew");
        assertTrue(expectedTrue);
        assertFalse(expectedFalse);
    }

    @Test
    void getNonLinkClassNamesTest(){
        nameAndPathManager.addNonLinkClassName("nameDup");
        nameAndPathManager.addNonLinkClassName("nameDup");
        nameAndPathManager.addNonLinkClassName("nameNew");
        Set<String> nonLinkClassNames = nameAndPathManager.getNonLinkClassNames();
        assertEquals(2, nonLinkClassNames.size());
    }

    @Test
    void getLinkClassNamesTest(){
        nameAndPathManager.addLinkClassName("nameDup");
        nameAndPathManager.addLinkClassName("nameDup");
        nameAndPathManager.addLinkClassName("nameNew1");
        nameAndPathManager.addLinkClassName("nameNew2");
        Set<String> LinkClassNames = nameAndPathManager.getLinkClassNames();
        assertEquals(3, LinkClassNames.size());
    }

    @Test
    void getNullLinkResultClassesTest(){
        nameAndPathManager.addNullLinkResultClass("keyTest", "valueTest");
        Map<String, String> map = nameAndPathManager.getNullLinkResultClasses();
        assertEquals("valueTest", map.get("keyTest"));
    }

    @Test
    void getResponseNamesOverrideTest(){
        String test = nameAndPathManager.getResponseNameOverride("components/componentId/versions/componentVersionId/licenses/licenseId/GET");
        assertEquals(test, "ComponentVersionLicenseLicensesLicense");
    }
}
