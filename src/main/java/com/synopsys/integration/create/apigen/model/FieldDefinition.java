package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synopsys.integration.util.Stringable;

public class FieldDefinition extends Stringable {
    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<FieldDefinition> fields;
    private final Map<String, String[]> fieldEnums;

    public FieldDefinition(final String path, final String type, final boolean optional, final List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new ArrayList<>();
        this.fieldEnums = new HashMap<>();
    }

    public FieldDefinition(final String path, final String type, final boolean optional) {
        this(path, type, optional, Collections.emptyList());
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

    public List<String> getAllowedValues() {
        return allowedValues;
    }

    public List<FieldDefinition> getSubFields() { return fields; }

    public void addSubFields(final List<FieldDefinition> fields) { fields.addAll(fields); }

    public Map<String, String[]> getFieldEnums() { return fieldEnums; }

    public void addFieldEnum(final String name, final String[] fieldEnum) { fieldEnums.put(name, fieldEnum); }

    /* Make the output of Fields indented/reflect dependency hierarchy */

    /*
    public void printFieldDefinition(int spaces) {
        Set<String> commonTypes = new HashSet<>(); {"String", "Array", "Object", "Number", "BigDecimal", "Boolean"});
        for (int i = 0; i < spaces; i++) {
            System.out.print(" ");
        }

        if (fieldEnums.size() > 0) {
            for (Map.Entry<String, String[]> fieldEnum : fieldEnums.entrySet()) {
                System.out.print(fieldEnum.getKey() + ": ");
                for (String value: fieldEnum.getValue()) {
                    System.out.print(value + " ");
                }
            }
            System.out.println("");
        } else {
            if (Arrays.asList(commonTypes).contains(type)) {
                System.out.println(path + " : " + type);
            } else {
                System.out.println(type);
            }
        }

        if (subFields.size() > 0) {
            for (FieldDefinition subField : subFields) {
                if (subField.getSubFields().size() > 0) {
                    subField.printFieldDefinition(spaces + 5);
                } else {
                    for (int i = 0; i < spaces+5; i++) {
                        System.out.print(" ");
                    }
                    System.out.println(subField.getPath() + " : " + subField.getType());
                }
            }
        }
    }
    */
}
