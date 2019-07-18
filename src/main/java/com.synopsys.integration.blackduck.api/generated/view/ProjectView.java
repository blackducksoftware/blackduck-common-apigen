package com.synopsys.integration.blackduck.api.generated.view;

    import java.util.HashMap;
    import java.util.Map;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ProjectCloneCategoriesEnum;
    import com.synopsys.integration.blackduck.api.core.LinkResponse;
    import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
    import com.synopsys.integration.blackduck.api.generated.view.ProjectVersionView;
    import com.synopsys.integration.blackduck.api.core.LinkSingleResponse;
    import com.synopsys.integration.blackduck.api.generated.view.ProjectVersionView;
    import com.synopsys.integration.blackduck.api.manual.response.AssignableUserView;
    import com.synopsys.integration.blackduck.api.manual.response.AssignableUserGroupView;
    import com.synopsys.integration.blackduck.api.manual.response.AssignedUserRequest;
    import com.synopsys.integration.blackduck.api.generated.response.AssignedUserGroupView;
    import com.synopsys.integration.blackduck.api.manual.view.TagView;
    import com.synopsys.integration.blackduck.api.manual.response.ProjectMappingView;
    import com.synopsys.integration.blackduck.api.generated.response.ProjectJournalView;
    import com.synopsys.integration.blackduck.api.generated.view.CustomFieldView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ProjectView extends BlackDuckView {
    public static final Map
    <String, LinkResponse> links = new HashMap<>();

        public static final String VERSIONS_LINK = "versions";
        public static final String CANONICALVERSION_LINK = "canonicalVersion";
        public static final String ASSIGNABLE_USERS_LINK = "assignable-users";
        public static final String ASSIGNABLE_USERGROUPS_LINK = "assignable-usergroups";
        public static final String USERS_LINK = "users";
        public static final String USERGROUPS_LINK = "usergroups";
        public static final String TAGS_LINK = "tags";
        public static final String PROJECT_MAPPINGS_LINK = "project-mappings";
        public static final String PROJECT_JOURNAL_LINK = "project-journal";
        public static final String CUSTOM_FIELDS_LINK = "custom-fields";

            public static final LinkMultipleResponses<ProjectVersionView> VERSIONS_LINK_RESPONSE = new LinkMultipleResponses<ProjectVersionView>(VERSIONS_LINK, ProjectVersionView.class);
            public static final LinkSingleResponse<ProjectVersionView> CANONICALVERSION_LINK_RESPONSE = new LinkSingleResponse<ProjectVersionView>(CANONICALVERSION_LINK, ProjectVersionView.class);
            public static final LinkMultipleResponses<AssignableUserView> ASSIGNABLE_USERS_LINK_RESPONSE = new LinkMultipleResponses<AssignableUserView>(ASSIGNABLE_USERS_LINK, AssignableUserView.class);
            public static final LinkMultipleResponses<AssignableUserGroupView> ASSIGNABLE_USERGROUPS_LINK_RESPONSE = new LinkMultipleResponses<AssignableUserGroupView>(ASSIGNABLE_USERGROUPS_LINK, AssignableUserGroupView.class);
            public static final LinkMultipleResponses<AssignedUserRequest> USERS_LINK_RESPONSE = new LinkMultipleResponses<AssignedUserRequest>(USERS_LINK, AssignedUserRequest.class);
            public static final LinkMultipleResponses<AssignedUserGroupView> USERGROUPS_LINK_RESPONSE = new LinkMultipleResponses<AssignedUserGroupView>(USERGROUPS_LINK, AssignedUserGroupView.class);
            public static final LinkMultipleResponses<TagView> TAGS_LINK_RESPONSE = new LinkMultipleResponses<TagView>(TAGS_LINK, TagView.class);
            public static final LinkMultipleResponses<ProjectMappingView> PROJECT_MAPPINGS_LINK_RESPONSE = new LinkMultipleResponses<ProjectMappingView>(PROJECT_MAPPINGS_LINK, ProjectMappingView.class);
            public static final LinkMultipleResponses<ProjectJournalView> PROJECT_JOURNAL_LINK_RESPONSE = new LinkMultipleResponses<ProjectJournalView>(PROJECT_JOURNAL_LINK, ProjectJournalView.class);
            public static final LinkMultipleResponses<CustomFieldView> CUSTOM_FIELDS_LINK_RESPONSE = new LinkMultipleResponses<CustomFieldView>(CUSTOM_FIELDS_LINK, CustomFieldView.class);

    static {
            links.put(VERSIONS_LINK, VERSIONS_LINK_RESPONSE);
            links.put(CANONICALVERSION_LINK, CANONICALVERSION_LINK_RESPONSE);
            links.put(ASSIGNABLE_USERS_LINK, ASSIGNABLE_USERS_LINK_RESPONSE);
            links.put(ASSIGNABLE_USERGROUPS_LINK, ASSIGNABLE_USERGROUPS_LINK_RESPONSE);
            links.put(USERS_LINK, USERS_LINK_RESPONSE);
            links.put(USERGROUPS_LINK, USERGROUPS_LINK_RESPONSE);
            links.put(TAGS_LINK, TAGS_LINK_RESPONSE);
            links.put(PROJECT_MAPPINGS_LINK, PROJECT_MAPPINGS_LINK_RESPONSE);
            links.put(PROJECT_JOURNAL_LINK, PROJECT_JOURNAL_LINK_RESPONSE);
            links.put(CUSTOM_FIELDS_LINK, CUSTOM_FIELDS_LINK_RESPONSE);
    }

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
