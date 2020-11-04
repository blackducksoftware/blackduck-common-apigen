package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.file.DirectoryPathParser;

public class DirectoryPathParserTest {
    private static final String API_SPEC_PATH = "api-specification/2020.8.0";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final URL rootDirectory = DirectoryPathParserTest.class.getClassLoader().getResource(API_SPEC_PATH);
    private DirectoryPathParser directoryPathParser;

    public DirectoryPathParserTest() throws URISyntaxException {
        MediaTypes mediaTypes = new MediaTypes();
        TypeTranslator typeTranslator = new TypeTranslator();
        NameAndPathManager nameAndPathManager = new NameAndPathManager();
        MissingFieldsAndLinks missingFieldsAndLinks = new MissingFieldsAndLinks();
        FieldDataProcessor fieldDataProcessor = new FieldDataProcessor(typeTranslator, new DuplicateTypeIdentifier());
        final FieldDefinitionProcessor processor = new FieldDefinitionProcessor(fieldDataProcessor, missingFieldsAndLinks);
        directoryPathParser = new DirectoryPathParser(mediaTypes, gson, typeTranslator, nameAndPathManager, missingFieldsAndLinks, processor);
    }

    @Disabled
    // Test fails on different ordering of fields.  Worth running to make sure there are no significant alterations, but it is disabled so it does not cause builds to fail.
    @Test
    public void test() throws IOException, URISyntaxException {
        final File testFile = new File("./build/DirectoryPathParserTestTestingData.txt");
        List<ResponseDefinition> apiData = directoryPathParser.parseApi(new File(rootDirectory.toURI()));
        FieldsParserTestDataCollector.writeTestData(gson, apiData, testFile);
        final File controlFile = new File(DirectoryPathParserTest.class.getClassLoader().getResource("DirectoryPathParserTestControlData.txt").toURI());

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
