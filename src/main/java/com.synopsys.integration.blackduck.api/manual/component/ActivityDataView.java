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
package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.ActivityDataTrendingEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ActivityDataView extends BlackDuckComponent {
    private Long commitCount12Month;
    private Long contributorCount12Month;
    private java.util.Date lastCommitDate;
    private ActivityDataTrendingEnum trending;

    public Long getCommitCount12Month() {
        return commitCount12Month;
    }

    public void setCommitCount12Month(final Long commitCount12Month) {
        this.commitCount12Month = commitCount12Month;
    }

    public Long getContributorCount12Month() {
        return contributorCount12Month;
    }

    public void setContributorCount12Month(final Long contributorCount12Month) {
        this.contributorCount12Month = contributorCount12Month;
    }

    public java.util.Date getLastCommitDate() {
        return lastCommitDate;
    }

    public void setLastCommitDate(final java.util.Date lastCommitDate) {
        this.lastCommitDate = lastCommitDate;
    }

    public ActivityDataTrendingEnum getTrending() {
        return trending;
    }

    public void setTrending(final ActivityDataTrendingEnum trending) {
        this.trending = trending;
    }

}
