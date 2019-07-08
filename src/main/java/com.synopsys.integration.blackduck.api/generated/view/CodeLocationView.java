package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import java.math.BigDecimal;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class CodeLocationView extends BlackDuckView {
    private String createdAt;
    private BigDecimal scanSize;
    private String mappedProjectVersion;
    private String name;
    private Object _meta;
    private String url;
    private String updatedAt;

    public String getCreatedAt() {
    return createdAt;
    }

    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    public BigDecimal getScanSize() {
    return scanSize;
    }

    public void setScanSize(BigDecimal scanSize) {
    this.scanSize = scanSize;
    }

    public String getMappedProjectVersion() {
    return mappedProjectVersion;
    }

    public void setMappedProjectVersion(String mappedProjectVersion) {
    this.mappedProjectVersion = mappedProjectVersion;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Object get_meta() {
    return _meta;
    }

    public void set_meta(Object _meta) {
    this._meta = _meta;
    }

    public String getUrl() {
    return url;
    }

    public void setUrl(String url) {
    this.url = url;
    }

    public String getUpdatedAt() {
    return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    }

}
