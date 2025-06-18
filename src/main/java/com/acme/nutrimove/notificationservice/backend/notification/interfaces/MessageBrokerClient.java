package com.acme.nutrimove.notificationservice.backend.notification.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Define el Feign Client para consumir la API del microservicio MessageBroker
@FeignClient(name = "messagebroker-service", url = "http://localhost:8082")  // Cambia el puerto y la URL según sea necesario
public interface MessageBrokerClient {

    @PostMapping("/message-broker/send")
    void sendMessage(@RequestBody String message);
}
