package com.synopsys.integration.create.apigen.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import org.apache.commons.io.FileUtils;
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

    }

    private List<FieldDefinition> parseFieldDefinitions(String fieldDefinitionName, File definitionFile) {
        JsonObject definition = gson.fromJson(definitionFile.getAbsolutePath(), JsonObject.class);
        JsonArray fields = definition.getAsJsonArray("fields");

        return parseFieldDefinitions(fieldDefinitionName, fields);
    }

    public List<FieldDefinition> parseFieldDefinitions(String fieldDefinitionName, JsonArray fields) {
        List<FieldDefinition> fieldDefinitions = new ArrayList<>();

        for (JsonElement field : fields) {
            JsonObject fieldObject = field.getAsJsonObject();
            String path = fieldObject.get("path").getAsString();
            String type = fieldObject.get("type").getAsString();
            boolean optional = fieldObject.get("optional").getAsBoolean();

            FieldDefinition fieldDefinition = null;

            // Ignore 'data' fields
            if (path.equals("data")) {
                continue;
            }

            // Strip brackets on array field
            if (type.equals("Array")) {
                // Strip plural suffix ('es' or 's') from Array types
                path = path.contains("statuses") ? path.replace("es[]", "") : path.replace("s[]", "");
            } else {
                // Preserve plural suffix for Objects of non-Array types
                path = path.replace("[]", "");
            }

            // Deal with fields of type 'Number'
            if (type.equals("Number")) {
                type = "BigDecimal";
            }

            // If field has subfields, recursively parse and link its subfields
            if ((type.equals("Object") || type.equals("Array")) && fieldObject.has("fields")) {
                // append subclass to create new field definition type
                type = fieldDefinitionName + StringUtils.capitalize(path);
                fieldDefinition = new FieldDefinition(path, type, optional);
                fieldDefinition.addSubFields(parseFieldDefinitions(type, fieldObject.getAsJsonArray("fields")));
            }

            // If field is not another object, just add it to list of subfields
            if (fieldDefinition == null) {
                fieldDefinition = new FieldDefinition(path, type, optional);
            }

            // If field has allowedValues field, we need to define an Enum before linking it to the field
            JsonElement allowedValues = fieldObject.get("allowedValues");
            if (allowedValues != null) {
                String nameOfEnum = fieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + "Enum";
                fieldDefinition.addFieldEnum(nameOfEnum, createEnum(nameOfEnum, allowedValues.toString()));
            }

            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

    public static JsonArray getFieldsAsJsonArray(Gson gson, File file) {
        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject json = gson.fromJson(jsonText, JsonObject.class);

        if (json != null && json.has("fields")) {
            return json.getAsJsonArray("fields");
        }

        return null;
    }

    /* Generate Enum (as of now, an array of Strings) for field that has specified set of allowed values */
    private String[] createEnum(String nameOfEnum, String allowedValues) {
        if (allowedValues.startsWith("[") && allowedValues.endsWith("]")) {
            // strip brackets from list of values
            allowedValues = allowedValues.substring(1, allowedValues.length()-1);
        }
        String[] allowedValuesEnum = allowedValues.split(",");
        Map<String, String[]> newEnum = new HashMap<>();
        newEnum.put(nameOfEnum, allowedValuesEnum);

        return allowedValuesEnum;
    }



}
