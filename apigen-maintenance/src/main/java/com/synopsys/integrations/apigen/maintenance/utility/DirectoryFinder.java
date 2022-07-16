/*
 * apigen-maintenance
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integrations.apigen.maintenance.utility;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryFinder {
    private static Logger logger = LoggerFactory.getLogger(DirectoryFinder.class);

    public File findDirectory(File file, String target) {
        if (file.getName().equals(target)) {
            return file;
        }
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                File targetDirectory = findDirectory(child, target);
                if (targetDirectory != null) {
                    return targetDirectory;
                }
            }
        }
        return null;
    }

    public static File getDirectoryFromPath(String path, String missingPathMessage) {
        if (StringUtils.isBlank(path)) {
            logger.error(missingPathMessage);
            System.exit(-1);
        }
        File directory = new File(path);
        directory.mkdirs();

        return directory;
    }
}
