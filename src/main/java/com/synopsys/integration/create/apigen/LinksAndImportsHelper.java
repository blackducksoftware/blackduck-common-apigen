package com.synopsys.integration.create.apigen;

import java.util.List;

public class LinksAndImportsHelper {
    private final List<LinkHelper> links;
    private final List<String> imports;

    public LinksAndImportsHelper(final List<LinkHelper> links, final List<String> imports) {
        this.links = links;
        this.imports = imports;
    }

    public List<LinkHelper> getLinks() { return this.links; }

    public List<String> getImports() { return this.imports; }

}
