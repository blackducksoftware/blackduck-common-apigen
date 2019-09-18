package com.synopsys.integration.create.apigen.parser;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.synopsys.integration.create.apigen.definitions.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class FieldDefinitionBuilder {
    private final String path;
    private final String type;
    private final List<String> allowedValues;
    private final String nonVersionedFieldDefinitionName;
    private boolean optional;
    private final boolean isArray;
    private List<FieldDefinition> subFields = Collections.EMPTY_LIST;

    private final MissingFieldsAndLinks MISSING_FIELDS_AND_LINKS = new MissingFieldsAndLinks();

    public FieldDefinitionBuilder(final FieldData fieldData, final List<String> allowedValues) {
        this.path = fieldData.getProcessedPath();
        this.type = fieldData.getProcessedType();
        this.nonVersionedFieldDefinitionName = fieldData.getNonVersionedFieldDefinitionName();
        this.allowedValues = allowedValues;
        this.isArray = fieldData.isArray();
    }

    public FieldDefinition build() {

        FieldDefinition fieldDefinition = null;

        final String nameOfEnum = nonVersionedFieldDefinitionName.replace("View", "") + StringUtils.capitalize(path) + UtilStrings.ENUM;

        // For fields with type 'Array', change type to a Java List<E>
        if (isArray) {
            final String coreType = type.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : type;
            fieldDefinition = allowedValues == null ? new FieldDefinition(path, "java.util.List<" + coreType + ">", optional) : new FieldDefinition(path, "java.util.List<" + nameOfEnum + ">", optional, allowedValues);
        } else {
            if (allowedValues == null) {
                fieldDefinition = new FieldDefinition(path, type, optional);
            } else if (NumberUtils.isCreatable(allowedValues.get(0))) {
                fieldDefinition = new FieldDefinition(path, UtilStrings.INTEGER, optional);
            } else {
                fieldDefinition = new FieldDefinition(path, nameOfEnum, optional, allowedValues);
            }
        }
        fieldDefinition.addSubFields(subFields);
        final List<FieldDefinition> missingFields = MISSING_FIELDS_AND_LINKS.getMissingFields(NameParser.getNonVersionedName(type));
        if (missingFields.size() > 0) {
            fieldDefinition.addSubFields(missingFields);
        }
        return fieldDefinition;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(final boolean optional) {
        this.optional = optional;
    }

    public List<FieldDefinition> getSubFields() {
        return subFields;
    }

    public void setSubFields(final List<FieldDefinition> subFields) {
        this.subFields = subFields;
    }

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public String getNonVersionedFieldDefinitionName() {
        return nonVersionedFieldDefinitionName;
    }
}
