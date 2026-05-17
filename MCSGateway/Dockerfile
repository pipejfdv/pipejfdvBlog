# Etapa 1: Build
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

# Copiar solo pom.xml primero (mejor caché)
COPY MCSGateway/pom.xml .
COPY pom.xml ../

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY MCSGateway/src ./src

# Compilar (skip tests para velocidad)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime (imagen liviana)
FROM eclipse-temurin:17-jre-alpine

# Crear usuario no root
RUN addgroup -S spring && adduser -S spring -G spring

# Directorio de trabajo
WORKDIR /app

# Copiar JAR desde builder
COPY --from=builder /build/target/*.jar app.jar

# Configurar JVM para bajo consumo
ENV JAVA_OPTS="-Xmx192m -Xms96m -XX:+UseG1GC -XX:+UseStringDeduplication"

# Puerto del servicio
EXPOSE 8080

# Cambiar a usuario no root
USER spring

# Comando de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]