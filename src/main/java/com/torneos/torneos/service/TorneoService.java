package com.torneos.torneos.service;

import com.torneos.torneos.dto.TorneoRequestDTO;
import com.torneos.torneos.dto.TorneoResponseDTO;
import com.torneos.torneos.model.EstadoTorneo;
import com.torneos.torneos.model.Torneo;
import com.torneos.torneos.repository.TorneoRepository;
import com.torneos.torneos.webclient.JuegoClient;
import com.torneos.torneos.webclient.PartidaClient;
import com.torneos.torneos.webclient.UsuarioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TorneoService {

    private final TorneoRepository torneoRepository;

    private final JuegoClient juegoClient;
    private final UsuarioClient usuarioClient;
    private final PartidaClient partidaClient;

    @Transactional
    public TorneoResponseDTO guardar(TorneoRequestDTO dto) {
        // Aquí a futuro puedes llamar a: validarJuego(dto.getJuegoId());

        Torneo torneo = new Torneo();
        torneo.setNombre(dto.getNombre());
        torneo.setFecha(dto.getFecha());
        torneo.setLugar(dto.getLugar());
        torneo.setIdJuego(dto.getIdJuego());
        torneo.setEstado(dto.getEstado());

        TorneoResponseDTO respuesta = mapToDto(torneoRepository.save(torneo));
        log.info("Torneo '{}' creado y guardado correctamente con ID: {}", dto.getNombre(), respuesta.getTorneoId());
        return respuesta;
    }

    @Transactional(readOnly = true)
    public List<TorneoResponseDTO> listarTodos() {
        log.info("Listando todos los torneos");
        List<Torneo> torneos = torneoRepository.findAll();
        log.info("Hay {} torneos en total", torneos.size());
        return torneos.stream().map(this::mapToDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Optional<TorneoResponseDTO> buscarPorId(Long torneoId) {
        return torneoRepository.findById(torneoId).map(torneo -> {
            TorneoResponseDTO dto = mapToDto(torneo);
            try {
                List<Object> partidas = partidaClient.obtenerPartidasPorTorneo(torneoId);
                dto.setPartidas(partidas);
                log.info("Se cargaron {} partidas para el torneo {}", partidas.size(), torneoId);
            } catch (Exception e) {
                log.error("No se pudieron cargar las partidas del torneo: {}", e.getMessage());
                dto.setPartidas(List.of()); //lista sin nada si falla
            }

            return dto;
        });
    }

    @Transactional
    public Optional<TorneoResponseDTO> actualizar(Long torneoId, TorneoRequestDTO dto) {
        return torneoRepository.findById(torneoId).map(existente -> {
            log.info("Torneo con ID: {} encontrado. Actualizando datos", torneoId);

            existente.setNombre(dto.getNombre());
            existente.setFecha(dto.getFecha());
            existente.setLugar(dto.getLugar());
            existente.setIdJuego(dto.getIdJuego());
            existente.setEstado(dto.getEstado());

            TorneoResponseDTO respuesta = mapToDto(torneoRepository.save(existente));
            log.info("El Torneo (ID: {}) fue actualizado correctamente", torneoId);
            return respuesta;
        });
    }

    @Transactional(readOnly = true)
    public List<TorneoResponseDTO> buscarPorJuegoId(Long idJuego) {
        log.info("Buscando torneos asociados al juego ID: {}", idJuego);
        return torneoRepository.findByIdJuego(idJuego).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TorneoResponseDTO> buscarPorEstado(EstadoTorneo estado) {
        log.info("Buscando torneos filtrados por estado: {}", estado);
        List<Torneo> torneos = torneoRepository.findByEstado(estado);
        return torneos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private TorneoResponseDTO mapToDto(Torneo torneo) {
        return new TorneoResponseDTO(
                torneo.getTorneoId(),
                torneo.getNombre(),
                torneo.getFecha(),
                torneo.getLugar(),
                torneo.getIdJuego(),
                torneo.getEstado(),
                null
        );
    }
}
