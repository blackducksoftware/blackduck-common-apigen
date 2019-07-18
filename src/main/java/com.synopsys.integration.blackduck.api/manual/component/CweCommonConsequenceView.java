package com.synopsys.integration.blackduck.api.manual.component;

import com.synopsys.integration.blackduck.api.core.BlackDuckComponent;
import com.synopsys.integration.blackduck.api.manual.enumeration.CweCommonConsequenceScopesEnum;
import com.synopsys.integration.blackduck.api.manual.enumeration.CweCommonConsequenceTechnicalImpactsEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class CweCommonConsequenceView extends BlackDuckComponent {
    private String note;
    private java.util.List<CweCommonConsequenceScopesEnum> scopes;
    private java.util.List<CweCommonConsequenceTechnicalImpactsEnum> technicalImpacts;

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public java.util.List<CweCommonConsequenceScopesEnum> getScopes() {
        return scopes;
    }

    public void setScopes(final java.util.List<CweCommonConsequenceScopesEnum> scopes) {
        this.scopes = scopes;
    }

    public java.util.List<CweCommonConsequenceTechnicalImpactsEnum> getTechnicalImpacts() {
        return technicalImpacts;
    }

    public void setTechnicalImpacts(final java.util.List<CweCommonConsequenceTechnicalImpactsEnum> technicalImpacts) {
        this.technicalImpacts = technicalImpacts;
    }

}
