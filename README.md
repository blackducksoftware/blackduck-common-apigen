# Overview
This program produces Java code from the API documentation of Black Duck to be used in [blackduck-common-api](https://github.com/blackducksoftware/blackduck-common-api)

# To use

### API Specification Source
Download API specification source from [HERE](https://artifactory.internal.synopsys.com/ui/repos/tree/General/bds-hub%2Fcom%2Fblackducksoftware%2Fhub%2Fapi-specification).
##### Note: 
If source is in src/main/resources, you need only specify the _version_ of the API.

### Environment Variables
```bash
# The location of the unzipped API specification source, or the path to the API spec zip
export APIGEN_SPECIFICATION_API_PATH=<spec_src>
# The directory the generated files will be written to, should be blackduck-common-api/.../generated
export APIGEN_GENERATED_DIRECTORY_PATH=<output_dir>
# Optional: As an alternative to using the APIGEN_SPECIFICATION_API_PATH variable, 
# you can specify a version of the API (if specification source is already in src/main/resources).
export APIGEN_SPECIFICATION_VERSION=<version_string>
# Optional: The directory in which to generate a maintenance report.
export APIGEN_MAINTENANCE_REPORT_PATH=<maintenance_report_dir>
```

### Adding new BlackDuckResponse's
All views will be BlackDuckComponent by default. To change BlackDuckComponent's to BlackDuckResponse's, ClassCategories must be modified.

### Adding new LinkResponse's
To add a new LinkResponse to a view, LinkResponseDefinitions needs to be modified to add the new link to the view.

### Generate Files
Once the environment variables are set, run the following command in the project's root directory: 
```bash
./gradlew bootRun
```

# Build

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Compile 
```
./gradlew clean build
```

# Where can I get the latest release?

https://github.com/blackducksoftware/blackduck-common-apigen/releases
