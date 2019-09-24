package com.synopsys.integration.create.apigen.helper;

import static com.synopsys.integration.create.apigen.Generator.COMPONENT_BASE_CLASS;
import static com.synopsys.integration.create.apigen.Generator.CORE_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.Generator.GENERATED_CLASS_PATH_PREFIX;
import static com.synopsys.integration.create.apigen.Generator.VIEW_BASE_CLASS;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.synopsys.integration.create.apigen.Generator;
import com.synopsys.integration.create.apigen.ResultClassData;
import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.NameParser;

public class ImportHelper {

    public static final String LINK_RESPONSE = "LinkResponse";
    public static final String LINK_MULTIPLE_RESPONSES = "LinkMultipleResponses";
    public static final String LINK_SINGLE_RESPONSE = "LinkSingleResponse";
    public static final String LINK_STRING_RESPONSE = "LinkStringResponse";

    private final ClassCategories classCategories;

    public ImportHelper(final ClassCategories classCategories) {
        this.classCategories = classCategories;
    }

    public void addFieldImports(final Set<String> imports, final List<FieldDefinition> fields) {
        imports.add(CORE_CLASS_PATH_PREFIX + VIEW_BASE_CLASS);

        for (final FieldDefinition field : fields) {
            addFieldImports(imports, field);
        }
    }

    public void addFieldImports(final Set<String> imports, final FieldDefinition field) {
        imports.add(CORE_CLASS_PATH_PREFIX + COMPONENT_BASE_CLASS);

        String fieldType = field.getType();
        if (fieldType.contains(UtilStrings.LIST)) {
            imports.add(UtilStrings.JAVA_LIST.replace("<", ""));
        }
        fieldType = NameParser.stripListNotation(fieldType);
        fieldType = NameParser.getNonVersionedName(fieldType);
        final String importPathPrefix = classCategories.isThrowaway(fieldType) ? GENERATED_CLASS_PATH_PREFIX.replace("generated", "manual.throwaway.generated") : GENERATED_CLASS_PATH_PREFIX;

        if (fieldType.equals(UtilStrings.BIG_DECIMAL)) {
            imports.add(UtilStrings.JAVA_BIG_DECIMAL);
        }

        if (Generator.isEnum(fieldType)) {
            imports.add(importPathPrefix + UtilStrings.ENUMERATION + "." + fieldType);
        } else if (!classCategories.isCommonType(fieldType)) {
            imports.add(importPathPrefix + UtilStrings.COMPONENT + "." + fieldType);
        }
    }

    public LinksAndImportsHelper getLinkImports(final Set<String> imports, final ResponseDefinition response, final Set<String> linkClassNames, final Map<String, String> nullLinkResultClasses) {
        final List<LinkDefinition> rawLinks = response.getLinks();
        final Set<LinkHelper> links = new HashSet<>();
        final String responseName = response.getName();

        if (!rawLinks.isEmpty()) {
            imports.add(CORE_CLASS_PATH_PREFIX + LINK_RESPONSE);
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkHelper link = new LinkHelper(rawLink.getRel(), responseName);
            try {
                final String resultClass = link.resultClass();
                final String linkType = link.linkType();
                final String linkImportType = getLinkImportType(linkType);

                final String linkImport = CORE_CLASS_PATH_PREFIX + linkImportType;
                imports.add(linkImport);

                if (resultClass != null) {
                    linkClassNames.add(resultClass);

                    final ResultClassData resultClassData = new ResultClassData(resultClass);
                    final String resultImportPath = resultClassData.getResultImportPath();
                    final String resultImportType = resultClassData.getResultImportType();
                    final boolean shouldAddImport = resultClassData.shouldAddImport();

                    if (shouldAddImport) {
                        imports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    nullLinkResultClasses.put(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                nullLinkResultClasses.put(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsHelper(links, imports);
    }

    private String getLinkImportType(final String linkType) {
        if (linkType.contains(LINK_MULTIPLE_RESPONSES)) {
            return LINK_MULTIPLE_RESPONSES;
        } else if (linkType.contains(LINK_SINGLE_RESPONSE)) {
            return LINK_SINGLE_RESPONSE;
        } else {
            return LINK_STRING_RESPONSE;
        }
    }
}
