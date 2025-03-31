# Blog Post Manager

A CRUD blog post backend application built with Spring Boot and MySQL.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Features

- Create, read, update, and delete blog posts
- RESTful API endpoints
- Exception handling
- Logging with Log4j2

## Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- MySQL 8.0 or higher

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/blog-post-manager.git
    cd blog-post-manager
    ```

2. Configure the MySQL database:
    - Create a database named `blogdb`.
    - Update the `src/main/resources/application.properties` file with your MySQL database credentials:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/blogdb
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
        ```

3. Build the project:
    ```sh
    mvn clean install
    ```

## Running the Application

1. Start the application:
    ```sh
    mvn spring-boot:run
    ```

2. The application will be available at `http://localhost:8080`.

## API Endpoints

- **GET /api/blogposts**: Get all blog posts
- **GET /api/blogposts/{id}**: Get a blog post by ID
- **POST /api/blogposts**: Create a new blog post
- **PUT /api/blogposts/{id}**: Update a blog post by ID
- **DELETE /api/blogposts/{id}**: Delete a blog post by ID

## Testing

Run the tests using the following command:
```sh
mvn test
