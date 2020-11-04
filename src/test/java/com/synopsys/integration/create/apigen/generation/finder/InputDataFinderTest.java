package com.synopsys.integration.create.apigen.generation.finder;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.data.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.LinkData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class InputDataFinderTest {

    private final InputDataFinder inputDataFinder = new InputDataFinder();

    @Test
    public void getViewInputDataTest() {
        Set<String> imports = new HashSet<>();
        imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS);
        Map<String, Object> inputData1 = inputDataFinder.getInputData(UtilStrings.GENERATED_VIEW_PACKAGE, imports, "java.util.List<ProjectViewV4>", UtilStrings.VIEW_BASE_CLASS, new HashSet<>(), new HashSet<>(), "json4");

        assertTrue(inputData1.size() == 8);
        assertTrue(inputData1.get(UtilStrings.PACKAGE_NAME).equals(UtilStrings.GENERATED_VIEW_PACKAGE));
        assertTrue(inputData1.get(UtilStrings.CLASS_NAME).equals("ProjectViewV4"));
        assertTrue(inputData1.get("imports") != null);
        assertTrue(inputData1.get(UtilStrings.MEDIA_TYPE).equals("json4"));

        Set<LinkData> links = new HashSet<>();
        links.add(new LinkData("label", new ResponseDefinition("", "", "", false), new LinkResponseDefinitions()));
        Map<String, Object> inputData2 = inputDataFinder.getInputData("", new HashSet<>(), "", "", new HashSet<>(), links, "");

        assertTrue(inputData2.size() == 11);
    }
}
