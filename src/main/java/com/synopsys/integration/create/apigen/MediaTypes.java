package com.synopsys.integration.create.apigen;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class MediaTypes {

    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>();

    static {
        //String resourceName = Application.CURRENT_API_SPECIFICATION + "/minified-media-types.csv";
        String resourceName = "/api-specification/2019.4.3/minified-media-types.csv";
        InputStream inputStream = MediaTypes.class.getResourceAsStream(resourceName);
        Reader reader = new InputStreamReader(inputStream);

        CSVReader csvReader = new CSVReader(reader);
        List<String[]> rows = null;

        try {
            rows = csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numRows = rows.size();
        final String[] LONG = new String[numRows];
        final String[] SHORT = new String[numRows];
        for (int i = 0; i < numRows; i++) {
            String[] row = rows.get(i);
            LONG[i] = row[0];
            SHORT[i] = row[1];
        }

        for (int i = 0; i < rows.size(); i++)
        {
            LONG_TO_SHORT.put(LONG[i], SHORT[i]);
            SHORT_TO_LONG.put(SHORT[i], LONG[i]);
        }

    }

    public static Set<String> getShortNames() {
        return new HashSet<>(LONG_TO_SHORT.values());
    }

    public static Set<String> getLongNames() {
        return new HashSet<>(LONG_TO_SHORT.keySet());
    }

    public static String getShortName(String longName) {
        return LONG_TO_SHORT.get(longName);
    }

    public static String getLongName(String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }
}
