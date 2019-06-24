package com.synopsys.integration.create.apigen.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

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
    public static final String ARRAY = "Array";
    public static final String DATA = "data";
    public static final String NUMBER = "Number";
    public static final String BIG_DECIMAL = "BigDecimal";
    public static final String OBJECT = "Object";
    private Gson gson;

    public FieldsParser(Gson gson) {
        this.gson = gson;
    }

    public static void main(String[] args) throws IOException {

    }

    public List<FieldDefinition> parseFieldDefinitions(String fieldDefinitionName, List<RawFieldDefinition> fields) {
        List<FieldDefinition> fieldDefinitions = new ArrayList<>();

        for (RawFieldDefinition field : fields) {
            String path = field.getPath();
            String type = field.getType();
            boolean optional = field.isOptional();

            FieldDefinition fieldDefinition = null;

            // Ignore 'data' fields
            if (path.equals(DATA)) {
                continue;
            }

            // Strip brackets on array field
            if (type.equals(ARRAY)) {
                // Strip plural suffix ('es' or 's') from Array types
                path = path.contains("statuses") ? path.replace("es[]", "") : path.replace("s[]", "");
            } else {
                // Preserve plural suffix for Objects of non-Array types
                path = path.replace("[]", "");
            }

            // Deal with fields of type 'Number'
            if (type.equals(NUMBER)) {
                type = BIG_DECIMAL;
            }

            // If field has subfields, recursively parse and link its subfields
            List<RawFieldDefinition> rawSubFields = field.getSubFields();
            if ((type.equals(OBJECT) || type.equals(ARRAY)) && rawSubFields != null) {
                // append subclass to create new field definition type
                type = fieldDefinitionName + StringUtils.capitalize(path);
                fieldDefinition = new FieldDefinition(path, type, optional);
                fieldDefinition.addSubFields(parseFieldDefinitions(type, field.getSubFields()));
            }

            // If field is not another object, just add it to list of subfields
            if (fieldDefinition == null) {
                fieldDefinition = new FieldDefinition(path, type, optional);
            }

            // If field has allowedValues field, we need to define an Enum before linking it to the field
            List<String> allowedValues = field.getAllowedValues();
            if (allowedValues != null) {
                String nameOfEnum = fieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + "Enum";
                fieldDefinition.addFieldEnum(nameOfEnum, createEnum(nameOfEnum, allowedValues.toString()));
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

    public List<RawFieldDefinition> getFieldsAsRawFieldDefinitions(Gson gson, File file) {
        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject json = gson.fromJson(jsonText, JsonObject.class);
        List<RawFieldDefinition> rawFieldDefinitions = new ArrayList<>();
        if (json != null && json.has("fields")) {
            for (JsonElement jsonElement : json.getAsJsonArray("fields")) {
                RawFieldDefinition rawFieldDefinition = gson.fromJson(jsonElement, RawFieldDefinition.class);
                rawFieldDefinitions.add(rawFieldDefinition);
            }
        }
        return rawFieldDefinitions;
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
