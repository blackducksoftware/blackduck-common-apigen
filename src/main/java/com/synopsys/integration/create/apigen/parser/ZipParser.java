package com.synopsys.integration.create.apigen.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.slf4j.Logger;

import com.synopsys.integration.exception.IntegrationException;
import com.synopsys.integration.log.IntLogger;
import com.synopsys.integration.log.Slf4jIntLogger;
import com.synopsys.integration.util.CommonZipExpander;

public class ZipParser {
    private final Logger logger;

    public ZipParser(final Logger logger) {
        this.logger = logger;
    }

    public File getEditedZipRootDirectory(URL url, List<SpecEditData> specEdits) throws URISyntaxException {
        File zipFile = new File(url.toURI());


        return zipFile;
    }

    public File getDirectoryFromZip(File zipFile) throws IntegrationException, ArchiveException, IOException, URISyntaxException {
        String nameOfUnzippedDirectory = zipFile.getAbsolutePath().replace(".zip", "");
        File unzippedDirectory = new File(nameOfUnzippedDirectory);
        unzippedDirectory.mkdirs();
        CommonZipExpander expander = new CommonZipExpander(new Slf4jIntLogger(logger));
        expander.expand(zipFile, unzippedDirectory);
        return unzippedDirectory;
    }

}
