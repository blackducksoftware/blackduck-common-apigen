/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.create.apigen

import com.opencsv.CSVReader

class MediaTypesG {
    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>()
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>()

    static {
        String resourceName = ApplicationG.CURRENT_API_SPECIFICATION + '/minified-media-types.csv'
        InputStream inputStream = MediaTypesG.class.getResourceAsStream(resourceName)
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
