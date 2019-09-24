package com.synopsys.integration.create.apigen.model;

import com.synopsys.integration.create.apigen.helper.UtilStrings;

public abstract class DefinitionParseParameters<T extends ThirdPartyDefinition> {

    public abstract String getJsonField();

    public abstract Class<T> getResultClass();

    public static DefinitionParseParameters<RawFieldDefinition> RAW_FIELD_PARAMETERS = new DefinitionParseParameters<RawFieldDefinition>() {
        @Override
        public String getJsonField() {
            return UtilStrings.FIELDS;
        }

        @Override
        public Class<RawFieldDefinition> getResultClass() {
            return RawFieldDefinition.class;
        }
    };

    public static DefinitionParseParameters<LinkDefinition> LINK_PARAMETERS = new DefinitionParseParameters<LinkDefinition>() {
        @Override
        public String getJsonField() {
            return UtilStrings.LINKS;
        }

        @Override
        public Class<LinkDefinition> getResultClass() {
            return LinkDefinition.class;
        }
    };
}
