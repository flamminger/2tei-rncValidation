package org.example;

import com.thaiopensource.validate.ValidationDriver;
import com.thaiopensource.validate.rng.CompactSchemaReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class RNCXMLValidator {
    public static void main(String[] args) {
        String schemaPath = "path2schema.rnc";
        String directoryPath = "path2inputDir";
        String outputPath ="path2output.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath, true))) {
            // Create a validation driver with a compact schema reader
            ValidationDriver driver = new ValidationDriver(CompactSchemaReader.getInstance());

            // Load the schema
            if (!driver.loadSchema(new InputSource(new File(schemaPath).toURI().toString()))) {
                System.out.println("Could not load the schema.");
                return;
            }

            // Iterate over Directory and validate each XML file
            try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".xml"))
                        .forEach(path -> validateXmlFile(driver, path, writer));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SAXException | IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void validateXmlFile(ValidationDriver driver, Path xmlFile, PrintWriter writer) {
        try {
            if (!driver.validate(new InputSource(xmlFile.toUri().toString()))) {
                System.out.println("The document is not valid: " + xmlFile);
                writer.println(xmlFile.toAbsolutePath() + ",Document is not valid");
            } else {
                System.out.println("The document is valid: " + xmlFile);
            }
        } catch (SAXException | IOException e) {
            System.out.println("An error occurred while validating " + xmlFile + ": " + e.getMessage());
            writer.println(xmlFile.toAbsolutePath() + "," + e.getMessage());
        }
    }
}