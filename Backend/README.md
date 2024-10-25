### **Backend README**

# Backend - Java Spring Boot Application
![Build Status](https://github.com/Danzigerrr/Photo-mapper/actions/workflows/SpringBootRunUnitTests.yml/badge.svg)  
![Java Version](https://img.shields.io/badge/Java-21-blue)  
![Maven](https://img.shields.io/badge/Maven-3.8.1-red)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-green)

The backend application exposes endpoints to extract and store metadata from selected images. The extracted data can be accessed via REST API endpoints.

## Metadata Extraction
Using the [metadata-extractor](https://github.com/drewnoakes/metadata-extractor) library, the backend extracts metadata including:
- Image dimensions, model, and timestamps
- GPS coordinates
- File type and size

Accepted formats:
- jpg
- jpeg
- png

### Running the Application

1. Ensure [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) is installed.
2. Navigate to the `Backend` directory:
   ```bash
   cd Backend
   ```
3. Build and run the application:
   ```bash
   mvn clean package
   java -jar target/PhotoMapperApplication.jar
   ```

### Tests
Run backend unit tests:
```bash
cd Backend
mvn test
```

## License
Licensed under the MIT License. See the [LICENSE](../LICENSE) file.