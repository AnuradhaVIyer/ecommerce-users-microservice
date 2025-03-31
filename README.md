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
│   │   │         ├── users	// Model, Data access layer, Controller - REST APIs, Business logic
│   │   └── resources
│   │        ├── static // For static assets (CSS, JS, images, etc.)
│   │        ├── templates // For dynamic HTML templates (Thymeleaf, FreeMarker, etc.)
│   │        ├── application.properties
│   │        ├── application-dev.properties // Properties for dev environment
│   │        ├── application-prod.properties // Properties for prod environment
│   │        └── data.sql // Initial seed data
│── Dockerfile // Docker instructions
│── docker-compose.yml // Docker Compose config
│── pom.xml // Maven build file
│── .github
│   ├── workflows
│   │   ├── build-test.yml // CI: Build, Test, Security Check, Upload Build Artifacts, Send Slack and email message
│   │   ├── docker-service-deploy-ec2.yml // Docker Build service and mysql and Deploy to AWS ECS
│   │   ├── docker-build.yml // Docker Build & Push image to to AWS ECR
│   │   ├── deploy-ec2.yml // Pull Docker image from ECR and Deploy to AWS EC2
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

### **4️⃣ Deploy to AWS ECS** (`.github/workflows/deploy-ecs.yml`)
Runs after Docker push is complete:
- Deploy to AWS ECS
- Rollback to the last working version if deployment fails
- Send Slack & Email Notifications on success/failure

## 🔄 Rollback Mechanism
If deployment fails, the workflow will automatically rollback to the previous ECS task definition.

## 💟 Storing Secrets in GitHub

Instead of committing sensitive information in a `.env` file, it's best to store secrets securely using **GitHub Secrets in the ENV_VARS** variable and other variables also.

### **1️⃣ Add Secrets to GitHub Repository**
1. Go to **Repository → Settings → Secrets and variables → Actions → New repository secret**
2. Add each environment variable as a secret:
   - **AWS_ACCESS_KEY_ID**
   - **AWS_ACCOUNT_ID**
   - **AWS_REGION**
   - **AWS_SECRET_ACCESS_KEY**
   - **EC2_INSTANCE_IP**
   - **ENV_VARS** → contents of .env file
   - **SSH_PRIVATE_KEY** → contents of .pem file
   - **EMAIL_PASSWORD**
   - **EMAIL_USERNAME**
   - **NOTIFICATION_EMAIL**
   - **SLACK_WEBHOOK**


### **2️⃣ Generate `.env` File During CI/CD**
Modify your GitHub Actions workflow to dynamically create the `.env` file:

```yaml
    - name: Generate .env file from GitHub Secrets
      run: |
        echo "${{ secrets.ENV_VARS }}" > $GITHUB_WORKSPACE/.env
```

### **3️⃣ Reference `.env` in Docker Compose**
Ensure that your `docker-compose.yml` includes the `.env` file:

```yaml
services:
  mysql-db:
    image: mysql:latest
    env_file:
      - .env
    volumes:
      - .env:/app/.env
```

## 👤 Sample Endpoints
- `GET v1/users` - Retrieve all users
- `POST v1/users/register` - Register a new user

## 📈 API Documentation
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

