# Tomcat header agent

This project contains Java Agent that is used to instrument Tomcat server
in order to inject custom header to all responses.

Custom header name and value can be set through environment variables.
To change the header name set the `THI_HEADER_NAME`, and to change the
header value set the `THI_HEADER_VALUE` environment variable.

## Requirements

- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later
- [Maven 3.3.3](https://maven.apache.org/download.cgi) or later
- Docker

## Building

To build the library run `mvn package`. This will build the library, and
produce `target/tha-javaagent.jar` file which will contain Java agent
which instruments Tomcat's `org.apache.catalina.core.StandardEngineValve`
and adds custom header to all responses.

To build the custom Docker image run `make pack` command which creates
new Docker image from the `tomcat:8.0`, and adds this project's Java Agent
JAR to Docker image, which will be used by custom `setenv.sh` script to
add the Java Agent to Tomcat JVM.


---

Copyright © 2017 Nemanja Zbiljić
