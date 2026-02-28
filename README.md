# spring-rabbitmq-consumer

Este projeto é um **consumer** de mensagens RabbitMQ, desenvolvido em Java com Spring Boot. Ele consome mensagens enviadas pelo projeto [spring-rabbitmq-producer](https://github.com/willbigas/spring-rabbitmq-producer).

## Como funciona

- Escuta a fila `notification.queue` no RabbitMQ.
- Desserializa as mensagens recebidas em JSON usando o conversor Jackson.
- Processa as mensagens conforme a lógica definida.

## Pré-requisitos

- Java 21+
- RabbitMQ em execução
- [spring-rabbitmq-producer](https://github.com/willbigas/spring-rabbitmq-producer) enviando mensagens para a mesma fila

## Como rodar

1. Clone este repositório:
   ```sh
   git clone https://github.com/willbigas/spring-rabbitmq-consumer.git
   ```
2. Instale as dependências:
   ```sh
   ./gradlew build
   ```
3. Execute a aplicação:
   ```sh
   ./gradlew bootRun
   ```

## Configuração

A fila utilizada é `notification.queue`. Certifique-se de que o producer está enviando mensagens para essa fila.

## Licença

MIT

