package com.synopsys.integration.create.apigen.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryWalkerTest {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final URL rootDirectory = DirectoryWalkerTest.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
    private final com.synopsys.integration.create.apigen.parser.DirectoryWalker directoryWalker;

    public DirectoryWalkerTest() throws URISyntaxException {
        this.directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson, new MediaTypes(), new TypeTranslator(), new NameAndPathManager(), new MissingFieldsAndLinks());
    }

    @Test
    public void test() throws IOException, URISyntaxException {
        FieldsParserTestDataCollector.writeTestData(gson, directoryWalker.parseDirectoryForResponses(false, false));
        final File controlFile = new File(DirectoryWalkerTest.class.getClassLoader().getResource("FieldsParserTestControlData.txt").toURI());
        final File testFile = new File(Application.PATH_TO_TEST_RESOURCES + "FieldsParserTestTestingData.txt");

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