# 📬 Notification Service Repository

## 1\. Overview

This repository contains the source code for the **Notification Service**, a dedicated, independent microservice responsible for handling all non-critical, asynchronous user communications (emails) for the Internship Platform UTN Conecta.

This service is decoupled from the main application via a **RabbitMQ** message broker, ensuring that mail failure or latency does not impact the core business logic or application performance.

### Key Responsibilities

  * Listening for application events (e.g., Trainee Accepted, Internship Modified).
  * Formatting emails using **Thymeleaf** templates.
  * Sending emails via an external SMTP provider (e.g., SendGrid, SES).
  * Handling bulk communications (e.g., newsletters).

## 2\. Architecture and Data Flow

The Notification Service operates on an **Event-Driven Architecture (EDA)**.

1.  **Publisher (Main Backend):** The `internship-platform-backend` completes a business action (e.g., changes trainee status) and publishes a simple message object (DTO) to a RabbitMQ Queue.
2.  **Broker (RabbitMQ):** Holds the message reliably until the consumer is ready.
3.  **Consumer (This Service):** The `NotificationListener` consumes the message, maps it to a Java object, and triggers the `MailService`.

## 3\. Technology Stack

  * **Framework:** Spring Boot 3.x
  * **Language:** Java 21
  * **Messaging:** Spring AMQP / RabbitMQ
  * **Templating:** Thymeleaf
  * **Build Tool:** Maven
  * **Configuration:** Environment variables (RABBITMQ_HOST, etc.) matching application.yml conventions.

## 4\. Getting Started

### Prerequisites

You must have the following running locally:

  * **Java Development Kit (JDK) 21**
  * **Maven**
  * **Docker** (to run the message broker and mail server locally)

## 5. Docker Support

To run the application along with its RabbitMQ dependency using Docker Compose:

```bash
docker-compose up --build
```

The application will be available at `http://localhost:8081` and RabbitMQ management console at `http://localhost:15672`.

## 6. Testing

Read WALKTHROUGH.md for more information on how to test the application.