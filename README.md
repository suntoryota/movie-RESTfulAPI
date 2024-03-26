# Movie RESTful

This is a simple Java / Maven / Spring Boot (version 3.2.4) application that can be used as a starter for creating a RESTful with validation and exeception handler.

## How to Run

This application is packaged as a war which has Tomcat 10 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

### Steps to Setup

Clone the application

    https://github.com/suntoryota/movie-restfulapi.git  

Create Postgresql database

    create database user_database

Create Table

    CREATE TABLE movie_tbl (
    id INTEGER PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    rating DECIMAL(2, 1),
    image VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );   

Change mysql username and password as per your installation

    - open src/main/resources/application.properties

    - change spring.datasource.username 

    - change spring.datasource.password 

## About the Service

The service is just a simple REST service. It uses Postgresql to store the data.

To use this API, ensure you have a server running. The server URL is http://localhost:9090

Here is what this little application demonstrates:
- Full integration with the latest Spring Framework dependency injection
- Writing a RESTful service using annotation: JSON request / response
-  Exception mapping from application exceptions to the right HTTP response 
- Spring Data JPA configuration and familiar annotations
- Automatic CRUD functionality the data source using Spring Repository pattern
- Demonstrates MockMVC test framework 

## API Reference

#### The app defines following CRUD APIs.

| Method    | Url                       | Description                   | 
| :-------- | :-------                  | :-------------------------    |
| `GET `    | `api/movie`               | Get all movies                |
| `POST `   | `api/movie/create`        | Create  movies                |
| `GET `    | `api/movie/detail/{id}`   | Get     movies by id          |
| `PUT `    | `api/movie/update/{id}`   | Update  movies                |
| `DELETE ` | `api/movie/delete/{id}`   | Delete  movies by id          |

You can test them using postman or any other rest client.


## Sample Valid JSON Request Bodys

#### POST    - api/movie/create
```
Request :

{
    "title": "Pengabdi Setan 4",
    "description": "Pengabdi Setan 4 Description",
    "rating": 5.0,
    "image":""
}

Response :

{
    "id": 2205,
    "title": "Pengabdi Setan 4",
    "description": "Pengabdi Setan 4 Description",
    "rating": 5.0,
    "image": "",
    "created_at": "2024-03-25 05:06:10",
    "updated_at": "2024-03-25 05:06:10"
}
```

#### GET    - api/movie
```
Response :

[
    {
        "id": 2202,
        "title": "Pengabdi Setan 1",
        "description": "Pengabdi Setan 1 Description",
        "rating": 5.0,
        "image": "",
        "created_at": "2024-03-25 05:03:29",
        "updated_at": "2024-03-25 05:03:29"
    },
    {
        "id": 2203,
        "title": "Pengabdi Setan 2",
        "description": "Pengabdi Setan 2 Description",
        "rating": 5.0,
        "image": "",
        "created_at": "2024-03-25 05:04:09",
        "updated_at": "2024-03-25 05:04:09"
    },
    {
        "id": 2204,
        "title": "Pengabdi Setan 3",
        "description": "Pengabdi Setan 3 Description Update",
        "rating": 5.0,
        "image": "",
        "created_at": "2024-03-25 05:04:19",
        "updated_at": "2024-03-25 05:05:29"
    }
]
```
#### GET BY ID   - api/movie/detail/{id}
```
Response :

{
    "id": 2204,
    "title": "Pengabdi Setan 3",
    "description": "Pengabdi Setan 3 Description",
    "rating": 5.0,
    "image": "",
    "created_at": "2024-03-25 05:04:19.746932",
    "updated_at": "2024-03-25 05:04:19.746932"
}
```
#### PUT   - api/movie/update/{id}
```
Resquest :

{
"title": "Pengabdi Setan 3",
"description": "Pengabdi Setan 3 Description Update",
"rating": 5.0,
"image":""
}

Response :

{
    "id": 2204,
    "title": "Pengabdi Setan 3",
    "description": "Pengabdi Setan 3 Description Update",
    "rating": 5.0,
    "image": "",
    "created_at": "2024-03-25 05:04:19.746932",
    "updated_at": "2024-03-25 05:05:29.711697"
}
```
#### DELETE   - api/movie/delete/{id}
```

Movie deleted successfully!

```
#### Validation Message & Exception
```
Request :

{
    "title": "",
    "description": "",
    "rating": 10,
    "image":""
}

Response :

{
    "timeStamp": "2024-03-25T05:24:41.8479396",
    "errorMessage": [
        "Description must be at least 10 characters long",
        "Rating must be less than or equal to 5",
        "title is mandatory",
        "title is mandatory",
        "description is mandatory",
        "description is mandatory",
        "Title must be at least 1 character long"
    ],
    "status": "BAD_REQUEST",
    "statusCode": 400
}
```
```
Request :

{
    "title": "Pengabdi Setan 4",
    "description": "Pengabdi Setan 4 Description",
    "rating": 5.0,
    "image":""
}

Response :

{
    "message": "Movie with title 'Pengabdi Setan 4' already exists!"
}
```
```
GET http://localhost:9090/api/movie/detail/4
PUT http://localhost:9090/api/movie/update/4
PUT http://localhost:9090/api/movie/delete/4

Response :

{
    "message": "Movie not found with ID: 4",
}
```

