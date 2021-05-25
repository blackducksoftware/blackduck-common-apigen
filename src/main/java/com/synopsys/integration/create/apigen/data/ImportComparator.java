/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2020 Synopsys, Inc.
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
package com.synopsys.integration.create.apigen.data;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

public class ImportComparator implements Comparator<String> {
    private static final String PACKAGE_JAVA = "java.";
    private static final String PACKAGE_JAVAX = "javax.";
    private static final String PACKAGE_ORG = "org.";

    private ImportComparator() {
    }

    public static ImportComparator of() {
        return new ImportComparator();
    }

    @Override
    public int compare(final String first, final String second) {
        if (startsWithPackage(PACKAGE_JAVA, first, second)
                || startsWithPackage(PACKAGE_JAVAX, first, second)
                || startsWithPackage(PACKAGE_ORG, first, second)) {
            return -1;
        }

        if (startsWithPackage(PACKAGE_JAVA, second, first)
                || startsWithPackage(PACKAGE_JAVAX, second, first)
                || startsWithPackage(PACKAGE_ORG, second, first)) {
            return 1;
        }

        return first.compareTo(second);
    }

    private boolean startsWithPackage(String packageName, String expectedFirst, String expectedSecond) {
        return StringUtils.startsWith(expectedFirst, packageName) && !StringUtils.startsWith(expectedSecond, packageName);
    }
}
