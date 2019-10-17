package com.synopsys.integration.create.apigen.generators;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.ImportHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionData;
import com.synopsys.integration.create.apigen.helper.UtilStrings;

import freemarker.template.Configuration;

@Component
public class MediaTypeMapGenerator {

    private final ImportHelper importHelper;
    private final FreeMarkerHelper freeMarkerHelper;
    private final Configuration config;

    public MediaTypeMapGenerator(final ImportHelper importHelper, final FreeMarkerHelper freeMarkerHelper, final Configuration config) {
        this.importHelper = importHelper;
        this.freeMarkerHelper = freeMarkerHelper;
        this.config = config;
    }

    public void generateMediaTypeMap(final Set<MediaVersionData> latestMediaVersions) throws Exception {
        final Map<String, Object> input = new HashMap<>();

        input.put("package", UtilStrings.GENERATED_DISCOVERY_PACKAGE);
        final List<MediaVersionData> sortedLatestMediaVersions = latestMediaVersions.stream().collect(Collectors.toList());
        Collections.sort(sortedLatestMediaVersions);
        input.put("mostRecentClassVersions", sortedLatestMediaVersions);

        final Set<String> imports = new HashSet<>();
        final Set<String> classNames = new HashSet<>();
        for (final MediaVersionData helper : sortedLatestMediaVersions) {
            classNames.add(helper.getNonVersionedClassName());
        }
        for (final String className : classNames) {
            importHelper.addFieldImports(imports, className);
        }
        input.put("imports", imports);

        final File mediaTypeMapBaseDirectory = new File(FreeMarkerHelper.getBaseDirectory(), UtilStrings.DISCOVERY_DIRECTORY_SUFFIX);
        freeMarkerHelper.writeFile("MediaTypeDiscovery", config.getTemplate("mediaTypeDiscovery.ftl"), input, mediaTypeMapBaseDirectory.getAbsolutePath());
    }
}
