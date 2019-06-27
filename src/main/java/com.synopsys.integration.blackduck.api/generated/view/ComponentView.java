package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ComponentApprovalStatusEnum;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ComponentSourceEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentView extends BlackDuckView {
    private ComponentApprovalStatusEnum approvalStatus;
    private String notes;
    private String name;
    private String description;
    private Object _meta;
    private ComponentSourceEnum source;
    private String url;
    private String primaryLanguage;
    private java.util.List<String> additionalHomepages;

    public ComponentApprovalStatusEnum getApprovalStatus() {
    return approvalStatus;
    }

    public void setApprovalStatus(ComponentApprovalStatusEnum approvalStatus) {
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

    public ComponentSourceEnum getSource() {
    return source;
    }

    public void setSource(ComponentSourceEnum source) {
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

    public java.util.List<String> getAdditionalHomepages() {
    return additionalHomepages;
    }

    public void setAdditionalHomepages(java.util.List<String> additionalHomepages) {
    this.additionalHomepages = additionalHomepages;
    }

}
