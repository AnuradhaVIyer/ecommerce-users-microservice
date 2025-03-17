# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file
COPY target/ecommerce-ms-users.jar ecommerce-ms-users.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "ecommerce-ms-users.jar"]
