package com.synopsys.integration.create.apigen.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.UtilStrings;
import com.synopsys.integration.create.apigen.model.FieldDefinition;
import com.synopsys.integration.create.apigen.model.FirstPassFieldDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;

@Component
public class FirstPassFieldDefinitionProcessor {

    private FirstPassTypeProcessor firstPassTypeProcessor;
    private FieldDefinitionPathProcessor pathProcessor;

    public FirstPassFieldDefinitionProcessor(FirstPassTypeProcessor firstPassTypeProcessor, FieldDefinitionPathProcessor pathProcessor) {
        this.firstPassTypeProcessor = firstPassTypeProcessor;
        this.pathProcessor = pathProcessor;
    }

    public Set<FirstPassFieldDefinition> processFirstPassDefinitions(String parentDefinitionName, Set<RawFieldDefinition> rawFieldDefinitions) {
        final Set<FirstPassFieldDefinition> firstPassDefinitions = new HashSet<>();
        for (final RawFieldDefinition rawField : rawFieldDefinitions) {
            // Ignore 'data' and '_meta' fields
            if (rawField.getPath().equals(UtilStrings.DATA) || rawField.getPath().equals(UtilStrings.META)) {
                continue;
            }

            String processedPath = pathProcessor.processedPath(rawField.getPath());
            String firstPassType = firstPassTypeProcessor.processFirstPassType(rawField, parentDefinitionName, processedPath);
            final FirstPassFieldDefinition firstPassDefinition = new FirstPassFieldDefinition(firstPassType, processedPath, rawField);

            if (rawField.getSubFields() != null) {
                // If field has subfields, recursively process and add its subfields
                final Set<FirstPassFieldDefinition> subFields = processFirstPassDefinitions(NameParser.stripListNotation(firstPassDefinition.getFirstPassType()), rawField.getSubFields());
                firstPassDefinition.addSubFields(subFields);
            }
            firstPassDefinitions.add(firstPassDefinition);
        }
        return firstPassDefinitions;
    }
}
