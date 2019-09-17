package com.synopsys.integration.create.apigen.parser;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.create.apigen.definitions.TypeTranslator;

public class FieldData {
    private static final TypeTranslator TYPE_TRANSLATOR = new TypeTranslator();

    private final String fieldDefinitionName;
    private final String path;
    private final String type;
    private final boolean hasSubFields;
    private final boolean isArray;

    public FieldData(final String path, final String type, final String fieldDefinitionName, final boolean hasSubFields) {
        this.fieldDefinitionName = fieldDefinitionName;
        this.path = path;
        this.type = type;
        this.hasSubFields = hasSubFields;
        this.isArray = type.equals("Array");
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getProcessedPath() {

        return path.replace("[]", "");
    }

    public String getProcessedType() {
        final String mediaVersion = ResponseNameParser.getMediaVersion(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = ResponseNameParser.getNonVersionedName(fieldDefinitionName);

        // Deal with fields of type 'Number'
        if (type.equals("Number")) {
            return "BigDecimal";
        }

        // Deal with special Swaggerhub - Apigen naming convention conflicts
        final String swaggerName = TYPE_TRANSLATOR.getFieldSwaggerName(nonVersionedFieldDefinitionName, path, type);
        if (swaggerName != null) {
            return swaggerName;
        }

        if (fieldDefinitionName.contains("ProjectVersionViewLicenseLicenseV5")) {
            System.out.println(fieldDefinitionName + " path: " + path + ", type: " + type);
        }

        if ((type.equals("Object") || type.equals("Array")) && hasSubFields) {
            // append subclass to create new field definition type
            final String processedType = nonVersionedFieldDefinitionName + StringUtils.capitalize(getProcessedPath());
            if (mediaVersion != null) {
                return processedType + "V" + mediaVersion;
            }
            return processedType;
        }
        return type;
    }

    public String getNonVersionedFieldDefinitionName() {
        return ResponseNameParser.getNonVersionedName(fieldDefinitionName);
    }

    public boolean isArray() {
        return isArray;
    }

}
