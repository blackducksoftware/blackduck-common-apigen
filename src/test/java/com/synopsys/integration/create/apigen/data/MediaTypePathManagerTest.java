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
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class MediaTypePathManagerTest {

    @Test
    public void mediaTypesTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<ResponseDefinition> responseDefinitions = new ArrayList<>();
        responseDefinitions.add(new ResponseDefinition("components/componentId/path", "", "componentMediaType", false));
        responseDefinitions.add(new ResponseDefinition("projects/projectId/versions/projectVersionId", "", "projectVersionMediaType", false));

        for (ResponseDefinition responseDefinition : responseDefinitions) {
            pathManager.addMapping(responseDefinition);
        }
        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertMediaType("componentMediaType", String.format("/api/components/%s/path", MediaTypePathManager.UUID_REGEX), mediaTypes);
        assertMediaType("projectVersionMediaType", String.format("/api/projects/%s/versions/%s", MediaTypePathManager.UUID_REGEX, MediaTypePathManager.UUID_REGEX), mediaTypes);
    }

    @Test
    public void getOnlyPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<ResponseDefinition> responseDefinitions = new ArrayList<>();
        responseDefinitions.add(new ResponseDefinition("GET", "", "", true));
        for (ResponseDefinition responseDefinition : responseDefinitions) {
            pathManager.addMapping(responseDefinition);
        }

        assertMediaType("", "", pathManager.getMediaTypeMappings());
    }

    @Test
    public void getInPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<ResponseDefinition> responseDefinitions = new ArrayList<>();
        responseDefinitions.add(new ResponseDefinition("reports.GET", "", "reportsMediaType", true));
        for (ResponseDefinition responseDefinition : responseDefinitions) {
            pathManager.addMapping(responseDefinition);
        }

        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertFalse(mediaTypes.isEmpty());
        assertMediaType("reportsMediaType", "/api/reports", mediaTypes);
    }

    @Test
    public void uuidAndGetPathTest() {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<ResponseDefinition> responseDefinitions = new ArrayList<>();
        responseDefinitions.add(new ResponseDefinition("reports.reportId.GET", "", "reportsMediaType", true));
        for (ResponseDefinition responseDefinition : responseDefinitions) {
            pathManager.addMapping(responseDefinition);
        }

        List<MediaTypeData> mediaTypes = pathManager.getMediaTypeMappings();
        assertMediaType("reportsMediaType", "/api/reports/" + MediaTypePathManager.UUID_REGEX, mediaTypes);
    }

    @Test
    public void testPatternMatch() throws MalformedURLException {
        MediaTypePathManager pathManager = new MediaTypePathManager();
        List<ResponseDefinition> responseDefinitions = new ArrayList<>();
        responseDefinitions.add(new ResponseDefinition("projects/projectId/versions", "", "projectVersionArrayMediaType", false));
        responseDefinitions.add(new ResponseDefinition("projects/projectId/versions/versionId", "", "projectVersionMediaType", false));
        responseDefinitions.add(new ResponseDefinition("projects/projectId", "", "projectMediaType", false));
        for (ResponseDefinition responseDefinition : responseDefinitions) {
            pathManager.addMapping(responseDefinition);
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
