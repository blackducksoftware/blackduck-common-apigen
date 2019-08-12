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
package com.synopsys.integration.blackduck.api.generated.view;

    import java.util.HashMap;
    import java.util.Map;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import java.math.BigDecimal;
    import com.synopsys.integration.blackduck.api.core.LinkResponse;
    import com.synopsys.integration.blackduck.api.core.LinkStringResponse;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class CodeLocation4View extends BlackDuckView {
    public static final Map
    <String, LinkResponse> links = new HashMap<>();

        public static final String SCANS_LINK = "scans";

            public static final LinkStringResponse SCANS_LINK_RESPONSE = new LinkStringResponse(SCANS_LINK, String.class);

    static {
            links.put(SCANS_LINK, SCANS_LINK_RESPONSE);
    }

    private String createdAt;
    private BigDecimal scanSize;
    private String mappedProjectVersion;
    private String name;
    private Object _meta;
    private String url;
    private String updatedAt;

    public String getCreatedAt() {
    return createdAt;
    }

    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    public BigDecimal getScanSize() {
    return scanSize;
    }

    public void setScanSize(BigDecimal scanSize) {
    this.scanSize = scanSize;
    }

    public String getMappedProjectVersion() {
    return mappedProjectVersion;
    }

    public void setMappedProjectVersion(String mappedProjectVersion) {
    this.mappedProjectVersion = mappedProjectVersion;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Object get_meta() {
    return _meta;
    }

    public void set_meta(Object _meta) {
    this._meta = _meta;
    }

    public String getUrl() {
    return url;
    }

    public void setUrl(String url) {
    this.url = url;
    }

    public String getUpdatedAt() {
    return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    }

}
