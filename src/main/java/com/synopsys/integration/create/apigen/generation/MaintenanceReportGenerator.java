/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.DuplicateOverrides;
import com.synopsys.integrations.apigen.maintenance.model.ApiDiff;
import com.synopsys.integrations.apigen.maintenance.utility.ApiDiffFinder;
import com.synopsys.integrations.apigen.maintenance.utility.DirectoryFinder;
import com.synopsys.integrations.apigen.maintenance.utility.GithubProjectCloner;

@Component
public class MaintenanceReportGenerator {
    private Logger logger = LoggerFactory.getLogger(MaintenanceReportGenerator.class);
    private GithubProjectCloner githubProjectCloner = new GithubProjectCloner(logger);
    private ApiDiffFinder apiDiffFinder = new ApiDiffFinder(logger);

    public void generateMaintenanceReport(String generatedApiPath, DuplicateOverrides duplicateOverrides, String reportDestination) throws IOException {
        File reportDirectory = new File(reportDestination);
        reportDirectory.mkdirs();
        if (!reportDirectory.exists()) {
            return;
        }

        File report = new File(reportDestination, "report.txt");
        FileWriter writer = new FileWriter(report);

        writer.write("\t\t\t\t\tMAINTENANCE REPORT\n");
        writeHeader(writer, "Api Diff Data");
        apiDiffFinder.writeDiffToFile(getApiDiff(generatedApiPath), writer);
        writeHeader(writer, "Duplicates that were overrided");
        writeDuplicateOverrides(duplicateOverrides, writer);

        writer.close();
    }

    private ApiDiff getApiDiff(String generatedApiPath) throws IOException {
        File bdCommonApiFromMaster = githubProjectCloner.cloneOrUpdateProject("blackduck-common-api");
        DirectoryFinder directoryFinder = new DirectoryFinder();
        File generatedDirectoryFromMaster = directoryFinder.findDirectory(bdCommonApiFromMaster, "generated");
        File bdCommonApiGenerated = new File(generatedApiPath);

        return apiDiffFinder.findDiffInApi(bdCommonApiGenerated, generatedDirectoryFromMaster);
    }

    private void writeDuplicateOverrides(DuplicateOverrides duplicateOverrides, FileWriter writer) throws IOException {
        Map<String, String> sortedDuplicateData = new TreeMap<>(duplicateOverrides.getFinalOverrides());
        for (Map.Entry<String, String> duplicateOverride : sortedDuplicateData.entrySet()) {
            writer.write(String.format("%s -> %s\n", duplicateOverride.getKey(), duplicateOverride.getValue()));
        }
    }

    private void writeHeader(FileWriter writer, String title) throws IOException {
        writeSeparator(writer);
        writer.write(String.format("%s\n", title));
        writeSeparator(writer);
        writer.write("\n");
    }

    private void writeSeparator(FileWriter writer) throws IOException {
        writer.write("********************************\n");
    }
}
