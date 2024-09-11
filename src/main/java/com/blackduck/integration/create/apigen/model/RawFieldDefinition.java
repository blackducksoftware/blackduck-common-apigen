/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RawFieldDefinition extends ThirdPartyDefinition {
    private final String path;
    private final String type;
    private final boolean optional;
    private final Set<String> allowedValues;
    private final Set<RawFieldDefinition> fields;
    private final Set<LinkDefinition> links;

    public RawFieldDefinition(final String path, final String type, final boolean optional, final Set<String> allowedValues) {
        this.path = path;
        this.type = type;
        this.optional = optional;
        this.allowedValues = allowedValues;
        this.fields = new HashSet<>();
        this.links = new HashSet<>();
    }

    public RawFieldDefinition(final String path, final String type, final boolean optional) {
        this(path, type, optional, Collections.emptySet());
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

    public Set<String> getAllowedValues() {
        return allowedValues;
    }

    public Set<RawFieldDefinition> getSubFields() { return fields; }

    public void addSubFields(Set<RawFieldDefinition> fields) {
        this.fields.addAll(fields);
    }

    public void addSubField(RawFieldDefinition field) {
        this.fields.add(field);
    }

    public Set<LinkDefinition> getLinks() { return links; }

    @Override
    public String toString() {
        return "path: " + path + " type: " + type + " optional: " + optional + " fields: " + fields;
    }

    @Override
    public int hashCode() {
        int hash = this.path.length() / 11
                       * this.type.length();
        if (this.allowedValues != null) {
            for (String value : this.allowedValues) {
                hash += value.length();
            }
        }
        if (this.fields != null) {
            for (RawFieldDefinition subField : this.fields) {
                hash += subField.hashCode();
            }
        }
        return hash;
    }

    @Override
    public boolean equals(final Object field) {
        if (field == null) {
            return false;
        }

        if (this == field) {
            return true;
        }

        if (field instanceof RawFieldDefinition) {
            RawFieldDefinition rawField = ((RawFieldDefinition) field);
            return rawField.getPath().equals(this.path) &&
                       rawField.getType().equals(this.type) &&
                       ((rawField.getAllowedValues() == null && this.allowedValues == null) || rawField.getAllowedValues().equals(this.allowedValues)) &&
                       (
                           (rawField.getSubFields() == null && this.fields == null) || ((rawField.getSubFields() != null && this.fields != null) &&
                                                                                            rawField.getSubFields().stream()
                                                                                                .filter(it -> !this.fields.contains(it))
                                                                                                .collect(Collectors.toSet())
                                                                                                .isEmpty())
                       );
        }
        return false;
    }

}
