# 🚲 Spring Boot eCommerce User Service

This repository contains a **Spring Boot** eCommerce User application built using a microservice architecture. The application covers core features of managing users. It uses an **in-memory H2 database** for ease of development and testing.

## 📌 Features

- **User Management**: Register, authenticate, and manage user profiles
- **In-Memory H2 Database**: Easy setup for development and testing
- **Password Encryption**: All user passwords are securely encrypted using **BCrypt** before being stored in the database.  This ensures strong protection against unauthorized access.  

**Note:** When logging in, the provided password is validated against the hashed password using `BCryptPasswordEncoder.matches()`.

## 🛠️ Tech Stack

- **Java**: Core programming language (Java 23)
- **Spring Boot**: Application framework
- **H2 Database**: In-memory database for development
- **MySQL Database**: MySQL database for production
- **Maven**: Build and dependency management
- **Docker**: Docker for containers
- **Docker Registry**: Either Docker Hub or AWS Elastic Container Registry (ECR)
- **AWS ECS / EC2**: Deployment targets
- **GitHub Actions**: CI/CD automation

## 📺 Project Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │    └── ecommerce-users-microservice
│   │   │         ├── aspect	// AOP Logging
│   │   │         ├── exception  // Exceptions and errors
│   │   │         ├── dto	// Data transfer objects
│   │   │         ├── filter      // Filter for adding tracing
│   │   │         ├── dto        // Data Transfer Objects (UserDTO, ProductDTO, etc.)
│   │   │         └── users	// Model, Data access layer, Controller - REST APIs, Business logic
│   │   └── resources
│   │        ├── static // For static assets (CSS, JS, images, etc.)
│   │        ├── templates // For dynamic HTML templates (Thymeleaf, FreeMarker, etc.)
│   │        ├── application.properties
│   │        ├── application-dev.properties // Properties for dev environment
│   │        ├── application-prod.properties // Properties for prod environment
│   │        └── data.sql // Initial seed data
│── Dockerfile // Docker instructions
│── docker-compose.yml // Docker Compose config
│── .env // Environment variables
│── pom.xml // Maven build file
│── .github
│   ├── workflows
│   │   ├── build-test.yml // CI: Build, Test, Security Check
│   │   ├── docker-build.yml // Docker Build & Push to AWS ECR
│   │   ├── deploy-ec2.yml // Deploy to AWS EC2
│   │   ├── deploy-ecs.yml // Deploy to AWS ECS
│── README.md
```

## 🚀 GitHub Actions CI/CD Pipeline

### **1️⃣ Build & Test** (`.github/workflows/build-test.yml`)
Runs on push & pull requests:
- Set up Java 23
- Build with Maven
- Run Unit Tests
- Run Security & Code Scanning (CodeQL, Checkstyle)
- Upload artifacts (`JAR`, `Dockerfile`, `docker-compose.yml`, `.env`, `*.properties`)
- Send Slack & Email Notifications on success/failure

### **2️⃣ Docker Build & Push** (`.github/workflows/docker-build.yml`)
Runs after Build & Test completes:
- Download build artifacts
- Log in to AWS ECR
- Build and push Docker image
- Send Slack & Email Notifications on success/failure

### **3️⃣ Deploy to AWS EC2** (`.github/workflows/deploy-ec2.yml`)
Runs after Docker push is complete:
- Deploy to AWS EC2 (pull Docker image & run it)
- Send Slack & Email Notifications on success/failure

### **3️⃣ Deploy to AWS ECS** (`.github/workflows/deploy-ecs.yml`)
Runs after Docker push is complete:
- Deploy to AWS ECS
- Rollback to the last working version if deployment fails
- Send Slack & Email Notifications on success/failure

## 🔄 Rollback Mechanism
If deployment fails, the workflow will automatically rollback to the previous ECS task definition.

## 📡 Notifications
- **Slack**: Notifies success/failure for each stage
- **Email**: Sends email alerts for build/test, Docker push, and deployment

## Sample Endpoints
- `GET v1/users` - Retrieve all users
- `POST v1/users/register` - Register a new user

## API Documentation
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 💍 H2 Database Console
```
http://localhost:8080/h2-console
```

## 🤝 Contributing
Feel free to fork the repository and submit pull requests. Contributions are welcome!

## 🐝 License
This project is licensed under the MIT License.

---

🌟 **Happy Coding!**

