/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen.generators;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassCategoryData;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.InputDataHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersions;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ComponentGenerator extends ClassGenerator {

    private final ClassCategories classCategories;
    private final ImportHelper importHelper;
    private final FreeMarkerHelper freeMarkerHelper;
    private final InputDataHelper inputDataHelper;
    private final DataManager dataManager;
    private final MediaVersions mediaVersions;

    @Autowired
    public ComponentGenerator(final ClassCategories classCategories, final ImportHelper importHelper, final FreeMarkerHelper freeMarkerHelper, final InputDataHelper inputDataHelper, final DataManager dataManager,
        final MediaVersions mediaVersions) {
        this.classCategories = classCategories;
        this.importHelper = importHelper;
        this.freeMarkerHelper = freeMarkerHelper;
        this.inputDataHelper = inputDataHelper;
        this.dataManager = dataManager;
        this.mediaVersions = mediaVersions;
    }

    @Override
    public boolean isApplicable(final FieldDefinition field) {
        final ClassCategoryData classCategoryData = ClassCategoryData.computeData(field.getType(), classCategories);
        final ClassTypeEnum classType = classCategoryData.getType();
        return classType.isNotCommonTypeOrEnum();
    }

    @Override
    public void generateClass(final FieldDefinition field, final String responseMediaType, final Template template) throws Exception {
        final Set<String> imports = new HashSet<>();
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            importHelper.addFieldImports(imports, subField.getType());
            if (isApplicable(subField)) {
                generateClass(subField, responseMediaType, template);
            }
        }
        final String fieldType = NameParser.stripListNotation(field.getType());
        final Map<String, Object> input = inputDataHelper.getViewInputData(UtilStrings.GENERATED_COMPONENT_PACKAGE, imports, fieldType, UtilStrings.COMPONENT_BASE_CLASS, subFields, responseMediaType);

        MediaVersions.updateLatestComponentMediaVersions(fieldType, input, responseMediaType);

        if (isApplicable(field)) {
            freeMarkerHelper.writeFile(fieldType, template, input, UtilStrings.PATH_TO_COMPONENT_FILES);
        }
        dataManager.addNonLinkClassName(fieldType);
        dataManager.addNonLinkClassName(NameParser.getNonVersionedName(fieldType));
    }

    @Override
    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
