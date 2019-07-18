package com.synopsys.integration.blackduck.api.manual.view;

import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
import com.synopsys.integration.blackduck.api.core.LinkResponse;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VulnerableComponentView extends BlackDuckView {
    public static final Map<String, LinkResponse> links = new HashMap<>();

    public static final String MATCHED_FILES_LINK = "matched-files";
    public static final String VULNERABILITIES_LINK = "vulnerabilities";

    public static final LinkMultipleResponses<MatchedFileView> MATCHED_FILES_LINK_RESPONSE = new LinkMultipleResponses<MatchedFileView>(MATCHED_FILES_LINK, MatchedFileView.class);
    public static final LinkMultipleResponses<VulnerabilityView> VULNERABILITIES_LINK_RESPONSE = new LinkMultipleResponses<VulnerabilityView>(VULNERABILITIES_LINK, VulnerabilityView.class);

    static {
        links.put(MATCHED_FILES_LINK, MATCHED_FILES_LINK_RESPONSE);
        links.put(VULNERABILITIES_LINK, VULNERABILITIES_LINK_RESPONSE);
    }

    private String component;
    private String componentName;
    private String componentVersion;
    private String componentVersionName;
    private String componentVersionOriginId;
    private String componentVersionOriginName;
    private ComplexLicenseView license;
    private VulnerabilityWithRemediationView vulnerabilityWithRemediation;

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        this.component = component;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(final String componentName) {
        this.componentName = componentName;
    }

    public String getComponentVersion() {
        return componentVersion;
    }

    public void setComponentVersion(final String componentVersion) {
        this.componentVersion = componentVersion;
    }

    public String getComponentVersionName() {
        return componentVersionName;
    }

    public void setComponentVersionName(final String componentVersionName) {
        this.componentVersionName = componentVersionName;
    }

    public String getComponentVersionOriginId() {
        return componentVersionOriginId;
    }

    public void setComponentVersionOriginId(final String componentVersionOriginId) {
        this.componentVersionOriginId = componentVersionOriginId;
    }

    public String getComponentVersionOriginName() {
        return componentVersionOriginName;
    }

    public void setComponentVersionOriginName(final String componentVersionOriginName) {
        this.componentVersionOriginName = componentVersionOriginName;
    }

    public ComplexLicenseView getLicense() {
        return license;
    }

    public void setLicense(final ComplexLicenseView license) {
        this.license = license;
    }

    public VulnerabilityWithRemediationView getVulnerabilityWithRemediation() {
        return vulnerabilityWithRemediation;
    }

    public void setVulnerabilityWithRemediation(final VulnerabilityWithRemediationView vulnerabilityWithRemediation) {
        this.vulnerabilityWithRemediation = vulnerabilityWithRemediation;
    }

}
