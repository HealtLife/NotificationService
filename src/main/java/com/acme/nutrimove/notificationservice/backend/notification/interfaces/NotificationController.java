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

    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> update(@PathVariable Long id, @RequestBody Notification notification) {
        return service.update(id, notification)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}