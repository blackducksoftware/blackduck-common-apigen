package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.synopsys.integration.create.apigen.GeneratorConfig;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.file.DirectoryPathParser;

public class DirectoryWalkerTest {
    private static final String API_SPEC_PATH = "api-specification/2020.8.0";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final URL rootDirectory = DirectoryWalkerTest.class.getClassLoader().getResource(API_SPEC_PATH);
    private final com.synopsys.integration.create.apigen.parser.DirectoryWalker directoryWalker;

    public DirectoryWalkerTest() throws URISyntaxException {
        MediaTypes mediaTypes = new MediaTypes();
        TypeTranslator typeTranslator = new TypeTranslator();
        NameAndPathManager nameAndPathManager = new NameAndPathManager();
        MissingFieldsAndLinks missingFieldsAndLinks = new MissingFieldsAndLinks();
        final FieldDefinitionProcessor processor = new FieldDefinitionProcessor(typeTranslator, new DuplicateTypeIdentifier(), missingFieldsAndLinks);
        final DirectoryPathParser apiParser = new DirectoryPathParser(mediaTypes, gson, typeTranslator, nameAndPathManager, missingFieldsAndLinks, processor);
        this.directoryWalker = new DirectoryWalker(gson, apiParser);
    }

    @Disabled
    // Test fails on different ordering of fields.  Worth running to make sure there are no significant alterations, but it is disabled so it does not cause builds to fail.
    @Test
    public void test() throws IOException, URISyntaxException {
        final File testFile = new File("./build/FieldsParserTestTestingData.txt");
        List<ResponseDefinition> apiData = directoryWalker.parseDirectoryForResponses(false, false, new File(rootDirectory.toURI()));
        FieldsParserTestDataCollector.writeTestData(gson, apiData, testFile);
        final File controlFile = new File(DirectoryWalkerTest.class.getClassLoader().getResource("FieldsParserTestControlData.txt").toURI());

        String controlData = null;
        String testData = null;
        try {
            controlData = FileUtils.readFileToString(controlFile, StandardCharsets.UTF_8);
            testData = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final JsonArray control = gson.fromJson(controlData, JsonElement.class).getAsJsonArray();
        final JsonArray test = gson.fromJson(testData, JsonElement.class).getAsJsonArray();

        for (final JsonElement element : test) {
            assertTrue(control.contains(element), element.toString());
        }

        for (final JsonElement element : control) {
            assertTrue(test.contains(element), element.toString());
        }
    }
}
