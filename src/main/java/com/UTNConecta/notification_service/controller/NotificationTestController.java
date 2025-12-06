package com.UTNConecta.notification_service.controller;

import com.UTNConecta.amqp_commons.config.RabbitMQConstants;
import com.UTNConecta.amqp_commons.dto.NotificationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class NotificationTestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/publish")
    public String publishNotification(@RequestBody NotificationRequest request) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.INTERNAL_EXCHANGE,
                RabbitMQConstants.INTERNAL_NOTIFICATION_ROUTING_KEY,
                request);
        return "Message published to RabbitMQ!";
    }
}
