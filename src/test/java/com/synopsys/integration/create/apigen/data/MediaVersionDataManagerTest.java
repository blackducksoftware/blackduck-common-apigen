package com.synopsys.integration.create.apigen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.Assert;

import com.synopsys.integration.create.apigen.model.MediaVersionData;

public class MediaVersionDataManagerTest {

    private final MediaVersionDataManager mediaVersionDataManager = new MediaVersionDataManager(new ClassCategories());

    @Test
    public void updateLatestMediaVersionsTest() {
        final String[] classNames = { "ProjectViewV4", "ComponentsViewV4", "ProjectVersionLicenseViewV5", "JobView", "ProjectViewV5" };

        for (String className : classNames) {
            mediaVersionDataManager.updateLatestMediaVersions(className, new HashMap<>(), "");
        }

        Map<String, MediaVersionData> latestViewMediaVersions = mediaVersionDataManager.getLatestViewMediaVersions();
        Map<String, MediaVersionData> latestResponseMediaVersions = mediaVersionDataManager.getLatestResponseMediaVersions();
        Map<String, MediaVersionData> latestComponentMediaVersions = mediaVersionDataManager.getLatestComponentMediaVersions();

        Assert.assertTrue(latestViewMediaVersions.size() == 3);
        Assert.assertTrue(latestResponseMediaVersions.size() == 1);
        Assert.assertTrue(latestComponentMediaVersions.size() == 0);

        Assert.assertTrue(latestViewMediaVersions.get("JobView").getMediaVersion() == 0);
        Assert.assertTrue(latestViewMediaVersions.get("ProjectView").getMediaVersion() == 5);
    }
}
