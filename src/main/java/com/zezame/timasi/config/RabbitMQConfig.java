package com.zezame.timasi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EMAIL_EX_CO = "email.notification.exchange.co";
    public static final String EMAIL_QUEUE_CO = "email.notification.queue.co";
    public static final String EMAIL_ROUTING_KEY_CO = "email.notification.key.co";

    public static final String EMAIL_EX_CI = "email.notification.exchange.ci";
    public static final String EMAIL_QUEUE_CI = "email.notification.queue.ci";
    public static final String EMAIL_ROUTING_KEY_CI = "email.notification.key.ci";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public DirectExchange checkoutExchange() {
        return new DirectExchange(EMAIL_EX_CO);
    }

    @Bean
    public DirectExchange checkInExchange() {
        return new DirectExchange(EMAIL_EX_CI);
    }

    @Bean
    public Queue checkInQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE_CI).build();
    }

    @Bean
    public Queue checkoutQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE_CO).build();
    }

    @Bean
    public Binding checkoutBinding() {
        return BindingBuilder.bind(checkoutQueue())
                .to(checkoutExchange())
                .with(EMAIL_ROUTING_KEY_CO);
    }

    @Bean
    public Binding checkInBinding() {
        return BindingBuilder.bind(checkInQueue())
                .to(checkInExchange())
                .with(EMAIL_ROUTING_KEY_CI);
    }
}
