/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data.mediatype;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.utility.StringUtil;

public class MediaTypePathUtility {
    public static String generateMediaTypeStatic(String mediaType) {
        String constantName = generateMediaTypeConstant(mediaType);
        return generateStaticVariable(constantName, mediaType);
    }

    public static String generateMediaTypeConstant(String mediaType) {
        String constantName = StringUtils.remove(mediaType, "application/");
        constantName = StringUtils.replace(constantName, "/", "_");
        constantName = StringUtils.replace(constantName, ".", "_");
        constantName = StringUtils.replace(constantName, "-", "_");
        constantName = StringUtils.replace(constantName, "+", "_");
        return constantName.toUpperCase();
    }

    public static String generatePathStatic(String pathRegex) {
        String constantName = generatePathConstant(pathRegex);
        StringBuilder formattedValue = new StringBuilder();
        formattedValue.append("String.format(\"");
        formattedValue.append(pathRegex);
        formattedValue.append("\"");
        int uuidConstantCount = StringUtils.countMatches(pathRegex, "%s");

        if (uuidConstantCount > 0) {
            formattedValue.append(", ");
            for (int index = 0; index < uuidConstantCount; index++) {
                formattedValue.append("UUID_REGEX");
                if (index < uuidConstantCount - 1) {
                    formattedValue.append(", ");
                }
            }
        }
        formattedValue.append(")");

        return String.format("%s = %s", constantName, formattedValue.toString());
    }

    public static String generatePathConstant(String pathRegex) {
        String constantName = StringUtils.replace(pathRegex, "/", "_");
        constantName = StringUtils.replace(constantName, "%s", "");
        constantName = StringUtils.replace(constantName, "__", "_");
        constantName = StringUtils.replace(constantName, "-", "_");
        constantName = StringUtils.replace(constantName, "_", "", 1);
        if (pathRegex.endsWith("%s")) {
            // if path ends with %s then the constant will end with an '_' character so just add W_ID
            constantName = String.format("%sWITH_ID", constantName);
        }
        return constantName.toUpperCase();
    }

    public static String generateStaticVariable(String constantVariable, String value) {
        return String.format("%s = \"%s\"", constantVariable, value);
    }

}
