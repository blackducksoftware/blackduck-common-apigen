package com.synopsys.integration.create.apigen.model;

import java.util.ArrayList;
import java.util.List;

public class DudResponseIdentifier {

    private List<FieldDefinition> getDudFields() {
        final List<FieldDefinition> dudFields = new ArrayList<>();
        final FieldDefinition items = new FieldDefinition("items[]", "Array", false);
        dudFields.add(items);
        final FieldDefinition meta = new FieldDefinition("_meta", "Object", false);
        dudFields.add(meta);
        final FieldDefinition totalCount = new FieldDefinition("totalCount", "Number", false);
        dudFields.add(totalCount);

        return dudFields;
    }

    public boolean isDudResponse(final ResponseDefinition response) {
        final List<FieldDefinition> dudFields = getDudFields();
        for (final FieldDefinition field : response.getFields()) {
            if (!dudFields.contains(field)) {
                return false;
            }
        }
        return true;
    }

}
