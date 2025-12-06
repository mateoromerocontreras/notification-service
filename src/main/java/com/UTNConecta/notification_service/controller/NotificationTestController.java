package com.UTNConecta.notification_service.controller;

import com.UTNConecta.notification_service.config.RabbitMQConfig;
import com.UTNConecta.notification_service.dto.NotificationRequest;
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
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                request);
        return "Message published to RabbitMQ!";
    }
}
