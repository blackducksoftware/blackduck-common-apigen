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
package com.synopsys.integration.create.apigen;

import java.util.Map;

public class ViewMediaVersionHelper {
    private final String viewClass;
    private final Integer mediaVersion;
    private final Map<String, Object> input;

    public ViewMediaVersionHelper(final String viewClass, final Integer mediaVersion, final Map<String, Object> input) {
        this.viewClass = viewClass.replace("ViewV", "View");
        this.mediaVersion = mediaVersion;
        this.input = input;
    }

    public String getViewClass() { return this.viewClass; }

    public Integer getMediaVersion() { return this.mediaVersion; }

    public Map<String, Object> getInput() { return this.input; }

    public String getVersionedClassName() { return this.viewClass + "V" + this.mediaVersion.toString(); }

    public String toString() {
        return this.viewClass + "\n" + this.mediaVersion + "\n\t" + this.input.get("className");
    }

}
