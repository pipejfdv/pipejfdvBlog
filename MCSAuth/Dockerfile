# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY MCSAuth/pom.xml .
COPY pom.xml ../

RUN mvn dependency:go-offline -B

COPY MCSAuth/src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

# Auth necesita un poco más de RAM por conexiones a BD
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:+UseStringDeduplication"

EXPOSE 9000

USER spring

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]