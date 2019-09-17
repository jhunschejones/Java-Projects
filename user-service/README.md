# User Service

## Overview
This is a basic web API using Dropwizard and Hibernate to fetch, create, and delete users.

## In Action
To launch the application, clone this folder and run:
1. `./gradlew clean build`
2. `./gradlew run`

Routes:
* POST to `http://localhost:8080/users`: Create a user
* GET `http://localhost:8080/users/{id}`: Fetch user by ID
* GET `http://localhost:8080/users?name={name}`: Find user by name
* PUT `http://localhost:8080/users/{id}`: Update user by ID
* GET `http://localhost:8080/users`: List all users
* DELETE `http://localhost:8080/users/{id}`: Delete a user

To create or update a user, submit a JSON payload with the following content:

```json
{
	"firstName": "Joshua",
	"lastName": "Jones",
	"email": "joshua@hunschejones.com"
}
```
