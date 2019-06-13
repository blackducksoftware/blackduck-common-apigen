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
package com.synopsys.integration.create.apigen

import com.google.gson.Gson
import com.synopsys.integration.create.apigen.model.FieldDefinition
import com.synopsys.integration.create.apigen.parser.FieldsParserG
import groovy.json.JsonSlurper

class ResponseFieldTypesG {
    public static void main(String[] args) {
        String resourceName = ApplicationG.CURRENT_API_SPECIFICATION + '/endpoints/api'
        File apiDirectory = new File(ResponseFieldTypesG.class.getResource(resourceName).toURI())

        List<File> responseSpecificationFiles = []
        apiDirectory.eachFileRecurse { file ->
            if ('response-specification.json' == file.name) {
                responseSpecificationFiles.add(file)
            }
        }

        def gson = new Gson()
        def fieldsParser = new FieldsParserG(gson)

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
