package com.synopsys.integration.blackduck.api.generated.view;

    import java.util.HashMap;
    import java.util.Map;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import com.synopsys.integration.blackduck.api.generated.component.ProjectVersionViewLicense;
    import com.synopsys.integration.blackduck.api.core.LinkResponse;
    import com.synopsys.integration.blackduck.api.core.LinkMultipleResponses;
    import com.synopsys.integration.blackduck.api.manual.view.ReportView;
    import com.synopsys.integration.blackduck.api.manual.view.ReportView;
    import com.synopsys.integration.blackduck.api.core.LinkSingleResponse;
    import com.synopsys.integration.blackduck.api.manual.response.VersionRiskProfileView;
    import com.synopsys.integration.blackduck.api.manual.view.VersionBomComponentView;
    import com.synopsys.integration.blackduck.api.manual.response.HierarchicalVersionBomComponentView;
    import com.synopsys.integration.blackduck.api.manual.view.VulnerableComponentView;
    import com.synopsys.integration.blackduck.api.manual.response.VersionBomComponentDiffView;
    import com.synopsys.integration.blackduck.api.generated.view.ProjectView;
    import com.synopsys.integration.blackduck.api.generated.view.CustomFieldView;
    import com.synopsys.integration.blackduck.api.manual.view.VersionBomPolicyStatusView;
    import com.synopsys.integration.blackduck.api.generated.response.VersionBomAttachmentView;
    import com.synopsys.integration.blackduck.api.generated.view.CodeLocationView;
    import com.synopsys.integration.blackduck.api.manual.view.IssueView;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ProjectVersionView extends BlackDuckView {
    public static final Map
    <String, LinkResponse> links = new HashMap<>();

        public static final String VERSIONREPORT_LINK = "versionReport";
        public static final String LICENSEREPORTS_LINK = "licenseReports";
        public static final String RISKPROFILE_LINK = "riskProfile";
        public static final String COMPONENTS_LINK = "components";
        public static final String HIERARCHICAL_COMPONENTS_LINK = "hierarchical-components";
        public static final String VULNERABLE_COMPONENTS_LINK = "vulnerable-components";
        public static final String COMPARISON_LINK = "comparison";
        public static final String PROJECT_LINK = "project";
        public static final String CUSTOM_FIELDS_LINK = "custom-fields";
        public static final String POLICY_STATUS_LINK = "policy-status";
        public static final String ATTACHMENTS_LINK = "attachments";
        public static final String CODELOCATIONS_LINK = "codelocations";
        public static final String ISSUES_LINK = "issues";

            public static final LinkMultipleResponses<ReportView> VERSIONREPORT_LINK_RESPONSE = new LinkMultipleResponses<ReportView>(VERSIONREPORT_LINK, ReportView.class);
            public static final LinkMultipleResponses<ReportView> LICENSEREPORTS_LINK_RESPONSE = new LinkMultipleResponses<ReportView>(LICENSEREPORTS_LINK, ReportView.class);
            public static final LinkSingleResponse<VersionRiskProfileView> RISKPROFILE_LINK_RESPONSE = new LinkSingleResponse<VersionRiskProfileView>(RISKPROFILE_LINK, VersionRiskProfileView.class);
            public static final LinkMultipleResponses<VersionBomComponentView> COMPONENTS_LINK_RESPONSE = new LinkMultipleResponses<VersionBomComponentView>(COMPONENTS_LINK, VersionBomComponentView.class);
            public static final LinkMultipleResponses<HierarchicalVersionBomComponentView> HIERARCHICAL_COMPONENTS_LINK_RESPONSE = new LinkMultipleResponses<HierarchicalVersionBomComponentView>(HIERARCHICAL_COMPONENTS_LINK, HierarchicalVersionBomComponentView.class);
            public static final LinkMultipleResponses<VulnerableComponentView> VULNERABLE_COMPONENTS_LINK_RESPONSE = new LinkMultipleResponses<VulnerableComponentView>(VULNERABLE_COMPONENTS_LINK, VulnerableComponentView.class);
            public static final LinkMultipleResponses<VersionBomComponentDiffView> COMPARISON_LINK_RESPONSE = new LinkMultipleResponses<VersionBomComponentDiffView>(COMPARISON_LINK, VersionBomComponentDiffView.class);
            public static final LinkSingleResponse<ProjectView> PROJECT_LINK_RESPONSE = new LinkSingleResponse<ProjectView>(PROJECT_LINK, ProjectView.class);
            public static final LinkMultipleResponses<CustomFieldView> CUSTOM_FIELDS_LINK_RESPONSE = new LinkMultipleResponses<CustomFieldView>(CUSTOM_FIELDS_LINK, CustomFieldView.class);
            public static final LinkSingleResponse<VersionBomPolicyStatusView> POLICY_STATUS_LINK_RESPONSE = new LinkSingleResponse<VersionBomPolicyStatusView>(POLICY_STATUS_LINK, VersionBomPolicyStatusView.class);
            public static final LinkMultipleResponses<VersionBomAttachmentView> ATTACHMENTS_LINK_RESPONSE = new LinkMultipleResponses<VersionBomAttachmentView>(ATTACHMENTS_LINK, VersionBomAttachmentView.class);
            public static final LinkMultipleResponses<CodeLocationView> CODELOCATIONS_LINK_RESPONSE = new LinkMultipleResponses<CodeLocationView>(CODELOCATIONS_LINK, CodeLocationView.class);
            public static final LinkMultipleResponses<IssueView> ISSUES_LINK_RESPONSE = new LinkMultipleResponses<IssueView>(ISSUES_LINK, IssueView.class);

    static {
            links.put(VERSIONREPORT_LINK, VERSIONREPORT_LINK_RESPONSE);
            links.put(LICENSEREPORTS_LINK, LICENSEREPORTS_LINK_RESPONSE);
            links.put(RISKPROFILE_LINK, RISKPROFILE_LINK_RESPONSE);
            links.put(COMPONENTS_LINK, COMPONENTS_LINK_RESPONSE);
            links.put(HIERARCHICAL_COMPONENTS_LINK, HIERARCHICAL_COMPONENTS_LINK_RESPONSE);
            links.put(VULNERABLE_COMPONENTS_LINK, VULNERABLE_COMPONENTS_LINK_RESPONSE);
            links.put(COMPARISON_LINK, COMPARISON_LINK_RESPONSE);
            links.put(PROJECT_LINK, PROJECT_LINK_RESPONSE);
            links.put(CUSTOM_FIELDS_LINK, CUSTOM_FIELDS_LINK_RESPONSE);
            links.put(POLICY_STATUS_LINK, POLICY_STATUS_LINK_RESPONSE);
            links.put(ATTACHMENTS_LINK, ATTACHMENTS_LINK_RESPONSE);
            links.put(CODELOCATIONS_LINK, CODELOCATIONS_LINK_RESPONSE);
            links.put(ISSUES_LINK, ISSUES_LINK_RESPONSE);
    }

    private String phase;
    private String createdByUser;
    private String settingUpdatedBy;
    private Object _meta;
    private String source;
    private String versionName;
    private String distribution;
    private String releasedOn;
    private String releaseComments;
    private ProjectVersionViewLicense license;
    private String createdAt;
    private String createdBy;
    private String nickname;
    private String settingUpdatedAt;
    private String settingUpdatedByUser;

    public String getPhase() {
    return phase;
    }

    public void setPhase(String phase) {
    this.phase = phase;
    }

    public String getCreatedByUser() {
    return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
    this.createdByUser = createdByUser;
    }

    public String getSettingUpdatedBy() {
    return settingUpdatedBy;
    }

    public void setSettingUpdatedBy(String settingUpdatedBy) {
    this.settingUpdatedBy = settingUpdatedBy;
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

    public String getVersionName() {
    return versionName;
    }

    public void setVersionName(String versionName) {
    this.versionName = versionName;
    }

    public String getDistribution() {
    return distribution;
    }

    public void setDistribution(String distribution) {
    this.distribution = distribution;
    }

    public String getReleasedOn() {
    return releasedOn;
    }

    public void setReleasedOn(String releasedOn) {
    this.releasedOn = releasedOn;
    }

    public String getReleaseComments() {
    return releaseComments;
    }

    public void setReleaseComments(String releaseComments) {
    this.releaseComments = releaseComments;
    }

    public ProjectVersionViewLicense getLicense() {
    return license;
    }

    public void setLicense(ProjectVersionViewLicense license) {
    this.license = license;
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

    public String getNickname() {
    return nickname;
    }

    public void setNickname(String nickname) {
    this.nickname = nickname;
    }

    public String getSettingUpdatedAt() {
    return settingUpdatedAt;
    }

    public void setSettingUpdatedAt(String settingUpdatedAt) {
    this.settingUpdatedAt = settingUpdatedAt;
    }

    public String getSettingUpdatedByUser() {
    return settingUpdatedByUser;
    }

    public void setSettingUpdatedByUser(String settingUpdatedByUser) {
    this.settingUpdatedByUser = settingUpdatedByUser;
    }

}
