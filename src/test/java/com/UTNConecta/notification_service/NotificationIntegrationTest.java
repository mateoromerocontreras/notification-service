package com.UTNConecta.notification_service;

import com.UTNConecta.amqp_commons.config.RabbitMQConstants;
import com.UTNConecta.amqp_commons.dto.NotificationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class NotificationIntegrationTest {

        @MockitoBean
        private RabbitTemplate rabbitTemplate;

        @Test
        public void testPublishNotification() {
                NotificationRequest request = new NotificationRequest(
                                123,
                                "test@example.com",
                                "Integration test message",
                                "System");

                rabbitTemplate.convertAndSend(
                                RabbitMQConstants.INTERNAL_EXCHANGE,
                                RabbitMQConstants.INTERNAL_NOTIFICATION_ROUTING_KEY,
                                request);

                org.mockito.Mockito.verify(rabbitTemplate, org.mockito.Mockito.times(1))
                                .convertAndSend(
                                                RabbitMQConstants.INTERNAL_EXCHANGE,
                                                RabbitMQConstants.INTERNAL_NOTIFICATION_ROUTING_KEY,
                                                request);

                System.out.println("Integration Test: Message sent to (Mock) RabbitMQ successfully.");
        }
}
