package br.com.willbigas.rabbitmq.consumer;

import br.com.willbigas.rabbitmq.config.NotificationRabbitConfig;
import br.com.willbigas.rabbitmq.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = NotificationRabbitConfig.QUEUE_NAME)
    public void handleNotification(NotificationMessage message) {
        try {
            System.out.println("Received: " + message);
            // Simulate processing
            Thread.sleep(1000); // 1 second processing time
            System.out.println("Notification sent to: " + message.to());
        } catch (Exception e) {
            // Lançando exceção para que o RabbitMQ envie para a DLQ
            throw new RuntimeException("Erro ao processar mensagem, enviando para DLQ", e);
        }
    }

    @RabbitListener(queues = NotificationRabbitConfig.DLQ_NAME)
    public void handleDlq(NotificationMessage message) {
        System.err.println("Mensagem encaminhada para DLQ: " + message);
        // Aqui você pode implementar lógica de alerta, log ou retentativa manual
    }
}
