/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.generation.generators;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.ClassTypeEnum;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.generation.FileGenerationData;
import com.synopsys.integration.create.apigen.generation.GeneratorDataManager;
import com.synopsys.integration.create.apigen.generation.finder.FilePathUtil;
import com.synopsys.integration.create.apigen.generation.finder.ImportFinder;
import com.synopsys.integration.create.apigen.generation.finder.InputDataFinder;
import com.synopsys.integration.create.apigen.model.ClassTypeData;
import com.synopsys.integration.create.apigen.model.LinksAndImportsData;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ViewGenerator {
    private final ImportFinder importFinder;
    private final InputDataFinder inputDataFinder;
    private final TypeTranslator typeTranslator;
    private final ClassCategories classCategories;
    private final FilePathUtil filePathUtil;
    private final GeneratorDataManager generatorDataManager;
    private final DeprecatedClassGenerator deprecatedClassGenerator;

    @Autowired
    public ViewGenerator(ImportFinder importFinder, InputDataFinder inputDataFinder, TypeTranslator typeTranslator,
        ClassCategories classCategories, FilePathUtil filePathUtil,
        GeneratorDataManager generatorDataManager, final DeprecatedClassGenerator deprecatedClassGenerator) {
        this.importFinder = importFinder;
        this.inputDataFinder = inputDataFinder;
        this.typeTranslator = typeTranslator;
        this.classCategories = classCategories;
        this.filePathUtil = filePathUtil;
        this.generatorDataManager = generatorDataManager;
        this.deprecatedClassGenerator = deprecatedClassGenerator;
    }

    public void generateClasses(final ResponseDefinition response, final Template template) {
        Set<String> imports = new HashSet<>();
        imports.addAll(importFinder.findFieldImports(response.getFields()));
        final LinksAndImportsData linkAndImportsData = importFinder.findLinkAndImportsData(response);
        imports.addAll(linkAndImportsData.getImports());

        final String responseMediaType = response.getMediaType();
        final String viewName = response.getName();

        final ClassTypeEnum classType = classCategories.computeData(viewName).getType();
        ClassTypeData classTypeData = new ClassTypeData(classType, filePathUtil);
        final Map<String, Object> input = inputDataFinder.getInputData(classTypeData, imports, viewName, response.getFields(), linkAndImportsData.getLinks(), responseMediaType);

        String deprecatedName = typeTranslator.getNameOfDeprecatedEquivalent(viewName);
        if (StringUtils.isNotBlank(deprecatedName)) {
            if (StringUtils.isBlank(typeTranslator.getNameOfDeprecatedEquivalent(deprecatedName))) {
                String pathToDeprecatedFiles = classTypeData.getPathToOutputDirectory().replace(UtilStrings.GENERATED, "generated/deprecated");
                String deprecatedPackage = classTypeData.getPackageName().replace(UtilStrings.GENERATED, "generated.deprecated");
                deprecatedClassGenerator.addDeprecatedClass(deprecatedName, viewName, template, input, pathToDeprecatedFiles, deprecatedPackage);
            }
        }
        String apiGenClassName = typeTranslator.getNewName(viewName);
        if (StringUtils.isNotBlank(apiGenClassName)) {
            input.put(UtilStrings.HAS_NEW_NAME, true);
            input.put(UtilStrings.NEW_NAME, apiGenClassName);
        }
        generatorDataManager.addFileData(new FileGenerationData(viewName, template, input, classTypeData.getPathToOutputDirectory()));
    }

    public Template getTemplate(final Configuration config) throws IOException {
        return config.getTemplate("viewTemplate.ftl");
    }
}
