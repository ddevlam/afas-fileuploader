# AFAS-Fileuploader
This little project was inspired by my wife. Whom else had to upload loads of files by hand.

## How the application works
When running the application a CSV will be used as input to upload files to the AFAS REST API.
The first request that will be made will be to the KnSubject connector. 

All work is encapsulated so that might an error occur not ALL work fails.

### Limitations
* Your upload speed

### Config file example
```
{
  "inputFile": "/example_csv.csv", // The location of the CSV
  "abboId" : "12345", // The abboId of the callee
  "tokenBase64" : "some-base64-string"
}
```

## Technical stuff 
### Requirements
* Java 8+
* Maven 3+ (or just use the mvnw)

### Building
A ```mvn clean package``` should be enough to build the project

### Running the project
After building the project run it with ```java jar AFAS-Fileuploader.jar [path-to-config.json]```
 