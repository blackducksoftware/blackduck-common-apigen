package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import java.math.BigDecimal;
    import com.synopsys.integration.blackduck.api.generated.enumeration.CustomFieldTypeEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class CustomFieldView extends BlackDuckView {
    private String createdAt;
    private Object updatedBy;
    private Object createdBy;
    private String description;
    private Boolean active;
    private Object _meta;
    private BigDecimal position;
    private String label;
    private CustomFieldTypeEnum type;
    private String updatedAt;

    public String getCreatedAt() {
    return createdAt;
    }

    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    public Object getUpdatedBy() {
    return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
    this.updatedBy = updatedBy;
    }

    public Object getCreatedBy() {
    return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
    this.createdBy = createdBy;
    }

    public String getDescription() {
    return description;
    }

    public void setDescription(String description) {
    this.description = description;
    }

    public Boolean getActive() {
    return active;
    }

    public void setActive(Boolean active) {
    this.active = active;
    }

    public Object get_meta() {
    return _meta;
    }

    public void set_meta(Object _meta) {
    this._meta = _meta;
    }

    public BigDecimal getPosition() {
    return position;
    }

    public void setPosition(BigDecimal position) {
    this.position = position;
    }

    public String getLabel() {
    return label;
    }

    public void setLabel(String label) {
    this.label = label;
    }

    public CustomFieldTypeEnum getType() {
    return type;
    }

    public void setType(CustomFieldTypeEnum type) {
    this.type = type;
    }

    public String getUpdatedAt() {
    return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
    }

}
