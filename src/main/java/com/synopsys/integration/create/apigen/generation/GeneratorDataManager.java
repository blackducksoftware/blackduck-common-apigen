package com.synopsys.integration.create.apigen.generation;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratorDataManager {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorDataManager.class);
    private List<FileGenerationData> fileDataList;
    private GeneratedClassWriter classWriter;

    @Autowired
    public GeneratorDataManager(GeneratedClassWriter classWriter) {
        this.fileDataList = new LinkedList<>();
        this.classWriter = classWriter;
    }

    public void addFileData(FileGenerationData data) {
        fileDataList.add(data);
    }

    public void writeFiles() {
        for (FileGenerationData fileData : fileDataList) {
            try {
                classWriter.writeFile(fileData.getClassName(), fileData.getTemplate(), fileData.getInput(), fileData.getDestination());
            } catch (Exception ex) {
                logger.error("Error generating class: {} at destination: {}", fileData.getClassName(), fileData.getDestination());
                logger.error("Cause: ", ex);
            }
        }
    }
}
