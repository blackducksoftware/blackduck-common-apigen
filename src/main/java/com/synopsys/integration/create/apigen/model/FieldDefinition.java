package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.util.Stringable;

import java.util.*;

public class FieldDefinition extends Stringable {
    private final String path;
    private final String type;
    private final boolean optional;
    private final List<String> allowedValues;
    private final List<FieldDefinition> subFields;
    private final Map<String, String[]> fieldEnums;

    public FieldDefinition(String path, String type, boolean optional, List<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.subFields = new ArrayList<>();
        this.fieldEnums = new HashMap<>();
    }

    public FieldDefinition(String path, String type, boolean optional) {
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

    public List<FieldDefinition> getSubFields() { return subFields; }

    public void addSubFields(List<FieldDefinition> fields) { subFields.addAll(fields); }

    public Map<String, String[]> getFieldEnums() { return fieldEnums; }

    public void addFieldEnum(String name, String[] fieldEnum) { fieldEnums.put(name, fieldEnum); }

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
