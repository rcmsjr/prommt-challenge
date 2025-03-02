# Prommt Developer Challenge
This repository contains a full-stack application built using Angular (frontend) and Spring Boot (backend). It follows Clean Architecture principles. 🚀

## 📂 Mono-Repo Structure
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
│   │   ├── pom.xml
│   │   ├── src/
│   │   └── ...
│
│── .github/                     # DevOps
│── .gitignore
│── .gitattributes
│── README.md
└── docker-compose.yml           # Development environment setup
```

## 📌 How to Run Locally?

```sh
docker-compose up -d
```
- Frontend → [http://localhost:4200](http://localhost:4200)
- Backend → [http://localhost:8080](http://localhost:8080)
