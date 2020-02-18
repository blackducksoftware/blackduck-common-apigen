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
