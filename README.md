# pipejfdvBlog - Plataforma de Juegos Terapéuticos para TCE

Plataforma backend de juegos terapéuticos para el tratamiento del **Trauma Craneoencefálico (TCE)**. Construida con una arquitectura de microservicios usando Spring Boot, Spring Cloud y seguridad JWT.

---

## Resumen de Arquitectura

```
Cliente → MCSGateway (puerto 8080)
              ↓
         MCSEureka (puerto 8761)
              ↓
    ┌──── MCSConfig (puerto 8888) ────┐
    ↓                                ↓
MCSAuth (9000) ←── Feign ──→ MCSUsersFM (8090) ←── Feign ──→ MCSJuegos (8091)
```

## Microservicios

| Servicio | Puerto | Descripción | README |
|---------|------|-------------|--------|
| **MCSGateway** | 8080 | API Gateway - punto único de entrada, validación JWT, enrutamiento | [README](MCSGateway/README.md) |
| **MCSEureka** | 8761 | Descubrimiento de Servicios - servidor Netflix Eureka | [README](MCSEureka/README.md) |
| **MCSConfig** | 8888 | Servidor de Configuración Centralizada | [README](MCSConfig/README.md) |
| **MCSAuth** | 9000 | Autenticación - creación JWT, inicio de sesión, refresco, cierre de sesión | [README](MCSAuth/README.md) |
| **MCSUsersFM** | 8090 | Usuarios, tutores, niños, clasificaciones TCE, perfiles | [README](MCSUsersFM/README.md) |
| **MCSJuegos** | 8091 | Juegos, categorías, puntuaciones, progreso infantil, sistema XP | [README](MCSJuegos/README.md) |

## Seguridad

### Flujo de Autenticación
1. **MCSAuth** recibe credenciales de inicio de sesión, consulta MCSUsersFM vía Feign para datos de usuario
2. Valida la contraseña con BCrypt, crea JWT (HS256) con claim `accountType`
3. El cliente incluye el JWT como `Authorization: Bearer <token>` en solicitudes subsecuentes

### Control de Acceso por Roles
| Rol | Descripción |
|------|-------------|
| `DemoUser` | Usuario básico - rutas compartidas + CRUD de niños |
| `PremiumUser` | Usuario premium - rutas compartidas + niños + progreso |
| `FMAdmin` | Acceso total de administrador en todos los servicios |
| `Medic` | Profesional médico - gestión TCE + niños |
| `Analyst` | Definido en BD pero no asignado a rutas |

### Token JWT
- Algoritmo: HMAC-SHA256
- Claims: `id` (UUID de usuario), `sub` (nombre de usuario), `accountType` (rol)
- Convertido a roles de Spring Security mediante `JwtConvertRol`/`ConverterRoleJWT`

## Patrones Utilizados

| Patrón | Implementación |
|---------|---------------|
| **MVP** | Interfaces de contrato que definen Vista/Presentador/Modelo |
| **Repository** | Repositorios Spring Data JPA |
| **DTO** | DTOs separados para vistas Pública y de Administrador |
| **Feign Client** | Comunicación entre servicios (MCSAuth → MCSUsersFM, MCSJuegos → MCSUsersFM) |
| **Global Exception Handler** | `@RestControllerAdvice` para manejo centralizado de errores |
| **API Gateway** | Spring Cloud Gateway para enrutamiento, seguridad, balanceo de carga |
| **Service Discovery** | Netflix Eureka para registro y descubrimiento de servicios |
| **Configuration Server** | Spring Cloud Config para configuración centralizada |

## Stack Tecnológico

- **Java 17**
- **Spring Boot 3.5**
- **Spring Cloud 2025.0.0** (Gateway, Config, Netflix Eureka, OpenFeign)
- **Spring Security** + **OAuth2 Resource Server**
- **Spring Data JPA** + Hibernate
- **Bases de Datos**: MySQL (MCSUsersFM), PostgreSQL (MCSAuth, MCSJuegos)
- **JWT**: librería jjwt
- **Lombok**
- **Maven**
