package com.synopsys.integration.create.apigen.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import groovy.json.JsonSlurper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class FieldsParser {
    private Gson gson;

    public FieldsParser(Gson gson) {
        this.gson = gson;
    }

    public static void main(String[] args) throws IOException {
        Gson gson = new Gson();

        String policyDefinitionPath = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/policy-rules/policyRuleId/GET/bds_policy_5_json/response-specification.json";
        String definitionPath = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/projects/projectId/GET/bds_project_detail_4_json/response-specification.json";
        File definitionFile = new File(policyDefinitionPath);
        String jsonText = FileUtils.readFileToString(definitionFile, StandardCharsets.UTF_8);
        JsonObject definition = gson.fromJson(jsonText, JsonObject.class);
        JsonArray fields = definition.getAsJsonArray("fields");

        FieldsParser fieldsParser = new FieldsParser(gson);

        Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<String, List<FieldDefinition>>();
        fieldsParser.populateFieldDefinitions(fieldDefinitions, "PolicyRuleView", fields);

        for (Map.Entry<String, List<FieldDefinition>> field : fieldDefinitions.entrySet())
        {
            System.out.println(field.getKey() + " => " + field.getValue().toString());
        }
    }

    Map<String, List<FieldDefinition>> parseFields(String fieldDefinitionName, File definitionFile) {
        Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<String, List<FieldDefinition>>();

        populateFieldDefinitions(fieldDefinitions, fieldDefinitionName, definitionFile);

        return fieldDefinitions;
    }

    void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, String fieldDefinitionName, File definitionFile) {
        JsonObject definition = gson.fromJson(definitionFile.getAbsolutePath(), JsonObject.class);
        JsonArray fields = definition.getAsJsonArray("fields");

        populateFieldDefinitions(fieldDefinitions, fieldDefinitionName, fields);
    }

    void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, String fieldDefinitionName, JsonArray fields) {
        if (fieldDefinitions.containsKey(fieldDefinitionName)) {
            return;
        } else {
            fieldDefinitions.put(fieldDefinitionName, new ArrayList<>());
        }

        for (JsonElement field : fields) {
            JsonObject fieldObject = field.getAsJsonObject();
            String path = fieldObject.get("path").getAsString();
            String type = fieldObject.get("type").getAsString();
            boolean optional = fieldObject.get("optional").getAsBoolean();

            // this field is a new field definition itself
            if (type.equals("Object") && fieldObject.has("fields")) {
                type = fieldDefinitionName + StringUtils.capitalize(path); // ?
                populateFieldDefinitions(fieldDefinitions, type, fieldObject.getAsJsonArray("fields"));
            }

            FieldDefinition fieldDefinition = new FieldDefinition(path, type, optional);
            fieldDefinitions.get(fieldDefinitionName).add(fieldDefinition);
        }
    }

    public static JsonArray getFieldsAsJsonArray(Gson gson, JsonSlurper jsonSlurper, File file) {

        JsonObject json = gson.fromJson((String) jsonSlurper.parse(file), JsonObject.class);

        if (json != null && json.has("fields")) {
            return json.getAsJsonArray("fields");
        }

        return null;
    }

}
