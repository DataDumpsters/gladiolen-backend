# Use an official Maven image to build the application
FROM maven:latest AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Use an official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Install curl
RUN apt-get update && apt-get install -y curl

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]