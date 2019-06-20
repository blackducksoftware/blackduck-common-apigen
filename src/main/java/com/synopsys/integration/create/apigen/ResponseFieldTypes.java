package com.synopsys.integration.create.apigen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import groovy.json.JsonSlurper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ResponseFieldTypes {

    public static void main(String[] args) {
        /*
        String resourceName = Application.CURRENT_API_SPECIFICATION + "/endpoints/api";
        ArrayList<String> targets = new ArrayList<String>();
        targets.add("response-specification.json");

        List <File> responseSpecificationFiles = DirectoryWalker.collectFiles(resourceName, targets);

        Gson gson = new Gson();
        FieldsParser fieldsParser = new FieldsParser(gson);

        Map<String, FieldDefinition> fieldDefinitions = new HashMap<String, FieldDefinition>();

        //fieldsParser.populateFieldDefinitions(fieldDefinitions, ???, ???);

        JsonSlurper jsonSlurper = new JsonSlurper();

        for (File file : responseSpecificationFiles) {
            JsonArray fields = FieldsParser.getFieldsAsJsonArray(gson, jsonSlurper, file);
        }

         */
    }
}
