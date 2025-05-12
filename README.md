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
```

---

####  **API Endpoints**
```markdown
## API Endpoints

### Auth

- `POST /auth/login` – Authenticate user and return JWT

### Tasks (Requires JWT)

- `GET /tasks` – Get all tasks
- `GET /tasks/{id}` – Get task by ID
- `POST /tasks` – Create a new task
- `PUT /tasks/{id}` – Update task
- `DELETE /tasks/{id}` – Delete task
```
## Authentication

All protected endpoints require a valid JWT token in the `Authorization` header:

Use the `/auth/login` endpoint to obtain a token by providing valid credentials.

## Sample JSON Requests

### Login
```json
{
  "username": "admin",
  "password": "admin123"
}
```
---

#### **Contributing**
```markdown
## Contributing

Feel free to fork this repo and submit pull requests.

1. Fork the repository
2. Create a new branch: `git checkout -b feature/YourFeature`
3. Commit your changes
4. Push and submit a PR
```
## License

This project is licensed under the MIT License.
