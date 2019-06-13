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

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.synopsys.integration.create.apigen.model.FieldDefinition

class FieldsParserG {
    private Gson gson

    FieldsParserG(Gson gson) {
        this.gson = gson
    }

    static void main(String[] args) {
        Gson gson = new Gson()

        String definitionPath = '/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/projects/projectId/GET/bds_project_detail_4_json/response-specification.json'
        File definitionFile = new File(definitionPath)
        JsonObject definition = gson.fromJson(definitionFile.text, JsonObject.class)
        JsonArray fields = definition.getAsJsonArray('fields')

        FieldsParserG fieldsParser = new FieldsParserG(gson)

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
