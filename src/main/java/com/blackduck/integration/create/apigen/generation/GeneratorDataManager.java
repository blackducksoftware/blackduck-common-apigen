/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.generation;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratorDataManager {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorDataManager.class);
    private Map<String, FileGenerationData> fileDataList;
    private GeneratedClassWriter classWriter;

    @Autowired
    public GeneratorDataManager(GeneratedClassWriter classWriter) {
        this.fileDataList = new HashMap<>(100);
        this.classWriter = classWriter;
    }

    public void addFileData(FileGenerationData data) {
        FileGenerationData dataToStore = data;
        if (fileDataList.containsKey(data.getClassName())) {
            FileGenerationData currentData = fileDataList.get(data.getClassName());
            Map<String, Object> newInputMap = new HashMap<>();
            newInputMap.putAll(currentData.getInput());
            newInputMap.putAll(data.getInput());
            dataToStore = new FileGenerationData(dataToStore.getClassName(), data.getTemplate(), newInputMap, data.getDestination());
        }

        fileDataList.put(dataToStore.getClassName(), dataToStore);
    }

    public void writeFiles() {
        logger.info("Writing Java files...");
        for (FileGenerationData fileData : fileDataList.values()) {
            try {
                classWriter.writeFile(fileData);
            } catch (Exception ex) {
                logger.error("Error generating class: {} at destination: {}", fileData.getClassName(), fileData.getDestination());
                logger.error("Cause: ", ex);
            }
        }
    }


}
