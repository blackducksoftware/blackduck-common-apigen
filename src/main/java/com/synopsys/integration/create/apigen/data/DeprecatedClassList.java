package com.synopsys.integration.create.apigen.data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import freemarker.template.Template;

@Component
public class DeprecatedClassList {
     private final Set<DeprecatedClassData> deprecatedClasses = new HashSet<>();

    public Set<DeprecatedClassData> getDeprecatedClasses() {
        return deprecatedClasses;
    }

    public void addDeprecatedClass(final String swaggerName, final String apigenName, final Template template, final Map<String, Object>input, final String destination) {
        deprecatedClasses.add(new DeprecatedClassData(swaggerName, apigenName, template, input, destination));
    }
}
