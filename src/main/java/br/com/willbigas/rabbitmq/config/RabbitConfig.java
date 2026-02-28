package br.com.willbigas.rabbitmq.config;

import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class RabbitConfig {

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, RetryOperationsInterceptor retryInterceptor) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setConsumerTagStrategy(queue -> "spring-rabbitmq-consumer-" + queue);
		factory.setMessageConverter(messageConverter(new ObjectMapper()));
		factory.setAdviceChain(retryInterceptor); // Adiciona o interceptor de retry
		return factory;
	}

	/**
	 * Configura pra deserializar em Json
	 */
	@Bean
	public MessageConverter messageConverter(ObjectMapper objectMapper) {
		return new Jackson2JsonMessageConverter(objectMapper);
	}

	/**
	 * Configura Retry no consumo usando Spring Retry
	 * @return
	 */
	@Bean
	public RetryOperationsInterceptor retryInterceptor() {
	    return RetryInterceptorBuilder.stateless()
	        .maxAttempts(3)
	        .backOffOptions(1000, 2.0, 10000) // delay inicial, multiplicador, delay máximo (opcional)
	        .recoverer(new RejectAndDontRequeueRecoverer()) // Garante rejeição para DLQ
	        .build();
	}

}
