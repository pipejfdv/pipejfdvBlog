# MCSGateway

## Overview

MCSGateway is the **API Gateway** for the pipejfdv microservices ecosystem. It acts as the **single entry point** for all client requests, built on top of **Spring Cloud Gateway** with a **reactive WebFlux** stack.

## Architecture

- **Port:** `8080`
- **Framework:** Spring Cloud Gateway (reactive, non-blocking)
- **Underlying engine:** Project Reactor (WebFlux)

The gateway routes incoming requests to internal microservices behind the scenes:

| Route Prefix | Target Microservice |
|---|---|
| `/pipejfdv/api/v1/auth/**` | **MCS-AUTH** – authentication & token management |
| `/pipejfdv/api/v1/funnyMind/**` | **MCS-USER-FM** – user & FunnyMind domain logic |
| `/pipejfdv/api/v1/games/**` | **MCS-JUEGOS** – games service |

All routes use **StripPrefix=3**, meaning the first three path segments (`/pipejfdv/api/v1`) are removed before forwarding to the downstream service.

## Security

The gateway implements a **two-chain security model**:

### 1. Public Chain (Order 1)
- Matches: user creation, login, logout, and token refresh endpoints
- All requests on this chain are **permitted without authentication**
- No JWT validation is performed

### 2. Secured Chain (Order 2)
- Catches **all other requests**
- Requires a **valid JWT** in the `Authorization` header
- JWT is validated using an **HMAC-SHA256** shared secret key
- The **`accountType`** claim inside the JWT is mapped to a Spring Security role via `ConverterRoleJWT`
- Role-based access control:
  - `/funnyMind/**` — `PremiumUser`, `DemoUser`, `FMAdmin`, `Medic`
  - `/auth/deleted/**` — `FMAdmin` only
  - `/games/**` — `PremiumUser`, `DemoUser`, `FMAdmin`, `Medic`

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Language |
| Spring Cloud Gateway 4.x | API Gateway |
| Spring Boot 3.x | Application framework |
| Spring WebFlux | Reactive HTTP layer |
| Spring Security | Authentication & authorization |
| OAuth2 Resource Server | JWT bearer token validation |
| Nimbus JOSE + JWT | JWT parsing & verification |
| JUnit 5 | Unit testing |
| Maven | Build & dependency management |

## Patterns Used

- **API Gateway Pattern** – single entry point that routes to multiple backend services
- **Security Filter Chain Pattern** – ordered filter chains for public vs secured endpoints
- **JWT Authentication Pattern** – stateless, token-based authentication
- **Role-Based Access Control (RBAC)** – role-checking via `hasAnyRole()`
- **Converter Pattern** – custom `Converter<Jwt, Mono<AbstractAuthenticationToken>>` to transform JWT claims into Spring Security authorities
- **Reactive / Non-Blocking** – all I/O is asynchronous using WebFlux
- **Secret Key Signing** – HMAC-SHA256 shared secret for JWT integrity
