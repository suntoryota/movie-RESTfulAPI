
# Movie RESTful

This is a sample Java / Maven / Spring Boot (version 3.2.4) application that can be used as a starter for creating a RESTful with validation and execption handler.


## How to Run

This application is packaged as a war which has Tomcat 10 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

## Steps to Setup

Clone the application

    https://github.com/suntoryota/movie-restfulapi.git  

Create Postgresql database

    create database user_database

Change mysql username and password as per your installation

    - open src/main/resources/application.properties

    - change spring.datasource.username and spring.datasource.password 

## About the Service

The service is just a simple REST service. It uses Postgresql to store the data.

To use this API, ensure you have a server running. The server URL is http://localhost:9090

Here is what this little application demonstrates:
- Full integration with the latest Spring Framework: inversion of control, dependency injection
- Writing a RESTful service using annotation: JSON request / response
-  Exception mapping from application exceptions to the right HTTP response 
- Spring Data Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
- Automatic CRUD functionality against the data source using Spring Repository pattern
- Demonstrates MockMVC test framework with associated libraries

     
    
## API Reference

#### The app defines following CRUD APIs.

| Method    | Url           | Description                   | 
| :-------- | :-------      | :-------------------------    |
| `GET `    | `/movies`     | Get all movies                |
| `POST `   | `/movies`     | Create  movies                |
| `GET `    | `/movies/{id}`| Get movies by id              |
| `PUT `    | `/movies/{id}`| Update movies                 |
| `DELETE ` | `/movies/{id}`| Delete movies by id           |

You can test them using postman or any other rest client.


## Sample Valid JSON Request Bodys

#### POST http://localhost:9090/movies/
```
{
    "title": "Pengabdi setan 1",
    "description": "1 Pengabdi setan",
    "rating": 3.0,
    "image":""
}

{
    "id": 2052,
    "title": "Pengabdi setan 1",
    "description": "1 Pengabdi setan",
    "rating": 3.0,
    "image": "",
    "created_at": "2024-03-24 02:12:41.478592",
    "updated_at": "2024-03-24 02:12:41.478592"
}
```

#### GET http://localhost:9090/movies/
```
[
    {
        "id": 2052,
        "title": "Pengabdi setan 1",
        "description": "1 Pengabdi setan",
        "rating": 3.0,
        "image": "",
        "created_at": "2024-03-24 02:12:41.478592",
        "updated_at": "2024-03-24 02:12:41.478592"
    },
    {
        "id": 2053,
        "title": "Pengabdi setan 2",
        "description": "2 Pengabdi setan",
        "rating": 4.0,
        "image": "",
        "created_at": "2024-03-24 02:15:02.858352",
        "updated_at": "2024-03-24 02:15:02.858352"
    }
]
```
#### GET http://localhost:9090/movies/2052
```
{
    "id": 2052,
    "title": "Pengabdi setan 1",
    "description": "1 Pengabdi setan",
    "rating": 3.0,
    "image": "",
    "created_at": "2024-03-24 02:12:41.478592",
    "updated_at": "2024-03-24 02:12:41.478592"
}
```
#### PUT http://localhost:9090/movies/2052
```
{
    "title": "Pengabdi setan 2 update",
    "description": "update 2 Pengabdi setan",
    "rating": 5.0,
    "image":""
}

{
    "id": 2052,
    "title": "Pengabdi setan 2 update",
    "description": "update 2 Pengabdi setan",
    "rating": 5.0,
    "image": "",
    "created_at": "2024-03-24 02:12:41.478592",
    "updated_at": "2024-03-24 02:17:14.671532"
}
```
#### DELETE http://localhost:9090/movies/2052
```
{
	"message : "Movie deleted successfully!"
}
```
#### Validation Message
```
{
    "title": "A",
    "description": "update 2 Pengabdi setan",
    "rating": 5.0,
    "image":""
}

{
    "title": "Title must be at least 1 character long"
}
```
```
{
    "title": "Sang Pengabdi Setan",
    "description": "U",
    "rating": 5.0,
    "image":""
}

{
    "description": "Description must be at least 10 characters long"
}
```
```
{
    "title": "Sang Pengabdi Setan",
    "description": "Setan Pengabdi Setan",
    "rating":11.0,
    "image":""
}

{
    "title": "Sang Pengabdi Setan",
    "description": "Setan Pengabdi Setan",
    "rating":11.0,
    "image":""
}
```
#### Exception 
#### POST http://localhost:9090/movies/

```
{
    "title": "Sang Pengabdi Setan",
    "description": "Setan Pengabdi Setan",
    "rating":5.0,
    "image":""
}

{
	errormessage: "This movie already exists!"
}
```

#### GET http://localhost:9090/movies/125
```
{
    "errormessage": "Movie not found with id: 125"
}
```





