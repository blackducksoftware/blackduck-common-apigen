/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation.generators;

import com.blackduck.integration.create.apigen.model.FieldDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

public abstract class ClassGenerator {

    public abstract boolean isApplicable(FieldDefinition field);

    public abstract void generateClass(FieldDefinition field, String responseMediaType, Template template) throws Exception;

    public abstract Template getTemplate(Configuration config) throws IOException;
}
