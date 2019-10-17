package com.synopsys.integration.create.apigen.generators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.synopsys.integration.create.apigen.definitions.ClassCategories;
import com.synopsys.integration.create.apigen.definitions.ClassTypeEnum;
import com.synopsys.integration.create.apigen.helper.DataManager;
import com.synopsys.integration.create.apigen.helper.FreeMarkerHelper;
import com.synopsys.integration.create.apigen.helper.MediaVersionData;
import com.synopsys.integration.create.apigen.helper.MediaVersions;
import com.synopsys.integration.create.apigen.helper.UtilStrings;
import com.synopsys.integration.create.apigen.parser.NameParser;

import freemarker.template.Template;

@Component
public class MediaVersionGenerator {

    private final MediaVersions mediaVersions;
    private final MediaTypeMapGenerator mediaTypeMapGenerator;
    private final ClassCategories classCategories;
    private final FreeMarkerHelper freeMarkerHelper;
    private final DataManager dataManager;

    public MediaVersionGenerator(final MediaVersions mediaVersions, final MediaTypeMapGenerator mediaTypeMapGenerator, final ClassCategories classCategories, final FreeMarkerHelper freeMarkerHelper,
        final DataManager dataManager) {
        this.mediaVersions = mediaVersions;
        this.mediaTypeMapGenerator = mediaTypeMapGenerator;
        this.classCategories = classCategories;
        this.freeMarkerHelper = freeMarkerHelper;
        this.dataManager = dataManager;
    }

    public void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToViewFiles, final String pathToComponentFiles)
        throws Exception {
        final Collection<MediaVersionData> latestViewMediaVersions = mediaVersions.getLatestViewMediaVersions().values();
        final Collection<MediaVersionData> latestComponentMediaVersions = mediaVersions.getLatestComponentMediaVersions().values();

        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToViewFiles, latestViewMediaVersions);
        generateMostRecentViewAndComponentMediaVersions(randomTemplate, pathToComponentFiles, latestComponentMediaVersions);

        final Set<MediaVersionData> latestMediaVersions = new HashSet<>();
        latestMediaVersions.addAll(latestComponentMediaVersions);
        latestMediaVersions.addAll(latestMediaVersions);
        mediaTypeMapGenerator.generateMediaTypeMap(latestMediaVersions);
    }

    private void generateMostRecentViewAndComponentMediaVersions(final Template randomTemplate, final String pathToFiles, final Collection<MediaVersionData> latestMediaVersions) throws Exception {
        for (final MediaVersionData latestMediaVersion : latestMediaVersions) {
            final Map<String, Object> input = latestMediaVersion.getInput();
            final String className = latestMediaVersion.getNonVersionedClassName();
            input.put(UtilStrings.CLASS_NAME, className);
            input.put(UtilStrings.PARENT_CLASS, latestMediaVersion.getVersionedClassName());
            try {
                final ClassTypeEnum classType = classCategories.computeType(className);
                final String importClass = classType.getImportClass().get();

                final String importPath = UtilStrings.CORE_CLASS_PATH_PREFIX + importClass;
                input.put(UtilStrings.IMPORT_PATH, importPath);
                freeMarkerHelper.writeFile(className, randomTemplate, input, pathToFiles);
            } catch (final NoSuchElementException e) {
                System.out.println("Class not categorized");
            }
            dataManager.addNonLinkClassName(className);
            dataManager.addNonLinkClassName(NameParser.getNonVersionedName(className));
        }
    }
}
