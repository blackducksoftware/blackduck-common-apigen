package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.zip.ZipFileMemoryParser;

public class ZipMemoryTest {
    private static final String API_SPEC_PATH = "api-specification/2019.12.0.zip";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void testZipFileParsing() throws Exception {
        URL rootDirectory = ZipMemoryTest.class.getClassLoader().getResource(API_SPEC_PATH);
        File targetFile = new File(rootDirectory.toURI());
        MediaTypes mediaTypes = new MediaTypes();
        TypeTranslator typeTranslator = new TypeTranslator();
        NameAndPathManager nameAndPathManager = new NameAndPathManager();
        MissingFieldsAndLinks missingFieldsAndLinks = new MissingFieldsAndLinks();
        FieldDefinitionProcessor processor = new FieldDefinitionProcessor(typeTranslator, nameAndPathManager, missingFieldsAndLinks);
        ZipFileMemoryParser zipFileParser = new ZipFileMemoryParser(mediaTypes, gson, typeTranslator, nameAndPathManager, missingFieldsAndLinks, processor);
        List<ResponseDefinition> responses = zipFileParser.parseApi(targetFile);
        assertTrue(responses.isEmpty());
    }
}
