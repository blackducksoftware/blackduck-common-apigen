package com.synopsys.integration.blackduck.api.generated.view;

import com.synopsys.integration.blackduck.api.generated.enumeration.LicenseFamilyLicenseFamilyRiskRulesUsageType;
import java.util.List;
import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import java.util.Optional;
import com.synopsys.integration.blackduck.api.generated.component.ComponentMatchedFilesItemsFilePathView;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ComponentVersionMatchedFilesViewV4 extends BlackDuckView {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-4+json";

    private ComponentMatchedFilesItemsFilePathView filePath;
    private java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> usages;

    public ComponentMatchedFilesItemsFilePathView getFilePath() {
	return filePath;
    }

    public void setFilePath(ComponentMatchedFilesItemsFilePathView filePath) {
	this.filePath = filePath;
    }

    public java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> getUsages() {
	return usages;
    }

    public void setUsages(java.util.List<LicenseFamilyLicenseFamilyRiskRulesUsageType> usages) {
	this.usages = usages;
    }


    public String getMediaType() {
	return mediaType;
    }

}
