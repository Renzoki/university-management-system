# Academic Service

Spring Boot academic management service running on `localhost:8081`.

## Responsibilities

The Academic Service manages the university's academic data and operations, including:

* Students
* Faculty
* Courses
* Enrollments
* Grades
* Academic statuses

It also validates JWTs issued by the Authentication Service and uses the user's role to control access to protected operations.

## Main Roles

* `ADMIN` — manages students, faculty, courses, enrollments, and grades
* `FACULTY` — manages assigned courses and student grades
* `STUDENT` — views their academic information and manages their own enrollments

## API Documentation

The complete endpoint documentation is provided separately due to the number of endpoints.

See:

`API.md`

The API documentation contains the available endpoints, HTTP methods, authorized roles, and the purpose of each endpoint.

## Structure

The service is organized into:

* **Controller** — REST API endpoints
* **Service** — academic business logic
* **Repository** — database access
* **Model** — entities and DTOs
* **Mapper** — entity-to-response mapping
* **Security** — JWT authentication and role-based authorization
* **Exception** — application error handling

The service requires a valid JWT issued by the Authentication Service for protected endpoints.
