package com.synopsys.integration.create.apigen.generator;

import static com.synopsys.integration.create.apigen.MediaTypes.getLongNames;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.tools.corba.se.idl.EnumGen;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.DirectoryWalker;
import com.synopsys.integration.create.apigen.MediaTypes;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

    public class Generator {
        public static final Set<String> COMMON_TYPES = new HashSet<>();
        public static final String GENERATED_CLASS_PATH_PREFIX = "com.synopsys.integration.blackduck.api.generated.";
        public static final String ENUM_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "enumeration";
        public static final String VIEW_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "view";
        public static final String VIEW_BASE_CLASS = "BlackDuckView";
        public static final String COMPONENT_PACKAGE = GENERATED_CLASS_PATH_PREFIX + "component";
        public static final String COMPONENT_BASE_CLASS = "BlackDuckComponent";

        public static void main(String[] args) throws Exception {
            Generator Generator = new Generator();
            Configuration config = Generator.configureFreeMarker();

            URL rootDirectory = Generator.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
            List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects(false);

            Template enumTemplate = config.getTemplate("EnumTemplate.ftl");
            Generator.generateEnumFiles(responses, enumTemplate);

            Template viewTemplate = config.getTemplate("ViewTemplate.ftl");
            Generator.generateViewFiles(responses, viewTemplate);

            Template componentTemplate = config.getTemplate("ViewTemplate.ftl");
            //Generator.generateComponentFiles(responses, componentTemplate);
        }

        private Configuration configureFreeMarker() throws URISyntaxException, IOException {
            Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

            // Where do we load the templates from:
            URL templateDirectory = Generator.class.getClassLoader().getResource("templates");
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

            return cfg;
        }

        private void generateEnumFiles(List<ResponseDefinition> responses, Template template) throws Exception {
            for (ResponseDefinition response : responses) {
                for (FieldDefinition field : response.getFields()) {
                    for (Map.Entry<String, String[]> fieldEnum : field.getFieldEnums().entrySet()) {
                        String className = fieldEnum.getKey();
                        Map<String, Object> input = getEnumInputData(ENUM_PACKAGE, className, fieldEnum.getValue());

                        String pathToEnumFiles = Application.PATH_TO_GENERATED_FILES + "enumeration";
                        writeFile(className, template, input, pathToEnumFiles);
                    }
                }
            }
        }

        private void generateViewFiles(List<ResponseDefinition> responses, Template template) throws Exception {
            MediaTypes mediaTypes = new MediaTypes();
            Set<String> longMediaTypes = mediaTypes.getLongNames();

            for (ResponseDefinition response : responses) {
                if (longMediaTypes.contains(response.getMediaType())) {
                    List<String> imports = new ArrayList<>();
                    for (FieldDefinition field : response.getFields()) {
                        for (String enumName : field.getFieldEnums().keySet()) {
                            imports.add(GENERATED_CLASS_PATH_PREFIX + enumName);
                        }
                        String fieldType = field.getType();
                        if (!COMMON_TYPES.contains(fieldType)) {
                            imports.add(GENERATED_CLASS_PATH_PREFIX + fieldType);
                        }
                    }
                    Map<String, Object> input = getViewInputData("view", VIEW_PACKAGE, imports, response.getName(), VIEW_BASE_CLASS, response.getFields());

                    String pathToViewFiles = Application.PATH_TO_GENERATED_FILES + "view";
                    writeFile(response.getName(), template, input, pathToViewFiles);
                }
            }
        }

        private void generateComponentFiles(List<ResponseDefinition> responses, Template template) throws Exception {
            for (ResponseDefinition response : responses) {
                    for (FieldDefinition field : response.getFields()) {
                        String componentType = field.getType();
                        if (!COMMON_TYPES.contains(componentType)) {
                            List<String> componentImports = new ArrayList<>();
                            List<FieldDefinition> subFields = field.getSubFields();
                            for (FieldDefinition subField : subFields) {
                                for (String enumName : subField.getFieldEnums().keySet()) {
                                    componentImports.add(GENERATED_CLASS_PATH_PREFIX + enumName);
                                }
                                String subFieldType = subField.getType();
                                if (!COMMON_TYPES.contains(subFieldType)) {
                                    componentImports.add(GENERATED_CLASS_PATH_PREFIX + subFieldType);
                                }
                            }
                            Map<String, Object> input = getViewInputData("component", COMPONENT_PACKAGE, componentImports, componentType, COMPONENT_BASE_CLASS, subFields);

                            String pathToComponentFiles = Application.PATH_TO_GENERATED_FILES + "component";
                            writeFile(response.getName(), template, input, pathToComponentFiles);
                        }
                    }
            }
        }

        private Map<String, Object> getEnumInputData(String enumPackage, String enumClassName, String[] enumValues) {
            Map<String, Object> inputData = new HashMap<>();

            inputData.put("enumPackage", enumPackage);
            inputData.put("enumClassName", enumClassName);
            inputData.put("enumValues", enumValues);

            return inputData;
        }

        private Map<String, Object> getViewInputData(String viewOrComponent, String viewPackage, List<String> imports, String className, String baseClass, List<FieldDefinition> classFields) {
            Map<String, Object> inputData = new HashMap<>();

            inputData.put(viewOrComponent + "Package", viewPackage);
            inputData.put("imports", imports);
            inputData.put("className", className);
            inputData.put("baseClass", baseClass);
            inputData.put("classFields", classFields);

            return inputData;
        }

        private void writeFile(String className, Template template, Map<String, Object> input, String destination) throws Exception {
            // Write output into a file:
            File testFile = new File(destination);
            testFile.mkdirs();
            Writer fileWriter = new FileWriter(new File(testFile,className + ".java"));
            try {
                template.process(input, fileWriter);
            } finally {
                fileWriter.close();
            }
        }
    }
