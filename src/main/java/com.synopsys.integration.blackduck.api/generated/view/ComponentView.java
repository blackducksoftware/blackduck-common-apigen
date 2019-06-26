package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.generated.ComponentApprovalStatusEnum;
    import com.synopsys.integration.blackduck.api.generated.ComponentSourceEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentView extends BlackDuckView {
    private String approvalStatus;
    private String notes;
    private String name;
    private String description;
    private Object _meta;
    private String source;
    private String url;
    private String primaryLanguage;
    private Array additionalHomepages;

    public String getApprovalStatus() {
    return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
    this.approvalStatus = approvalStatus;
    }

    public String getNotes() {
    return notes;
    }

    public void setNotes(String notes) {
    this.notes = notes;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    public Object get_meta() {
    return _meta;
    }

    public void set_meta(Object _meta) {
    this._meta = _meta;
    }

    public String getSource() {
    return source;
    }

    public void setSource(String source) {
    this.source = source;
    }

    public String getUrl() {
    return url;
    }

    public void setUrl(String url) {
    this.url = url;
    }

    public String getPrimaryLanguage() {
    return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
    this.primaryLanguage = primaryLanguage;
    }

    public Array getAdditionalHomepages() {
    return additionalHomepages;
    }

    public void setAdditionalHomepages(Array additionalHomepages) {
    this.additionalHomepages = additionalHomepages;
    }

}
