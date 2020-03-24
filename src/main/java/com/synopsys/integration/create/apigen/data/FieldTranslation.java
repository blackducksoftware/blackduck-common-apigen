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
package com.synopsys.integration.create.apigen.data;

public class FieldTranslation {
    private String path;
    private String swaggerType;
    private String apiSpecsType;

    public FieldTranslation(final String path, final String swaggerType, final String apiSpecsType) {
        this.path = path;
        this.swaggerType = swaggerType;
        this.apiSpecsType = apiSpecsType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getSwaggerName() {
        return swaggerType;
    }

    public void setSwaggerName(final String swaggerType) {
        this.swaggerType = swaggerType;
    }

    public String getApiSpecsName() {
        return apiSpecsType;
    }

    public void setApiSpecsName(final String api_genType) {
        this.apiSpecsType = api_genType;
    }

}
