# Microservice-Based Application

This project represents a microservice-based architecture where multiple independent services work together to deliver a comprehensive solution. The application includes **user authentication**, **user management**, **email notifications**, and **service discovery** as core features.

---

## 🗺️ Architecture Overview

The project includes the following main microservices and components:

- **API Gateway**  
  The main entry point that handles all external requests, routes them to the appropriate service, and enforces security policies.

- **Eureka Service**  
  A service registry and discovery server that allows microservices to find each other.

- **Auth Service**  
  Manages user authentication and account activation workflows. Produces events for email notifications via Kafka.

- **User Service**  
  Manages user information.

- **Share Service**  
  (Manages shares of user.)

- **Email Service**  
  Consumes events from Kafka produced by the Auth Service and sends activation or notification emails to users.

> 📌 [Microservice Architecture Diagram Image]

                                          |
                                          V
                                 +------------------+
                                 |    API Gateway   |
                     |---------> +------------------+
                     |                     |
                   +----------+-----------+-----------+----------+
                   | |         |                       |          |
                   v |         v                       v
        +----------------+  +----------------+  +----------------+
        |  Auth Service  |  |  User Service  |  |  Share Service |
        | +------------+ |  | +------------+ |  | +------------+ |
        | | Auth DB    | |  | | User DB    | |  | | Share DB   | |
        | +------------+ |  | +------------+ |  | +------------+ |
        +----------------+  +----------------+  +----------------+
        |  |  ^            |  ^                   |
        |  |  |            |  |                   |
        |  |  +------------+  +-------------------+
        |  |         Feign Client
        |  |
        |  | Kafka (Event)
        v  |
        +----------------+
        |     Kafka      |
        +----------------+
        |
        v
        +----------------+
        | Email Service  |
        +----------------+
        
               All Services Register to:
                     +----------------+
                     | Eureka Server  |
                     +----------------+
                       ^      ^      ^
                       |      |      |
                       |      |      |
                       |      |      ----------------- 
                       |      |                      |
        +----------------+  +----------------+  +----------------+
        |  Auth Service  |  |  User Service  |  |  Share Service |
        +----------------+  +----------------+  +----------------+


---

## ⚙️ Technology Stack

- **Backend:** Java, Spring Boot
- **Database:** MySQL (for Auth Service, User Service, Share Service)
- **Message Queue:** Apache Kafka (for communication from Auth Service to Email Service)
- **Service Discovery:** Netflix Eureka
- **Inter-Service Communication:** Feign Client
- **Build Tool:** Apache Maven
- **Frontend:** React

---

## 🔎 Service Details and Dependencies

### 🛡️ API Gateway
- The first point of contact for all external requests.
- Routes requests to the appropriate microservices.
- Enforces security checks (e.g., JWT validation).

---

### 📚 Eureka Service
- Microservices register themselves with Eureka.

---

### 🔐 Auth Service
- **Database:** MySQL
- **Dependencies:**
    - **User Service**
    - **Kafka**

---

### 👤 User Service
- **Database:** MySQL
- **Dependencies:**
    - **Auth Service:**

---

### 📤 Share Service
- **Database:** MySQL
- **Dependencies:**
    - **User Service:**

---

### ✉️ Email Service
- **Dependencies:**
    - **Kafka:**

---

## 🗂️ Project Structure

- All backend projects are developed using **Maven**.
- Each service is structured as a separate Maven project.
