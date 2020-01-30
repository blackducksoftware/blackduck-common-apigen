package com.synopsys.integration.blackduck.api.generated.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import java.util.Optional;

/**
* this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
* **/
public class ComponentMatchedFilesItemsFilePathView extends BlackDuckComponent {
	public static final String mediaType = "application/vnd.blackducksoftware.bill-of-materials-6+json";

    private String compositePathContext;
    private String archiveContext;
    private String path;
    private String fileName;

    public String getCompositePathContext() {
	return compositePathContext;
    }

    public void setCompositePathContext(String compositePathContext) {
	this.compositePathContext = compositePathContext;
    }

    public String getArchiveContext() {
	return archiveContext;
    }

    public void setArchiveContext(String archiveContext) {
	this.archiveContext = archiveContext;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }


    public String getMediaType() {
	return mediaType;
    }

}
