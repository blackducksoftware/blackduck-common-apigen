package com.blackduck.integration.create.apigen.parser;

import com.blackduck.integration.create.apigen.data.NameAndPathManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NameParserTest {

    private final NameParser nameParser = new NameParser(new NameAndPathManager());

    @Test
    public void computeResponseNameTest() {
        String componentViewPath = "components/componentId/GET/bds_component_detail_4_json/response-specification.json";
        String projectVersionViewPath = "projects/projectId/versions/projectVersionId/GET/bds_project_detail_5_json/response-specification.json";
        String componentsViewPath = "components/GET/bds_component_detail_4_json/response-specification.json";

        assertTrue(nameParser.computeResponseName(componentViewPath).equals("ComponentView"));
        assertTrue(nameParser.computeResponseName(projectVersionViewPath).equals("ProjectVersionView"));
        assertTrue(nameParser.computeResponseName(componentsViewPath).equals("ComponentsView"));
    }

    @Test
    public void verifyDifferentiatingPrefixesAreAddedTest() {
        String componentVersionPath = "components/componentId/versions/componentVersionId/GET/bds_component_detail_4_json/response-specification.json";
        String result1 = nameParser.computeResponseName(componentVersionPath);
        assertEquals("ComponentVersionView", result1);

        String projectVersionComponentVersionPath = "projects/projectId/versions/projectVersionId/components/componentId/versions/componentVersionId/GET/bds_bill_of_materials_4_json/response-specification.json";
        String result2 = nameParser.computeResponseName(projectVersionComponentVersionPath);
        assertEquals("ProjectVersionComponentVersionView", result2);
    }

}
