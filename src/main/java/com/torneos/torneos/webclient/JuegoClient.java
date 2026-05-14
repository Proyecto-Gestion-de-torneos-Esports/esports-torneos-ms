package com.torneos.torneos.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JuegoClient {

    private final WebClient webClient;

    public JuegoClient(@Value("${juego-service.url}") String juegosUrl) {
        this.webClient = WebClient.builder().baseUrl(juegosUrl).build();
    }

    public void validarJuegoExiste(Long idJuego) {
        this.webClient.get()
                .uri("/" + idJuego)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Error: El juego con ID '" + idJuego + "' no existe en el sistema.")))
                .bodyToMono(Void.class) // Como tú hiciste, solo queremos saber si da 200 OK
                .block();
    }
}
