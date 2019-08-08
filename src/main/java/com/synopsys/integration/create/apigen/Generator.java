package com.synopsys.integration.create.apigen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ResponseNameParser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Generator {
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String CORE_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.core.";
    public static final String MANUAL_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.manual.";
    public static final String ENUMERATION = "enumeration";
    public static final String VIEW = "view";
    public static final String ENUM = "Enum";
    public static final String COMPONENT = "component";
    public static final String RESPONSE = "response";
    public static final String GENERATED_ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String GENERATED_VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + VIEW;
    public static final String GENERATED_COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + COMPONENT;
    public static final String GENERATED_RESPONSE_PACKAGE = GENERATED_CLASS_PATH_PREFIX + RESPONSE;
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String RESPONSE_BASE_CLASS = "BlackDuckResponse";
    public static final String JAVA_LIST = "java.util.List<";
    public static final String CLASS_NAME = "className";
    public static String BLACKDUCK_COMMON_API_BASE_DIRECTORY;
    public static final String ENUM_DIRECTORY_SUFFIX = "/enumeration";
    public static final String VIEW_DIRECTORY_SUFFIX = "/view";
    public static final String RESPONSE_DIRECTORY_SUFFIX = "/response";
    public static final String COMPONENT_DIRECTORY_SUFFIX = "/component";

    public static Set<String> NON_LINK_CLASS_NAMES = new HashSet<>();
    public static Set<String> LINK_CLASS_NAMES = new HashSet<>();
    public static List<String> RANDOM_LINK_CLASS_NAMES = new ArrayList<>();
    public static Map<String, String> NULL_LINK_RESULT_CLASSES = new HashMap<>();

    public static Set<String> MEDIA_VERSION_NUMBERS = new HashSet<>();
    private static final Map<String, ViewMediaVersionHelper> LATEST_VIEW_MEDIA_VERSIONS = new HashMap<>();
    private static final LinkResponseDefinitions LINK_RESPONSE_DEFINITIONS = new LinkResponseDefinitions();
    private static final Map<String, Map<String, LinkResponseDefinitions.LinkResponseDefinitionItem>> LINK_RESPONSE_DEFINITIONS_LIST = LINK_RESPONSE_DEFINITIONS.getDefinitions();
    private final ClassCategories CLASS_CATEGORIES = new ClassCategories();

    public static void main(final String[] args) throws Exception {
        final Generator Generator = new Generator();
        final Configuration config = Generator.configureFreeMarker();

        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects(false);

        final Template enumTemplate = config.getTemplate("enumTemplate.ftl");
        Generator.generateEnumFiles(responses, enumTemplate);

        final Template viewTemplate = config.getTemplate("viewTemplate.ftl");
        final Template randomTemplate = config.getTemplate("randomTemplate.ftl");
        Generator.generateViewFiles(responses, viewTemplate, randomTemplate);

        System.out.println("\n******************************\nThere are " + RANDOM_LINK_CLASS_NAMES.size() + " classes that are referenced but have no definition in the API specs: \n");
        for (final String randomClassName : RANDOM_LINK_CLASS_NAMES) {
            System.out.println(randomClassName);
        }

        System.out.println("\n******************************\nThere are " + NULL_LINK_RESULT_CLASSES.size() + " classes that are referenced as link results in API specs but we have no information about what Object they correspond to: \n");
        for (final Map.Entry nullLinkResultClass : NULL_LINK_RESULT_CLASSES.entrySet()) {
            System.out.println(nullLinkResultClass.getKey() + " - " + nullLinkResultClass.getValue());
        }
    }

    // taken from SwaggerHub
    public static File getBaseDirectory() {
        final String baseDirectory = System.getenv(Application.PATH_TO_GENERATED_FILES_KEY);
        if (baseDirectory == null) {
            System.out.println("Please set Environment variable 'BLACKDUCK_COMMON_API_BASE_DIRECTORY' to directory in which generated files will live");
            for (final Map.Entry<String, String> var : System.getenv().entrySet()) {
                System.out.println(var.getKey() + " : " + var.getValue());
            }
            System.exit(0);
        }
        return new File(baseDirectory);
    }

    private Configuration configureFreeMarker() throws URISyntaxException, IOException {
        final Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // Where do we load the templates from:
        final URL templateDirectory = Generator.class.getClassLoader().getResource("templates");
        cfg.setDirectoryForTemplateLoading(new File(templateDirectory.toURI()));
        // Other Settings
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        BLACKDUCK_COMMON_API_BASE_DIRECTORY = getBaseDirectory().getAbsolutePath();

        // Assuming no more than 9 mediaVersions per View class
        MEDIA_VERSION_NUMBERS.add("1");
        MEDIA_VERSION_NUMBERS.add("2");
        MEDIA_VERSION_NUMBERS.add("3");
        MEDIA_VERSION_NUMBERS.add("4");
        MEDIA_VERSION_NUMBERS.add("5");
        MEDIA_VERSION_NUMBERS.add("6");
        MEDIA_VERSION_NUMBERS.add("7");
        MEDIA_VERSION_NUMBERS.add("8");
        MEDIA_VERSION_NUMBERS.add("9");

        return cfg;
    }

    private void generateEnumFiles(final List<ResponseDefinition> responses, final Template template) throws Exception {
        for (final ResponseDefinition response : responses) {
            for (final FieldDefinition field : response.getFields()) {
                final String classType = field.getType().replace(JAVA_LIST, "").replace(">", "");
                if (classType.contains(ENUM)) {
                    final Map<String, Object> input = getEnumInputData(GENERATED_ENUM_PACKAGE, classType, field.getAllowedValues(), response.getMediaType());

                    final String pathToEnumFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + ENUM_DIRECTORY_SUFFIX;

                    writeFile(classType, template, input, pathToEnumFiles);
                }
            }
        }
    }

    private void generateViewFiles(final List<ResponseDefinition> responses, final Template viewTemplate, final Template randomTemplate) throws Exception {
        final MediaTypes mediaTypes = new MediaTypes();
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        final String pathToViewFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + VIEW_DIRECTORY_SUFFIX;

        for (final ResponseDefinition response : responses) {
            if (longMediaTypes.contains(response.getMediaType())) {
                List<String> imports = new ArrayList<>();
                imports = getFieldImports(imports, response, viewTemplate);
                final LinksAndImportsHelper helper = getLinkImports(imports, response);
                imports = helper.getImports();
                final List<LinkHelper> links = helper.getLinks();

                final Map<String, Object> input = getViewInputData(GENERATED_VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields(), links, response.getMediaType());
                final String viewName = response.getName();

                updateViewClassMediaVersions(viewName, input);
                writeFile(viewName, viewTemplate, input, pathToViewFiles);
                NON_LINK_CLASS_NAMES.add(viewName);
                NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(viewName));
            }
        }

        generateMostRecentViewMediaVersions(randomTemplate, pathToViewFiles);

        generateDummyClassesForReferencedButUndefinedObjects(randomTemplate);
    }

    private void generateMostRecentViewMediaVersions(final Template randomTemplate, final String pathToViewFiles) throws Exception {
        for (final ViewMediaVersionHelper latestViewMediaVersion : LATEST_VIEW_MEDIA_VERSIONS.values()) {
            final String viewClassName = latestViewMediaVersion.getViewClass();
            final Map<String, Object> input = latestViewMediaVersion.getInput();
            input.put("parentClass", latestViewMediaVersion.getVersionedClassName());
            final String parentClass;
            if (CLASS_CATEGORIES.isView(viewClassName)) {
                parentClass = VIEW_BASE_CLASS;
            } else if (CLASS_CATEGORIES.isResponse(viewClassName)) {
                parentClass = RESPONSE_BASE_CLASS;
            } else {
                parentClass = COMPONENT_BASE_CLASS;
            }
            final String importPath = CORE_CLASS_PATH_PREFIX + parentClass;
            input.put("importPath", importPath);
            input.put(CLASS_NAME, viewClassName);
            writeFile(viewClassName, randomTemplate, input, pathToViewFiles);
            NON_LINK_CLASS_NAMES.add(viewClassName);
            NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(viewClassName));
        }
    }

    private void generateDummyClassesForReferencedButUndefinedObjects(final Template randomTemplate) throws Exception {
        for (final String linkClassName : LINK_CLASS_NAMES) {
            if (!CLASS_CATEGORIES.isManual(linkClassName) && !CLASS_CATEGORIES.isCommonType(linkClassName)) {
                final Map<String, Object> randomInput = new HashMap<>();
                randomInput.put(CLASS_NAME, linkClassName);
                final String packageName;
                final String destinationSuffix;
                final String importPath;
                final String parentClass;
                if (CLASS_CATEGORIES.isView(linkClassName)) {
                    packageName = GENERATED_VIEW_PACKAGE;
                    destinationSuffix = VIEW_DIRECTORY_SUFFIX;
                    parentClass = VIEW_BASE_CLASS;
                } else if (CLASS_CATEGORIES.isResponse(linkClassName)) {
                    packageName = GENERATED_RESPONSE_PACKAGE;
                    destinationSuffix = RESPONSE_DIRECTORY_SUFFIX;
                    parentClass = RESPONSE_BASE_CLASS;
                } else {
                    packageName = GENERATED_COMPONENT_PACKAGE;
                    destinationSuffix = COMPONENT_DIRECTORY_SUFFIX;
                    parentClass = COMPONENT_BASE_CLASS;
                }
                importPath = CORE_CLASS_PATH_PREFIX + parentClass;
                randomInput.put("parentClass", parentClass);
                randomInput.put("packageName", packageName);
                randomInput.put("importPath", importPath);

                if (!NON_LINK_CLASS_NAMES.contains(linkClassName) && !RANDOM_LINK_CLASS_NAMES.contains(linkClassName)) {
                    writeFile(linkClassName, randomTemplate, randomInput, BLACKDUCK_COMMON_API_BASE_DIRECTORY + destinationSuffix);
                    RANDOM_LINK_CLASS_NAMES.add(linkClassName);
                }
            }
        }
    }

    private void generateComponentFile(final FieldDefinition field, final Template template, final String responseMediaType) throws Exception {
        final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
        final List<String> imports = new ArrayList<>();
        imports.add(CORE_CLASS_PATH_PREFIX + COMPONENT_BASE_CLASS);
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            final String subFieldType = subField.getType().replace(JAVA_LIST, "").replace(">", "");
            if (subFieldType.contains(ENUM)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + subFieldType);
            }
            if (!CLASS_CATEGORIES.isCommonType(subFieldType)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + subFieldType);
                generateComponentFile(subField, template, responseMediaType);
            }
        }
        final Map<String, Object> input = getViewInputData(GENERATED_COMPONENT_PACKAGE, imports, fieldType, COMPONENT_BASE_CLASS, subFields, responseMediaType);

        final String pathToComponentFiles = BLACKDUCK_COMMON_API_BASE_DIRECTORY + COMPONENT_DIRECTORY_SUFFIX;

        writeFile(fieldType, template, input, pathToComponentFiles);
        NON_LINK_CLASS_NAMES.add(fieldType);
        NON_LINK_CLASS_NAMES.add(ResponseNameParser.getNonVersionedName(fieldType));
    }

    private Map<String, Object> getEnumInputData(final String enumPackage, final String enumClassName, final List<String> enumValues, final String mediaType) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put("packageName", enumPackage);
        inputData.put("enumClassName", enumClassName);
        inputData.put("enumValues", enumValues);
        inputData.put("mediaType", mediaType);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final String mediaType) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put("packageName", viewPackage);
        inputData.put("imports", imports);
        inputData.put("className", className);
        inputData.put("baseClass", baseClass);
        inputData.put("classFields", classFields);
        inputData.put("mediaType", mediaType);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields, final List<LinkHelper> links,
        final String mediaType) {
        final HashMap<String, Object> inputData = getViewInputData(viewPackage, imports, className, baseClass, classFields, mediaType);

        if (links != null && links.size() > 0) {
            inputData.put("hasLinksWithResults", true);
            inputData.put("hasLinks", true);
            inputData.put("links", links);
        }

        return inputData;
    }

    private List<String> getFieldImports(final List<String> imports, final ResponseDefinition response, final Template template) throws Exception {
        imports.add(CORE_CLASS_PATH_PREFIX + VIEW_BASE_CLASS);
        for (final FieldDefinition field : response.getFields()) {
            final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
            if (fieldType.contains(ENUM)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + fieldType);
            } else if (!CLASS_CATEGORIES.isCommonType(fieldType)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + fieldType);
                generateComponentFile(field, template, response.getMediaType());
            } else if (fieldType.equals("BigDecimal")) {
                imports.add("java.math.BigDecimal");
            }
        }
        return imports;
    }

    private LinksAndImportsHelper getLinkImports(final List<String> imports, final ResponseDefinition response) {
        final List<LinkDefinition> rawLinks = response.getLinks();
        final List<LinkHelper> links = new ArrayList<>();
        final String responseName = response.getName();

        if (rawLinks.size() > 0) {
            imports.add(CORE_CLASS_PATH_PREFIX + "LinkResponse");
        }

        for (final LinkDefinition rawLink : rawLinks) {
            final LinkHelper link = new LinkHelper(rawLink.getRel(), responseName);
            try {
                final String linkType = link.linkType();
                final String linkImportType;
                final String resultClass;
                String resultImportType = null;
                String resultImportPath = null;

                if (linkType.contains("LinkMultipleResponses")) {
                    linkImportType = "LinkMultipleResponses";
                } else if (linkType.contains("LinkSingleResponse")) {
                    linkImportType = "LinkSingleResponse";
                } else {
                    linkImportType = "LinkStringResponse";
                }
                final String linkImport = CORE_CLASS_PATH_PREFIX + linkImportType;
                if (!imports.contains(linkImport)) {
                    imports.add(linkImport);
                }

                boolean shouldAddImport = true;
                resultClass = link.resultClass();
                if (resultClass != null) {
                    if (CLASS_CATEGORIES.isView(resultClass)) {
                        resultImportType = VIEW;
                    } else if (CLASS_CATEGORIES.isResponse(resultClass)) {
                        resultImportType = RESPONSE;
                    } else if (CLASS_CATEGORIES.isComponent(resultClass)) {
                        resultImportType = COMPONENT;
                    } else {
                        shouldAddImport = false;
                    }

                    if (CLASS_CATEGORIES.isManual(resultClass)) {
                        resultImportPath = MANUAL_CLASS_PATH_PREFIX;
                    } else if (CLASS_CATEGORIES.isGenerated(resultClass)) {
                        resultImportPath = GENERATED_CLASS_PATH_PREFIX;
                    } else {
                        shouldAddImport = false;
                    }

                    if (shouldAddImport) {
                        imports.add(resultImportPath + resultImportType + "." + resultClass);
                    }
                    links.add(link);
                } else {
                    NULL_LINK_RESULT_CLASSES.put(responseName, linkType);
                }
            } catch (final NullPointerException e) {
                NULL_LINK_RESULT_CLASSES.put(responseName, rawLink.getRel());
            }
        }
        return new LinksAndImportsHelper(links, imports);
    }

    private void writeFile(final String className, final Template template, final Map<String, Object> input, final String destination) throws Exception {
        final File testFile = new File(destination);
        testFile.mkdirs();
        final Writer fileWriter = new FileWriter(new File(testFile, className + ".java"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }

    private void updateViewClassMediaVersions(final String viewName, final Map<String, Object> input) {
        final ViewMediaVersionHelper newHelper = getMediaVersion(viewName, input);
        final String viewClass = newHelper.getViewClass();
        final Integer mediaVersion = newHelper.getMediaVersion();
        final ViewMediaVersionHelper oldHelper = LATEST_VIEW_MEDIA_VERSIONS.get(viewClass);

        if (mediaVersion != null) {
            if (oldHelper == null || mediaVersion > oldHelper.getMediaVersion()) {
                LATEST_VIEW_MEDIA_VERSIONS.put(viewClass, newHelper);
            }
        }
    }

    private ViewMediaVersionHelper getMediaVersion(final String viewName, final Map<String, Object> input) {
        Integer mediaVersion = null;
        String viewClass = null;
        for (final String number : MEDIA_VERSION_NUMBERS) {
            if (viewName.contains(number)) {
                mediaVersion = Integer.decode(number);
                viewClass = viewName.replace(number, "");
                input.put(CLASS_NAME, viewClass);
            }
        }
        return new ViewMediaVersionHelper(viewClass, mediaVersion, input);
    }

    /* *****************************************************
                        Helper Classes
    *******************************************************/

    private class ViewMediaVersionHelper {
        private final String viewClass;
        private final Integer mediaVersion;
        private final Map<String, Object> input;

        public ViewMediaVersionHelper(final String viewClass, final Integer mediaVersion, final Map<String, Object> input) {
            this.viewClass = viewClass.replace("ViewV", "View");
            this.mediaVersion = mediaVersion;
            this.input = input;
        }

        public String getViewClass() { return this.viewClass; }

        public Integer getMediaVersion() { return this.mediaVersion; }

        public Map<String, Object> getInput() { return this.input; }

        public String getVersionedClassName() { return this.viewClass + "V" + this.mediaVersion.toString(); }

        public String toString() {
            return this.viewClass + "\n" + this.mediaVersion + "\n\t" + this.input.get(CLASS_NAME);
        }

    }

    public class LinkHelper {
        public final String javaConstant;
        public final String label;
        private boolean hasMultipleResults;
        public String resultClass;
        public String linkType;

        public LinkHelper(final String label, final String responseName) {
            this.label = label;
            this.javaConstant = label.toUpperCase().replace('-', '_') + "_LINK";

            try {
                final String nonVersionedResponseName = ResponseNameParser.getNonVersionedName(responseName);
                final Map<String, LinkResponseDefinitions.LinkResponseDefinitionItem> linkResponseDefinitionsMap = LINK_RESPONSE_DEFINITIONS_LIST.get(nonVersionedResponseName);
                final LinkResponseDefinitions.LinkResponseDefinitionItem linkResponseDefinitionItem = linkResponseDefinitionsMap.get(label);

                this.hasMultipleResults = linkResponseDefinitionItem.hasMultipleResults();
                final String result_class = linkResponseDefinitionItem.getResultClass();

                this.resultClass = result_class;
                LINK_CLASS_NAMES.add(result_class);

                if (result_class.equals("String")) {
                    this.linkType = "LinkStringResponse";
                } else {
                    this.linkType = this.hasMultipleResults ? "LinkMultipleResponses<" + this.resultClass + ">" : "LinkSingleResponse<" + this.resultClass + ">";
                }

            } catch (final NullPointerException e) {
                this.hasMultipleResults = false;
                this.resultClass = null;
                this.linkType = null;
            }
        }

        public String javaConstant() { return this.javaConstant; }

        public String getLabel() { return this.label; }

        public String resultClass() { return this.resultClass; }

        public String linkType() { return this.linkType; }

        public boolean hasMultipleResults() { return this.hasMultipleResults; }

    }

    private class LinksAndImportsHelper {
        private final List<LinkHelper> links;
        private final List<String> imports;

        public LinksAndImportsHelper(final List<LinkHelper> links, final List<String> imports) {
            this.links = links;
            this.imports = imports;
        }

        public List<LinkHelper> getLinks() { return this.links; }

        public List<String> getImports() { return this.imports; }

    }

    private enum LinkResponseType {
        SINGLE,
        MULTIPLE;
    }

}
