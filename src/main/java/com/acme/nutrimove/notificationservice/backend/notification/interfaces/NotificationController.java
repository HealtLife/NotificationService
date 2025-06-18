package com.acme.nutrimove.notificationservice.backend.notification.interfaces;

import com.acme.nutrimove.notificationservice.backend.notification.domain.Notification;
import com.acme.nutrimove.notificationservice.backend.notification.application.NotificationService;
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
    public List<Notification> getAll() {
        return service.getAll();
    }

    // Obtener una notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva notificación y enviar un mensaje a RabbitMQ
    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        Notification createdNotification = service.create(notification);

        String message = "New notification created: " + createdNotification.getId();
        messageBrokerClient.sendMessage(message);

        return createdNotification;
    }

    // Actualizar una notificación y enviar un mensaje a RabbitMQ
    @PutMapping("/{id}")
    public ResponseEntity<Notification> update(@PathVariable Long id, @RequestBody Notification notification) {
        return service.update(id, notification)
                .map(updatedNotification -> {
                    String message = "Notification updated: " + updatedNotification.getId();
                    messageBrokerClient.sendMessage(message);

                    return ResponseEntity.ok(updatedNotification);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una notificación y enviar un mensaje a RabbitMQ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        // Enviar un mensaje a MessageBroker notificando que la notificación ha sido eliminada
        String message = "Notification deleted: " + id;
        messageBrokerClient.sendMessage(message);  // Llamada al microservicio MessageBroker

        return ResponseEntity.noContent().build();
    }
}
