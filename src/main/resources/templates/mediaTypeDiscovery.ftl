package ${package};

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class MediaTypeDiscovery {
public static final String DEFAULT_MEDIA_TYPE = "application/json";
private List
<MediaTypeMatcher> mediaTypeMatchers = new LinkedList<>();

    // This class contains a list of regular expressions to perform matches on the URL path.
    // A URL path should only match one expression in the list.
    // The expressions are sorted in alphabetical order.
    // The forward slash in the path should be escaped to avoid potential errors.
    // The parts of a path that are variable are the UUIDs for the resources.

    // The string to escape forward slash is: \\/
    // The regex pattern for a UUID is: \\b[a-f0-9]{8}\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\b[a-f0-9]{12}\\b
    public MediaTypeDiscovery() {
    <#list mediaTypeExpressions as mediaTypeData>
        mediaTypeMatchers.add(new MediaTypeMatcher("${mediaTypeData.getPathRegex()}", "${mediaTypeData.getMediaType()}"));
    </#list>
    }

    public String determineMediaType(String url) {
    try {
    URL apiUrl = new URL(url);
    String path = apiUrl.getPath();
    return mediaTypeMatchers.stream()
    .filter(matcher -> matcher.getPattern().matcher(path).matches())
    .map(MediaTypeMatcher::getMediaType)
    .findFirst()
    .orElse(DEFAULT_MEDIA_TYPE);
    } catch (MalformedURLException ex) {
    return DEFAULT_MEDIA_TYPE;
    }
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
