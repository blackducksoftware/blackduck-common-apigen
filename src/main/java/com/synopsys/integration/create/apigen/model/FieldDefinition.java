/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

public class FieldDefinition extends Definition {
    private final String path;
    private String type;
    private final boolean optional;
    @Nullable
    private final Set<String> allowedValues;
    private final Set<FieldDefinition> fields;
    private boolean typeWasOverrided;

    public FieldDefinition(final String path, final String type, final boolean optional, @Nullable final Set<String> allowedValues, boolean typeWasOverrided) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new HashSet<>();
        this.typeWasOverrided = typeWasOverrided;
    }

    // Constructor to effectively clone a FieldDefinition
    public FieldDefinition(FieldDefinition fieldDefinition) {
        this.path = fieldDefinition.getPath();
        this.type = fieldDefinition.getType();
        this.optional = fieldDefinition.isOptional();
        this.allowedValues = fieldDefinition.getAllowedValues();
        this.fields = fieldDefinition.getSubFields();
    }

    public FieldDefinition(final String path, final String type, final boolean optional, boolean typeWasOverrided) {
        this(path, type, optional, Collections.emptySet(), typeWasOverrided);
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public void setType(final String newType) { this.type = newType; }

    public boolean isOptional() {
        return optional;
    }

    public Set<String> getAllowedValues() {
        return allowedValues;
    }

    public Set<FieldDefinition> getSubFields() { return fields; }

    public boolean typeWasOverrided() {
        return typeWasOverrided;
    }

    public void addSubFields(final Set<FieldDefinition> subFields) { fields.addAll(subFields); }

    // When comparing constructed FieldDefinition's, we will only consider path and type, as the construction process will have already
    // assigned distinct types to distinct definitions.
    @Override
    public int hashCode() {
        return this.path.length() / 11 * this.type.length();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (obj instanceof FieldDefinition) {
            FieldDefinition field = ((FieldDefinition) obj);
            return field.getPath().equals(this.path) &&
                       field.getType().equals(this.type);
        }
        return false;
    }

}
