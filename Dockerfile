# Use an official Java runtime as a parent image
FROM eclipse-temurin:23-jdk AS build

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file
COPY target/user-service.jar user-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "user-service.jar"]
