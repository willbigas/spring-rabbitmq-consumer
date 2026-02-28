package br.com.willbigas.rabbitmq.consumer;

import br.com.willbigas.rabbitmq.config.NotificationQueueConfig;
import br.com.willbigas.rabbitmq.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = NotificationQueueConfig.QUEUE_NAME)
    public void handleNotification(NotificationMessage message) {
        try {
            System.out.println("Received: " + message);
            // Simulate processing
            Thread.sleep(1000); // 1 second processing time
            System.out.println("Notification sent to: " + message.to());
//            throw new RuntimeException("Erro ao processar mensagem"); // Forçar Erro para teste de DLQ
        } catch (Exception e) {
            // Lançando exceção para que o RabbitMQ envie para a DLQ
            throw new RuntimeException("Erro ao processar mensagem, enviando para DLQ", e);
        }
    }

    @RabbitListener(queues = NotificationQueueConfig.DLQ_NAME)
    public void handleDlq(NotificationMessage message) {
        System.err.println("Mensagem encaminhada para DLQ: " + message);
        // Aqui você pode implementar lógica de alerta, log ou retentativa manual
    }
}
