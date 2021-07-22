/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.generation.generators;

import java.io.IOException;

import com.synopsys.integration.create.apigen.model.FieldDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class ClassGenerator {

    public abstract boolean isApplicable(FieldDefinition field);

    public abstract void generateClass(FieldDefinition field, String responseMediaType, Template template) throws Exception;

    public abstract Template getTemplate(Configuration config) throws IOException;
}
