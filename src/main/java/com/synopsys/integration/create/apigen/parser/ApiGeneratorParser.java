package com.synopsys.integration.create.apigen.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.model.ParsedApiData;
import com.synopsys.integration.create.apigen.parser.file.DirectoryPathParser;
import com.synopsys.integration.create.apigen.parser.zip.ZipFileExpandingParser;

@Component
public class ApiGeneratorParser implements ApiParser {
    private static final Logger logger = LoggerFactory.getLogger(ApiGeneratorParser.class);
    private DirectoryPathParser directoryPathParser;
    private ZipFileExpandingParser zipFileExpandingParser;

    @Autowired
    public ApiGeneratorParser(DirectoryPathParser directoryPathParser, ZipFileExpandingParser zipFileExpandingParser) {
        this.directoryPathParser = directoryPathParser;
        this.zipFileExpandingParser = zipFileExpandingParser;
    }

    @Override
    public ParsedApiData parseApi(final File target) {
        if (target.isFile()) {
            String format;
            // we need to use an InputStream where inputStream.markSupported() == true
            try (InputStream in = new BufferedInputStream(Files.newInputStream(target.toPath()))) {
                format = ArchiveStreamFactory.detect(in);
                if (ArchiveStreamFactory.ZIP.equals(format)) {
                    return zipFileExpandingParser.parseApi(target);
                }
            } catch (IOException | ArchiveException ex) {
                logger.error("Error processing zip file target.", ex);
            }
        }

        return directoryPathParser.parseApi(target);
    }
}
