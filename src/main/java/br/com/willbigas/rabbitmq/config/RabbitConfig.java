package br.com.willbigas.rabbbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "notification.queue";

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NAME, true); // durable = true
    }

    @Bean
    public SimpleMessageConverter messageConverter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("br.com.willbigas.rabbitmq.dto.*"));
        return converter;
    }
}
