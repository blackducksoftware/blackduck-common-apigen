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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.MediaTypes;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.InputDataHelper;
import com.synopsys.integration.create.apigen.helper.LinkHelper;
import com.synopsys.integration.create.apigen.helper.LinksAndImportsHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionHelper;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class ViewGenerator {

    private final ComponentGenerator componentGenerator;
    private final EnumGenerator enumGenerator;
    private final ClassCategories classCategories;
    private final FreeMarkerHelper freeMarkerHelper;
    private final MediaTypes mediaTypes;
    private final ImportHelper importHelper;
    private final InputDataHelper inputDataHelper;
    private final DataManager dataManager;

    @Autowired
    public ViewGenerator(final ComponentGenerator componentGenerator, final EnumGenerator enumGenerator, final ClassCategories classCategories, final FreeMarkerHelper freeMarkerHelper, final MediaTypes mediaTypes, final ImportHelper importHelper,
        final InputDataHelper inputDataHelper, final DataManager dataManager) {
        this.componentGenerator = componentGenerator;
        this.enumGenerator = enumGenerator;
        this.classCategories = classCategories;
        this.freeMarkerHelper = freeMarkerHelper;
        this.mediaTypes = mediaTypes;
        this.importHelper = importHelper;
        this.inputDataHelper = inputDataHelper;
        this.dataManager = dataManager;
    }

    public boolean isApplicable(final ResponseDefinition response) {
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        return (longMediaTypes.contains(response.getMediaType()));
    }

    public void generateClasses(final ResponseDefinition response, final Template viewAndComponentTemplate, final Template enumTemplate) throws Exception {
        Set<String> imports = new HashSet<>();
        importHelper.addFieldImports(imports, response.getFields());
        final LinksAndImportsHelper helper = importHelper.getLinkImports(imports, response);
        imports = helper.getImports();
        final Set<LinkHelper> links = helper.getLinks();

        final String responseMediaType = response.getMediaType();
        final Map<String, Object> input = inputDataHelper.getViewInputData(UtilStrings.GENERATED_VIEW_PACKAGE, imports, response.getName(), UtilStrings.VIEW_BASE_CLASS, response.getFields(), links, responseMediaType);
        final String viewName = response.getName();

        MediaVersionHelper.updateLatestMediaVersions(viewName, input, dataManager.getLatestViewMediaVersions());
        freeMarkerHelper.writeFile(viewName, viewAndComponentTemplate, input, UtilStrings.PATH_TO_VIEW_FILES);

        //final ComponentGenerator componentGenerator = new ComponentGenerator(classCategories, importHelper, freeMarkerHelper, inputDataHelper, dataManager);
        //final EnumGenerator enumGenerator = new EnumGenerator(classCategories, freeMarkerHelper, inputDataHelper);
        for (final FieldDefinition field : response.getFields()) {
            generateClasses(field, componentGenerator, enumGenerator, responseMediaType, viewAndComponentTemplate, enumTemplate);
        }

        dataManager.addNonLinkClassName(viewName);
        dataManager.addNonLinkClassName(NameParser.getNonVersionedName(viewName));
    }

    private void generateClasses(final FieldDefinition field, final ComponentGenerator componentGenerator, final EnumGenerator enumGenerator, final String responseMediaType, final Template viewAndComponentTemplate,
        final Template enumTemplate) throws Exception {
        if (componentGenerator.isApplicable(field)) {
            componentGenerator.generateClass(field, responseMediaType, viewAndComponentTemplate);
        }
        if (enumGenerator.isApplicable(field)) {
            enumGenerator.generateClass(field, responseMediaType, enumTemplate);
        }

        for (final FieldDefinition subField : field.getSubFields()) {
            generateClasses(subField, componentGenerator, enumGenerator, responseMediaType, viewAndComponentTemplate, enumTemplate);
        }
    }
}
