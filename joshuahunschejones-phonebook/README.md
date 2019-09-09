# Phonebook

## Overview
This directory contains the code for a lightweight Dropwizard web API I built while working through the [RESTful Web Services with Dropwizard](https://www.oreilly.com/library/view/restful-web-services/9781783289530/) book by Alexandros Dallas.

## Run Locally
*Required: Maven, Java JDK 1.8*
1. From the `./joshuahunschejones-phonebook` dir, run `mvn clean package`
2. To launch the executable JAR, run `java -jar target/joshuahunschejones-phonebook-1.0-SNAPSHOT.jar server config.yaml`
3. To view healthchecks on the running application, navigate to `http://localhost:8081/healthcheck`
4. To use the basic HTTP client against the running application's contact resource endpoints:
  * GET  `http://localhost:8080/client/showContact?id=1`
  * POST `http://localhost:8080/client/newContact?firstName=Carl&lastName=Fox&phone=98765432`
  * PUT  `http://localhost:8080/client/updateContact?id=1&firstName=Alex&lastName=Updated&phone=98765432`
  * DELETE ` http://localhost:8080/client/deleteContact?id=1`

