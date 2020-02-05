package com.synopsys.integration.create.apigen.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.model.MediaTypeData;
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
        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertMediaType("componentMediaType", String.format("/api/components/%s/path", MediaTypePathManager.UUID_REGEX), mediaTypes);
        assertMediaType("projectVersionMediaType", String.format("/api/projects/%s/versions/%s", MediaTypePathManager.UUID_REGEX, MediaTypePathManager.UUID_REGEX), mediaTypes);
    }

    @Test
    public void getOnlyPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("GET", ""));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        assertMediaType("", "", pathManager.getMediaTypeMappings());
    }

    @Test
    public void getInPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("reports.GET", "reportsMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertFalse(mediaTypes.isEmpty());
        assertMediaType("reportsMediaType", "/api/reports", mediaTypes);
    }

    @Test
    public void uuidAndGetPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("reports.reportId.GET", "reportsMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertMediaType("reportsMediaType", "/api/reports/" + MediaTypePathManager.UUID_REGEX, mediaTypes);
    }

    @Test
    public void pathEndsInGETTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<RequestDefinition> RequestDefinitions = new ArrayList<>();
        RequestDefinitions.add(new RequestDefinition("projects/projectId/versions/GET", "projectVersionArrayMediaType"));
        for (RequestDefinition RequestDefinition : RequestDefinitions) {
            pathManager.addMapping(RequestDefinition);
        }

        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertMediaType("projectVersionArrayMediaType", "/api/projects/" + MediaTypePathManager.UUID_REGEX + "/versions", mediaTypes);
    }

    @Test
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
        List<MediaTypeData> mediaTypeMappings = pathManager.getMediaTypeMappings();
        List<MediaTypeMatcher> mediaTypeMatchers = new LinkedList<>();
        for (MediaTypeData mediaTypeData : mediaTypeMappings) {
            mediaTypeMatchers.add(new MediaTypeMatcher(StringUtils.replace(mediaTypeData.getPathRegex(), "\\\\", "\\"), mediaTypeData.getMediaType()));
        }

        String mediaType = mediaTypeMatchers.stream()
                               .filter(matcher -> matcher.getPattern().matcher(path).matches())
                               .map(MediaTypeMatcher::getMediaType)
                               .findFirst()
                               .orElse("");

        assertEquals("projectMediaType", mediaType);
    }

    private void assertMediaType(String expectedMediaType, String expectedRegex, List<MediaTypeData> mediaTypes) {
        String mediaType = mediaTypes.stream()
                               .filter(data -> expectedRegex.equals(data.getPathRegex()))
                               .map(MediaTypeData::getMediaType)
                               .findFirst()
                               .orElse("");

        assertEquals(expectedMediaType, mediaType);
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
