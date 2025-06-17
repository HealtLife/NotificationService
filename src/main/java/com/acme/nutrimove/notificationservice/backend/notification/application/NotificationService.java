package com.acme.nutrimove.notificationservice.backend.notification.application;

import com.acme.nutrimove.notificationservice.backend.notification.domain.Notification;
import com.acme.nutrimove.notificationservice.backend.notification.infrastructure.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public List<Notification> getAll() {
        return repository.findAll();
    }

    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }

    public List<Notification> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    public Optional<Notification> update(Long id, Notification notification) {
        return repository.findById(id).map(existing -> {
            existing.setUserId(notification.getUserId());
            existing.setMessage(notification.getMessage());
            existing.setType(notification.getType());
            existing.setStatus(notification.getStatus());
            existing.setTimestamp(notification.getTimestamp());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}