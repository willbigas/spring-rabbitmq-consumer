package br.com.willbigas.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NotificationQueueConfig {

	public static final String QUEUE_NAME = "notification.queue";
	public static final String DLQ_NAME = "notification.queue.dlq";
	public static final String EXCHANGE_NAME = "notification.exchange";
	public static final String DLQ_EXCHANGE_NAME = "notification.exchange.dlq";
	public static final String ROUTING_KEY = "notification.send";
	public static final String DLQ_ROUTING_KEY = "notification.send.dlq";


	@Bean
	public DirectExchange notificationExchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean
	public DirectExchange notificationDlqExchange() {
		return new DirectExchange(DLQ_EXCHANGE_NAME);
	}

	@Bean
	public Queue notificationQueue() {
		Map<String, Object> args = new HashMap<>();
		args.put("x-dead-letter-exchange", DLQ_EXCHANGE_NAME);
		args.put("x-dead-letter-routing-key", DLQ_ROUTING_KEY);
		return new Queue(QUEUE_NAME, true, false, false, args);
	}

	@Bean
	public Queue notificationDlqQueue() {
		return new Queue(DLQ_NAME, true);
	}

	@Bean
	public Binding notificationBinding() {
		return BindingBuilder.bind(notificationQueue())
				.to(notificationExchange())
				.with(ROUTING_KEY);
	}

	@Bean
	public Binding notificationDlqBinding() {
		return BindingBuilder.bind(notificationDlqQueue())
				.to(notificationDlqExchange())
				.with(DLQ_ROUTING_KEY);
	}
}
