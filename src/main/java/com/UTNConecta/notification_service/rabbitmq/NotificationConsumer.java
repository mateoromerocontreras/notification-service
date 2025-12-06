package com.UTNConecta.notification_service.rabbitmq;

import com.UTNConecta.amqp_commons.config.RabbitMQConstants;
import com.UTNConecta.amqp_commons.dto.NotificationRequest;
import com.UTNConecta.notification_service.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConstants.NOTIFICATION_QUEUE)
    public void consume(NotificationRequest notificationRequest) {
        log.info("Consumed {} from queue", notificationRequest);

        emailService.sendEmail(
                notificationRequest.toUserEmail(),
                "Notification from " + notificationRequest.sender(),
                notificationRequest.message());
    }
}
