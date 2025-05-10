# TaskManager Application

A Spring Boot-based RESTful API for managing tasks with user authentication, JWT security, and CRUD operations.

## Features

- User login with JWT authentication
- Role-based access control
- Create, Read, Update, Delete (CRUD) for tasks
- Input validation with meaningful error responses
- Global exception handling

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA + Hibernate
- H2 / MySQL (choose based on your config)
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven 3.8+
- (Optional) MySQL if you're not using H2

### Clone the Repository
```bash
git clone https://github.com/lokhandesh/SpringBootTaskManager.git
cd SpringBootTaskManager

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run


