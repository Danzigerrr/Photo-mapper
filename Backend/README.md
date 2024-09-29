# Backend - Java Spring Boot Application

The backend application shares endpoints which are used to extract metadata from images stored in a selected directory.
Then the metadata is stored in a database and can be retrieved using other endpoints.

## Metadata Extraction
Library used to extract metadata: [metadata-extractor](https://github.com/drewnoakes/metadata-extractor).

Metadata extracted from images (with example data):
- **Image Width:** 2250 pixels
- **Image Height:** 4000 pixels 
- **Model:** realme 8 Pro 
- **Date/Time Original:** 2024:08:28 16:43:39 
- **Time Zone Original:** -07:00 
- **GPS Latitude Ref:** N
- **GPS Latitude:** 36° 36' 24.36"
- **GPS Longitude Ref:** W
- **GPS Longitude:** -117° 6' 54.44"
- **GPS Altitude Ref:** Below sea level 
- **GPS Altitude:** 44.13 metres
- **Detected File Type Name:** JPEG
- **Detected File Type Long Name:** Joint Photographic Experts Group
- **File Name:** IMG20240828164339.jpg 
- **File Size:** 4884167 bytes

Accepted image formats:
- jpg
- jpeg
- png

## Running the Application

To run the Spring Boot application, follow these steps:

1. Ensure you have [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed on your machine.
2. Navigate to the `Backend` directory of the project in your terminal:
   ```bash
   cd Backend
   ```
3. Build the application using Maven:
   ```bash
   mvn clean package
   ```
4. Run the application:
   ```bash
   java -jar target/PhotoMapperApplication.jar
   ```
   
The application will start, and you can access the endpoints for metadata extraction.

## Tests

The backend application includes unit tests to verify the functionality of the controller and service components. To run the tests:

1. Ensure you are in the `Backend` directory:
   ```bash
   cd Backend
   ```
2. Execute the following command:
   ```bash
   mvn test
   ```

This command will run all the tests defined in the project.