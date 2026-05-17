# pipejfdvBlog - TBI Treatment Gaming Platform

Backend platform of therapeutic games for the treatment of **Traumatic Brain Injury (TCE)**. Built with a microservices architecture using Spring Boot, Spring Cloud, and JWT security.

---

## Architecture Overview

```
Client → MCSGateway (port 8080)
              ↓
         MCSEureka (port 8761)
              ↓
    ┌──── MCSConfig (port 8888) ────┐
    ↓                                ↓
MCSAuth (9000) ←── Feign ──→ MCSUsersFM (8090) ←── Feign ──→ MCSJuegos (8091)
```

## Microservices

| Service | Port | Description | README |
|---------|------|-------------|--------|
| **MCSGateway** | 8080 | API Gateway - single entry point, JWT validation, routing | [README](MCSGateway/README.md) |
| **MCSEureka** | 8761 | Service Discovery - Netflix Eureka server | [README](MCSEureka/README.md) |
| **MCSConfig** | 8888 | Centralized Configuration Server | [README](MCSConfig/README.md) |
| **MCSAuth** | 9000 | Authentication - JWT creation, login, refresh, logout | [README](MCSAuth/README.md) |
| **MCSUsersFM** | 8090 | Users, guardians, children, TCE classifications, profiles | [README](MCSUsersFM/README.md) |
| **MCSJuegos** | 8091 | Games, categories, scores, child progress, XP system | [README](MCSJuegos/README.md) |

## Security

### Authentication Flow
1. **MCSAuth** receives login credentials, queries MCSUsersFM via Feign for user data
2. Validates password with BCrypt, creates JWT (HS256) with `accountType` claim
3. Client includes JWT as `Authorization: Bearer <token>` in subsequent requests

### Role-Based Access
| Role | Description |
|------|-------------|
| `DemoUser` | Basic user - shared paths + children CRUD |
| `PremiumUser` | Premium user - shared paths + children + progress |
| `FMAdmin` | Full admin access across all services |
| `Medic` | Medical professional - TCE management + children |
| `Analyst` | Defined in DB but not assigned to routes |

### JWT Token
- Algorithm: HMAC-SHA256
- Claims: `id` (user UUID), `sub` (username), `accountType` (role)
- Converted to Spring Security roles via `JwtConvertRol`/`ConverterRoleJWT`

## Patterns Used

| Pattern | Implementation |
|---------|---------------|
| **MVP** | Contract interfaces defining View/Presenter/Model |
| **Repository** | Spring Data JPA repositories |
| **DTO** | Separate DTOs for Public and Admin views |
| **Feign Client** | Inter-service communication (MCSAuth → MCSUsersFM, MCSJuegos → MCSUsersFM) |
| **Global Exception Handler** | `@RestControllerAdvice` for centralized error handling |
| **API Gateway** | Spring Cloud Gateway for routing, security, load balancing |
| **Service Discovery** | Netflix Eureka for service registration and discovery |
| **Configuration Server** | Spring Cloud Config for centralized configuration |

## Tech Stack

- **Java 17**
- **Spring Boot 3.5**
- **Spring Cloud 2025.0.0** (Gateway, Config, Netflix Eureka, OpenFeign)
- **Spring Security** + **OAuth2 Resource Server**
- **Spring Data JPA** + Hibernate
- **Databases**: MySQL (MCSUsersFM), PostgreSQL (MCSAuth, MCSJuegos)
- **JWT**: jjwt library
- **Lombok**
- **Maven**
