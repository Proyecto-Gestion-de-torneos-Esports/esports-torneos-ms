package com.torneos.torneos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "notificaciones-service", url = "http://localhost:8009/api/notificacion")
public interface NotificacionesClient {

    @PostMapping
    void generarNotificacion(@RequestParam("correo") String correo,
                             @RequestParam("fecha") LocalDateTime fecha);
}
