package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

import java.util.*;

public class FieldDefinition extends Stringable {
    private String path;
    private String type;
    private boolean optional;
    private List<String> allowedValues;
    private List<FieldDefinition> subFields;
    private Map<String, String[]> fieldEnums;

    public FieldDefinition(String path, String type, boolean optional, List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.subFields = new ArrayList<>();
        this.fieldEnums = new HashMap<>();
    }

    public FieldDefinition(String path, String type, boolean optional) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.subFields = new ArrayList<>();
        this.fieldEnums = new HashMap<>();
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

    public List<FieldDefinition> getSubFields() { return subFields; }

    public void addSubField(FieldDefinition field) { subFields.add(field); }

    public Map<String, String[]> getFieldEnums() { return fieldEnums; }

    public void addFieldEnum(String name, String[] fieldEnum) { fieldEnums.put(name, fieldEnum); }

    public void addFieldEnums(Map<String, String[]> fieldEnums) { fieldEnums.putAll(fieldEnums); }

    /* Make the output of Fields indented/reflect dependency hierarchy */

    public void prettyPrintFieldsAndEnums(int spaces) {

        if (subFields.size() > 0) {
            System.out.println("");

            for (FieldDefinition subField : subFields) {
                for (int i = 0; i < spaces - 5; i++) System.out.print(" ");
                System.out.println(subField.getPath());
                if ((subField.getType().equals("Object") || subField.getType().equals("Array")) && subField.getSubFields().size() > 0) {
                    subField.prettyPrintFieldsAndEnums(spaces + 5);
                }
            }
        }

        if (fieldEnums.size() > 0) {
            for (Map.Entry<String, String[]> fieldEnum : fieldEnums.entrySet()) {
                for (int i = 0; i < spaces-5; i++) System.out.print(" ");
                System.out.print(fieldEnum.getKey() + ": ");
                for (String value: fieldEnum.getValue()) {
                    System.out.print(value + " ");
                }
            }
            System.out.println("\n");
        }
    }
}
