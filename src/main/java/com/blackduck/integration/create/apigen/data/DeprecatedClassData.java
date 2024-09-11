/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data;

import java.util.Map;

import freemarker.template.Template;

public class DeprecatedClassData {

    private final String oldName;
    private final String apigenName;
    private final Template template;
    private final Map<String, Object> input;
    private final String destination;

    public DeprecatedClassData(final String oldName, final String apigenName, final Template template, final Map<String, Object> input, final String destination) {
        this.oldName = oldName;
        this.apigenName = apigenName;
        this.template = template;
        this.input = input;
        this.destination = destination;
    }

    public String getOldName() {
        return oldName;
    }

    public String getApigenName() {
        return apigenName;
    }

    public Template getTemplate() {
        return template;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public String getDestination() {
        return destination;
    }
}
