/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.model;

import java.util.List;

public class ClassCharacteristics {
    private List<Field> fields;
    private List<String> namePieces;

    private boolean mustHaveAllFields;
    private boolean mustHaveAllNamePieces;

    public ClassCharacteristics(final List<Field> fields, final List<String> namePieces, final boolean mustHaveAllFields, final boolean mustHaveAllNamePieces) {
        this.fields = fields;
        this.namePieces = namePieces;
        this.mustHaveAllFields = mustHaveAllFields;
        this.mustHaveAllNamePieces = mustHaveAllNamePieces;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<String> getNamePieces() {
        return namePieces;
    }

    public boolean mustHaveAllFields() {
        return mustHaveAllFields;
    }

    public boolean mustHaveAllNamePieces() {
        return mustHaveAllNamePieces;
    }
}
