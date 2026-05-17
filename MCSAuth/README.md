# MCSAuth - Authentication Microservice

## Overview

MCSAuth is the authentication microservice for the pipejfdvBlog platform. It handles user login, JWT token creation, token refresh, and session management. It validates credentials against MCSUsersFM and issues signed JWT tokens for subsequent authenticated requests.

## Architecture

- **Port**: 9000 (internal), exposed via API Gateway on port 8081
- **Service Discovery**: Registered with Eureka under the name `MCS-AUTH`
- **Inter-service Communication**: Uses Feign Client to call `MCSUsersFM` (`localhost:8090`) for user credential validation
- **Database**: MySQL via JPA/Hibernate for storing authentication tokens

### Flow

```
Client -> API Gateway (8081) -> MCSAuth (9000) -> MCSUsersFM (8090) -> MySQL
```

## Security

- **JWT Algorithm**: HS256 (HMAC with SHA-256)
- **Secret Key**: Base64-encoded, configured via `MCSConfig` properties
- **Token Claims**: `id` (user UUID), `sub` (username), `accountType` (role), `iat`, `exp`
- **Password Encoding**: BCrypt for credential verification
- **Session**: Stateless (no HTTP session), each request carries JWT
- **Routes**:
  - `/auth/**` - Public (login, refresh)
  - `/auth/deleted/**` - Restricted to `FMAdmin` role

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Runtime |
| Spring Boot 3 | Application framework |
| Spring Security | Authentication & authorization |
| Spring Cloud Netflix (Eureka) | Service discovery |
| Spring Cloud OpenFeign | Inter-service HTTP client |
| JPA / Hibernate | ORM for token persistence |
| MySQL | Database |
| jjwt (io.jsonwebtoken) | JWT creation and parsing |
| BCrypt | Password hashing |
| Lombok | Boilerplate reduction |
| Maven | Build tool |

## Database

### Table: `auth_tokens`

| Column | Type | Description |
|---|---|---|
| `id` | CHAR(36) PK | UUID primary key |
| `token` | VARCHAR | JWT token string |
| `type_token` | VARCHAR | Token type (BEARER) |
| `revoked` | BOOLEAN | Whether token is revoked |
| `expired` | BOOLEAN | Whether token is expired |
| `userId` | CHAR(36) | Foreign key to user in MCSUsersFM |

## Patterns Used

- **MVP (Model-View-Presenter)**: Separates concerns into View (`AuthController`), Presenter (`UserPresenterFM`), and Model (`AuthService`, `MCSUsersFMServices`) layers via the `UserContractFM` interface
- **Repository Pattern**: `AuthTokenRepository` abstracts JPA data access for the `auth_tokens` table
- **DTO Pattern**: `UserPassDTO`, `AuthResponse`, `UserCredentials` transfer data between layers without exposing entities
- **Feign Client Pattern**: `FunnyMindDB` interface declares remote HTTP calls to MCSUsersFM declaratively
- **Global Exception Handler**: `GlobalExceptionHandler` provides consistent error response formatting
- **Configuration Properties**: `JwtProperties` loads JWT configuration from externalized properties
