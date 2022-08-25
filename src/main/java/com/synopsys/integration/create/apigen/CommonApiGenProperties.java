package com.synopsys.integration.create.apigen;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public class CommonApiGenProperties {

    @Value("${api-gen.path.specification-key:}")
    private String PATH_TO_API_SPECIFICATION;

    @Value("${api-gen.path.generated-directory:}")
    private String PATH_TO_API_GENERATED_DIRECTORY;

    @Value("${api-gen.specification-version:}")
    private String API_SPECIFICATION_VERSION;

    @Value("${media.types.csv-name:minified-media-types.csv}")
    private String MEDIA_TYPES_CSV_NAME;

    private Optional<String> getOptionalString(String value) {
        if (StringUtils.isNotBlank(value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public Optional<String> getPathToApiSpecification(){
        return getOptionalString(PATH_TO_API_SPECIFICATION);
    }

    public Optional<String> getApiVersion(){
        return getOptionalString(API_SPECIFICATION_VERSION);
    }

    public Optional<String> getPathToApiDirectory(){
        return getOptionalString(PATH_TO_API_GENERATED_DIRECTORY);
    }

    public Optional<String> getMediaTypesName(){
        return getOptionalString(MEDIA_TYPES_CSV_NAME);
    }
}

