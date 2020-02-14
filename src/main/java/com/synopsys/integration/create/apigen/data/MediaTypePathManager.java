/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.data;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.MediaTypeData;
import com.synopsys.integration.create.apigen.model.MediaTypeDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

@Component
public class MediaTypePathManager {
    public static final String UUID_REGEX = "\\\\b[a-f0-9]{8}\\\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\\\b[a-f0-9]{12}\\\\b";
    public static final String UUID_TOKEN = "\" + UUID_REGEX + \"";
    private final Map<String, MediaTypeDefinition> mediaTypeMappings;
    private final Set<String> ignoredSegments;
    private final Set<String> uniqueMediaTypes;
    private final Set<String> uniquePaths;

    public MediaTypePathManager() {
        mediaTypeMappings = new LinkedHashMap<>();
        uniqueMediaTypes = new HashSet<>();
        uniquePaths = new HashSet<>();
        ignoredSegments = populatePathSegmentsToIgnore();
        addMissingPaths();
    }

    public static String generateMediaTypeStatic(String mediaType) {
        String constantName = generateMediaTypeConstant(mediaType);
        return generateStaticVariable(constantName, mediaType);
    }

    public static String generateMediaTypeConstant(String mediaType) {
        String constantName = StringUtils.remove(mediaType, "application/");
        constantName = StringUtils.replace(constantName, ".", "_");
        constantName = StringUtils.replace(constantName, "-", "_");
        constantName = StringUtils.replace(constantName, "+", "_");
        return constantName.toUpperCase();
    }

    public static String generatePathStatic(String pathRegex) {
        String constantName = generatePathConstant(pathRegex);
        StringBuilder formattedValue = new StringBuilder();
        formattedValue.append("String.format(\"");
        formattedValue.append(pathRegex);
        formattedValue.append("\"");
        int uuidConstantCount = StringUtils.countMatches(pathRegex, "%s");

        if (uuidConstantCount > 0) {
            formattedValue.append(", ");
            for (int index = 0; index < uuidConstantCount; index++) {
                formattedValue.append("UUID_REGEX");
                if (index < uuidConstantCount - 1) {
                    formattedValue.append(", ");
                }
            }
        }
        formattedValue.append(")");

        return String.format("%s = %s", constantName, formattedValue.toString());
    }

    public static String generatePathConstant(String pathRegex) {
        String constantName = StringUtils.replace(pathRegex, "/", "_");
        constantName = StringUtils.replace(constantName, "%s", "");
        constantName = StringUtils.replace(constantName, "__", "_");
        constantName = StringUtils.replace(constantName, "-", "_");
        constantName = StringUtils.replace(constantName, "_", "", 1);
        if (pathRegex.endsWith("%s")) {
            // if path ends with %s then the constant will end with an '_' character so just add W_ID
            constantName = String.format("%sWITH_ID", constantName);
        }
        return constantName.toUpperCase();
    }

    public static String generateStaticVariable(String constantVariable, String value) {
        return String.format("%s = \"%s\"", constantVariable, value);
    }

    private void addMissingPaths() {
        //TODO May need to update that for each version of the API.  These are not currently in the API spec that we traverse.
        String pathRegex = "/api/projects/%s/project-mappings";
        addMapping(pathRegex, "application/vnd.blackducksoftware.project-detail-4+json");

        pathRegex = "/api/projects/%s/versions/%s/code-locations";
        addMapping(pathRegex, "application/vnd.blackducksoftware.internal-1+json");

        pathRegex = "/api/projects/%s/versions/%s/license-reports";
        addMapping(pathRegex, "application/vnd.blackducksoftware.report-4+json");

        pathRegex = "/api/projects/%s/versions/%s/issues";
        addMapping(pathRegex, "application/json");

        pathRegex = "/api/usergroups/%s/users";
        addMapping(pathRegex, "application/vnd.blackducksoftware.user-4+json");

        pathRegex = "/api/users/%s/notifications";
        addMapping(pathRegex, "application/vnd.blackducksoftware.notification-4+json");

    }

    public void addMapping(ResponseDefinition responseDefinition) {
        String pathPattern = createPathRegex(responseDefinition.getResponseSpecificationPath());
        if (StringUtils.isNotBlank(pathPattern)) {
            String mediaType = responseDefinition.getMediaType();
            String pathRegex = "/api/" + pathPattern;
            addMapping(pathRegex, mediaType);
        }
    }

    private void addMapping(String pathRegex, String mediaType) {
        uniqueMediaTypes.add(mediaType);
        uniquePaths.add(pathRegex);
        String pathConstant = generatePathConstant(pathRegex);
        String mediaTypeConstant = generateMediaTypeConstant(mediaType);
        if ("JSON".equals(mediaTypeConstant)) {
            mediaTypeConstant = "DEFAULT_MEDIA_TYPE";
        }
        mediaTypeMappings.put(pathRegex, new MediaTypeDefinition(pathConstant, mediaTypeConstant));
    }

    public List<MediaTypeDefinition> getMediaTypeMappings() {
        List<MediaTypeDefinition> mediatTypes = mediaTypeMappings.values().stream()
                                                    .sorted(Comparator.comparing(MediaTypeDefinition::getPathRegex))
                                                    .collect(Collectors.toList());
        return mediatTypes;
    }

    public MediaTypeData getMediaTypeData() {
        List<String> mediaTypeConstants = uniqueMediaTypes.stream()
                                              .sorted()
                                              .map(MediaTypePathManager::generateMediaTypeStatic)
                                              .filter(variable -> !variable.startsWith("JSON"))
                                              .collect(Collectors.toList());
        List<String> mediaTypePaths = uniquePaths.stream()
                                          .sorted()
                                          .map(MediaTypePathManager::generatePathStatic)
                                          .collect(Collectors.toList());
        List<MediaTypeDefinition> sortedDefintions = mediaTypeMappings.values().stream()
                                                         .sorted(Comparator.comparing(MediaTypeDefinition::getPathRegex))
                                                         .collect(Collectors.toList());

        return new MediaTypeData(mediaTypeConstants, mediaTypePaths, sortedDefintions);
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
}
