package com.torneos.torneos.service;

import com.torneos.torneos.client.AuditoriaClient;
import com.torneos.torneos.client.JuegoClient;
import com.torneos.torneos.client.PartidaClient;
import com.torneos.torneos.client.UsuarioClient;
import com.torneos.torneos.dto.AuditoriaRequestDTO;
import com.torneos.torneos.dto.TorneoRequestDTO;
import com.torneos.torneos.dto.TorneoResponseDTO;
import com.torneos.torneos.model.EstadoTorneo;
import com.torneos.torneos.model.Torneo;
import com.torneos.torneos.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TorneoService {

    private final TorneoRepository torneoRepository;

    private final JuegoClient juegoClient;
    private final PartidaClient partidaClient;
    private final UsuarioClient usuarioClient;
    private final AuditoriaClient auditoriaClient;

    @Transactional
    public TorneoResponseDTO guardar(TorneoRequestDTO dto) {
        validarJuegoExiste(dto.getIdJuego());

        Torneo torneo = new Torneo();
        torneo.setNombre(dto.getNombre());
        torneo.setFecha(dto.getFecha());
        torneo.setLugar(dto.getLugar());
        torneo.setIdJuego(dto.getIdJuego());
        torneo.setEstado(dto.getEstado());

        TorneoResponseDTO respuesta = mapToDto(torneoRepository.save(torneo));
        log.info("Torneo '{}' creado y guardado correctamente con ID: {}", dto.getNombre(), respuesta.getTorneoId());

        String detalleAuditoria = "Se creó un nuevo torneo: '" + dto.getNombre() + "' (ID: " + respuesta.getTorneoId() +
                ") asociado al Juego ID: " + dto.getIdJuego() +
                ". Estado inicial: " + dto.getEstado();
        generarAuditoria(detalleAuditoria);

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

            String detalleAuditoria = "Se actualizaron los datos del torneo con ID: " + torneoId;
            generarAuditoria(detalleAuditoria);
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

    public String obtenerRolUsuario(Long usuarioId) {
        Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorId(usuarioId);
        if (usuario == null || !usuario.containsKey("rol")) {
            throw new RuntimeException("Usuario no encontrado o no autorizado.");
        }
        return usuario.get("rol").toString();
    }

    public void validarJuegoExiste(Long idJuego) {
        Map<String, Object> juego = juegoClient.validarJuegoExiste(idJuego);

        if (juego == null) {
            throw new RuntimeException("Error: El juego con ID '" + idJuego + "' no existe en el sistema.");
        }
    }

    public void generarAuditoria(String detalle){
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        LocalDate ahora = LocalDate.now();
        dto.setDetalle(detalle);
        dto.setFecha(ahora);
        auditoriaClient.generarAuditoria(dto);
        log.info("Auditoria generada con exito!");
    }





}
