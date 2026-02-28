package br.com.willbigas.rabbbitmq.dto;

import java.io.Serializable;

public record NotificationMessage(String to, String subject, String body) implements Serializable {
}
