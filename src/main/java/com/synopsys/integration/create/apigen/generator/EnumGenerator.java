package com.synopsys.integration.create.apigen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.DirectoryWalker;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

    public class EnumGenerator {

        public static void main(String[] args) throws Exception {

            // 1. Configure FreeMarker
            //
            // You should do this ONLY ONCE, when your application starts,
            // then reuse the same Configuration object elsewhere.

            Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

            // Where do we load the templates from:
            cfg.setClassForTemplateLoading(EnumGenerator.class, "templates");

            // Some other recommended settings:
            cfg.setIncompatibleImprovements(new Version(2, 3, 20));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLocale(Locale.US);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            // 2. Process template(s)

            // 2.1. Prepare the template input:
            URL rootDirectory = DirectoryWalker.class.getClassLoader().getResource("api-specification/2019.4.3");
            Gson gson = new Gson();

            DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
            List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects();
            for (ResponseDefinition response : responses) {
                for (FieldDefinition field : response.getFields()) {
                    for (Map.Entry<String, String[]> fieldEnum : field.getFieldEnums().entrySet()) {

                    }
                }
            }

            Map<String, Object> input = getInputData();






            // 2.2. Get the template

            Template template = cfg.getTemplate("helloworld.ftl");

            // 2.3. Generate the output

            // Write output to the console
            Writer consoleWriter = new OutputStreamWriter(System.out);
            template.process(input, consoleWriter);

            // For the sake of example, also write output into a file:
            Writer fileWriter = new FileWriter(new File("output.html"));
            try {
                template.process(input, fileWriter);
            } finally {
                fileWriter.close();
            }

        }

        static Map<String, Object> getInputData() {
            Map<String, Object> inputData = new HashMap<>();
            

            return inputData;
        }
    }
