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
package com.synopsys.integration.blackduck.api.manual.view;

import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.core.LinkResponse;
import com.synopsys.integration.blackduck.api.core.LinkStringResponse;
import com.synopsys.integration.blackduck.api.manual.enumeration.ReportEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.ReportFormatEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ReportView extends BlackDuckView {
    public static final Map<String, LinkResponse> links = new HashMap<>();

    public static final String DOWNLOAD_LINK = "download";
    public static final String CONTENT_LINK = "content";

    public static final LinkStringResponse CONTENT_LINK_RESPONSE = new LinkStringResponse(CONTENT_LINK, String.class);

    static {
        links.put(CONTENT_LINK, CONTENT_LINK_RESPONSE);
    }

    private java.util.Date createdAt;
    private String createdBy;
    private String createdByUser;
    private String fileName;
    private String fileNamePrefix;
    private Long fileSize;
    private java.util.Date finishedAt;
    private String locale;
    private ReportFormatEnum reportFormat;
    private ReportEnum reportType;
    private java.util.Date updatedAt;

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(final String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(final String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(final Long fileSize) {
        this.fileSize = fileSize;
    }

    public java.util.Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(final java.util.Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public ReportFormatEnum getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(final ReportFormatEnum reportFormat) {
        this.reportFormat = reportFormat;
    }

    public ReportEnum getReportType() {
        return reportType;
    }

    public void setReportType(final ReportEnum reportType) {
        this.reportType = reportType;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}

