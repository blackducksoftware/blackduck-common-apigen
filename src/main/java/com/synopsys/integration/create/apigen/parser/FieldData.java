package com.synopsys.integration.create.apigen.parser;

import static com.synopsys.integration.create.apigen.parser.FieldsParser.ARRAY;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.BIG_DECIMAL;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.NUMBER;
import static com.synopsys.integration.create.apigen.parser.FieldsParser.OBJECT;

import org.apache.commons.lang3.StringUtils;

import com.synopsys.integration.create.apigen.definitions.TypeTranslator;

public class FieldData {
    private static final TypeTranslator TYPE_TRANSLATOR = new TypeTranslator();

    private final String fieldDefinitionName;
    private final String path;
    private final String type;
    private final boolean hasSubFields;

    public FieldData(final String path, final String type, final String fieldDefinitionName, final boolean hasSubFields) {
        this.fieldDefinitionName = fieldDefinitionName;
        this.path = path;
        this.type = type;
        this.hasSubFields = hasSubFields;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getProcessedPath() {
        final String processedPath;

        if (type.equals(ARRAY)) {
            // Strip plural suffix ('es' or 's') from Array types
            processedPath = path.contains("statuses") ? path.replace("es[]", "") : path.replace("s[]", "");
        } else {
            // Preserve plural suffix for Objects of non-Array types
            processedPath = path.replace("[]", "");
        }

        return processedPath;
    }

    public String getProcessedType() {
        final String mediaVersion = ResponseNameParser.getMediaVersion(fieldDefinitionName);
        final String nonVersionedFieldDefinitionName = ResponseNameParser.getNonVersionedName(fieldDefinitionName);

        // Deal with fields of type 'Number'
        if (type.equals(NUMBER)) {
            return BIG_DECIMAL;
        }

        // Deal with special Swaggerhub - Apigen naming convention conflicts
        final String swaggerName = TYPE_TRANSLATOR.getFieldSwaggerName(nonVersionedFieldDefinitionName, path, type);
        if (swaggerName != null) {
            return swaggerName;
        }

        if ((type.equals(OBJECT) || type.equals(ARRAY)) && hasSubFields) {
            // append subclass to create new field definition type
            final String processedType = fieldDefinitionName + StringUtils.capitalize(path);
            if (mediaVersion != null) {
                return processedType.replace("V" + mediaVersion, "") + "V" + mediaVersion;
            }
            return processedType;
        }
        return type;
    }

    public String getNonVersionedFieldDefinitionName() {
        return ResponseNameParser.getNonVersionedName(fieldDefinitionName);
    }

}
