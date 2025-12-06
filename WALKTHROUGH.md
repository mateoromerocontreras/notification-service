# Notification Service Walkthrough

This document outlines the features implemented in the Notification Service, including RabbitMQ integration, Email sending (mocked with MailHog), and Swagger UI for testing.

## 1. key Features

### RabbitMQ Integration
- **Queue**: `notification.queue`
- **Exchange**: `internal.exchange`
- **Consumer**: Listens for `NotificationRequest` messages and triggers the email service.

### Email Service (Mocked)
- Uses **MailHog** to capture sent emails without needing a real SMTP server.
- **SMTP Port**: `1025`
- **Web Interface**: `http://localhost:8025`

### Swagger UI
- Provides an interface to visualize and interact with the API.
- **URL**: `http://localhost:8081/swagger-ui.html`
- **Test Controller**: Includes a `/test/publish` endpoint to manually trigger notification events.

## 2. Setup & Running

The service is containerized using Docker and Docker Compose.

### Prerequisites
- Docker
- Docker Compose

### Start the Application
Run the following command in the project root:

```bash
docker-compose up -d --build
```

This will start:
1.  **RabbitMQ** (Message Broker)
2.  **MailHog** (SMTP Server & Web UI)
3.  **Notification Service** (The Application)

## 3. How to Test (Step-by-Step)

### Step 1: Trigger a Notification
You can simulate a "Background Event" (like a user registration) using the Swagger UI.

1.  Open Swagger: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
2.  Expand the `notification-test-controller`.
3.  Click on `POST /test/publish` -> **Try it out**.
4.  Enter the sample payload:
    ```json
    {
      "toUserId": 1,
      "toUserEmail": "test@utn.edu.ar",
      "message": "Welcome to the platform!",
      "sender": "Admin"
    }
    ```
5.  Click **Execute**.
6.  You should see a "200 OK" response with "Message published to RabbitMQ!".

### Step 2: Verify the Email
1.  Open MailHog: [http://localhost:8025](http://localhost:8025)
2.  Check the inbox. You should see a new email:
    - **From**: `noreply@utnconecta.com`
    - **To**: `test@utn.edu.ar`
    - **Subject**: `Notification from Admin`
    - **Body**: `Welcome to the platform!`

## 4. Troubleshooting

- **Swagger 404**: If you don't see Swagger, ensure you rebuilt the container (`docker-compose up -d --build`) to include the latest dependencies.
- **Connection Refused**: If the app fails to connect to RabbitMQ or MailHog, ensure all containers are running (`docker ps`) and healthy.
