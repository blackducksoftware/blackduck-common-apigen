/**
 * blackduck-common-apigen
 *
 * Copyright (c) 2019 Synopsys, Inc.
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.ClassCategories;
import com.synopsys.integration.create.apigen.data.DeprecatedClassData;
import com.synopsys.integration.create.apigen.generation.GeneratedClassWriter;

import freemarker.template.Template;

@Component
public class DeprecatedClassGenerator {
     private final GeneratedClassWriter generatedClassWriter;
     private final ClassCategories classCategories;

     @Autowired
     public DeprecatedClassGenerator(GeneratedClassWriter generatedClassWriter, ClassCategories classCategories) {
         this.generatedClassWriter = generatedClassWriter;
         this.classCategories = classCategories;
     }

    public void generateDeprecatedClasses() throws Exception {
        for (DeprecatedClassData deprecatedClassData : classCategories.getDeprecatedClasses()) {
            //debug
            if (deprecatedClassData.getSwaggerName().equals("VersionBomPolicyRuleView")) {
                System.out.println("nop");
            }
            generatedClassWriter.writeFile(deprecatedClassData.getSwaggerName(), deprecatedClassData.getTemplate(), deprecatedClassData.getInput(), deprecatedClassData.getDestination());
        }
    }
}
