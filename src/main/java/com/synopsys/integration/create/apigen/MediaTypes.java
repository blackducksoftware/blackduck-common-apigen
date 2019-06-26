package com.synopsys.integration.create.apigen;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;

public class MediaTypes {

    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>();

    static {
        //String resourceName = Application.CURRENT_API_SPECIFICATION + "/minified-media-types.csv";
        final String resourceName = "/" + Application.API_SPECIFICATION_VERSION + "/minified-media-types.csv";
        final InputStream inputStream = MediaTypes.class.getResourceAsStream(resourceName);
        final Reader reader = new InputStreamReader(inputStream);

        final CSVReader csvReader = new CSVReader(reader);
        List<String[]> rows = null;

        try {
            rows = csvReader.readAll();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final int numRows = rows.size();
        final String[] LONG = new String[numRows];
        final String[] SHORT = new String[numRows];
        for (int i = 0; i < numRows; i++) {
            final String[] row = rows.get(i);
            LONG[i] = row[0];
            SHORT[i] = row[1];
        }

        for (int i = 0; i < rows.size(); i++) {
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

    public static String getShortName(final String longName) {
        return LONG_TO_SHORT.get(longName);
    }

    public static String getLongName(final String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }
}
