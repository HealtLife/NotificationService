package com.acme.nutrimove.notificationservice.backend.notification.interfaces;

import com.acme.nutrimove.notificationservice.backend.notification.application.dto.NotificationDto;
import com.acme.nutrimove.notificationservice.backend.notification.domain.Notification;
import com.acme.nutrimove.notificationservice.backend.notification.application.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @Autowired
    private MessageBrokerClient messageBrokerClient;

    // Obtener todas las notificaciones
    @GetMapping
    public List<NotificationDto> getAll() {
        return service.getAll();
    }

    // Obtener una notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva notificación y enviar un mensaje al MessageBroker
    @PostMapping
    public NotificationDto create(@RequestBody NotificationDto dto) {
        NotificationDto created = service.create(dto);
        String message = "New notification created: " + created.getUserId();
        messageBrokerClient.sendMessage(message);
        return created;
    }

    // Actualizar una notificación y enviar un mensaje al MessageBroker
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> update(@PathVariable Long id, @RequestBody NotificationDto dto) {
        return service.update(id, dto)
                .map(updated -> {
                    String message = "Notification updated: " + updated.getUserId();
                    messageBrokerClient.sendMessage(message);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una notificación y notificar al MessageBroker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        messageBrokerClient.sendMessage("Notification deleted: " + id);
        return ResponseEntity.noContent().build();
    }
}
