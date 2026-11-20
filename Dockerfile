
# Etapa 1: compilar con Java 21 y Gradle
FROM gradle:9.2.1-jdk21 AS build

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN gradle --no-daemon clean bootJar

# Etapa 2: ejecutar la API
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=70.0"

ENTRYPOINT ["java", "-jar", "app.jar"]
