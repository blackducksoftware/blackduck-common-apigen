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
package com.synopsys.integration.create.apigen.generation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;

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
        makeClassNamesPrettier();
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

    private void makeClassNamesPrettier() {
        for (FileGenerationData fileData : fileDataList.values()) {
            editRedundantName(fileData, fileDataList.keySet());
        }
    }

    private void editRedundantName(FileGenerationData fileData, Set<String> classNames) {
        String oldName = fileData.getClassName();
        String editedName = oldName.replace("TypeType", "Type");
        if (!classNames.contains(editedName)) {
            fileData.setClassName(editedName);
            fileData.getInput().put(UtilStrings.CLASS_NAME, editedName);
            // make sure classes that had fields of the previous type get updated
            for (FileGenerationData fileGenerationData : fileDataList.values()) {
                Set<FieldDefinition> fields = (Set<FieldDefinition>) fileGenerationData.getInput().get(UtilStrings.CLASS_FIELDS);
                List<String> imports = (List<String>) fileGenerationData.getInput().get("imports");
                if (fields == null || imports == null) continue;
                for (FieldDefinition field : fields) {
                    if (field.getType().equals(oldName)) {
                        field.setType(editedName);
                    }
                }
                imports.replaceAll(importString -> importString.replace(oldName, editedName));
            }
        }
    }
}
