/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.synopsys.integration.create.apigen.data.mediatype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class MediaTypes {
    private static final Map<String, String> LONG_TO_SHORT = new HashMap<>();
    private static final Map<String, String> SHORT_TO_LONG = new HashMap<>();

    public MediaTypes() {
        LONG_TO_SHORT.put("application/vnd.blackducksoftware.admin-4+json", "bds_admin_4_json");
        SHORT_TO_LONG.put("bds_admin_4_json", "application/vnd.blackducksoftware.admin-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.bill-of-materials-4+json", "bds_bill_of_materials_4_json");
        SHORT_TO_LONG.put("bds_bill_of_materials_4_json", "application/vnd.blackducksoftware.bill-of-materials-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.bill-of-materials-5+json", "bds_bill_of_materials_5_json");
        SHORT_TO_LONG.put("bds_bill_of_materials_5_json", "application/vnd.blackducksoftware.bill-of-materials-5+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.bill-of-materials-6+json", "bds_bill_of_materials_6_json");
        SHORT_TO_LONG.put("bds_bill_of_materials_6_json", "application/vnd.blackducksoftware.bill-of-materials-6+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.component-detail-4+json", "bds_component_detail_4_json");
        SHORT_TO_LONG.put("bds_component_detail_4_json", "application/vnd.blackducksoftware.component-detail-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.component-detail-5+json", "bds_component_detail_5_json");
        SHORT_TO_LONG.put("bds_component_detail_5_json", "application/vnd.blackducksoftware.component-detail-5+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.journal-4+json", "bds_journal_4_json");
        SHORT_TO_LONG.put("bds_journal_4_json", "application/vnd.blackducksoftware.journal-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.notification-4+json", "bds_notification_4_json");
        SHORT_TO_LONG.put("bds_notification_4_json", "application/vnd.blackducksoftware.notification-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.policy-4+json", "bds_policy_4_json");
        SHORT_TO_LONG.put("bds_policy_4_json", "application/vnd.blackducksoftware.policy-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.policy-5+json", "bds_policy_5_json");
        SHORT_TO_LONG.put("bds_policy_5_json", "application/vnd.blackducksoftware.policy-5+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.project-detail-4+json", "bds_project_detail_4_json");
        SHORT_TO_LONG.put("bds_project_detail_4_json", "application/vnd.blackducksoftware.project-detail-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.project-detail-5+json", "bds_project_detail_5_json");
        SHORT_TO_LONG.put("bds_project_detail_5_json", "application/vnd.blackducksoftware.project-detail-5+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.report-4+json", "bds_report_4_json");
        SHORT_TO_LONG.put("bds_report_4_json", "application/vnd.blackducksoftware.report-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.scan-4+json", "bds_scan_4_json");
        SHORT_TO_LONG.put("bds_scan_4_json", "application/vnd.blackducksoftware.scan-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.status-4+json", "bds_status_4_json");
        SHORT_TO_LONG.put("bds_status_4_json", "application/vnd.blackducksoftware.status-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.user-4+json", "bds_user_4_json");
        SHORT_TO_LONG.put("bds_user_4_json", "application/vnd.blackducksoftware.user-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.vulnerability-4+json", "bds_vulnerability_4_json");
        SHORT_TO_LONG.put("bds_vulnerability_4_json", "application/vnd.blackducksoftware.vulnerability-4+json");

        LONG_TO_SHORT.put("application/vnd.blackducksoftware.system-announcement-1+json","bds_system_announcement_1_json");
        SHORT_TO_LONG.put("bds_system_announcement_1_json","application/vnd.blackducksoftware.system-announcement-1+json");
    }

    public static Set<String> getShortNames() {
        return new HashSet<>(LONG_TO_SHORT.values());
    }

    public static Set<String> getLongNames() {
        return new HashSet<>(LONG_TO_SHORT.keySet());
    }

    public static String getShortName(final String longName) {
        return LONG_TO_SHORT.get(longName);
    }

    public static String getLongName(final String shortName) {
        return SHORT_TO_LONG.get(shortName);
    }

}
