# User Service

## Overview
This is a basic web API using Dropwizard and JDBI to fetch, create, and delete users. It also provides a "grants" resource, simulating a join table used in role based access control. 

## In Action
To launch the application, clone this folder and run:
1. `./gradlew clean build`
2. `./gradlew run`

Routes:
* POST `http://localhost:8080/users`: Create a user
* GET `http://localhost:8080/users/{id}`: Fetch user by ID
* GET `http://localhost:8080/users?name={name}`: Find user by first or last name
* PUT `http://localhost:8080/users/{id}`: Update user by ID
* GET `http://localhost:8080/users`: List all users
* DELETE `http://localhost:8080/users/{id}`: Delete a user by ID
* POST `http://localhost:8080/grants`: Create a grant
* GET `http://localhost:8080/grants?user_id={userId}`: List all grants for a user
* GET `http://localhost:8080/grants?account_id={accountId}`: List all grants on an account
* DELETE `http://localhost:8080/grants`: Delete a single grant matching payload
* GET `http://localhost:8081/healthcheck`: basic API and MySQL health checks

To create or update a user, POST a JSON payload with the following structure:

```json
{
    "firstName": "Joshua",
    "lastName": "Jones",
    "email": "joshua@hunschejones.com"
}
```

To create a grant, POST a JSON payload with the following structure:
```json
{
    "userId": 1,
    "accountId": 1,
    "roleId": 1
}
```
