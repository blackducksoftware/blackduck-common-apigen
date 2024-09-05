/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2021 Synopsys, Inc.
 *
 * Use subject to the terms and conditions of the Synopsys End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.data.mediatype;

import java.util.Arrays;
import java.util.List;

import com.blackduck.integration.create.apigen.model.MediaTypeDefinition;

public class ExtraMediaTypeDefinitions {
    public static final String CODE_LOCATIONS_PATH = "/api/projects/%s/versions/%s/code-locations";
    public static final String LICENSE_REPORTS_PATH = "/api/projects/%s/versions/%s/license-reports";
    public static final String USERS_PATH = "/api/usergroups/%s/users";
    public static final String NOTIFICATIONS_PATH = "/api/users/%s/notifications";

    public static final String COMPONENTS_LICENSE_TEXT = "/api/components/%s/versions/%s/licenses/%s/text";
    public static final String LICENSE_TEXT = "/api/licenses/%s/text";

    public static final String INTERNAL_1_MEDIA_TYPE = "application/vnd.blackducksoftware.internal-1+json";
    public static final String REPORT_4_MEDIA_TYPE = "application/vnd.blackducksoftware.report-4+json";
    public static final String USER_4_MEDIA_TYPE = "application/vnd.blackducksoftware.user-4+json";
    public static final String NOTIFICATION_4_MEDIA_TYPE = "application/vnd.blackducksoftware.notification-4+json";

    public static final String PLAIN_TEXT_MEDIA_TYPE = "text/plain";

    public static final MediaTypeDefinition CODE_LOCATIONS = new MediaTypeDefinition(CODE_LOCATIONS_PATH, INTERNAL_1_MEDIA_TYPE);
    public static final MediaTypeDefinition LICENSE_REPORTS = new MediaTypeDefinition(LICENSE_REPORTS_PATH, REPORT_4_MEDIA_TYPE);
    public static final MediaTypeDefinition USERS = new MediaTypeDefinition(USERS_PATH, USER_4_MEDIA_TYPE);
    public static final MediaTypeDefinition NOTIFICATIONS = new MediaTypeDefinition(NOTIFICATIONS_PATH, NOTIFICATION_4_MEDIA_TYPE);

    public static final MediaTypeDefinition COMPONENTS_LICENSE = new MediaTypeDefinition(COMPONENTS_LICENSE_TEXT, PLAIN_TEXT_MEDIA_TYPE);
    public static final MediaTypeDefinition LICENSE = new MediaTypeDefinition(LICENSE_TEXT, PLAIN_TEXT_MEDIA_TYPE);

    public static List<MediaTypeDefinition> getMissingPaths() {
        return Arrays.asList(CODE_LOCATIONS, LICENSE_REPORTS, USERS, NOTIFICATIONS);
    }

    public static List<MediaTypeDefinition> getPathOverrides() {
        return Arrays.asList(COMPONENTS_LICENSE, LICENSE);
    }

}
