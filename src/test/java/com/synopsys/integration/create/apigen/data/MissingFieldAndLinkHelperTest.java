package com.synopsys.integration.create.apigen.data;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.LinkDefinition;

class MissingFieldAndLinkHelperTest {
    @Test
    void fieldsTest() {
        MissingFieldAndLinkHelper missingFieldAndLinkHelper = new MissingFieldAndLinkHelper();
        FieldDefinition fieldDefinition = new FieldDefinition("", "ComponentsView", false, null, false);
        FieldDefinition fieldDefinition2 = new FieldDefinition("", "giraffe", false, null, false);
        FieldDefinition fieldDefinition3 = new FieldDefinition("", "duck", false, null, false);
        FieldDefinition fieldDefinitionDup = new FieldDefinition("", "ComponentsView", false, null, false);

        missingFieldAndLinkHelper.addField(fieldDefinition);
        missingFieldAndLinkHelper.addField(fieldDefinition2);
        missingFieldAndLinkHelper.addField(fieldDefinition3);
        missingFieldAndLinkHelper.addField(fieldDefinitionDup);

        Set<FieldDefinition> fieldDefinitions = missingFieldAndLinkHelper.getMissingFields();

        assertEquals(3, fieldDefinitions.size());

        Set<FieldDefinition> missingFields = new HashSet<>();
        missingFields.add(fieldDefinition);
        missingFields.add(fieldDefinition2);
        missingFields.add(fieldDefinition3);
        missingFields.add(fieldDefinitionDup);
        assertEquals(fieldDefinitions, missingFields);
    }

    @Test
    void linksTest() {
        MissingFieldAndLinkHelper missingFieldAndLinkHelper2 = new MissingFieldAndLinkHelper();
        LinkDefinition linkDefinition = new LinkDefinition("text");
        LinkDefinition linkDefinitionDup = new LinkDefinition("text");
        LinkDefinition linkDefinitionDup2 = new LinkDefinition("text");
        LinkDefinition linkDefinition2 = new LinkDefinition("giraffe");

        missingFieldAndLinkHelper2.addLink(linkDefinition);
        missingFieldAndLinkHelper2.addLink(linkDefinitionDup);
        missingFieldAndLinkHelper2.addLink(linkDefinitionDup2);
        missingFieldAndLinkHelper2.addLink(linkDefinition2);

        Set<LinkDefinition> linkDefinitions = missingFieldAndLinkHelper2.getMissingLinks();

        assertEquals(2, linkDefinitions.size());

        Set<LinkDefinition> missingLinks = new HashSet<>();
        missingLinks.add(linkDefinition);
        missingLinks.add(linkDefinitionDup);
        missingLinks.add(linkDefinitionDup2);
        missingLinks.add(linkDefinition2);
        assertEquals(missingLinks, linkDefinitions);
    }
}
