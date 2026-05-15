package com.torneos.torneos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "juego-service", url = "http://localhost:8012/api/juego")
public interface JuegoClient {

    @GetMapping("/{id}")
    Map<String, Object> validarJuegoExiste(@PathVariable("id") Long id);

}
