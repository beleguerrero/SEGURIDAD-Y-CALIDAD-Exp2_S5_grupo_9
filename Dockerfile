
# Etapa 1: Compilar el proyecto
FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación
FROM eclipse-temurin:25.0.5_8-jre-alpine-3.23

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]