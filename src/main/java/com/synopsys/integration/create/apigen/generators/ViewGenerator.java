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
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.MediaTypes;
import com.synopsys.integration.create.apigen.definitions.UtilStrings;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.InputDataHelper;
import com.synopsys.integration.create.apigen.helper.LinkData;
import com.synopsys.integration.create.apigen.helper.LinksAndImportsHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersions;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ViewGenerator {
    private final FreeMarkerHelper freeMarkerHelper;
    private final MediaTypes mediaTypes;
    private final ImportHelper importHelper;
    private final InputDataHelper inputDataHelper;
    private final DataManager dataManager;
    private final MediaVersions mediaVersions;

    @Autowired
    public ViewGenerator(final FreeMarkerHelper freeMarkerHelper, final MediaTypes mediaTypes, final ImportHelper importHelper,
        final InputDataHelper inputDataHelper, final DataManager dataManager, final MediaVersions mediaVersions) {
        this.freeMarkerHelper = freeMarkerHelper;
        this.mediaTypes = mediaTypes;
        this.importHelper = importHelper;
        this.inputDataHelper = inputDataHelper;
        this.dataManager = dataManager;
        this.mediaVersions = mediaVersions;
    }

    public boolean isApplicable(final ResponseDefinition response) {
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        return (longMediaTypes.contains(response.getMediaType()));
    }

    public void generateClasses(final ResponseDefinition response, final Template template) throws Exception {
        Set<String> imports = new HashSet<>();
        importHelper.addFieldImports(imports, response.getFields());
        final LinksAndImportsHelper helper = importHelper.getLinkImports(imports, response);
        imports = helper.getImports();
        final Set<LinkData> links = helper.getLinks();

        final String responseMediaType = response.getMediaType();
        final Map<String, Object> input = inputDataHelper.getViewInputData(UtilStrings.GENERATED_VIEW_PACKAGE, imports, response.getName(), UtilStrings.VIEW_BASE_CLASS, response.getFields(), links, responseMediaType);
        final String viewName = response.getName();

        MediaVersions.updateLatestViewMediaVersions(viewName, input, responseMediaType);
        freeMarkerHelper.writeFile(viewName, template, input, UtilStrings.PATH_TO_VIEW_FILES);

        dataManager.addNonLinkClassName(viewName);
        dataManager.addNonLinkClassName(NameParser.getNonVersionedName(viewName));
    }

    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
