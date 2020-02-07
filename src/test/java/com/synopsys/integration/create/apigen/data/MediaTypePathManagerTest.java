package com.synopsys.integration.create.apigen.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.model.MediaTypeData;
import com.synopsys.integration.create.apigen.model.MediaTypeDefinition;
import com.synopsys.integration.create.apigen.model.RequestDefinition;

public class MediaTypePathManagerTest {

    @Test
    public void mediaTypesTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("components/componentId/path", "componentMediaType"));
        RequestDefinitions.add(new RequestDefinition("projects/projectId/versions/projectVersionId", "projectVersionMediaType"));

        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }
        MediaTypeData mediaTypeData = pathManager.getMediaTypeData();
        assertMediaType("/api/components/%s/path", "componentMediaType", mediaTypeData);
        assertMediaType("/api/projects/%s/versions/%s", "projectVersionMediaType", mediaTypeData);
    }

    @Test
    public void getOnlyPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("GET", ""));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        assertEquals(6, pathManager.getMediaTypeData().getConstantsMapping().size(), "Only missing media types should be present");
    }

    @Test
    public void getInPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("reports.GET", "reportsMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        MediaTypeData mediaTypeData = pathManager.getMediaTypeData();
        assertMediaType("/api/reports", "reportsMediaType", mediaTypeData);
    }

    @Test
    public void uuidAndGetPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("reports.reportId.GET", "reportsMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        MediaTypeData mediaTypeData = pathManager.getMediaTypeData();
        assertMediaType("/api/reports/%s", "reportsMediaType", mediaTypeData);
    }

    @Test
    public void pathEndsInGETTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("projects/projectId/versions/GET", "projectVersionArrayMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        MediaTypeData mediaTypeData = pathManager.getMediaTypeData();
        assertMediaType("/api/projects/%s/versions", "projectVersionArrayMediaType", mediaTypeData);
    }

    @Test
    public void mediaTypeDataTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        MediaTypeData data = pathManager.getMediaTypeData();

        assertNotNull(data);
    }

    @Test
    @Disabled
    public void testPatternMatch() throws MalformedURLException {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("projects/projectId/versions", "projectVersionArrayMediaType"));
        RequestDefinitions.add(new RequestDefinition("projects/projectId/versions/versionId", "projectVersionMediaType"));
        RequestDefinitions.add(new RequestDefinition("projects/projectId", "projectMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        String apiUrl = "https://blackduck.example.com/api/projects/8bf90232-025a-480b-aeb0-9be8d0c4c24a?testQuery=test";
        URL url = new URL(apiUrl);
        String path = url.getPath();
        List<MediaTypeDefinition> mediaTypeMappings = pathManager.getMediaTypeMappings();
        List<MediaTypeMatcher> mediaTypeMatchers = new LinkedList<>();
        for (MediaTypeDefinition mediaTypeDefinition : mediaTypeMappings) {
            mediaTypeMatchers.add(new MediaTypeMatcher(StringUtils.replace(mediaTypeDefinition.getPathRegex(), "\\\\", "\\"), mediaTypeDefinition.getMediaType()));
        }

        String mediaType = mediaTypeMatchers.stream()
                               .filter(matcher -> matcher.getPattern().matcher(path).matches())
                               .map(MediaTypeMatcher::getMediaType)
                               .findFirst()
                               .orElse("");

        assertEquals("projectMediaType", mediaType);
    }

    private void assertMediaType(String expectedPath, String expectedMediaType, MediaTypeData mediaTypeData) {
        String pathVariable = MediaTypePathManager.generatePathStatic(expectedPath);
        String mediaTypeVariable = MediaTypePathManager.generateMediaTypeStatic(expectedMediaType);
        MediaTypeDefinition expectedDefinition = new MediaTypeDefinition(MediaTypePathManager.generatePathConstant(expectedPath), MediaTypePathManager.generateMediaTypeConstant(expectedMediaType));

        boolean containsPathVar = mediaTypeData.getMediaTypePaths().stream()
                                      .anyMatch(variable -> variable.contains(pathVariable));

        boolean containsMediaTypeVar = mediaTypeData.getMediaTypeConstants().stream()
                                           .anyMatch(variable -> variable.contains(mediaTypeVariable));

        assertTrue(containsPathVar, String.format("Expected media path constant missing: %s", pathVariable));
        assertTrue(containsMediaTypeVar, String.format("Expecte media type constant missing: %s", mediaTypeVariable));
        assertTrue(mediaTypeData.getConstantsMapping().contains(expectedDefinition), String.format("Expected definition missing: %s", expectedDefinition));
    }

    private class MediaTypeMatcher {
        private Pattern pattern;
        private String mediaType;

        public MediaTypeMatcher(final String pattern, final String mediaType) {
            this.pattern = Pattern.compile(pattern);
            this.mediaType = mediaType;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String getMediaType() {
            return mediaType;
        }
    }
}
