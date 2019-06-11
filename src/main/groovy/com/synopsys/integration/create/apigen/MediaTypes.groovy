package com.synopsys.integration.create.apigen

import com.opencsv.CSVReader

class MediaTypes {
    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>()
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>()

    static {
        String resourceName = Application.CURRENT_API_SPECIFICATION + '/minified-media-types.csv'
        InputStream inputStream = MediaTypes.class.getResourceAsStream(resourceName)
        Reader reader = new InputStreamReader(inputStream)
        reader.withReader {
            CSVReader csvReader = new CSVReader(reader)
            List<String[]> rows = csvReader.readAll()
            LONG_TO_SHORT[rows[0]] = rows[1]
            SHORT_TO_LONG[rows[1]] = rows[0]
        }
    }

    static Set<String> getShortNames() {
        return new HashSet<>(LONG_TO_SHORT.values())
    }

    static Set<String> getLongNames() {
        return new HashSet<>(LONG_TO_SHORT.keySet())
    }

    static String getShortName(String longName) {
        return LONG_TO_SHORT.get(longName)
    }

    static String getLongName(String shortName) {
        return SHORT_TO_LONG.get(shortName)
    }

}
