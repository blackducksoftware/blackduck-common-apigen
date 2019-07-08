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
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Generator {
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String BASE_CLASS_PACKAGE = "com.synopsys.integration.blackduck.api.core.";
    public static final String ENUMERATION = "enumeration";
    public static final String ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "view";
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "component";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String VIEW = "view";
    public static final String ENUM = "Enum";
    public static final String COMPONENT = "component";
    public static final String JAVA_LIST = "java.util.List<";
    public static final String CLASS_NAME = "className";

    public static final Set<String> COMMON_TYPES = new HashSet<>();
    public static Set<String> MEDIA_VERSION_NUMBERS = new HashSet<>();
    private static Map<String, ViewMediaVersionHelper> LATEST_VIEW_MEDIA_VERSIONS = new HashMap<>();

    public static void main(final String[] args) throws Exception {
        final Generator Generator = new Generator();
        final Configuration config = Generator.configureFreeMarker();

        final URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects(false);

        final Template enumTemplate = config.getTemplate("EnumTemplate.ftl");
        Generator.generateEnumFiles(responses, enumTemplate);

        final Template viewTemplate = config.getTemplate("ViewTemplate.ftl");
        Generator.generateViewFiles(responses, viewTemplate);
    }

    private Configuration configureFreeMarker() throws URISyntaxException, IOException {
        final Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // Where do we load the templates from:
        final URL templateDirectory = Generator.class.getClassLoader().getResource("templates");
        cfg.setDirectoryForTemplateLoading(new File(templateDirectory.toURI())); // hmmm can't find the template for some reason...
        // Other Settings
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        COMMON_TYPES.add("Array");
        COMMON_TYPES.add("Object");
        COMMON_TYPES.add("Boolean");
        COMMON_TYPES.add("BigDecimal");
        COMMON_TYPES.add("String");
        COMMON_TYPES.add("Integer");

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
                    final Map<String, Object> input = getEnumInputData(ENUM_PACKAGE, classType, field.getAllowedValues());

                    final String pathToEnumFiles = Application.PATH_TO_GENERATED_FILES + ENUMERATION;
                    writeFile(classType, template, input, pathToEnumFiles);
                }
            }
        }
    }

    private void generateViewFiles(final List<ResponseDefinition> responses, final Template template) throws Exception {
        final MediaTypes mediaTypes = new MediaTypes();
        final Set<String> longMediaTypes = mediaTypes.getLongNames();
        final String pathToViewFiles = Application.PATH_TO_GENERATED_FILES + VIEW;

        for (final ResponseDefinition response : responses) {
            if (longMediaTypes.contains(response.getMediaType())) {
                final List<String> imports = new ArrayList<>();
                imports.add(BASE_CLASS_PACKAGE + VIEW_BASE_CLASS);
                for (final FieldDefinition field : response.getFields()) {
                    final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
                    if (fieldType.contains(ENUM)) {
                        imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + fieldType);
                    } else if (!COMMON_TYPES.contains(fieldType)) {
                        imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + fieldType);
                        generateComponentFile(field, template);
                    } else if (fieldType.equals("BigDecimal")) {
                        imports.add("java.math.BigDecimal");
                    }
                }
                final HashMap<String, Object> input = getViewInputData(VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields());
                final String viewName = response.getName();

                final HashMap<String, Object> clonedInput = (HashMap<String, Object>) input.clone();
                LATEST_VIEW_MEDIA_VERSIONS = getUpdatedViewClassMediaVersions(LATEST_VIEW_MEDIA_VERSIONS, viewName, clonedInput);
                writeFile(viewName, template, input, pathToViewFiles);
            }
        }

        // Create View classes for most recent Media Version of that View
        for (final ViewMediaVersionHelper latestViewMediaVersion : LATEST_VIEW_MEDIA_VERSIONS.values()) {
            final String viewClassName = latestViewMediaVersion.getViewClass();
            final Map<String, Object> viewInput = latestViewMediaVersion.getInput();
            writeFile(viewClassName, template, viewInput, pathToViewFiles);
        }
    }

    private void generateComponentFile(final FieldDefinition field, final Template template) throws Exception {
        final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
        final List<String> imports = new ArrayList<>();
        imports.add(BASE_CLASS_PACKAGE + COMPONENT_BASE_CLASS);
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            final String subFieldType = subField.getType().replace(JAVA_LIST, "").replace(">", "");
            if (subFieldType.contains(ENUM)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + subFieldType);
            }
            if (!COMMON_TYPES.contains(subFieldType)) {
                imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + subFieldType);
                generateComponentFile(subField, template);
            }
        }
        final Map<String, Object> input = getViewInputData(COMPONENT_PACKAGE, imports, fieldType, COMPONENT_BASE_CLASS, subFields);

        final String pathToComponentFiles = Application.PATH_TO_GENERATED_FILES + COMPONENT;
        writeFile(fieldType, template, input, pathToComponentFiles);
    }

    private Map<String, Object> getEnumInputData(final String enumPackage, final String enumClassName, final List<String> enumValues) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put("enumPackage", enumPackage);
        inputData.put("enumClassName", enumClassName);
        inputData.put("enumValues", enumValues);

        return inputData;
    }

    private HashMap<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields) {
        final HashMap<String, Object> inputData = new HashMap<>();

        inputData.put("viewPackage", viewPackage);
        inputData.put("imports", imports);
        inputData.put("className", className);
        inputData.put("baseClass", baseClass);
        inputData.put("classFields", classFields);

        return inputData;
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

    private Map<String, ViewMediaVersionHelper> getUpdatedViewClassMediaVersions(final Map<String, ViewMediaVersionHelper> viewClassMediaVersions, final String viewName, final Map<String, Object> input) {
        final ViewMediaVersionHelper newHelper = getMediaVersion(viewName, input);
        final String viewClass = newHelper.getViewClass();
        final Integer mediaVersion = newHelper.getMediaVersion();
        final ViewMediaVersionHelper oldHelper = viewClassMediaVersions.get(viewClass);

        if (mediaVersion != null) {
            if (oldHelper == null || mediaVersion > oldHelper.getMediaVersion()) {
                viewClassMediaVersions.put(viewClass, newHelper);
            }
        }
        return viewClassMediaVersions;
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

    private class ViewMediaVersionHelper {
        private final String viewClass;
        private final Integer mediaVersion;
        private final Map<String, Object> input;

        public ViewMediaVersionHelper(final String viewClass, final Integer mediaVersion, final Map<String, Object> input) {
            this.viewClass = viewClass;
            this.mediaVersion = mediaVersion;
            this.input = input;
        }

        public String getViewClass() { return this.viewClass; }

        public Integer getMediaVersion() { return this.mediaVersion; }

        public Map<String, Object> getInput() { return this.input; }

        public String toString() {
            return this.viewClass + "\n" + this.mediaVersion + "\n\t" + this.input.get(CLASS_NAME);
        }

    }

}
