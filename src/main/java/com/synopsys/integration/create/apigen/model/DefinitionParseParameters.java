package com.synopsys.integration.create.apigen.model;

public abstract class DefinitionParseParameters<T extends ThirdPartyDefinition> {

    public abstract String getJsonField();

    public abstract Class<T> getResultClass();

    public static DefinitionParseParameters<RawFieldDefinition> RAW_FIELD_PARAMETERS = new DefinitionParseParameters<RawFieldDefinition>() {
        @Override
        public String getJsonField() {
            return "fields";
        }

        @Override
        public Class<RawFieldDefinition> getResultClass() {
            return RawFieldDefinition.class;
        }
    };

    public static DefinitionParseParameters<LinkDefinition> LINK_PARAMETERS = new DefinitionParseParameters<LinkDefinition>() {
        @Override
        public String getJsonField() {
            return "links";
        }

        @Override
        public Class<LinkDefinition> getResultClass() {
            return LinkDefinition.class;
        }
    };
}
