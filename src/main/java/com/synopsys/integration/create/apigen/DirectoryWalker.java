package com.synopsys.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import com.synopsys.integration.create.apigen.parser.ResponseParser;
import java.io.File;
import java.util.*;

public class DirectoryWalker {

    static void parseDirectoryForObjects(File rootDirectory, Gson gson) {
        ResponseParser responseParser = new ResponseParser();
        FieldsParser fieldsParser = new FieldsParser(gson);

        // Get response-specification.json files from directory
        List<ResponseDefinition> responseDefinitions = responseParser.parseResponses(rootDirectory);

        // For each response file, parse the JSON for FieldDefinition objects
        for (ResponseDefinition response : responseDefinitions) {
            String absolutePath = rootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            File responseSpecificationFile = new File (absolutePath);

            JsonArray fields = FieldsParser.getFieldsAsJsonArray(gson, responseSpecificationFile);
            response.addFields(fieldsParser.parseFieldDefinitions(response.getName(), fields));

            response.printResponseDefinition();
        }
    }

    /* Main method for testing */

    public static void main(String args[]) {

        String rootDirectory= "/Users/crowley/Documents/source/blackduck-common-apigen/src/main/resources/api-specification/2019.4.3";
        Gson gson = new Gson();

        parseDirectoryForObjects(new File(rootDirectory), gson);
    }
}
