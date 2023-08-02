# XML Validation using Relax NG Compact (RNC) Schema

This Java application is designed to validate XML files against a given RNC schema.

## Usage

The main class is `RNCXMLValidator`. It takes three parameters: the path to the RNC schema, the path to the directory containing the XML files to validate and a path for the log containing non valid files.

Replace:
- `path_to_schema.rnc` with the path to your RNC schema file
- `path_to_directory` with the path to the directory containing the XML files
- `outputPath` with the path to the log file


The application will iterate over the specified directory and all its subdirectories, validate each XML file it finds against the RNC schema, and print the validation results.