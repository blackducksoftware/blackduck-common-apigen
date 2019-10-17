package com.synopsys.integration.create.apigen.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.parser.NameParser;

@Component
public class MediaVersions {

    private static final Map<String, MediaVersionData> latestViewMediaVersions = new HashMap<>();
    private static final Map<String, MediaVersionData> latestComponentMediaVersions = new HashMap<>();

    public Map<String, MediaVersionData> getLatestViewMediaVersions() {
        return latestViewMediaVersions;
    }

    public Map<String, MediaVersionData> getLatestComponentMediaVersions() {
        return latestComponentMediaVersions;
    }

    public static void updateLatestViewMediaVersions(final String className, final Map<String, Object> input, final String mediaType) {
        updateLatestMediaVersions(className, input, latestViewMediaVersions, mediaType);
    }

    public static void updateLatestComponentMediaVersions(final String className, final Map<String, Object> input, final String mediaType) {
        updateLatestMediaVersions(className, input, latestComponentMediaVersions, mediaType);
    }

    public static void updateLatestMediaVersions(final String className, final Map<String, Object> input, final Map<String, MediaVersionData> latestMediaVersions, final String mediaType) {
        final MediaVersionData newHelper = getMediaVersionHelper(className, input, mediaType);
        if (newHelper == null) {
            return;
        }
        final String nonVersionedClass = newHelper.getNonVersionedClassName();
        final Integer mediaVersion = newHelper.getMediaVersion();
        final MediaVersionData oldHelper = latestMediaVersions.get(nonVersionedClass);

        if (mediaVersion != null) {
            if (oldHelper == null || mediaVersion > oldHelper.getMediaVersion()) {
                latestMediaVersions.put(nonVersionedClass, newHelper);
            }
        }
    }

    private static MediaVersionData getMediaVersionHelper(final String className, final Map<String, Object> input, final String mediaType) {
        final Integer mediaVersion;
        final String nonVersionedClassName;
        try {
            nonVersionedClassName = NameParser.getNonVersionedName(className);
            mediaVersion = Integer.decode(NameParser.getMediaVersion(className));
            input.put("className", className);

            return new MediaVersionData(nonVersionedClassName, mediaVersion, input, mediaType);
        } catch (final NullPointerException e) {
            return null;
        }
    }
}
