package com.synopsys.integration.create.apigen.parser


import com.synopsys.integration.create.apigen.model.ResponseDefinition
import com.synopsys.integration.create.apigen.Application
import groovy.json.JsonSlurper

class ResponseParser {
    Set<ResponseDefinition> parseResponses(File specificationRootDirectory) {
        def endpointsPath = new File(specificationRootDirectory, 'endpoints')
        def apiPath = new File(endpointsPath, 'api')
        JsonSlurper
        Set<ResponseDefinition> responseDefinitions = new HashSet<>()
        populateResponses(responseDefinitions, apiPath, endpointsPath.absolutePath.length() + 1)
    }

    private void populateResponses(def responses, def parent, int prefixLength) {
        parent.eachFile { child ->
            if ('response-specification.json' == child.name && parent.absolutePath.contains(Application.RESPONSE_TOKEN)) {
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
