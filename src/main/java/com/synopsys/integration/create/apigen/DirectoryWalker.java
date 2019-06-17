package com.synopsys.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import com.synopsys.integration.create.apigen.parser.ResponseParser;
import groovy.json.JsonSlurper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DirectoryWalker {

    /* Return filtered list of files given the resource name of a root directory and a list of targetFiles */

    static List<File> collectFiles(String resourceName, List<String> targetFiles) {
        File directory = null;

        System.out.println("\nResource: " + resourceName);

        directory = new File(resourceName);
        System.out.println(directory.isDirectory());

        List<File> files = collectFilesHelper(directory, new ArrayList<>(), targetFiles);

        return files;
    }

    static List<File> collectFilesHelper(File root, List<File> files, List<String> targetFiles) {


        for (File file : root.listFiles() ) {
            if (file.isDirectory()) {
                files = collectFilesHelper(file, files, targetFiles);
            }
            if (targetFiles.contains(file.getName())) {
                files.add(file);
            }
        }

        return files;
    }

    /* Print All Objects in list of Files */

    static void printObjects(List<File> files) {

        for (File file : files) {
            /* Parse fields, print if it's an object */

            JsonArray fields = FieldsParser.getFieldsAsJsonArray(new Gson(), new JsonSlurper(), file);

            for (JsonElement field : fields) {
                JsonObject fieldObject = field.getAsJsonObject();

                if (fieldObject.get("type").getAsString().equals("Object") || fieldObject.get("type").getAsString().equals("Array")) {
                    System.out.println(file.getAbsolutePath());
                    System.out.println(fieldObject.get("path").getAsString());
                }
            }
        }
    }

    /* Print out all Field Objects in a directory */

    static void parseDirectoryForObjects(File rootDirectory, Gson gson) {

        ResponseParser responseParser = new ResponseParser();
        FieldsParser fieldsParser = new FieldsParser(gson);
        //Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<String, List<FieldDefinition>>();

        /* Get response-specification.json files from directory */

        ArrayList<ResponseDefinition> responses = responseParser.parseResponses(rootDirectory);

        /* For each response file, parse the JSON for FieldDefinition objects */

        for (ResponseDefinition response : responses) {

            //System.out.println(response.getName());

            Map<String, List<FieldDefinition>> fieldDefinitions = new HashMap<>();
            Map<String, String[]> fieldEnums = new HashMap<>();

            String absolutePath = rootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            File responseSpecificationFile = new File (absolutePath);

            String jsonText = null;
            try {
                jsonText = FileUtils.readFileToString(responseSpecificationFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }

            JsonObject definition = gson.fromJson(jsonText, JsonObject.class);
            JsonArray fields = definition.getAsJsonArray("fields");

            fieldsParser.populateFieldDefinitions(fieldDefinitions, fieldEnums, response.getName(), fields, 5);

            /* Print out objects in alphabetical order */
            List<Map.Entry<String, List<FieldDefinition>>> fieldDefinitionsSorted = new ArrayList<>(fieldDefinitions.entrySet());
            fieldDefinitionsSorted.sort(Map.Entry.comparingByKey());
            for (Map.Entry<String, List<FieldDefinition>> field : fieldDefinitionsSorted)
            {
                //System.out.println(field.getKey() + " => " + field.getValue().toString());
            }
        }
    }

    /* Generate a mapping of dependent API classes from directory of JSON files */

    /* Main method for testing */

    public static void main(String args[]) {

        String resourceName = "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3";

        Gson gson = new Gson();

        //System.out.println(responseSpecificationFiles.toString());

        parseDirectoryForObjects(new File(resourceName), gson);

    }
}
