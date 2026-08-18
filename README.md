# University Management System

## Specifications & Structure

### Technologies

| Component  | Technology                  |
| ---------- | --------------------------- |
| Backend    | Java, Spring Boot           |
| Security   | JWT, Spring Security        |
| ORM        | Spring Data JPA / Hibernate |
| Database   | PostgreSQL 17               |
| Frontend   | HTML, CSS, JavaScript       |
| Web Server | `npx serve`                 |
| Containers | Docker / Docker Compose     |
| Build Tool | Maven                       |
| API        | REST                        |

### Prerequisites

You must have the following installed on your device:
```text
Java JDK 
Maven 
Node.js 
npm 
Docker Desktop (installed and running)
```

### Ports

```text
Authentication Service     : 8080
Academic Service           : 8081
Authentication PostgreSQL  : 5432
Academic PostgreSQL        : 5433
HTML Frontend              : 5173
```

### Project Structure

```text
university-management-system/
├── authentication/
│   ├── src/
│   ├── pom.xml
│   └── docker-compose.yml
│
├── academic-service/
│   ├── src/
│   ├── pom.xml
│   └── docker-compose.yml
│
└── html-frontend/
    ├── index.html
    ├── login.html
    ├── css/
    ├── js/
    └── ...
```

---

## Running the System

Open the **project root**, then open **3 PowerShell terminals**.

### Terminal 1 — Authentication

```powershell
cd authentication
docker compose up -d
.\mvnw.cmd spring-boot:run
```

Runs on `http://localhost:8080`.

### Terminal 2 — Academic Service

```powershell
cd academic-service
docker compose up -d
.\mvnw.cmd spring-boot:run
```

Runs on `http://localhost:8081`.

### Terminal 3 — HTML Frontend

```powershell
cd html-frontend
Start-Process "http://localhost:5173/login.html"; npx serve -l 5173
```

Runs on `http://localhost:5173`.

### First-Time Setup

When the Login page opens, **click the `Seed Academic Data` button first** before attempting to log in.

Wait for the seed to finish, then log in using the seeded accounts.

The seed creates the required development users, courses, students, faculty, and enrollments.

---

## Section 3 — Other Information

### Check PostgreSQL Containers

```powershell
docker ps
```

You should see:

```text
postgres-db
academic-postgres
```

with:

```text
5432 → Authentication Database
5433 → Academic Database
```

### Stop the System

Stop the Spring Boot applications and frontend with:

```text
Ctrl + C
```

Then stop PostgreSQL:

```powershell
docker stop academic-postgres postgres-db
```

### Restart PostgreSQL

From the project root:

```powershell
cd authentication
docker compose up -d
```

Then:

```powershell
cd ..\academic-service
docker compose up -d
```

Afterward, start both Spring Boot services and the frontend again.
