package br.com.willbigas.rabbitmq.dto;

import java.io.Serializable;

public record NotificationMessage(String to, String subject, String body) implements Serializable {
}
