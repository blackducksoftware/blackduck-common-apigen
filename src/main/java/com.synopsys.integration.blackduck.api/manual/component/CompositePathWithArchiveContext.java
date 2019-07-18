package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class CompositePathWithArchiveContext extends BlackDuckComponent {
    private String archiveContext;
    private String compositePathContext;
    private String fileName;
    private String path;

    public String getArchiveContext() {
        return archiveContext;
    }

    public void setArchiveContext(final String archiveContext) {
        this.archiveContext = archiveContext;
    }

    public String getCompositePathContext() {
        return compositePathContext;
    }

    public void setCompositePathContext(final String compositePathContext) {
        this.compositePathContext = compositePathContext;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

}
