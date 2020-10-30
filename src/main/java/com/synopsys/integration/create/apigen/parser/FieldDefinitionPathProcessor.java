package com.synopsys.integration.create.apigen.parser;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.data.UtilStrings;

@Component
public class FieldDefinitionPathProcessor {

    private static final Set<String> javaKeyWords = UtilStrings.getJavaKeyWords();

    public String processedPath(String path) {
        if (javaKeyWords.contains(path)) {
            path = path + "_";
        }
        return path.replace("[]", "");
    }
}
