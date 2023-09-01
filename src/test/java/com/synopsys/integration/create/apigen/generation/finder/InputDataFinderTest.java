package com.synopsys.integration.create.apigen.generation.finder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.LinkResponseDefinitions;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.ClassTypeData;
import com.synopsys.integration.create.apigen.model.LinkData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class InputDataFinderTest {

    private final InputDataFinder inputDataFinder = new InputDataFinder();
    private final ClassCategories classCategories = new ClassCategories(new ClassNameManager());

    @Test
    public void getViewInputDataTest() {
        Set<String> imports = new HashSet<>();
        imports.add(UtilStrings.CORE_CLASS_PATH_PREFIX + UtilStrings.VIEW_BASE_CLASS);
        String className = "ProjectView";

        FilePathUtil mockFilePathUtil = Mockito.mock(FilePathUtil.class);
        Mockito.when(mockFilePathUtil.getOutputPathToViewFiles()).thenReturn("");
        ClassTypeData classTypeData = new ClassTypeData(classCategories.computeData(className).getType(), mockFilePathUtil);
        Map<String, Object> inputData1 = inputDataFinder.getInputData(classTypeData, imports, className, new HashSet<>(), "json4");

        assertEquals(8, inputData1.size());
        assertEquals("com.synopsys.integration.blackduck.api.manual.view", inputData1.get(UtilStrings.PACKAGE_NAME));
        assertEquals("ProjectView", inputData1.get(UtilStrings.CLASS_NAME));
        assertNotEquals(null, inputData1.get("imports"));
        assertEquals("json4", inputData1.get(UtilStrings.MEDIA_TYPE));

        Set<LinkData> links = new HashSet<>();
        links.add(new LinkData("label", new ResponseDefinition("", "", "", false), new LinkResponseDefinitions()));
        Map<String, Object> inputData2 = inputDataFinder.getInputData(new ClassTypeData(ClassTypeEnum.COMMON, mockFilePathUtil), new HashSet<>(), "", new HashSet<>(), links, "");

        assertEquals(11, inputData2.size());
    }
}
