package com.synopsys.integration.blackduck.api.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.synopsys.integration.blackduck.api.manual.component.ResourceLink;
import com.synopsys.integration.blackduck.api.manual.component.ResourceMetadata;

/**
 * A marker class used when a BlackDuckResponse has the '_meta' property which, for now, must be determined manually by actually performing requests against Hub endpoints.
 */
public class BlackDuckView extends BlackDuckResponse {
    private ResourceMetadata _meta;

    public ResourceMetadata getMeta() {
        return _meta;
    }

    public void setMeta(final ResourceMetadata meta) {
        _meta = meta;
    }

    public boolean hasLink(final String linkKey) {
        if (null == _meta || null == _meta.getLinks()) {
            return false;
        }

        return _meta.getLinks().stream()
                   .map(ResourceLink::getRel)
                   .anyMatch(linkKey::equals);
    }

    public Optional<String> getFirstLink(final String linkKey) {
        if (null == _meta || null == _meta.getLinks()) {
            return Optional.empty();
        }

        return _meta.getLinks().stream()
                   .filter(resourceLink -> resourceLink.getRel().equals(linkKey))
                   .findFirst()
                   .map(ResourceLink::getHref);
    }

    public List<String> getLinks(final String linkKey) {
        return getResourceLinks().stream()
                   .map(ResourceLink::getHref)
                   .collect(Collectors.toList());
    }

    public Optional<ResourceMetadata> getResourceMetadata() {
        if (null == _meta) {
            return Optional.empty();
        }

        return Optional.of(_meta);
    }

    public List<ResourceLink> getResourceLinks() {
        if (null == _meta || null == _meta.getLinks()) {
            return Collections.emptyList();
        }

        return _meta.getLinks();
    }

    public List<String> getAvailableLinks() {
        return getResourceLinks().stream()
                   .map(ResourceLink::getRel)
                   .collect(Collectors.toList());
    }

    public List<String> getAllowedMethods() {
        if (null == _meta || null == _meta.getAllow()) {
            return Collections.emptyList();
        }

        return _meta.getAllow();
    }

    public Optional<String> getHref() {
        if (null == _meta || null == _meta.getHref()) {
            return Optional.empty();
        }

        return Optional.of(_meta.getHref());
    }

}
