package com.UTNConecta.notification_service.rabbitmq;

import com.UTNConecta.notification_service.config.RabbitMQConfig;
import com.UTNConecta.notification_service.dto.NotificationRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @org.springframework.beans.factory.annotation.Autowired
    private com.UTNConecta.notification_service.service.EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consume(NotificationRequest notificationRequest) {
        log.info("Consumed {} from queue", notificationRequest);

        emailService.sendEmail(
                notificationRequest.toUserEmail(),
                "Notification from " + notificationRequest.sender(),
                notificationRequest.message());
    }
}
