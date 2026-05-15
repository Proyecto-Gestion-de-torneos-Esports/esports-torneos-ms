package com.torneos.torneos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "partidas", url = "http://localhost:8003/api/partidas")
public interface PartidaClient {

    @GetMapping("/torneo/{torneoId}")
    List<Object> obtenerPartidasPorTorneo(@PathVariable("torneoId") Long torneoId);
}
