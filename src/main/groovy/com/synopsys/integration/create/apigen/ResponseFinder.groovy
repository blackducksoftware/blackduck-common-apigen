package com.synopsys.integration.create.apigen

RESPONSE_TOKEN = 'Id' + File.separator + 'GET' + File.separator

def rootPath = new File('C:\\source\\blackduck-common-apigen\\src\\main\\resources\\api-specification\\2019.6.0')

def endpointsPath = new File(rootPath, 'endpoints')
def apiPath = new File(endpointsPath, 'api')

Set<String> responsePaths = new HashSet<>()
populateResponses(responsePaths, apiPath, endpointsPath.absolutePath.length() + 1)

responsePaths.each {
    println it
}
println ''

Set<String> mediaTypes = MediaTypes.shortNames
Set<String> responseNames = new HashSet<>()
responsePaths.each {
    String responseName = getResponseName(it)
    String mediaType = it.substring(it.lastIndexOf(File.separator) + 1)
    if (responseName) {
        println it
        println responseName
        println mediaType
        println ''
        responseNames.add(responseName)
    }
}

private void populateResponses(def responses, def parent, int prefixLength) {
    parent.eachFile { child ->
        if ('response-specification.json' == child.name && parent.absolutePath.contains(RESPONSE_TOKEN)) {
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
