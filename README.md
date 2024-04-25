# User Management RestFul API

Test task for Clear Solutions

This project is a simple User Management Restul API developed using Spring Boot. It provides functionalities for creating, updating, and searching for users. 
The API does not use a database, and instead operates with a transient storage solution.

## Features

- **Create User**: Allows registering new users with validation for age.
- **Update User**: Supports updating single or multiple user fields.
- **Delete User**: Enables user deletion.
- **Search Users**: Facilitates searching for users within a specified birth date range.
- **Validation**: Includes email pattern validation, not blank and birth date validations.
- **Error Handling**: Implements error handling for RESTful responses.
- **Postman**: Includes postman collection

## API Reference
The User Management API supports various operations through RESTful endpoints. Below you'll find detailed descriptions and examples for each available API endpoint.

### Create User

Creates a new user with mandatory fields such as email, first name, last name, and birth date, and optional fields such as address and phone number. The user must be over 18 years old as defined in the properties file.

- **URL**: `/v1/users`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Body**:

```json
{
    "email": "emailnew@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "Kyiv city",
    "phoneNumber": "+380912345678"
}
```
- **Success Response**:
```json
{
    "id": 254272000,
    "email": "emailnew@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "Kyiv city",
    "phoneNumber": "+380912345678"
}
```
- **Code**: 201 CREATED
- **Error Response**:
  - Code: 400 BAD REQUEST
  - Content: { "error": "ERROR: Validation failed: Invalid email format (or smth else)" }

### Get user by ID

Retrieves a user by their ID.

- **URL**: /v1/users/{id}
- **Method**: `GET`
- **URL Parameters**:
    - id [required]: The unique identifier of the user. 
- **Success Response**:
```json
{
    "id": 254272000,
    "email": "emailnew@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "Kyiv city",
    "phoneNumber": "+380912345678"
}
```
- **Code**: 200 OK
- **Error Response**:
  - Code: 404 NOT FOUND
  - Content: { "error": "User with id: 133 not found" }

### Update specified fields for a user

Updates specified fields for a user by ID. Any field in the request body will be updated, other fields will remain unchanged.

- **URL**: `/v1/users/{id}`
- **Method**: `PATCH`
- **URL Parameters**:
    - id [required]: The unique identifier of the user. 
- **Content-Type**: `application/json`
- **Body** (example: updating address and phone number):

```json
{
    "address": "New Address, New City",
    "phoneNumber": "+380987654321"
}
```
- **Success Response**:
```json
{
    "id": 1,
    "email": "email1@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "New Address, New City",
    "phoneNumber": "+380987654321"
}
```
- **Code**: 200 OK

### Update User

Updates user by ID.

- **URL**: `/v1/users/{id}`
- **Method**: `PUT`
- **URL Parameters**:
    - id [required]: The unique identifier of the user. 
- **Content-Type**: `application/json`
- **Body**:

```json
{
    "email": "emailnew@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "Kyiv city",
    "phoneNumber": "+380912345678"
}
```
- **Success Response**:
```json
{
    "id": 1,
    "email": "emailnew@email.com",
    "firstName": "Anton",
    "lastName": "Smith",
    "birthDate": "2000-12-30",
    "address": "Kyiv city",
    "phoneNumber": "+380912345678"
}
```
- **Code**: 200 OK
- **Error Response**:
  - Code: 400 BAD REQUEST
  - Content: { "error": "ERROR: Validation failed: FirstName is required (or smth else)" }

### Delete user by ID

Deletes a user by their ID.

- **URL**: /v1/users/{id}
- **Method**: `DELETE`
- **URL Parameters**:
    - id [required]: The unique identifier of the user. 
- **Success Response**:
```
No Content
```
- **Code**: 204 NO CONTENT
- **Error Response**:
  - Code: 404 NOT FOUND
  - Content: { "error": "User with id: 133 not found" }
 
###Get All Users or Search by Birth Date Range

Retrieves a list of all users or searches for users born between specified dates if both `from` and `to` date parameters are provided.

- **URL**: `/v1/users`
- **Method**: `GET`
- **Query Parameters**:
  - **from** [optional]: Start date of the birth date range (`YYYY-MM-DD`).
  - **to** [optional]: End date of the birth date range (`YYYY-MM-DD`).
- **Success Response**:
```json
[
    {
        "id": 2,
        "email": "email2@email.com",
        "firstName": "Andrew",
        "lastName": "Kondratenko",
        "birthDate": "2002-11-03",
        "address": "Lviv city",
        "phoneNumber": "+380002345678"
    },
    {
        "id": 514998000,
        "email": "emailnew@emailcom",
        "firstName": "Anton",
        "lastName": "Smith",
        "birthDate": "2000-12-30",
        "address": "Kyiv city",
        "phoneNumber": "+380912345678"
    }
]
```
- **Code**: 200 OK
- **Error Response**:
  - Code: 400 BAD REQUEST
  - Content: { "error": "ERROR: 'From' date cannot be after 'To' date" } or { "error": "ERROR: Both 'from' and 'to' dates are required for filtering" }

## Prerequisites

Before running the project, make sure you have the following installed on your local machine:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) 17 or later
- [Apache Maven](https://maven.apache.org/download.cgi)

## Issues and Troubleshooting

If you encounter any issues or have trouble running the project, please check the following:

- Ensure you have Java 17 or later installed.
- Verify that Maven is installed and properly configured.
- Review the project's dependencies and ensure they are correctly specified in the `pom.xml` file.

For additional support, please contact the project maintainer.
