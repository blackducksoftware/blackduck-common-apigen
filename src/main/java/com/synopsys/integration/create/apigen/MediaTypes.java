package com.synopsys.integration.create.apigen;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class MediaTypes {

    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>();

    static {
        String resourceName = Application.CURRENT_API_SPECIFICATION + "/minified-media-types.csv";
        InputStream inputStream = MediaTypes.class.getResourceAsStream(resourceName);
        Reader reader = new InputStreamReader(inputStream);

        CSVReader csvReader = new CSVReader(reader);
        List<String[]> rows = null;

        try {
            rows = csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String[] LONG = rows.get(0);
        final String[] SHORT = rows.get(1);

        for (int i = 0; i < rows.get(0).length; i++)
        {
            LONG_TO_SHORT.put(LONG[i], SHORT[i]);
            SHORT_TO_LONG.put(SHORT[i], LONG[i]);
        }

    }

    static Set<String> getShortNames() {
        return new HashSet<>(LONG_TO_SHORT.values());
    }

    static Set<String> getLongNames() {
        return new HashSet<>(LONG_TO_SHORT.keySet());
    }

    static String getShortName(String longName) {
        return LONG_TO_SHORT.get(longName);
    }

    static String getLongName(String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }
}
