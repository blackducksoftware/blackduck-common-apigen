package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.core.BlackDuckView;
    import com.synopsys.integration.blackduck.api.generated.enumeration.PolicyRule5SeverityEnum;
    import com.synopsys.integration.blackduck.api.generated.component.PolicyRule5ViewExpression;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class PolicyRule5View extends BlackDuckView {
    private PolicyRule5SeverityEnum severity;
    private PolicyRule5ViewExpression expression;
    private String name;
    private Boolean overridable;
    private String description;
    private Object _meta;
    private Boolean enabled;

    public PolicyRule5SeverityEnum getSeverity() {
    return severity;
    }

    public void setSeverity(PolicyRule5SeverityEnum severity) {
    this.severity = severity;
    }

    public PolicyRule5ViewExpression getExpression() {
    return expression;
    }

    public void setExpression(PolicyRule5ViewExpression expression) {
    this.expression = expression;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public Boolean getOverridable() {
    return overridable;
    }

    public void setOverridable(Boolean overridable) {
    this.overridable = overridable;
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

    public Boolean getEnabled() {
    return enabled;
    }

    public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
    }

}
