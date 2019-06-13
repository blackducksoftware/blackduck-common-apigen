package com.synopsys.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import groovy.json.JsonSlurper;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryWalker {

    /* Return filtered list of files given the resource name of a root directory and a list of targetFiles */

    static List<File> collectFiles(String resourceName, List<String> targetFiles) {
        File directory = null;

        System.out.println("\nResource: " + resourceName);

        try {
            directory = new File(DirectoryWalker.class.getResource(resourceName).toURI()); // Does it matter if we're in a different class?
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        List<File> files = new ArrayList<File>();
        for (File file : directory.listFiles() ) {
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
                if (fieldObject.get("type").equals("Object")) {
                    System.out.println(fieldObject.toString());
                }
            }
        }
    }

    /* Generate a mapping of dependent API classes from directory of JSON files */


    /* Main method for testing */

    public static void main(String args[]) {

        String resourceName = Application.CURRENT_API_SPECIFICATION + "/endpoints/api";

        /* Get all response-specification.json files */

        List<String> response_targets = new ArrayList<String>();
        response_targets.add("response-specification.json");
        List<File> responseSpecificationFiles = DirectoryWalker.collectFiles(resourceName, response_targets);

        /* Get all request-specification.json files */

        List<String> request_targets = new ArrayList<String>();
        request_targets.add("request-specification.json");
        List <File> requestSpecificationFiles = DirectoryWalker.collectFiles(resourceName, request_targets);

        /* Combine lists */

        responseSpecificationFiles.addAll(requestSpecificationFiles);

        /* Print Objects */

        printObjects(responseSpecificationFiles);
    }
}
