package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectCloneCategoriesEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ProjectView extends BlackDuckView {
    private String updatedByUser;
    private Integer projectTier;
    private String updatedBy;
    private String createdByUser;
    private Boolean projectLevelAdjustments;
    private String description;
    private Object _meta;
    private String createdAt;
    private String createdBy;
    private String name;
    private String projectOwner;
    private java.util.List<ProjectCloneCategoriesEnum> cloneCategories;
    private Boolean customSignatureEnabled;
    private String updatedAt;

    public String getUpdatedByUser() {
    return updatedByUser;
    }

    public void setUpdatedByUser(String updatedByUser) {
    this.updatedByUser = updatedByUser;
    }

    public Integer getProjectTier() {
    return projectTier;
    }

    public void setProjectTier(Integer projectTier) {
    this.projectTier = projectTier;
    }

    public String getUpdatedBy() {
    return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
    }

    public String getCreatedByUser() {
    return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
    this.createdByUser = createdByUser;
    }

    public Boolean getProjectLevelAdjustments() {
    return projectLevelAdjustments;
    }

    public void setProjectLevelAdjustments(Boolean projectLevelAdjustments) {
    this.projectLevelAdjustments = projectLevelAdjustments;
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

    public String getCreatedAt() {
    return createdAt;
    }

    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    public String getCreatedBy() {
    return createdBy;
    }

    public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public String getProjectOwner() {
    return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
    this.projectOwner = projectOwner;
    }

    public java.util.List<ProjectCloneCategoriesEnum> getCloneCategories() {
    return cloneCategories;
    }

    public void setCloneCategories(java.util.List<ProjectCloneCategoriesEnum> cloneCategories) {
    this.cloneCategories = cloneCategories;
    }

    public Boolean getCustomSignatureEnabled() {
    return customSignatureEnabled;
    }

    public void setCustomSignatureEnabled(Boolean customSignatureEnabled) {
    this.customSignatureEnabled = customSignatureEnabled;
    }

    public String getUpdatedAt() {
    return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    }

}
