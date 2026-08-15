# University Management System — Frontend

React frontend for the university management system.

## Features

### Authentication

* User login and logout
* JWT-based authentication
* Role-based routing
* Automatic handling of expired authentication

### Student

Students can:

* View their dashboard
* View available courses
* Enroll in courses
* View their enrollments
* Drop active enrollments
* View their grades

### Faculty

Faculty can:

* View their dashboard
* View their assigned courses
* View students enrolled in their courses
* View and manage student grades

### Admin

Admin functionality is supported through the application's role-based routing and API access.

## API Communication

The frontend communicates with the backend services using Axios.

* Authentication Service: `http://localhost:8080`
* Academic Service: `http://localhost:8081`

JWT access tokens are automatically included in authenticated Academic Service requests.

## Data Management

TanStack Query is used for:

* Server state management
* API data fetching
* Mutations
* Cache invalidation
* Loading and error states

## Environment Variables

The frontend uses environment variables for the backend URLs:

```text
VITE_API_BASE_URL
VITE_AUTH_API_URL
VITE_ACADEMIC_API_URL
```

## Project Structure

```text
src/
├── api
├── assets
├── components
├── contexts
├── hooks
├── layouts
├── pages
└── routes
```
