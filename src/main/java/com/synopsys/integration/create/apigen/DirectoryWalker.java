package com.synopsys.integration.create.apigen;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synopsys.integration.create.apigen.model.LinkDefinition;
import com.synopsys.integration.create.apigen.model.RawFieldDefinition;
import com.synopsys.integration.create.apigen.model.ResponseDefinition;
import com.synopsys.integration.create.apigen.parser.FieldsParser;
import com.synopsys.integration.create.apigen.parser.LinksParser;
import com.synopsys.integration.create.apigen.parser.ResponseParser;

public class DirectoryWalker {
    private final File rootDirectory;
    private final Gson gson;

    public DirectoryWalker(final File rootDirectory, final Gson gson) {
        this.rootDirectory = rootDirectory;
        this.gson = gson;
    }

    public List<ResponseDefinition> parseDirectoryForObjects(final boolean showOutput) {
        final ResponseParser responseParser = new ResponseParser();
        final FieldsParser fieldsParser = new FieldsParser();
        final LinksParser linksParser = new LinksParser();

        // Get response-specification.json files from directory
        final List<ResponseDefinition> responseDefinitions = responseParser.parseResponses(rootDirectory);

        // For each response file, parse the JSON for FieldDefinition objects
        for (final ResponseDefinition response : responseDefinitions) {
            final String absolutePath = rootDirectory.getAbsolutePath() + "/endpoints/api/" + response.getResponseSpecificationPath();
            final File responseSpecificationFile = new File(absolutePath);

            final List<RawFieldDefinition> fields = fieldsParser.getFieldsAsRawFieldDefinitions(gson, responseSpecificationFile);
            final List<LinkDefinition> links = linksParser.getLinksAsLinkDefinition(gson, responseSpecificationFile);

            response.addFields(fieldsParser.parseFieldDefinitions(response.getName(), fields));
            response.addLinks(links);
            if (showOutput) {
                System.out.println("***********************\n" + gson.toJson(response));
            }
        }
        return responseDefinitions;
    }

    public static void main(final String[] args) throws URISyntaxException {
        // Replace with environment variable
        final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        final URL rootDirectory = DirectoryWalker.class.getClassLoader().getResource(Application.API_SPECIFICATION_VERSION);
        final DirectoryWalker directoryWalker = new DirectoryWalker(new File(rootDirectory.toURI()), gson);
        final List<ResponseDefinition> responses = directoryWalker.parseDirectoryForObjects(true);
    }
}
