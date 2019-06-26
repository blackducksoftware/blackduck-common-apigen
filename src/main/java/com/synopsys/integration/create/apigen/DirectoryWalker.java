package com.synopsys.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import com.synopsys.integration.create.apigen.parser.ResponseParser;
import com.synopsys.integration.util.ResourceUtil;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class DirectoryWalker {
    private File rootDirectory;
    private Gson gson;

    public DirectoryWalker(File rootDirectory, Gson gson) {
        this.rootDirectory = rootDirectory;
        this.gson = gson;
    }

    public List<ResponseDefinition> parseDirectoryForObjects(boolean showOutput) {
        ResponseParser responseParser = new ResponseParser();
        FieldsParser fieldsParser = new FieldsParser(gson);

        // Get response-specification.json files from directory
        List<ResponseDefinition> responseDefinitions = responseParser.parseResponses(rootDirectory);

        // For each response file, parse the JSON for FieldDefinition objects
        for (ResponseDefinition response : responseDefinitions) {
            String absolutePath = rootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            File responseSpecificationFile = new File (absolutePath);

            List<RawFieldDefinition> fields = fieldsParser.getFieldsAsRawFieldDefinitions(gson, responseSpecificationFile);

            response.addFields(fieldsParser.parseFieldDefinitions(response.getName(), fields));
            if (showOutput) {
                System.out.println("***********************\n" + gson.toJson(response));
            }
        }
        return responseDefinitions;
    }

    public static void main(String args[]) throws URISyntaxException {
        // Replace with environment variable
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        URL rootDirectory = DirectoryWalker.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects(true);
    }
}
