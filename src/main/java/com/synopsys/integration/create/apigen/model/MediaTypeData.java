package com.synopsys.integration.create.apigen.model;

import java.util.List;

public class MediaTypeData {
    private List<String> mediaTypeConstants;
    private List<String> mediaTypePaths;
    private List<MediaTypeDefinition> constantsMapping;

    public MediaTypeData(final List<String> mediaTypeConstants, final List<String> mediaTypePaths, final List<MediaTypeDefinition> constantsMapping) {
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

    public List<MediaTypeDefinition> getConstantsMapping() {
        return constantsMapping;
    }
}
