package com.synopsys.integration.create.apigen.parser;

import java.util.ArrayList;
import java.util.List;

import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;

public class DudResponseIdentifier {

    private static List<FieldDefinition> getDudFields() {
        final List<FieldDefinition> dudFields = new ArrayList<>();
        final FieldDefinition items = new FieldDefinition("items", "java.util.List<String>", false);
        dudFields.add(items);
        final FieldDefinition meta = new FieldDefinition("_meta", "Object", false);
        dudFields.add(meta);
        final FieldDefinition totalCount = new FieldDefinition("totalCount", "BigDecimal", false);
        dudFields.add(totalCount);

        return dudFields;
    }

    private static List<String> getDudFieldNames() {
        final List<String> dudFieldNames = new ArrayList<>();
        dudFieldNames.add("items");
        dudFieldNames.add("_meta");
        dudFieldNames.add("totalCount");
        return dudFieldNames;
    }

    public static boolean isDudResponse(final ResponseDefinition response) {
        final List<FieldDefinition> dudFields = getDudFields();
        for (final FieldDefinition field : response.getFields()) {
            if (!dudFields.contains(field)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDudResponse2(final ResponseDefinition response) {
        final List<String> dudFieldNames = getDudFieldNames();
        for (final FieldDefinition field : response.getFields()) {
            final String fieldName = field.getPath();
            if (!dudFieldNames.contains(fieldName)) {
                return false;
            }
        }
        return true;
    }

}
