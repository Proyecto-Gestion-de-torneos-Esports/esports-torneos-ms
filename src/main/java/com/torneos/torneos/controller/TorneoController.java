package com.torneos.torneos.controller;

import com.torneos.torneos.dto.TorneoRequestDTO;
import com.torneos.torneos.dto.TorneoResponseDTO;
import com.torneos.torneos.model.EstadoTorneo;
import com.torneos.torneos.service.TorneoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/torneos")
@RequiredArgsConstructor

public class TorneoController {

    private final TorneoService torneoService;

    @GetMapping
    public ResponseEntity<List<TorneoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(torneoService.listarTodos());
    }

    @GetMapping("/{torneoId}")
    public ResponseEntity<TorneoResponseDTO> buscarPorId(@PathVariable Long torneoId) {
        return torneoService.buscarPorId(torneoId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TorneoResponseDTO> crear(@Valid @RequestBody TorneoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(torneoService.guardar(dto));
    }

    @PutMapping("/{torneoId}")
    public ResponseEntity<TorneoResponseDTO> actualizar(@PathVariable Long torneoId, @Valid @RequestBody TorneoRequestDTO dto) {
        return torneoService.actualizar(torneoId, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TorneoResponseDTO>> buscarPorEstado(@PathVariable EstadoTorneo estado) {
        return ResponseEntity.ok(torneoService.buscarPorEstado(estado));
    }

    @GetMapping("/juego/{idJuego}")
    public ResponseEntity<List<TorneoResponseDTO>> buscarPorJuego(@PathVariable Long idJuego) {
        return ResponseEntity.ok(torneoService.buscarPorJuegoId(idJuego));
    }

}
