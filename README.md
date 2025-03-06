# Prommt Developer Challenge
This repository contains a full-stack application built using Angular (frontend) and Spring Boot (backend). It follows Clean Architecture principles. 🚀

## Mono-Repo Structure
```
root/
│── apps/
│   ├── frontend/                # Angular Frontend
│   │   ├── src/
│   │   ├── angular.json
│   │   ├── package.json
│   │   ├── tsconfig.json
│   │   └── ...
│   │
│   ├── server/                  # Spring Boot Backend
│   │   ├── application/         # Application entrypoint, DTO's, Controllers & API Endpoints
│   │   ├── domain/              # Core domain entities, Use cases, services & business logic
│   │   ├── infrastructure/      # Persistence, etc.
│   │   ├── tests/               # Unit, Functional & Integration tests
│   │   ├── build.gradle.kts
│   │   ├── src/
│   │   └── ...
│
│── .github/                     # DevOps
│── .gitignore
│── .gitattributes
│── README.md
└── docker-compose.yml           # Development environment setup
```

## Technologies Used
### Frontend
- ✅ Framework: Angular
- State Management:
- ❌ Authentication: JWT-based
- Styling: SCSS

### Backend
- ✅ Framework: Spring Boot 3
- ✅ Architecture: Clean Architecture
- ❌ Authentication: Spring Security (JWT)
- ❌ Authorization: Role-Based & Permission-Based
- ✅ Persistence: Spring Data JPA (In-memory)
- ❌ Logging: Slf4j
- ❌ Database migrations
- ✅ Structured error responses by handlin all exceptions automatically
- ✅ Tests: Unit & Integration Testing

#### Why This Structure?
- Keeps business logic (`domain/`) isolated from application concerns
- Ensures clean separation of concerns
- Presentation (controllers) only acts as a "entry point and delivery mechanism"
- Encourages modularity for SOA & Microservices
- Better scalability for SOA/MS based architecture
- High Maintainability and Testability with clear separation of concerns between application, domain, and infrastructure layers.
- Allow different entry points (e.g., API (REST/GraphQL), SQS, SNS, Serverless)
- Resiliency: Services can work asynchronously without blocking each other.

### Development Environment
- ✅ Docker: Containerized services
- ✅ Docker Compose: ~~Manages workspace~~, ~~frontend~~, and backend services
- ✅ Testing: JUnit, Mockito, ~~e2e~~
- ❌ CI/CD: Github Actions

## Architectural Principles
- 🏗️ Clean Architecture – Separates concerns (Domain, Application, Infrastructure).
- 🧩 Service-Oriented Architecture (SOA) – Modular and scalable services.
- 📦 Domain-Driven Design (DDD) – Focused on business logic.

## Run tests inside a Docker container
First, make sure you run the Docker command from the root folder of the mono-repo.
```
docker run --rm \
  -v "$(pwd)/apps/payment-service:/app" \
  -w /app \
  openjdk:17-jdk-slim \
  ./gradlew test
```

## How to Run Locally?

```sh
docker-compose up -d
```
- Frontend → [http://localhost:4200](http://localhost:4200)
- Backend → [http://localhost:8080](http://localhost:8080)
