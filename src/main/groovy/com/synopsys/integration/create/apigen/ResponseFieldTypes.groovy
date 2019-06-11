package com.synopsys.integration.create.apigen

import com.google.gson.Gson
import com.synopsys.integration.create.apigen.model.FieldDefinition
import com.synopsys.integration.create.apigen.parser.FieldsParser
import groovy.json.JsonSlurper

class ResponseFieldTypes {
    public static void main(String[] args) {
        String resourceName = Application.CURRENT_API_SPECIFICATION + '/endpoints/api'
        File apiDirectory = new File(ResponseFieldTypes.class.getResource(resourceName).toURI())

        List<File> responseSpecificationFiles = []
        apiDirectory.eachFileRecurse { file ->
            if ('response-specification.json' == file.name) {
                responseSpecificationFiles.add(file)
            }
        }

        def gson = new Gson()
        def fieldsParser = new FieldsParser(gson)

        Map<String, FieldDefinition> fieldDefinitions = {}
        fieldsParser.populateFieldDefinitions()

        def jsonSlurper = new JsonSlurper()
        responseSpecificationFiles.each { file ->
            def json = jsonSlurper.parse(file)
            if (json && json['fields']) {
                def fields = json['fields']

            }
        }
    }
}
