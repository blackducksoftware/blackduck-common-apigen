package com.synopsys.integration.create.apigen.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import groovy.json.JsonSlurper;
import net.minidev.json.JSONObject;
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
        //String definitionPath = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3/endpoints/api/projects/projectId/GET/bds_project_detail_4_json/response-specification.json";
        File definitionFile = new File(policyDefinitionPath);
        String jsonText = FileUtils.readFileToString(definitionFile, StandardCharsets.UTF_8);
        JsonObject definition = gson.fromJson(jsonText, JsonObject.class);
        JsonArray fields = definition.getAsJsonArray("fields");

        FieldsParser fieldsParser = new FieldsParser(gson);

        Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<String, List<FieldDefinition>>();
        Map<String, String[]> fieldEnums = new HashMap<>();
        fieldsParser.populateFieldDefinitions(fieldDefinitions, fieldEnums, "PolicyRuleView", null, fields, 5);

        for (Map.Entry<String, List<FieldDefinition>> field : fieldDefinitions.entrySet())
        {
            // System.out.println(field.getKey() + " => " + field.getValue().toString());
        }
    }

    Map<String, List<FieldDefinition>> parseFields(String fieldDefinitionName, File definitionFile) {
        Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<String, List<FieldDefinition>>();

        populateFieldDefinitions(fieldDefinitions, fieldDefinitionName, definitionFile);

        return fieldDefinitions;
    }

    private void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, String fieldDefinitionName, File definitionFile) {
        JsonObject definition = gson.fromJson(definitionFile.getAbsolutePath(), JsonObject.class);
        JsonArray fields = definition.getAsJsonArray("fields");
        Map<String, String[]> fieldEnums = new HashMap<>();

        populateFieldDefinitions(fieldDefinitions, fieldEnums, fieldDefinitionName, null, fields, 5);
    }

    public void populateFieldDefinitions(Map<String, List<FieldDefinition>> fieldDefinitions, Map<String, String[]> fieldEnums, String fieldDefinitionName, FieldDefinition parentField, JsonArray fields, int spaces) {
        if (fieldDefinitions.containsKey(fieldDefinitionName)) {
            return;
        } else {
            fieldDefinitions.put(fieldDefinitionName, new ArrayList<>());
        }

        // display
        Boolean redundantPrintOfSubClass = false;

        //if (spaces <= 5) System.out.println("");
        //for (int i = 0; i < spaces-5; i++) System.out.print(" ");
        //System.out.println(fieldDefinitionName);

        for (JsonElement field : fields) {
            JsonObject fieldObject = field.getAsJsonObject();
            String path = fieldObject.get("path").getAsString();
            if (path.equals("data")) {
                continue;
            }
            String type = fieldObject.get("type").getAsString();
            boolean optional = fieldObject.get("optional").getAsBoolean();

            if (type.equals("Array")) {
                // strip brackets on array field <-- should we leave 's' for non-array fields?
                path = path.replace("s[]", "");
            } else {
                path = path.replace("[]", "");
            }

            FieldDefinition fieldDefinition = null;

            // this field is a new field definition itself
            if ((type.equals("Object") || type.equals("Array")) && fieldObject.has("fields")) {
                // append subclass to create new field definition type
                type = fieldDefinitionName + StringUtils.capitalize(path);
                fieldDefinition = new FieldDefinition(path, type, optional);
                populateFieldDefinitions(fieldDefinitions, fieldEnums, type, fieldDefinition, fieldObject.getAsJsonArray("fields"), spaces + 5);
                redundantPrintOfSubClass = true;
            }

            if (type.equals("Number")) {
                type = "BigDecimal";
            }

            if (fieldDefinition == null) {
                fieldDefinition = new FieldDefinition(path, type, optional);
                if (parentField != null) {
                    parentField.addSubField(fieldDefinition);
                }
            }

            // If field has allowedValues field, we need to define an enum
            JsonElement allowedValues = fieldObject.get("allowedValues");
            String nameOfEnum = null;
            if (allowedValues != null) {
                nameOfEnum = fieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + "Enum";
                createEnum(fieldEnums, nameOfEnum, allowedValues.toString(), fieldDefinition, spaces);
            }

            // display
            for (int i = 0; i < spaces; i++) System.out.print(" ");
            String fieldPrintOutput = nameOfEnum != null ? "" : path + " : " + type ;
            if (!redundantPrintOfSubClass) {
                //System.out.println(fieldPrintOutput);
            }
            redundantPrintOfSubClass = false;

            fieldDefinitions.get(fieldDefinitionName).add(fieldDefinition);
        }
    }

    public static JsonArray getFieldsAsJsonArray(Gson gson, JsonSlurper jsonSlurper, File file) {

        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject json = gson.fromJson(jsonText, JsonObject.class);

        //JsonObject json = gson.fromJson((String) jsonSlurper.parse(file), JsonObject.class);

        if (json != null && json.has("fields")) {
            return json.getAsJsonArray("fields");
        }

        return null;
    }

    /* Generate Enum (as of now, an array of Strings) for field that has specified set of allowed values */
    private void createEnum(Map<String, String[]> fieldEnums, String nameOfEnum, String allowedValues, FieldDefinition parent, int spaces) {

        if (allowedValues.startsWith("[") && allowedValues.endsWith("]")) allowedValues = allowedValues.substring(1, allowedValues.length()-1); // strip brackets
        String[] allowedValuesEnum = allowedValues.split(",");
        fieldEnums.put(nameOfEnum, allowedValuesEnum);
        parent.addFieldEnum(nameOfEnum, allowedValuesEnum);

        /*
        for (int i = 0; i < spaces; i++) System.out.print(" ");
        System.out.print(nameOfEnum + ": ");
        for (String value: allowedValuesEnum) {
            System.out.print(value + " ");
        }
        */

    }



}
