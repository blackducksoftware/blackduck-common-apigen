<#include "licenseHeader.ftl">
package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.entity.ContentType;

import com.synopsys.integration.rest.HttpUrl;

<#include "doNotEdit.ftl">
public class BlackDuckMediaTypeDiscovery {
    public static final String DEFAULT_MEDIA_TYPE = ContentType.APPLICATION_JSON.getMimeType();
    public static final String UUID_REGEX = "\\b[a-f0-9]{8}\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\b[a-f0-9]{12}\\b";
    public static final Set<String> VALUES_TO_REPLACE = new HashSet<>(Arrays.asList(null, DEFAULT_MEDIA_TYPE));

    <#list mediaTypeData.getMediaTypeConstants() as mediaTypeConstant>
    public static final String ${mediaTypeConstant};
    </#list>

    <#list mediaTypeData.getMediaTypePaths() as mediaTypePath>
    public static final String ${mediaTypePath};
    </#list>

    private List<MediaTypeMatcher> mediaTypeMatchers = new LinkedList<>();

    // This class contains a list of regular expressions to perform matches on the URL path.
    // A URL path should only match one expression in the list.
    // The expressions are sorted in alphabetical order.
    // The forward slash in the path should be escaped to avoid potential errors.
    // The parts of a path that are variable are the UUIDs for the resources.

    // The string to escape forward slash is: \\/
    // The regex pattern for a UUID is: \\b[a-f0-9]{8}\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\b[a-f0-9]{12}\\b
    public BlackDuckMediaTypeDiscovery() {
    <#list mediaTypeData.getConstantsMapping() as mediaTypeDefinition>
        mediaTypeMatchers.add(new MediaTypeMatcher(${mediaTypeDefinition.getPathRegex()}, ${mediaTypeDefinition.getMediaType()}));
    </#list>
    }

    public String determineMediaType(HttpUrl url, String currentMediaType) {
        if (null != url && VALUES_TO_REPLACE.contains(currentMediaType)) {
            return determineMediaType(url);
        } else {
            return currentMediaType;
        }
    }

    public String determineMediaType(HttpUrl url) {
        String path = url.url().getPath();
        return mediaTypeMatchers.stream()
                   .filter(matcher -> matcher.getPattern().matcher(path).matches())
                   .map(MediaTypeMatcher::getMediaType)
                   .findFirst()
                   .orElse(DEFAULT_MEDIA_TYPE);
    }

    private class MediaTypeMatcher {
        private Pattern pattern;
        private String mediaType;

        public MediaTypeMatcher(final String pattern, final String mediaType) {
            this.pattern = createPattern(pattern);
            this.mediaType = mediaType;
        }

        private Pattern createPattern(String pattern) {
            String regexPattern = pattern.replaceAll("/","\\/");
            return Pattern.compile(regexPattern);
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String getMediaType() {
            return mediaType;
        }
    }

}
