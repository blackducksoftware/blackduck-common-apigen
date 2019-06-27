package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import java.math.BigDecimal;
    import com.synopsys.integration.blackduck.api.generated.enumeration.ComponentCustomFieldTypeEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class ComponentCustomFieldView extends BlackDuckView {
    private java.util.List<String> values;
    private String description;
    private Boolean active;
    private Object _meta;
    private BigDecimal position;
    private String label;
    private ComponentCustomFieldTypeEnum type;

    public java.util.List<String> getValues() {
    return values;
    }

    public void setValues(java.util.List<String> values) {
    this.values = values;
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

    public ComponentCustomFieldTypeEnum getType() {
    return type;
    }

    public void setType(ComponentCustomFieldTypeEnum type) {
    this.type = type;
    }

}
