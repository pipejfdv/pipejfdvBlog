# MCSConfig — Centralized Configuration Server

## Overview

MCSConfig is a centralized configuration server built with **Spring Cloud Config**. It provides externalized configuration for all microservices in the pipejfdvBlog platform, allowing configuration changes without rebuilding or redeploying services.

## Architecture

| Property       | Value                 |
|----------------|-----------------------|
| Port           | 8888                  |
| Profile        | native                |
| Config Format  | YAML                  |
| Backend        | filesystem-based      |

The server runs on port `8888` using the `native` profile, which reads configuration files directly from the classpath. Each microservice fetches its configuration from this server at startup and can be refreshed at runtime.

## Configurations Served

| Microservice   | Config File                     |
|----------------|---------------------------------|
| MCS-EUREKA     | `mcs-eureka.yml`                |
| MCS-GATEWAY    | `mcs-gateway.yml`               |
| MCS-AUTH       | `mcs-auth.yml`                  |
| MCS-JUEGOS     | `mcs-juegos.yml`                |
| MCS-USER-FM    | `mcs-user-fm.yml`               |

## Tech Stack & Patterns

- **Java 17**
- **Spring Boot 3.x**
- **Spring Cloud Config Server**
- **Native profile** for filesystem-backed configuration
- **Config-first bootstrap** — all services load config from this server on startup
