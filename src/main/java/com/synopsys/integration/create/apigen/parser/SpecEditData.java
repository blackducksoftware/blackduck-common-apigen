package com.synopsys.integration.create.apigen.parser;

import java.util.List;
import java.util.Map;

import com.synopsys.integration.create.apigen.model.FieldDefinition;

public class SpecEditData {

    /*
        Get to a file
        Get to field in a file
        Edits you'd want to make:
            Add a subfield
            Remove a subfield
            Edit attribute of a field
     */

    private final List<String> pathToField;
    // attribute, replacementValue
    private final Map<String, String> attributeEdits;
    private final List<FieldDefinition> fieldsToAdd;
    private final List<FieldDefinition> fieldsToRemove;

    public SpecEditData(final List<String> pathToField, final Map<String, String> attributeEdits, final List<FieldDefinition> fieldsToAdd, final List<FieldDefinition> fieldsToRemove) {
        this.pathToField = pathToField;
        this.attributeEdits = attributeEdits;
        this.fieldsToAdd = fieldsToAdd;
        this.fieldsToRemove = fieldsToRemove;
    }

    public List<String> getPathToField() {
        return pathToField;
    }

    public Map<String, String> getAttributeEdits() {
        return attributeEdits;
    }

    public List<FieldDefinition> getFieldsToAdd() {
        return fieldsToAdd;
    }

    public List<FieldDefinition> getFieldsToRemove() {
        return fieldsToRemove;
    }
}
