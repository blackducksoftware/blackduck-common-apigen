package com.synopsys.integration.create.apigen.parser

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.synopsys.integration.create.apigen.model.FieldDefinition

class FieldsParser {
    private Gson gson

    FieldsParser(Gson gson) {
        this.gson = gson
    }

    static void main(String[] args) {
        Gson gson = new Gson()

        String definitionPath = 'C:\\source\\blackduck-common-apigen\\src\\main\\resources\\api-specification\\2019.6.0\\endpoints\\api\\projects\\projectId\\GET\\bds_project_detail_4_json\\response-specification.json'
        File definitionFile = new File(definitionPath)
        JsonObject definition = gson.fromJson(definitionFile.text, JsonObject.class)
        JsonArray fields = definition.getAsJsonArray('fields')

        FieldsParser fieldsParser = new FieldsParser(gson)

        Map<String, FieldDefinition> fieldDefinitions = new HashMap<>()
        fieldsParser.populateFieldDefinitions(fieldDefinitions, 'ProjectView', fields)

        fieldDefinitions.each { k, v ->
            println "${k} => ${v}"
        }
    }

    Map<String, FieldDefinition> parseFields(String fieldDefinitionName, File definitionFile) {
        Map<String, FieldDefinition> fieldDefinitions = {}

        populateFieldDefinitions(fieldDefinitions, fieldDefinitionName, definitionFile)

        fieldDefinitions
    }

    void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, String fieldDefinitionName, File definitionFile) {
        JsonObject definition = gson.fromJson(definitionFile.text, JsonObject.class)
        JsonArray fields = definition.getAsJsonArray('fields')

        populateFieldDefinitions(fieldDefinitions, fieldDefinitionName, fields)
    }

    void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, String fieldDefinitionName, JsonArray fields) {
        if (fieldDefinitions.containsKey(fieldDefinitionName)) {
            return
        } else {
            fieldDefinitions[fieldDefinitionName] = []
        }

        for (JsonElement field : fields) {
            JsonObject fieldObject = field.asJsonObject
            String path = fieldObject.get('path').asString
            String type = fieldObject.get('type').asString
            boolean optional = fieldObject.get('optional').asBoolean

            // this field is a new field definition itself
            if ('Object' == type && fieldObject.has('fields')) {
                type = fieldDefinitionName + path.capitalize()
                populateFieldDefinitions(fieldDefinitions, type, fieldObject.getAsJsonArray('fields'))
            }

            FieldDefinition fieldDefinition = new FieldDefinition()
            fieldDefinition.path = path
            fieldDefinition.type = type
            fieldDefinition.optional = optional

            fieldDefinitions[fieldDefinitionName].add(fieldDefinition)
        }
    }

}
