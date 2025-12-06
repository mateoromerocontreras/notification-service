package com.UTNConecta.notification_service.dto;

public record NotificationRequest(
        Integer toUserId,
        String toUserEmail,
        String message,
        String sender) {
}
