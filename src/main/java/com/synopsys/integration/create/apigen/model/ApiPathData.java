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
package com.synopsys.integration.create.apigen.model;

public class ApiPathData {
    public final String path;
    public final String javaConstant;
    public final String resultClass;
    public final boolean hasMultipleResults;
    public final String linkType;

    public ApiPathData(final String path, final String resultClass, final boolean hasMultipleResults) {
        this.path = "/api/" + path;
        this.javaConstant = path.toUpperCase().replace('-', '_') + "_PATH";
        this.resultClass = resultClass;
        this.hasMultipleResults = hasMultipleResults;
        this.linkType = hasMultipleResults ? "BlackDuckPathMultipleResponses<" + resultClass + ">" : "BlackDuckPathSingleResponse<" + resultClass + ">";
    }

    public String getPath() {
        return path;
    }

    public String getJavaConstant() {
        return javaConstant;
    }

    public String getResultClass() {
        return resultClass;
    }

    public boolean hasMultipleResults() {
        return hasMultipleResults;
    }

    public String getLinkType() {
        return linkType;
    }
}
