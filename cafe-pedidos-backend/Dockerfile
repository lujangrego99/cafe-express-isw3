# ---- Etapa 1: build con Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Cacheo de dependencias: primero el pom, luego el codigo.
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ---- Etapa 2: imagen liviana de ejecucion ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/cafe-pedidos-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
