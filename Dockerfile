# Multi-stage Dockerfile for repairmsg (Spring Boot, Java 25)

# Build stage
FROM maven:3.9.16-eclipse-temurin-25 AS build
WORKDIR /app
# Copy maven wrapper and pom first to leverage layer caching
COPY mvnw pom.xml .
COPY .mvn .mvn
COPY pom.xml .
# Copy source
COPY src ./src

# Build the application (skip tests by default for image build speed; remove -DskipTests=false if you want tests)
RUN mvn -B -DskipTests package

# Run stage
FROM eclipse-temurin:25-jre
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Use a non-root user for security
RUN addgroup --system app && adduser --system --ingroup app app || true
USER app

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
