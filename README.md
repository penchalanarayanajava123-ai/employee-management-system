# Employee Management System

A REST API built with Java 17, Spring Boot, Spring Data JPA, Maven and MySQL.
It includes Docker and Docker Compose files for local container practice.

## Requirements

- JDK 17
- Maven 3.9+
- MySQL 8+ (if running without Docker)
- Docker Desktop (optional, for container execution)

## Run locally with Maven

1. Start MySQL and create a database named `employee_db` (or allow the configured URL to create it).
2. Check the username and password in `src/main/resources/application.properties`.
3. Run:

```bash
mvn clean spring-boot:run
```

Application URL: `http://localhost:8080`

## Build and run the JAR

```bash
mvn clean package
java -jar target/employee-management-system-0.0.1-SNAPSHOT.jar
```

## Run with Docker Compose

Build the JAR first, then start the application and MySQL:

```bash
mvn clean package
docker compose up --build
```

Stop containers:

```bash
docker compose down
```

Stop containers and remove the database volume (this deletes local database data):

```bash
docker compose down -v
```

## REST API

Base URL: `http://localhost:8080/api/employees`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/employees` | Create employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |

### Sample request body

```json
{
  "firstName": "Ravi",
  "lastName": "Kumar",
  "email": "ravi.kumar@example.com",
  "department": "Engineering",
  "salary": 65000.00
}
```

### cURL examples

Create:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ravi","lastName":"Kumar","email":"ravi.kumar@example.com","department":"Engineering","salary":65000.00}'
```

Get all:

```bash
curl http://localhost:8080/api/employees
```

Get by ID:

```bash
curl http://localhost:8080/api/employees/1
```

Update:

```bash
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ravi","lastName":"Kumar","email":"ravi.kumar@example.com","department":"Platform","salary":70000.00}'
```

Delete:

```bash
curl -X DELETE http://localhost:8080/api/employees/1
```

## Project structure

```text
employee-management-system
├── src/main/java/com/example/employeemanagement
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── exception
│   ├── repository
│   ├── service
│   └── EmployeeManagementApplication.java
├── src/main/resources/application.properties
├── Dockerfile
├── compose.yaml
└── pom.xml
```

## Notes

- This is a learning/demo project, not a production-ready HR system.
- For production, use database migrations (Flyway/Liquibase), secrets management,
  authentication/authorization, pagination, audit fields and stronger error responses.
- Change the demo database password before using this outside your local machine.
