package com.synopsys.integration.create.apigen.parser;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
public class FirstPassTypeProcessor {

    private static final Set<String> dateSuffixes = UtilStrings.getDateSuffixes();

    public String processFirstPassType(RawFieldDefinition rawFieldDefinition, String parentDefinitionName, String path) {
        String rawPath = rawFieldDefinition.getPath();
        String rawType = rawFieldDefinition.getType();
        final String mediaVersion = NameParser.getMediaVersionFromResponseName(parentDefinitionName);
        final String nonVersionedParentDefinitionName = NameParser.getNonVersionedName(parentDefinitionName);
        // shouldBeVersioned will be true for generated types (aside from duplicate overrides), and false for common/non-generated types (ex. String)
        boolean shouldBeVersioned = false;
        String processedType = rawType;

        // Handle fields of type 'Number'
        if (processedType.equals(UtilStrings.NUMBER)) {
            processedType = UtilStrings.BIG_DECIMAL;
        }

        // Handle Date fields
        for (String dateSuffix : dateSuffixes) {
            if (rawPath.endsWith(dateSuffix)) {
                processedType = UtilStrings.JAVA_DATE;
            }
        }

        // Handle objects that have subfields
        if (rawFieldDefinition.getSubFields() != null) {
            // append path (name) of field to create new type
            processedType = NameParser.reorderViewInName(nonVersionedParentDefinitionName + StringUtils.capitalize(path));
            shouldBeVersioned = true;
        }

        // Handle enums (field definitions that have a set of allowed values)
        if (rawFieldDefinition.getAllowedValues() != null) {
            // If it is an enum with integer values, it is just an integer
            if (NumberUtils.isCreatable(rawFieldDefinition.getAllowedValues().iterator().next())) {
                processedType = UtilStrings.INTEGER;
            } else {
                processedType = nonVersionedParentDefinitionName.replace("View", "") + StringUtils.capitalize(path) + UtilStrings.ENUM;
                shouldBeVersioned = true;
            }
        }

        // Append media version
        if (mediaVersion != null && shouldBeVersioned) {
            processedType = String.format("%sV%s", processedType, mediaVersion);
        }

        // Appropriately wrap list types
        if (rawType.equals(UtilStrings.ARRAY)) {
            final String coreType = processedType.equals(UtilStrings.ARRAY) ? UtilStrings.STRING : processedType;
            processedType = "java.util.List<" + coreType + ">";
        }

        return processedType;
    }
}
