/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation;

import java.util.Map;

import freemarker.template.Template;

public class FileGenerationData {
    private String className;
    private final Template template;
    private final Map<String, Object> input;
    private final String destination;

    public FileGenerationData(final String className, final Template template, final Map<String, Object> input, final String destination) {
        this.className = className;
        this.template = template;
        this.input = input;
        this.destination = destination;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
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
