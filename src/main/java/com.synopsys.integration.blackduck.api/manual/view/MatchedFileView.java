package com.synopsys.integration.blackduck.api.manual.view;

import java.util.HashMap;
import java.util.Map;

import com.synopsys.integration.blackduck.api.core.BlackDuckResponse;
import com.synopsys.integration.blackduck.api.core.LinkResponse;
import com.synopsys.integration.blackduck.api.core.LinkSingleResponse;
import com.synopsys.integration.blackduck.api.generated.view.CodeLocationView;
import com.synopsys.integration.blackduck.api.manual.component.CompositePathWithArchiveContext;
import com.synopsys.integration.blackduck.api.manual.enumeration.MatchedFileUsagesEnum;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class MatchedFileView extends BlackDuckResponse {
    public static final Map<String, LinkResponse> links = new HashMap<>();

    public static final String CODELOCATIONS_LINK = "codelocations";

    public static final LinkSingleResponse<CodeLocationView> CODELOCATIONS_LINK_RESPONSE = new LinkSingleResponse<CodeLocationView>(CODELOCATIONS_LINK, CodeLocationView.class);

    static {
        links.put(CODELOCATIONS_LINK, CODELOCATIONS_LINK_RESPONSE);
    }

    private CompositePathWithArchiveContext filePath;
    private java.util.List<MatchedFileUsagesEnum> usages;

    public CompositePathWithArchiveContext getFilePath() {
        return filePath;
    }

    public void setFilePath(final CompositePathWithArchiveContext filePath) {
        this.filePath = filePath;
    }

    public java.util.List<MatchedFileUsagesEnum> getUsages() {
        return usages;
    }

    public void setUsages(final java.util.List<MatchedFileUsagesEnum> usages) {
        this.usages = usages;
    }
}
