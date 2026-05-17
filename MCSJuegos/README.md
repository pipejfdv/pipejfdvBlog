# MCSJuegos - Games Microservice

Games microservice for the TBI (Traumatic Brain Injury) cognitive rehabilitation platform. Manages games, categories, scores, and child progress tracking across different cognitive domains.

## Architecture

- **Port:** `8091`
- **Service Discovery:** Eureka client (`@EnableDiscoveryClient`)
- **Inter-service Communication:** Feign client to `MCS-USER-FM` (port `8090`) for child data
- **Database:** MySQL via JPA/Hibernate
- **Auth:** JWT validation via Spring Security OAuth2 Resource Server

## Security

All endpoints under `/games` are protected by JWT authentication. The `accountType` claim in the JWT is mapped to a Spring Security role:

| Role | Access |
|------|--------|
| **Public** | `/games/listGames`, `/games/listCategories` |
| **DemoUser / PremiumUser / FMAdmin / Medic** | `/games/read/**`, `/games/read/category/**` |
| **PremiumUser / FMAdmin** | `/games/progress`, `/games/createGameStat` |
| **Medic** | `/games/progress/**`, `/games/read/**` |
| **FMAdmin** | All `/games/**` endpoints |

## Game Categories

Three cognitive domains are seeded on startup:

1. **FUNCIÓN EJECUTIVA** (Executive Function) — Planning, logical sequencing, strategic problem solving
   - Game: *Torre de Hanói*
2. **MEMORIA Y ATENCIÓN** (Memory & Attention) — Visual memory, sustained concentration, pattern recognition
   - Game: *Los pares*
3. **VELOCIDAD DE PROCESAMIENTO** (Processing Speed) — Mental reflexes, reaction time, quick response
   - Game: *Reaction*

## XP System & Level Progression

### Scoring per Category
- **Executive Function / Memory & Attention:** `(correct/total) * 100 - mistakes * 4.2`
- **Processing Speed:** `((timeAllowed - timeUsed) / timeAllowed) * 100`

### XP Calculation
1. Raw score is divided by `StandardPointsCategory` (4.0) to get base XP
2. XP is multiplied by a **level multiplier** (0.5x to 2.5x)
3. A **penalty** reduces XP based on attempt count (2nd=80%, 3rd=60%, 4th=40%, 5th=20%)
4. Total XP determines the **level**:

| XP Threshold | Level |
|-------------|-------|
| 0–199 | Inicial (Initial) |
| 200–399 | Básico (Basic) |
| 400–599 | Intermedio (Intermediate) |
| 600–799 | Avanzado (Advanced) |
| 800–999 | Experto (Expert) |

### Daily Attempts
Daily attempt counters reset automatically at midnight via a scheduled task (`@Scheduled(cron = "0 0 0 * * ?")`).

## Database Tables

| Table | Description |
|-------|-------------|
| `games` | Game definitions linked to a category |
| `category_of_games` | Cognitive domain categories |
| `game_stats` | Per-child, per-game score tracking |
| `child_progres` | Per-child, per-category XP and level (unique on child + category) |

## Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** (OAuth2 Resource Server, JWT)
- **Spring Cloud** (Eureka Discovery, OpenFeign)
- **Spring Data JPA** (Hibernate)
- **MySQL**
- **Lombok**
- **Maven**

## Patterns Used

- **DTO Pattern** — Data transfer objects for API responses
- **Service Layer** — Business logic separated from controllers
- **Repository Pattern** — Data access via Spring Data JPA
- **Global Exception Handler** — `@RestControllerAdvice` for consistent error responses
- **Feign Client** — Declarative HTTP client for inter-service communication
- **CommandLineRunner** — Database seeding on startup

## Scheduled Tasks

- **Daily reset** — Resets `attemptsDaily` to 0 for all children at midnight
