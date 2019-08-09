package com.synopsys.integration.create.apigen;

import java.util.Map;

public class ViewMediaVersionHelper {
    private final String viewClass;
    private final Integer mediaVersion;
    private final Map<String, Object> input;

    public ViewMediaVersionHelper(final String viewClass, final Integer mediaVersion, final Map<String, Object> input) {
        this.viewClass = viewClass.replace("ViewV", "View");
        this.mediaVersion = mediaVersion;
        this.input = input;
    }

    public String getViewClass() { return this.viewClass; }

    public Integer getMediaVersion() { return this.mediaVersion; }

    public Map<String, Object> getInput() { return this.input; }

    public String getVersionedClassName() { return this.viewClass + "V" + this.mediaVersion.toString(); }

    public String toString() {
        return this.viewClass + "\n" + this.mediaVersion + "\n\t" + this.input.get("className");
    }

}
