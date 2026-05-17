# MCSEureka — Service Discovery Server

## Overview

MCSEureka is a **Netflix Eureka** service discovery server. All microservices in the pipejfdvBlog platform register themselves with this server, enabling dynamic service discovery and load-balanced communication without hardcoded URLs.

## Architecture

| Property       | Value                 |
|----------------|-----------------------|
| Port           | 8761                  |
| Type           | Eureka Server         |
| Dashboard      | `/` (web UI)          |

The Eureka server runs on port `8761`. Each microservice acts as a Eureka client and registers with its hostname, port, and health status. The Eureka dashboard provides a real-time view of all registered services.

## How Services Discover Each Other

1. Every microservice (Gateway, Auth, Juegos, User-FM) registers with MCSEureka at startup using its `application name`.
2. When one service needs to call another, it queries Eureka by the target service's application name.
3. Eureka returns the list of available instances (with load balancing via **Spring Cloud LoadBalancer** or **Ribbon**).
4. The calling service routes the request to a healthy instance.

This eliminates hardcoded host:port pairs and makes the system resilient to instance failures and scaling events.

## Tech Stack & Patterns

- **Java 17**
- **Spring Boot 3.x**
- **Spring Cloud Netflix Eureka Server**
- **Service discovery pattern** — services find each other by logical names
- **Client-side load balancing** — integrated with Spring Cloud LoadBalancer
- **Health checks** — Eureka monitors instance heartbeats and removes dead instances
- **Self-preservation mode** — protects against network partitions
