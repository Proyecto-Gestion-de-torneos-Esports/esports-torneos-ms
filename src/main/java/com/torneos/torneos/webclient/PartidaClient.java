package com.torneos.torneos.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class PartidaClient {

    private final WebClient webClient;

    public PartidaClient(@Value("${partida-service.url}") String partidasUrl) {
        this.webClient = WebClient.builder().baseUrl(partidasUrl).build();
    }

    public List<Object> obtenerPartidasPorTorneo(Long torneoId) {
        return this.webClient.get()
                .uri("/torneo/" + torneoId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Object>>() {})
                .block();
    }
}
