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

## 4\. Getting Started

### Prerequisites

You must have the following running locally:

  * **Java Development Kit (JDK) 21**
  * **Maven**
  * **Docker** (to run the message broker and mail server locally)

### Local Setup

1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/your-org/notification-service-repo.git
    cd notification-service-repo
    ```

2.  **Start Dependencies (RabbitMQ & MailHog)**
    We recommend using **Docker Compose** to run local dependencies for development. This example uses **MailHog**—a fake SMTP server that catches emails without actually sending them, perfect for testing.

    *Create a `docker-compose.yml` file with RabbitMQ and MailHog services.*

    ```bash
    docker compose up -d
    ```

    > You can view intercepted emails at: `http://localhost:8025`

3.  **Configure Application**
    Update the `src/main/resources/application.yml` file with your specific credentials, or ensure the required environment variables are set.

4.  **Run the Service**

    ```bash
    mvn spring-boot:run
    ```

## 5\. Key Configuration Details

| Config Property | Description | Default Local Value |
| :--- | :--- | :--- |
| `spring.rabbitmq.host` | Hostname of the message broker. | `localhost` |
| `spring.mail.host` | Hostname of the SMTP server. | `localhost` (MailHog) |
| `server.port` | The port this service runs on. | `8081` |

### Message Queues

This service consumes messages from the following queues, which are defined in `RabbitMQConfig.java`:

  * `application.events.queue`: Handles accepted/rejected status updates.
  * `marketing.events.queue`: Handles newsletter or bulk communications.

## 6\. Testing

### Unit Tests

Run isolated tests on the `MailService` and message parsing logic using Mockito:

```bash
mvn test
```

### Integration Tests

Use **Testcontainers** (highly recommended) to run tests against a temporary, real RabbitMQ instance and verify message consumption:

```bash
mvn clean install
```