package com.UTNConecta.notification_service.config;

import com.UTNConecta.amqp_commons.config.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange internalExchange() {
        return new TopicExchange(RabbitMQConstants.INTERNAL_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(RabbitMQConstants.NOTIFICATION_QUEUE);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalExchange())
                .with(RabbitMQConstants.INTERNAL_NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
