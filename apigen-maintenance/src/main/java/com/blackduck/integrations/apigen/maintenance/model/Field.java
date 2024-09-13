/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.model;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Field {
    private final String name;
    @Nullable
    private final String type;

    public Field(final String name, @Nullable final String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Optional<String> getType() {
        return Optional.ofNullable(type);
    }
}
