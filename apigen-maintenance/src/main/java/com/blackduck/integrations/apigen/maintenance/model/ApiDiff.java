/*
 * apigen-maintenance
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integrations.apigen.maintenance.model;

import java.util.Set;

public class ApiDiff {

    private Set<String> missingClasses;
    private Set<String> newClasses;

    public ApiDiff(final Set<String> missingClasses, final Set<String> newClasses) {
        this.missingClasses = missingClasses;
        this.newClasses = newClasses;
    }

    public Set<String> getMissingClasses() {
        return missingClasses;
    }

    public Set<String> getNewClasses() {
        return newClasses;
    }
}
