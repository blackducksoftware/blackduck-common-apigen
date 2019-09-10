package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.Application;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class FieldsParserTestDataCollector {

    public static void writeControlData(final Gson gson, final List<ResponseDefinition> responses) throws IOException {
        writeData(gson, responses, "FieldsParserTestControlData.txt");
    }

    public static void writeTestData(final Gson gson, final List<ResponseDefinition> responses) throws IOException {
        writeData(gson, responses, "FieldsParserTestTestingData.txt");
    }

    public static void writeData(final Gson gson, final List<ResponseDefinition> responses, final String fileName) throws IOException {
        final File controlDataFile = new File(Application.PATH_TO_TEST_RESOURCES + fileName);
        final FileWriter writer = new FileWriter(controlDataFile);
        controlDataFile.mkdirs();
        writer.write("[");
        final Iterator<ResponseDefinition> responseIterator = responses.iterator();
        while (responseIterator.hasNext()) {
            writer.write(gson.toJson(responseIterator.next()));
            if (responseIterator.hasNext()) {
                writer.write(",\n");
            }
        }
        writer.write("]");
        writer.close();
    }
}
