package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

public class FieldsParser {
    public static final String ARRAY = "Array";
    public static final String DATA = "data";
    public static final String NUMBER = "Number";
    public static final String BIG_DECIMAL = "BigDecimal";
    public static final String OBJECT = "Object";
    public static final String FIELDS = "fields";
    public static final String STRING = "String";
    //private final Gson gson;

    public FieldsParser() { }

    public static void main(final String[] args) throws IOException {

    }

    public List<FieldDefinition> parseFieldDefinitions(final String fieldDefinitionName, final List<RawFieldDefinition> fields) {
        final List<FieldDefinition> fieldDefinitions = new ArrayList<>();
        final String mediaVersion = ResponseNameParser.getMediaVersion(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = ResponseNameParser.getNonVersionedName(fieldDefinitionName);

        for (final RawFieldDefinition field : fields) {
            String path = field.getPath();
            String type = field.getType();
            final boolean optional = field.isOptional();

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
            final List<RawFieldDefinition> rawSubFields = field.getSubFields();
            if ((type.equals(OBJECT) || type.equals(ARRAY)) && rawSubFields != null) {
                // append subclass to create new field definition type
                type = fieldDefinitionName + StringUtils.capitalize(path);
                if (mediaVersion != null) {
                    type = type.replace("V" + mediaVersion, "") + "V" + mediaVersion;
                }
                fieldDefinition = new FieldDefinition(path, type, optional);
                final List<FieldDefinition> subFields = parseFieldDefinitions(type, field.getSubFields());
                fieldDefinition.addSubFields(subFields);
            }

            // Variables to hold info on potential enum fields
            final List<String> allowedValues = field.getAllowedValues();
            String nameOfEnum = nonVersionedFieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + "Enum";
            if (mediaVersion != null) {
                nameOfEnum = nameOfEnum.replace("V" + mediaVersion, "") + "V" + mediaVersion;
            }

            // If field is not another object, just add it to list of subfields
            if (fieldDefinition == null) {
                // For fields with type 'Array', change type to a Java List<E>
                if (type.equals(ARRAY)) {
                    if (!path.endsWith("s")) {
                        path += "s";
                    } else if (path.endsWith("ss")) {
                        path += "es";
                    }
                    fieldDefinition = allowedValues == null ? new FieldDefinition(path, "java.util.List<" + STRING + ">", optional) : new FieldDefinition(path, "java.util.List<" + nameOfEnum + ">", optional, allowedValues);
                } else {
                    if (allowedValues == null) {
                        fieldDefinition = new FieldDefinition(path, type, optional);
                    } else if (NumberUtils.isCreatable(allowedValues.get(0))) {
                        fieldDefinition = new FieldDefinition(path, "Integer", optional);
                    } else {
                        fieldDefinition = new FieldDefinition(path, nameOfEnum, optional, allowedValues);
                    }
                }
            }
            fieldDefinitions.add(fieldDefinition);
        }
        return fieldDefinitions;
    }

    public List<RawFieldDefinition> getFieldsAsRawFieldDefinitions(final Gson gson, final File file) {
        String jsonText = null;
        try {
            jsonText = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final JsonObject json = gson.fromJson(jsonText, JsonObject.class);
        final List<RawFieldDefinition> rawFieldDefinitions = new ArrayList<>();
        if (json != null && json.has(FIELDS)) {
            for (final JsonElement jsonElement : json.getAsJsonArray(FIELDS)) {
                final RawFieldDefinition rawFieldDefinition = gson.fromJson(jsonElement, RawFieldDefinition.class);
                rawFieldDefinitions.add(rawFieldDefinition);
            }
        }
        return rawFieldDefinitions;
    }

}
