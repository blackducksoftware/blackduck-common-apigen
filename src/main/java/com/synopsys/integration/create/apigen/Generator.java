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
    public static final Set<String> COMMON_TYPES = new HashSet<>();
    public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
    public static final String BASE_CLASS_PACKAGE = "com.synopsys.integration.blackduck.api.core.";
    public static final String ENUMERATION = "enumeration";
    public static final String ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + ENUMERATION;
    public static final String VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "view";
    public static final String VIEW_BASE_CLASS = "BlackDuckView";
    public static final String COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "component";
    public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";
    public static final String VIEW = "view";
    public static final String COMPONENT = "component";
    public static final String JAVA_LIST = "java.util.List<";

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

        //final Template componentTemplate = config.getTemplate("ViewTemplate.ftl");
        //Generator.generateComponentFiles(responses, viewTemplate);
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

        return cfg;
    }

    private void generateEnumFiles(final List<ResponseDefinition> responses, final Template template) throws Exception {
        for (final ResponseDefinition response : responses) {
            for (final FieldDefinition field : response.getFields()) {
                final String classType = field.getType().replace(JAVA_LIST, "").replace(">", "");
                if (classType.contains("Enum")) {
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

        for (final ResponseDefinition response : responses) {
            if (longMediaTypes.contains(response.getMediaType())) {
                final List<String> imports = new ArrayList<>();
                imports.add(BASE_CLASS_PACKAGE + VIEW_BASE_CLASS);
                for (final FieldDefinition field : response.getFields()) {
                    final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
                    if (fieldType.contains("Enum")) {
                        imports.add(GENERATED_CLASS_PATH_PREFIX + ENUMERATION + "." + fieldType);
                    } else if (!COMMON_TYPES.contains(fieldType)) {
                        imports.add(GENERATED_CLASS_PATH_PREFIX + COMPONENT + "." + fieldType);
                        generateComponentFile(field, template);
                    } else if (fieldType.equals("BigDecimal")) {
                        imports.add("java.math.BigDecimal");
                    }
                }
                final Map<String, Object> input = getViewInputData(VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields());

                final String pathToViewFiles = Application.PATH_TO_GENERATED_FILES + VIEW;
                writeFile(response.getName(), template, input, pathToViewFiles);
            }
        }
    }

    private void generateComponentFile(final FieldDefinition field, final Template template) throws Exception {
        final String fieldType = field.getType().replace(JAVA_LIST, "").replace(">", "");
        final List<String> imports = new ArrayList<>();
        imports.add(BASE_CLASS_PACKAGE + COMPONENT_BASE_CLASS);
        final List<FieldDefinition> subFields = field.getSubFields();
        for (final FieldDefinition subField : subFields) {
            final String subFieldType = subField.getType().replace(JAVA_LIST, "").replace(">", "");
            if (subFieldType.contains("Enum")) {
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

    private Map<String, Object> getViewInputData(final String viewPackage, final List<String> imports, final String className, final String baseClass, final List<FieldDefinition> classFields) {
        final Map<String, Object> inputData = new HashMap<>();

        inputData.put("viewPackage", viewPackage);
        inputData.put("imports", imports);
        inputData.put("className", className);
        inputData.put("baseClass", baseClass);
        inputData.put("classFields", classFields);

        return inputData;
    }

    private void writeFile(final String className, final Template template, final Map<String, Object> input, final String destination) throws Exception {
        // Write output into a file:
        final File testFile = new File(destination);
        testFile.mkdirs();
        final Writer fileWriter = new FileWriter(new File(testFile, className + ".java"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }
    }
}
