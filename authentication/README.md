# Authentication Service

Spring Boot authentication service running on `localhost:8080`.

## Endpoints

### Authentication

| Method | Endpoint      | Used By  | Purpose                                              |
| ------ | ------------- | -------- | ---------------------------------------------------- |
| `POST` | `/auth/login` | Everyone | Authenticates a user and returns a JWT access token. |

### Development Seeding

| Method | Endpoint         | Used By    | Purpose                              |
| ------ | ---------------- | ---------- | ------------------------------------ |
| `POST` | `/seed/academic` | Developers | Seeds the development academic data. |

The academic seed creates the predefined faculty, students, courses, enrollments, and selected completed enrollments with grades.

If the data has already been seeded, the endpoint returns `409 Conflict`.

## Authentication

Successful login returns a JWT.

The token is then sent with authenticated requests using:

```text
Authorization: Bearer <token>
```

The JWT contains the authenticated user's:

* ID
* Email
* Role

### Roles

* `ADMIN`
* `FACULTY`
* `STUDENT`
