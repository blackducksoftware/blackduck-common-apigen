/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data.mediatype;

import com.blackduck.integration.create.apigen.exception.NullMediaTypeException;
import com.blackduck.integration.create.apigen.model.MediaTypeData;
import com.blackduck.integration.create.apigen.model.MediaTypeDefinition;
import com.blackduck.integration.create.apigen.model.ResponseDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MediaTypePathManager {
    public static final String UUID_REGEX = "\\\\b[a-f0-9]{8}\\\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\\\b[a-f0-9]{12}\\\\b";
    public static final String UUID_TOKEN = "\" + UUID_REGEX + \"";

    public static final Set<MediaTypeDefinition> PATH_OVERRIDES = new HashSet<>(Arrays.asList(ExtraMediaTypeDefinitions.COMPONENTS_LICENSE, ExtraMediaTypeDefinitions.LICENSE));
    public static final Set<String> PATH_REGEX_OVERRIDES = PATH_OVERRIDES.stream().map(MediaTypeDefinition::getPathRegex).collect(Collectors.toSet());

    private final Map<String, MediaTypeDefinition> mediaTypeMappings;
    private final Set<String> ignoredSegments;
    private final Set<String> uniqueMediaTypes;
    private final Set<String> uniquePaths;
    private final Map<String, String> mediaTypeOverrides;

    public MediaTypePathManager() {
        mediaTypeMappings = new LinkedHashMap<>();
        uniqueMediaTypes = new HashSet<>();
        uniquePaths = new HashSet<>();
        ignoredSegments = populatePathSegmentsToIgnore();
        mediaTypeOverrides = populateMediaTypeOverrides();

        addMissingMappings();

        PATH_OVERRIDES.stream().forEach(this::addMapping);
    }

    public void addMapping(ResponseDefinition responseDefinition) throws NullMediaTypeException {
        String pathPattern = createPathRegex(responseDefinition.getResponseSpecificationPath());
        if (StringUtils.isNotBlank(pathPattern)) {
            String mediaType = responseDefinition.getMediaType();

            mediaType = Optional.ofNullable(mediaTypeOverrides.get(mediaType)).orElse(mediaType);

            if (mediaType == null) {
                throw new NullMediaTypeException(pathPattern);
            }
            String pathRegex = "/api/" + pathPattern;

            MediaTypeDefinition mediaTypeDefinition = new MediaTypeDefinition(pathRegex, mediaType);
            addMapping(mediaTypeDefinition);
        }
    }

    public List<MediaTypeDefinition> getMediaTypeMappings() {
        List<MediaTypeDefinition> mediaTypes = mediaTypeMappings.values().stream()
                                                   .sorted(Comparator.comparing(MediaTypeDefinition::getPathRegex))
                                                    .collect(Collectors.toList());
        return mediaTypes;
    }

    public MediaTypeData getMediaTypeData() {
        List<String> mediaTypeConstants = uniqueMediaTypes.stream()
                                              .sorted()
                                              .map(MediaTypePathUtility::generateMediaTypeStatic)
                                              .filter(variable -> !variable.startsWith("JSON"))
                                              .collect(Collectors.toList());
        List<String> mediaTypePaths = uniquePaths.stream()
                                          .sorted()
                                          .map(MediaTypePathUtility::generatePathStatic)
                                          .collect(Collectors.toList());
        List<MediaTypeDefinition> sortedDefintions = mediaTypeMappings.values().stream()
                                                         .sorted(Comparator.comparing(MediaTypeDefinition::getPathRegex))
                                                         .collect(Collectors.toList());

        return new MediaTypeData(mediaTypeConstants, mediaTypePaths, sortedDefintions);
    }

    private void addMissingMappings() {
        //TODO May need to update these for each version of the API.  These are not currently in the API spec that we traverse.
        addMapping(ExtraMediaTypeDefinitions.CODE_LOCATIONS);
        addMapping(ExtraMediaTypeDefinitions.LICENSE_REPORTS);
        addMapping(ExtraMediaTypeDefinitions.USERS);
        addMapping(ExtraMediaTypeDefinitions.NOTIFICATIONS);
    }

    private void addMapping(MediaTypeDefinition mediaTypeDefinition) {
        uniqueMediaTypes.add(mediaTypeDefinition.mediaType);
        uniquePaths.add(mediaTypeDefinition.pathRegex);
        String pathConstant = MediaTypePathUtility.generatePathConstant(mediaTypeDefinition.pathRegex);
        String mediaTypeConstant = MediaTypePathUtility.generateMediaTypeConstant(mediaTypeDefinition.mediaType);
        if ("JSON".equals(mediaTypeConstant)) {
            mediaTypeConstant = "DEFAULT_MEDIA_TYPE";
        }

        if (!mediaTypeMappings.containsKey(mediaTypeDefinition.pathRegex) || !PATH_REGEX_OVERRIDES.contains(mediaTypeDefinition.pathRegex)) {
            mediaTypeMappings.put(mediaTypeDefinition.pathRegex, new MediaTypeDefinition(pathConstant, mediaTypeConstant));
        }
    }

    private String createPathRegex(String apiResponsePath) {
        String[] pathPieces = apiResponsePath.split("/");
        int count = pathPieces.length;
        StringBuilder pathExpression = new StringBuilder(count);
        for (int index = 0; index < count; index++) {
            String segment = pathPieces[index];
            if (segment.contains(".")) {
                String subSegment = StringUtils.replace(segment, ".", "/");
                String subExpresssion = createPathRegex(subSegment);
                if (StringUtils.isNotBlank(subExpresssion)) {
                    pathExpression.append(subExpresssion);
                }
            } else {
                if (!ignoredSegments.contains(segment)) {
                    if (StringUtils.endsWith(segment, "Id")) {
                        pathExpression.append("%s");
                    } else {
                        pathExpression.append(segment);
                    }
                    if (index <= count - 1) {
                        pathExpression.append("/");
                    }
                } else {
                    break;
                }
            }
        }
        return StringUtils.removeEnd(pathExpression.toString(), "/");
    }

    private Set<String> populatePathSegmentsToIgnore() {
        final Set<String> segmentsToIgnore = new HashSet<>();
        segmentsToIgnore.add("GET");
        segmentsToIgnore.add("POST");
        segmentsToIgnore.add("PUT");
        segmentsToIgnore.add("DELETE");

        return segmentsToIgnore;
    }

    private Map<String, String> populateMediaTypeOverrides() {
        Map<String, String> mediaTypeOverrides = new HashMap<>();

        mediaTypeOverrides.put("application/vnd.blackducksoftware.scan-5+json", "application/vnd.blackducksoftware.scan-4+json");

        return mediaTypeOverrides;
    }

}
