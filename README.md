# Overview
This produces a command line utility to generate Java code from the API documentation of Black Duck to be used in [blackduck-common-api](https://github.com/blackducksoftware/blackduck-common-api)

# Build

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Compile 
```
./gradlew clean build
```

## Running on the Command Line
```
./gradlew bootRun -Dspring-boot.run.arguments=--api.gen.input.path=<DIRECTORY_WITH_API_FILES>,--api.gen.output.path=<OUTPUT_DIRECTORY>
```

# Where can I get the latest release?

https://github.com/blackducksoftware/blackduck-common-apigen/releases
