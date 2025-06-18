package com.acme.nutrimove.notificationservice.backend.notification.application.services;

import com.acme.nutrimove.notificationservice.backend.notification.application.dto.NotificationDto;
import com.acme.nutrimove.notificationservice.backend.notification.application.mapper.NotificationMapper;
import com.acme.nutrimove.notificationservice.backend.notification.domain.Notification;
import com.acme.nutrimove.notificationservice.backend.notification.infrastructure.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationMapper mapper;

    public List<NotificationDto> getAll() {

        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<NotificationDto> getById(Long id) {

        return repository.findById(id).map(mapper::toDto);
    }

    public List<NotificationDto> getByUserId(Long userId) {

        return repository.findByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public NotificationDto create(NotificationDto dto) {
        Notification entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));

    }

    public Optional<NotificationDto> update(Long id, NotificationDto dto) {
        return repository.findById(id).map(existing -> {
            existing.setUserId(dto.getUserId());
            existing.setMessage(dto.getMessage());
            existing.setType(dto.getType());
            existing.setStatus(dto.getStatus());
            existing.setTimestamp(dto.getTimestamp());
            return mapper.toDto(repository.save(existing));
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}