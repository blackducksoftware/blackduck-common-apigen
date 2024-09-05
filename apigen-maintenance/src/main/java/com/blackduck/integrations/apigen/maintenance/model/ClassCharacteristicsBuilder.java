package com.blackduck.integrations.apigen.maintenance.model;

import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;


public class ClassCharacteristicsBuilder {
    private final List<Field> fields;
    private final List<String> namePieces;

    private boolean mustHaveAllFields = true;
    private boolean mustHaveAllNamePieces = true;

    public ClassCharacteristicsBuilder() {
        fields = new LinkedList<>();
        namePieces = new LinkedList<>();
    }

    public ClassCharacteristics build() {
        return new ClassCharacteristics(fields, namePieces, mustHaveAllFields, mustHaveAllNamePieces);
    }

    public ClassCharacteristicsBuilder addField(String name) {
        return addField(name, null);
    }

    public ClassCharacteristicsBuilder addField(String name, @Nullable String type) {
        fields.add(new Field(name, type));
        return this;
    }

    public ClassCharacteristicsBuilder addNamePiece(String namePiece) {
        namePieces.add(namePiece);
        return this;
    }

    public ClassCharacteristicsBuilder mustHaveAllFields(boolean mustHaveAllFields) {
        this.mustHaveAllFields = mustHaveAllFields;
        return this;
    }

    public ClassCharacteristicsBuilder mustHaveAllNamePieces(boolean mustHaveAllNamePieces) {
        this.mustHaveAllNamePieces = mustHaveAllNamePieces;
        return this;
    }
}
