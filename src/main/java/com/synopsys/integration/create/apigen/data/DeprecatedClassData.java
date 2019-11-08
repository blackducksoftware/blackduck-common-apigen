package com.synopsys.integration.create.apigen.data;

import java.util.Map;

import freemarker.template.Template;

public class DeprecatedClassData {

    private final String swaggerName;
    private final String apigenName;
    private final Template template;
    private final Map<String, Object> input;
    private final String destination;

    public DeprecatedClassData(final String swaggerName, final String apigenName, final Template template, final Map<String, Object> input, final String destination) {
        this.swaggerName = swaggerName;
        this.apigenName = apigenName;
        this.template = template;
        this.input = input;
        this.destination = destination;
    }

    public String getSwaggerName() {
        return swaggerName;
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
