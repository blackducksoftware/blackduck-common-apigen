package com.synopsys.integration.create.apigen.model;

import java.util.List;
import java.util.Map;

public class MediaTypeData {
    private List<String> mediaTypeConstants;
    private List<String> mediaTypePaths;
    private Map<String, String> constantsMapping;

    public MediaTypeData(final List<String> mediaTypeConstants, final List<String> mediaTypePaths, final Map<String, String> constantsMapping) {
        this.mediaTypeConstants = mediaTypeConstants;
        this.mediaTypePaths = mediaTypePaths;
        this.constantsMapping = constantsMapping;
    }

    public List<String> getMediaTypeConstants() {
        return mediaTypeConstants;
    }

    public List<String> getMediaTypePaths() {
        return mediaTypePaths;
    }

    public Map<String, String> getConstantsMapping() {
        return constantsMapping;
    }
}
