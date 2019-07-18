package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class VersionBomOriginView extends BlackDuckComponent {
    private String externalId;
    private String externalNamespace;
    private Boolean externalNamespaceDistribution;
    private String name;
    private String origin;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

    public String getExternalNamespace() {
        return externalNamespace;
    }

    public void setExternalNamespace(final String externalNamespace) {
        this.externalNamespace = externalNamespace;
    }

    public Boolean getExternalNamespaceDistribution() {
        return externalNamespaceDistribution;
    }

    public void setExternalNamespaceDistribution(final Boolean externalNamespaceDistribution) {
        this.externalNamespaceDistribution = externalNamespaceDistribution;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

}
