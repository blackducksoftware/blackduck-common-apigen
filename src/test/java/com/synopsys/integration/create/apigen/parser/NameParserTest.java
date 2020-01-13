package com.synopsys.integration.create.apigen.parser;

import org.junit.Test;
import org.junit.Assert;

import com.synopsys.integration.create.apigen.data.NameAndPathManager;

public class NameParserTest {

    private final NameParser nameParser = new NameParser(new NameAndPathManager());

    @Test
    public void getResponseNameTest() {
        String componentViewPath = "components/componentId/GET/bds_component_detail_4_json/response-specification.json";
        String projectVersionViewPath = "projects/projectId/versions/projectVersionId/GET/bds_project_detail_5_json/response-specification.json";
        String componentsViewPath = "components/GET/bds_component_detail_4_json/response-specification.json";

        Assert.assertTrue(nameParser.getResponseName(componentViewPath).equals("ComponentViewV4"));
        Assert.assertTrue(NameParser.getNonVersionedName(nameParser.getResponseName(projectVersionViewPath)).equals("ProjectVersionView"));
        Assert.assertTrue(nameParser.getResponseName(componentsViewPath).equals("ComponentsViewV4"));
    }
}
