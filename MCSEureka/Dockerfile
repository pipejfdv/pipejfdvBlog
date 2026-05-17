# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY MCSEureka/pom.xml .
COPY pom.xml ../

RUN mvn dependency:go-offline -B

COPY MCSEureka/src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

# Eureka necesita menos RAM (es solo discovery)
ENV JAVA_OPTS="-Xmx128m -Xms64m -XX:+UseG1GC"

EXPOSE 8761

USER spring

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]