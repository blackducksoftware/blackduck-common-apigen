package com.synopsys.integration.create.apigen.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class ResponseTypeIdentifierTest {

    private ResponseTypeIdentifier responseTypeIdentifier = new ResponseTypeIdentifier();

    @Test
    public void testIdentifiesArrayResponse() {
        ResponseDefinition responseDefinition = new ResponseDefinition("", "", "", false);
        responseDefinition.addField(new FieldDefinition("items", "", false, false));
        responseDefinition.addField(new FieldDefinition("_meta", "", false, false));
        responseDefinition.addField(new FieldDefinition("totalCount", "", false, false));

        assertEquals(ResponseType.ARRAY, responseTypeIdentifier.getResponseType(responseDefinition));
    }

    @Test
    public void testIdentifiesDataIsSubfieldOfItems() {
        ResponseDefinition responseDefinition = new ResponseDefinition("", "", "", false);
        FieldDefinition items = new FieldDefinition("items", "", false, false);
        FieldDefinition itemsSubfield = new FieldDefinition("", "", false, false);
        items.addSubFields(Collections.singleton(itemsSubfield));
        responseDefinition.addField(items);
        responseDefinition.addField(new FieldDefinition("_meta", "", false, false));
        responseDefinition.addField(new FieldDefinition("totalCount", "", false, false));

        assertEquals(ResponseType.DATA_IS_SUBFIELD_OF_ITEMS, responseTypeIdentifier.getResponseType(responseDefinition));
    }

    @Test
    public void testIdentifiesStandardResponse() {
        ResponseDefinition responseDefinition = new ResponseDefinition("", "", "", false);
        FieldDefinition items = new FieldDefinition("items", "", false, false);
        FieldDefinition itemsSubfield = new FieldDefinition("", "", false, false);
        items.addSubFields(Collections.singleton(itemsSubfield));
        responseDefinition.addField(items);
        responseDefinition.addField(new FieldDefinition("_meta", "", false, false));
        responseDefinition.addField(new FieldDefinition("totalCount", "", false, false));
        responseDefinition.addField(new FieldDefinition("legit", "Object", false, false));

        assertEquals(ResponseType.STANDARD, responseTypeIdentifier.getResponseType(responseDefinition));
    }
}
