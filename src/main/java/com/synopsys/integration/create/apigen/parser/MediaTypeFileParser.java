package com.synopsys.integration.create.apigen.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MediaTypeFileParser {
    private BufferedReader reader;
    private Map<String, String> longToShort;
    private Map<String, String> shortToLong;


    public MediaTypeFileParser(File mediaTypesFile) {
        try {
            this.reader = new BufferedReader(new FileReader(mediaTypesFile));

            Map<String, String> longToShort = new HashMap<>();
            Map<String, String> shortToLong = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] longAndShort = line.split(",");
                String longForm = trimQuotes(longAndShort[0]);
                String shortForm = trimQuotes(longAndShort[1]);
                longToShort.put(longForm, shortForm);
                shortToLong.put(shortForm, longForm);
            }

            this.longToShort = longToShort;
            this.shortToLong = shortToLong;

        } catch (IOException e) {
                throw new RuntimeException(String.format("Could not parse media types from %s", mediaTypesFile.getAbsolutePath()));
        }
    }

    public Map<String, String> getLongToShort() {
        return longToShort;
    }

    public Map<String, String> getShortToLong() {
        return shortToLong;
    }

    private String trimQuotes(String str) {
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length()-1);
        }
        return str;
    }
}
