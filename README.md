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

### Generate Files
Once the environment variables are set, run the following command in the project's root directory: 
```bash
./gradlew bootRun
```
##### Notes:
* If a generated directory already exists at APIGEN_GENERATED_DIRECTORY_PATH, it should be deleted prior to a new run of the generator.
* For API specification versions <2020.12.0, the specification file at endpoints/api/projects/projectId/versions/projectVersionId/comparison/GET/<latest media type>/response-specification.json contains two "items" fields.  One is an array of objects, one is of type "Array" but has no fields.  Delete the one that has no fields prior to running the generator.

# Build

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Compile 
```
./gradlew clean build
```

# Where can I get the latest release?

https://github.com/blackducksoftware/blackduck-common-apigen/releases

# Maintenance

## Manual Data
Several aspects of the generation process rely on manually maintained data in classes in the "data" package.

### ClassCategories
A class' type and its source, which determine the package in which a class lives, are manually stipulated in ClassCategories.
* Class type: The core class a class extends (BlackDuckView, BlackDuckResponse, BlackDuckComponent).  A class' type will default to component (BlackDuckComponent).
* Class source: How a class is maintained (generated, manual, temporary (manual classes that are expected to be generated in the future), deprecated).  A class' source will default to generated.
##### Note: 
All enum classes' names are suffixed with "Type".  This allows enum class types to be easily identified without ClassCategories (with the exception of FacetType- a manually maintained BlackDuckComponent).  However, for enums whose source is not generated, an entry in ClassCategories is required.

#### Common issues which likely require a modification to ClassCategories
* A generated class is in the wrong package/extends the wrong base class.
* The import path for a class is incorrect.

### LinkResponseDefinitions
The response classes of links are manually stipulated in LinkResponseDefinitions.

#### Adding new links
To add a new LinkResponse to a view, LinkResponseDefinitions needs to be modified.  
##### Note: 
An entry in LinkResponseDefinitions maps an object to a map of link names to link responses.  If the specification file corresponding to an object does not contain a link name stipulated in LinkResponseDefinitions, it will not be generated unless MissingFieldsAndLinks is modified. 

### TypeTranslator
Mappings of generated class names to their deprecated equivalents, as well as override types for certain class fields, are manually stipulated in TypeTranslator.

## apigen-maintenance
The apigen-maintenance project consists of classes that offer the following utilities:
* Creating a local portfolio of BlackDuck integrations projects (IntegrationsPortfolioCreator).
* Comparing two blackduck-common-api directories (ApiDiffRunner).
* Evaluating the usage of certain classes by other projects (ClassUsageSearchRunner).
* Identifying redundant/equivalent classes within a blackduck-common-api (RedundantClassFinderRunner).