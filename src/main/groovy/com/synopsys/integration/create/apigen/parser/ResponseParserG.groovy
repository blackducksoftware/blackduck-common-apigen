/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.parser


import com.synopsys.integration.create.apigen.model.ResponseDefinition
import com.synopsys.integration.create.apigen.ApplicationG
import groovy.json.JsonSlurper

class ResponseParserG {
    Set<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        def endpointsPath = new File(specificationRootDirectory, 'endpoints')
        def apiPath = new File(endpointsPath, 'api')
        JsonSlurper
        Set<ResponseDefinition> responseDefinitions = new HashSet<>()
        populateResponses(responseDefinitions, apiPath, endpointsPath.absolutePath.length() + 1)
    }

    private void populateResponses(def responses, def parent, int prefixLength) {
        parent.eachFile { child ->
            if ('response-specification.json' == child.name && parent.absolutePath.contains(ApplicationG.RESPONSE_TOKEN)) {
                def responseRelativePath = parent.absolutePath.substring(prefixLength)
                responses.add(responseRelativePath)
            } else if (child.isDirectory()) {
                populateResponses(responses, child, prefixLength)
            }
        }
    }

    private String getResponseName(String responsePath) {
        List<String> resourceNamePieces = new ArrayList()
        responsePath.split('\\\\').each { piece ->
            if (piece.endsWith('Id')) {
                String pieceToAdd = piece[0..-3].capitalize()
                if (resourceNamePieces.size() > 0 && pieceToAdd.startsWith(resourceNamePieces.last())) {
                    pieceToAdd = pieceToAdd.substring(resourceNamePieces.last().length())
                }
                resourceNamePieces.add(pieceToAdd)
            }
        }

        if (resourceNamePieces.size() > 0) {
            return resourceNamePieces.join('') + 'View'
        } else {
            return ''
        }
    }

}
