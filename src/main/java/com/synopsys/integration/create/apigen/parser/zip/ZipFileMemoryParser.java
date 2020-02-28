package com.synopsys.integration.create.apigen.parser.zip;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.synopsys.integration.create.apigen.data.MediaTypes;
import com.synopsys.integration.create.apigen.data.MissingFieldsAndLinks;
import com.synopsys.integration.create.apigen.data.NameAndPathManager;
import com.synopsys.integration.create.apigen.data.TypeTranslator;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.ApiParser;
import com.synopsys.integration.create.apigen.parser.FieldDefinitionProcessor;

public class ZipFileMemoryParser implements ApiParser {
    private static final Logger logger = LoggerFactory.getLogger(ZipFileMemoryParser.class);
    private final MediaTypes mediaTypes;
    private final Gson gson;
    private final TypeTranslator typeTranslator;
    private final NameAndPathManager nameAndPathManager;
    private final MissingFieldsAndLinks missingFieldsAndLinks;
    private final FieldDefinitionProcessor processor;

    public ZipFileMemoryParser(final MediaTypes mediaTypes, final Gson gson, final TypeTranslator typeTranslator, final NameAndPathManager nameAndPathManager, final MissingFieldsAndLinks missingFieldsAndLinks,
        FieldDefinitionProcessor processor) {
        this.mediaTypes = mediaTypes;
        this.gson = gson;
        this.typeTranslator = typeTranslator;
        this.nameAndPathManager = nameAndPathManager;
        this.missingFieldsAndLinks = missingFieldsAndLinks;
        this.processor = processor;
    }

    @Override
    public List<ResponseDefinition> parseApi(final File target) {
        List<ResponseDefinition> responses = Collections.emptyList();

        try (ZipFile zipFile = new ZipFile(target)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    byte[] buffer = new byte[1024];
                    try (InputStream zipInputStream = zipFile.getInputStream(entry);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream)) {
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            bufferedOutputStream.write(buffer, 0, length);
                        }
                        bufferedOutputStream.flush();
                        logger.info("Entry: {}, content: {}", entry.getName(), byteArrayOutputStream.toByteArray());
                    } catch (IOException ex) {
                        logger.error("Error reading entry", entry.getName());
                        logger.error("Caused by: ", ex);
                    }
                }
            }
        } catch (IOException ex) {
            logger.error("Error reading zip file: {}", target);
            logger.error("Caused by: ", ex);
        }
        return responses;
    }

}
