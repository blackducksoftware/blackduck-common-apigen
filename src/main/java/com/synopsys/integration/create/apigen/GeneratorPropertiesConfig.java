package com.synopsys.integration.create.apigen;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorPropertiesConfig {
    // TODO @Value can also be used to read things as java.io.File
    @Value("${com.synopsys.integration.create.apigen.spec.path:}")
    public String apiSpecInputPath;

    @Value("${com.synopsys.integration.create.apigen.output.path:}")
    public String generatorOutputPath;

    @Value("${com.synopsys.integration.create.apigen.spec.version:}")
    public String apiSpecVersion;

    @Value("${com.synopsys.integration.create.apigen.maintenance.report.path:}")
    public String maintenanceReportPath;

}
