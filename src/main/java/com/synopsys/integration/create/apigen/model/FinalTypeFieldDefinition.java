package com.synopsys.integration.create.apigen.model;

import java.util.HashSet;
import java.util.Set;

public class FinalTypeFieldDefinition {

    private String finalType;
    private String path;
    private boolean typeWasOverrided;
    private RawFieldDefinition rawFieldDefinition;
    private Set<FinalTypeFieldDefinition> subFields = new HashSet<>();

    public FinalTypeFieldDefinition(FirstPassFieldDefinition firstPassDefinition) {
        this.finalType = firstPassDefinition.getFirstPassType();
        this.path = firstPassDefinition.getPath();
        this.typeWasOverrided = false;
        this.rawFieldDefinition = firstPassDefinition.getRawFieldDefinition();
        this.subFields = convertSubFieldsToFinalTypeDefinitions(firstPassDefinition.getSubFields());
    }

    private Set<FinalTypeFieldDefinition> convertSubFieldsToFinalTypeDefinitions(Set<FirstPassFieldDefinition> firstPassDefinitions) {
        Set<FinalTypeFieldDefinition> finalTypeFieldDefinitions = new HashSet<>();
        for (FirstPassFieldDefinition firstPassDefinition : firstPassDefinitions) {
            finalTypeFieldDefinitions.add(new FinalTypeFieldDefinition(firstPassDefinition));
            if (firstPassDefinition.getSubFields() != null) {
                finalTypeFieldDefinitions.addAll(convertSubFieldsToFinalTypeDefinitions(firstPassDefinition.getSubFields()));
            }
        }
        return finalTypeFieldDefinitions;
    }

    public String getFinalType() {
        return finalType;
    }

    public void setFinalType(final String finalType) {
        this.finalType = finalType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public boolean isTypeWasOverrided() {
        return typeWasOverrided;
    }

    public void setTypeWasOverrided(final boolean typeWasOverrided) {
        this.typeWasOverrided = typeWasOverrided;
    }

    public RawFieldDefinition getRawFieldDefinition() {
        return rawFieldDefinition;
    }

    public void setRawFieldDefinition(final RawFieldDefinition rawFieldDefinition) {
        this.rawFieldDefinition = rawFieldDefinition;
    }

    public Set<FinalTypeFieldDefinition> getSubFields() {
        return subFields;
    }

    public void addSubFields(final Set<FinalTypeFieldDefinition> subFields) {
        this.subFields.addAll(subFields);
    }
}
