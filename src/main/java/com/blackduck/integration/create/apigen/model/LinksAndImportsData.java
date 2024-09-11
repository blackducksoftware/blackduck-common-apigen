/*
 * blackduck-common-apigen
 *
 * Copyright (c) 2024 Black Duck Software, Inc.
 *
 * Use subject to the terms and conditions of the Black Duck Software End User Software License and Maintenance Agreement. All rights reserved worldwide.
 */
package com.blackduck.integration.create.apigen.model;

import java.util.Set;

public class LinksAndImportsData {
    private final Set<LinkData> links;
    private final Set<String> imports;

    public LinksAndImportsData(final Set<LinkData> links, final Set<String> imports) {
        this.links = links;
        this.imports = imports;
    }

    public Set<LinkData> getLinks() { return this.links; }

    public Set<String> getImports() { return this.imports; }

}
