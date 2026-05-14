package com.torneos.torneos.dto;

import com.torneos.torneos.model.EstadoTorneo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TorneoResponseDTO {

    private Long torneoId;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private Long idJuego;
    private EstadoTorneo estado;

    private List<Object> partidas;
}
