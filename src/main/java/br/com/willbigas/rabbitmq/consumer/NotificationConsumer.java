package br.com.willbigas.rabbitmq.consumer;

import br.com.willbigas.rabbitmq.config.RabbitConfig;
import br.com.willbigas.rabbitmq.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleNotification(NotificationMessage message) {
        System.out.println("Received: " + message);

        // Simulate processing
        try {
            Thread.sleep(1000); // 1 second processing time
            System.out.println("Notification sent to: " + message.to());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
