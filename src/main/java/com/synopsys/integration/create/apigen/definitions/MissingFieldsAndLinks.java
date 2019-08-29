package com.synopsys.integration.create.apigen.definitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

public class MissingFieldsAndLinks {

    private final Map<String, MissingFieldAndLinkHelper> missingFieldAndLinkMap;

    public MissingFieldsAndLinks() {
        this.missingFieldAndLinkMap = populateMissingFieldsAndLinks();
    }

    private Map<String, MissingFieldAndLinkHelper> populateMissingFieldsAndLinks() {
        final Map<String, MissingFieldAndLinkHelper> missingFieldAndLinkMap = new HashMap<>();

        // PolicyRuleViewExpression
        final MissingFieldAndLinkHelper prveFieldsAndLinks = new MissingFieldAndLinkHelper();
        prveFieldsAndLinks.addField(new FieldDefinition("expressions", "List<PolicyRuleViewExpressionExpression>", false));
        missingFieldAndLinkMap.put("PolicyRuleViewExpression", prveFieldsAndLinks);

        // UserView
        final MissingFieldAndLinkHelper uvFieldsAndLinks = new MissingFieldAndLinkHelper();
        uvFieldsAndLinks.addLink(new LinkDefinition("notifications", false));
        uvFieldsAndLinks.addLink(new LinkDefinition("projects", false));
        uvFieldsAndLinks.addLink(new LinkDefinition("roles", false));
        uvFieldsAndLinks.addLink(new LinkDefinition("inherited-roles", false));
        missingFieldAndLinkMap.put("UserView", uvFieldsAndLinks);

        // UserGroupView
        final MissingFieldAndLinkHelper ugvFieldsAndLinks = new MissingFieldAndLinkHelper();
        ugvFieldsAndLinks.addLink(new LinkDefinition("users", false));
        missingFieldAndLinkMap.put("UserGroupView", ugvFieldsAndLinks);

        return missingFieldAndLinkMap;
    }

    public List<FieldDefinition> getMissingFields(final String className) {
        return missingFieldAndLinkMap.getOrDefault(className, new MissingFieldAndLinkHelper()).getMissingFields();
    }

    public List<LinkDefinition> getMissingLinks(final String className) {
        return missingFieldAndLinkMap.getOrDefault(className, new MissingFieldAndLinkHelper()).getMissingLinks();
    }

    public class MissingFieldAndLinkHelper {
        private final List<FieldDefinition> missingFields;
        private final List<LinkDefinition> missingLinks;

        public MissingFieldAndLinkHelper() {
            this.missingFields = new ArrayList<>();
            this.missingLinks = new ArrayList<>();
        }

        public void addField(final FieldDefinition field) {
            missingFields.add(field);
        }

        public void addLink(final LinkDefinition link) {
            missingLinks.add(link);
        }

        public List<FieldDefinition> getMissingFields() {
            return this.missingFields;
        }

        public List<LinkDefinition> getMissingLinks() {
            return this.missingLinks;
        }
    }

}
