# AI-Assisted Production Incident Management System

A full-stack incident management system that helps support teams triage production incidents faster. New incidents are matched against a knowledge base of past incidents using TF-IDF and cosine similarity, automatically classified and routed to the owning team, and application logs can be analysed by a local LLM (Ollama) to suggest a root cause.

## Screenshots

### Dashboard
Live counts of incidents by status and severity, plus the size of the knowledge base.

![Dashboard](docs/screenshots/dashboard.png)

### Incidents
Searchable incident list with severity badges, service and creation time.

![Incidents](docs/screenshots/incidents.png)

### AI Log Analyzer
Pick an environment and service, and the system analyses the logs, summarises the failure, suggests a root cause, routes the incident to a team with a confidence score, and lists similar historical incidents.

![AI Log Analyzer](docs/screenshots/ai-log-analyzer.png)

### Knowledge Base
Past incidents with root cause and resolution, used for similarity matching.

![Knowledge Base](docs/screenshots/knowledge-base.png)

### Ownership Mapping
Maps incident categories to the responsible team and contact, used by automatic routing.

![Ownership Mapping](docs/screenshots/ownership.png)

## Features

- **Incident management**: create, list, search and fetch incidents through a REST API and a React UI
- **Similar incident matching**: TF-IDF and cosine similarity over past incidents and their resolutions
- **Automatic triage and routing**: classifies an incident by keywords, maps it to an owning team, and returns a confidence score and a decision (`AUTO ASSIGNED` or `SUGGESTED`)
- **AI log analysis**: a local LLM (Ollama) analyses application logs, writes a summary and proposes a probable root cause
- **Dashboard**: status and severity distribution at a glance
- **Security**: JWT authentication with role-based access control (`ADMIN`, `SUPPORT`, `VIEWER`)
- **Validation and error handling**: consistent JSON errors (400, 401, 403, 404, 500)

## Architecture

```mermaid
flowchart LR
    UI[React UI] -->|REST + JWT| API[Spring Boot API]
    API --> DB[(PostgreSQL)]
    API -->|optional| LLM[Ollama LLM]
    API --> MATCH[TF-IDF matching engine]
    MATCH --> DB
```

## Tech stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot, Spring Security, JPA/Hibernate |
| Frontend | React (`incident-ui`) |
| Database | PostgreSQL 16 |
| AI / matching | Ollama (optional), TF-IDF, cosine similarity |
| Build and test | Maven, JUnit, REST Assured |
| DevOps | Docker, Docker Compose, GitHub Actions |

## Run with Docker

Requires Docker Desktop.

```bash
docker compose up --build
```

The API is then available at `http://localhost:8080`, backed by PostgreSQL on port 5432.

On first start the app seeds three development users:

| User | Role |
|---|---|
| `admin1` | ADMIN |
| `support1` | SUPPORT |
| `viewer1` | VIEWER |

These are for local development only.

Log in to get a token:

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"userName":"admin1","password":"admin123"}'
```

Then pass it as `Authorization: Bearer <token>` on the other endpoints.

The AI Log Analyzer needs [Ollama](https://ollama.com) running locally. The rest of the application works without it.

## Run the frontend

```bash
cd incident-ui
npm install
npm run dev
```

The UI runs at `http://localhost:5173`.

## API overview

| Method | Endpoint | Roles | Description |
|---|---|---|---|
| POST | `/auth/login` | public | Get a JWT |
| POST | `/api/incidents` | ADMIN | Create an incident |
| GET | `/api/incidents` | ADMIN, SUPPORT, VIEWER | List incidents |
| GET | `/api/incidents/{id}` | ADMIN, SUPPORT, VIEWER | Get one incident |
| GET | `/api/incidents/match?description=...` | ADMIN, SUPPORT, VIEWER | Find similar past incidents |
| POST | `/api/incidents/triage` | ADMIN, SUPPORT | Create, match and classify in one call |
| POST | `/api/ai/analyse` | authenticated | LLM log analysis (needs Ollama) |

## Tests and CI

API tests use REST Assured against the running application and a real PostgreSQL database. They cover:

- Login success and failure
- Unauthenticated requests are rejected
- Create and fetch an incident
- Request validation
- Role-based access (a `VIEWER` cannot create incidents)

Run them locally with Maven:

```bash
mvn verify
```

GitHub Actions runs the same tests on every push and pull request against a PostgreSQL service container, and builds the Docker image.
