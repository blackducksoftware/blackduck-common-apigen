package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.file.DirectoryPathParser;
import com.synopsys.integration.create.apigen.parser.zip.ZipFileMemoryParser;

public class ParserEquivalenceTest {
    private static final String API_SPEC_ZIP_FILE_PATH = "api-specification/2019.12.0.zip";
    private static final String API_SPEC_DIRECTORY_PATH = "api-specification/2019.12.0";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ZipFileMemoryParser zipFileParser;
    private DirectoryPathParser directoryPathParser;

    @BeforeEach
    public void initTest() {
        initDirectoryPathParser();
        initZipFileMemoryParser();
    }

    private void initZipFileMemoryParser() {
        MediaTypes mediaTypes = new MediaTypes();
        TypeTranslator typeTranslator = new TypeTranslator();
        NameAndPathManager nameAndPathManager = new NameAndPathManager();
        MissingFieldsAndLinks missingFieldsAndLinks = new MissingFieldsAndLinks();
        FieldDefinitionProcessor processor = new FieldDefinitionProcessor(typeTranslator, nameAndPathManager, missingFieldsAndLinks);
        zipFileParser = new ZipFileMemoryParser(mediaTypes, gson, typeTranslator, nameAndPathManager, missingFieldsAndLinks, processor);
    }

    private void initDirectoryPathParser() {
        MediaTypes mediaTypes = new MediaTypes();
        TypeTranslator typeTranslator = new TypeTranslator();
        NameAndPathManager nameAndPathManager = new NameAndPathManager();
        MissingFieldsAndLinks missingFieldsAndLinks = new MissingFieldsAndLinks();
        FieldDefinitionProcessor processor = new FieldDefinitionProcessor(typeTranslator, nameAndPathManager, missingFieldsAndLinks);
        directoryPathParser = new DirectoryPathParser(mediaTypes, gson, typeTranslator, nameAndPathManager, missingFieldsAndLinks, processor);
    }

    private File createTestFile(String filePath) throws Exception {
        URL rootDirectory = ParserEquivalenceTest.class.getClassLoader().getResource(filePath);
        return new File(rootDirectory.toURI());
    }

    @Test
    public void testParserResultsMatch() throws Exception {
        File testZipFile = createTestFile(API_SPEC_ZIP_FILE_PATH);
        File testDirectory = createTestFile(API_SPEC_DIRECTORY_PATH);
        List<ResponseDefinition> zipParserResponses = zipFileParser.parseApi(testZipFile);
        List<ResponseDefinition> directoryParserResponses = directoryPathParser.parseApi(testDirectory);
        zipParserResponses.sort(Comparator.comparing(ResponseDefinition::getResponseSpecificationPath));
        directoryParserResponses.sort(Comparator.comparing(ResponseDefinition::getResponseSpecificationPath));
        assertEquals(directoryParserResponses.size(), zipParserResponses.size());
    }
}
