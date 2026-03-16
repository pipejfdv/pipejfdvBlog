FROM maven:3.9.9-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
ARG MODULE
RUN mvn clean package -pl ${MODULE} -am -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app
ARG MODULE
COPY --from=build /app/${MODULE}/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
