package com.synopsys.integration.blackduck.api.generated.view;

    import com.synopsys.integration.blackduck.api.generated.PolicyRuleSeverityEnum;
    import com.synopsys.integration.blackduck.api.generated.PolicyRuleViewExpression;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class PolicyRuleView extends BlackDuckView {
    private String severity;
    private PolicyRuleViewExpression expression;
    private String name;
    private Boolean overridable;
    private String description;
    private Object _meta;
    private Boolean enabled;

    public String getSeverity() {
    return severity;
    }

    public void setSeverity(String severity) {
    this.severity = severity;
    }

    public PolicyRuleViewExpression getExpression() {
    return expression;
    }

    public void setExpression(PolicyRuleViewExpression expression) {
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
