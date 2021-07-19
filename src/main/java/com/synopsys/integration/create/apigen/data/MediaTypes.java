/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class MediaTypes {
    private Set<String> LONG_FORM_NAMES = new HashSet<>();
    private Map<String, String> SHORT_TO_LONG = new HashMap<>();


    public MediaTypes(File mediaTypesFile) {
        try {
            CSVParser csvParser = CSVParser.parse(mediaTypesFile, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : csvParser.getRecords()) {
                String longMediaType = csvRecord.get(0);
                String shortMediaType = csvRecord.get(1);

                LONG_FORM_NAMES.add(longMediaType);
                SHORT_TO_LONG.put(shortMediaType, longMediaType);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not parse media types from %s", mediaTypesFile.getAbsolutePath()));
        }
    }

    public Set<String> getLongNames() {
        return LONG_FORM_NAMES;
    }


    public String getLongName(final String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }

}
