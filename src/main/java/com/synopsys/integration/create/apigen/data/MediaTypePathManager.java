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
import com.synopsys.integration.create.apigen.model.RequestDefinition;

@Component
public class MediaTypePathManager {
    public static final String UUID_REGEX = "\\\\b[a-f0-9]{8}\\\\b-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-\\\\b[a-f0-9]{12}\\\\b";
    private final Map<String, MediaTypeData> mediaTypeMappings;
    private final Set<String> ignoredSegments;

    public MediaTypePathManager() {
        mediaTypeMappings = new LinkedHashMap<>();
        ignoredSegments = populatePathSegmentsToIgnore();
        addMissingPaths();
    }

    private void addMissingPaths() {
        String pathRegex = String.format("/api/codelocations/%s/scan-summaries", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.scan-4+json"));

        pathRegex = String.format("/api/projects/%s/project-mappings", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.project-detail-4+json"));

        pathRegex = String.format("/api/projects/%s/versions/%s/code-locations", UUID_REGEX, UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.internal-1+json"));

        pathRegex = String.format("/api/projects/%s/versions/%s/license-reports", UUID_REGEX, UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.report-4+json"));

        pathRegex = String.format("/api/projects/%s/versions/%s/issues", UUID_REGEX, UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/json"));

        pathRegex = String.format("/api/roles/%s/users", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.user-4+json"));

        pathRegex = String.format("/api/usergroups/%s/users", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.user-4+json"));

        pathRegex = String.format("/api/users/%s/inherited-roles", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.user-4+json"));

        pathRegex = String.format("/api/users/%s/notifications", UUID_REGEX);
        mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, "application/vnd.blackducksoftware.notification-4+json"));

    }

    public void addMapping(RequestDefinition requestDefinition) {
        String pathPattern = createPathRegex(requestDefinition.getResponseSpecificationPath());
        if (StringUtils.isNotBlank(pathPattern)) {
            String pathRegex = "/api/" + pathPattern;
            mediaTypeMappings.put(pathRegex, new MediaTypeData(pathRegex, requestDefinition.getMediaType()));
        }
    }

    public List<MediaTypeData> getMediaTypeMappings() {
        List<MediaTypeData> mediatTypes = mediaTypeMappings.values().stream()
                                              .sorted(Comparator.comparing(MediaTypeData::getPathRegex))
                                              .collect(Collectors.toList());
        return mediatTypes;
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
                        pathExpression.append(UUID_REGEX);
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
