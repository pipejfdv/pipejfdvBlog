# MCSUsersFM - Users & FunnyMind Microservice

## Overview

MCSUsersFM is the backend microservice for a gaming platform designed for the treatment of **Traumatic Brain Injury (TCE)**. It manages users, guardians, children, profiles, document types, relationships, and TCE classifications.

This service is part of a microservices architecture that works together to provide a complete platform for patients, guardians, and medical professionals.

---

## Architecture

The project follows a **microservices architecture** with 5 services:

| Service | Port | Description |
|---------|------|-------------|
| **MCSAuth** | 8081 | Authentication service - JWT creation, login, refresh |
| **MCSConfig** | 8888 | Centralized configuration server |
| **MCSEureka** | 8761 | Service discovery (Eureka) |
| **MCSGateway** | 8080 | API Gateway - single entry point for all requests |
| **MCSUsersFM** | 8090 | Users, guardians, children, TCE data management |
| **MCSJuegos** | 8082 | Games microservice |

### Connection Flow

```
Client → MCSGateway (port 8080) → MCSUsersFM (port 8090)
                                  → MCSJuegos (port 8082)
```

- **MCSGateway** uses Spring Cloud Gateway (reactive) to route all requests
- All services register with **MCSEureka** for service discovery
- Configuration is centralized in **MCSConfig**
- **MCSAuth** communicates with MCSUsersFM via Feign clients to validate user credentials

## Patterns Used

### 1. MVP Pattern (Model-View-Presenter)
Each entity follows a contract-based MVP:
- **Model** (`Model/Services/`): Business logic and database operations
- **View** (`View/Controllers/`): HTTP endpoints
- **Presenter** (`Presenter/Class/`): Mediates between View and Model, transforms data into DTOs
- **Contract** (`Presenter/Interfaces/`): Defines interfaces for View, Presenter, and Model

### 2. Repository Pattern
Spring Data JPA repositories (`Model/Repositories/`) abstract database access.

### 3. DTO Pattern
Separate DTOs for public and admin views to control data exposure:
- `ModelsDTO/Public/`: Limited data for regular users
- `ModelsDTO/Admin/`: Full data for admin users

### 4. Global Exception Handling
`GlobalExceptionHandler` with `@RestControllerAdvice` centralizes error handling.

### 5. Dependency Injection
Spring Boot constructor-based DI throughout the service.

---

## Security

### Authentication (JWT)
1. **MCSAuth** creates JWT tokens with HMAC-SHA256 containing user data and an `accountType` claim (DemoUser, PremiumUser, FMAdmin, Medic, Analyst)
2. Tokens are validated at the Gateway level and again at each microservice

### Authorization (Roles)
The `JwtConvertRol` converter transforms the `accountType` JWT claim into Spring Security roles (`ROLE_DemoUser`, `ROLE_PremiumUser`, `ROLE_FMAdmin`, `ROLE_Medic`).

### Security Rules (MCSUsersFM)
| Route | Roles |
|-------|-------|
| `/funnyMind/User/create/**`, `/funnyMind/tceClassification/list` | Public |
| `/funnyMind/User/userData/**`, `/funnyMind/User/update/**`, ... | DemoUser, PremiumUser, FMAdmin, Medic |
| `/funnyMind/guardianChildren/**`, `/funnyMind/children/update` | DemoUser, PremiumUser |
| `/funnyMind/tceClassification/update/**`, `/funnyMind/tceClassification/updateByChildren/**`, `/funnyMind/children/public/list` | Medic |
| `/funnyMind/User/list`, `/funnyMind/tceClassification/**`, `/funnyMind/children/getAdmin/**` | FMAdmin |

### Gateway Security
The gateway validates JWT before routing and blocks unauthorized requests at the entry point.

---

## Database

MCSUsersFM uses **MySQL** with the following main tables:

- `users` - User accounts linked to account types
- `guardians` - Guardian profiles (1:1 with users)
- `children` - Children under guardianship
- `tce_clssification` - TBI classifications (Leve/Moderado/Grave)
- `guardian_children` - Relationship between guardians and children
- `document_type` - Identity document types
- `relationships` - Family relationship types
- `profiles` - Game profiles for children
- `account_type` - User role types

## Project Structure

```
MCSUsersFM/
├── Config/              # Security, JWT, encryption config
├── Model/
│   ├── Models/          # JPA entities
│   ├── ModelsDTO/       # Data transfer objects (Admin/Public)
│   ├── Repositories/    # Spring Data JPA repositories
│   │   └── InsertData/  # Initial data seeding
│   └── Services/        # Business logic layer
├── Presenter/
│   ├── Class/           # Presenter implementations
│   └── Interfaces/      # Contract definitions (MVP)
├── View/
│   ├── Controllers/     # REST endpoints
│   └── ResponsesHTTP/   # Response DTOs and error handling
└── Exceptions/          # Custom exceptions
```

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Security + OAuth2 Resource Server
- Spring Cloud (Eureka, Gateway, Config)
- Spring Data JPA
- MySQL
- Lombok
- JWT (jjwt)
