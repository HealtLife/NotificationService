package com.acme.nutrimove.notificationservice.backend.notification.application.mapper;

import com.acme.nutrimove.notificationservice.backend.notification.application.dto.NotificationDto;
import com.acme.nutrimove.notificationservice.backend.notification.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public Notification toEntity(NotificationDto dto){
        return new Notification(
                dto.getUserId(),
                dto.getMessage(),
                dto.getType(),
                dto.getStatus(),
                dto.getTimestamp()
        );
    }

    public NotificationDto toDto(Notification notification){
        NotificationDto dto = new NotificationDto();
        dto.setUserId(notification.getUserId());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setStatus(notification.getStatus());
        dto.setTimestamp(notification.getTimestamp());
        return dto;
    }
}
