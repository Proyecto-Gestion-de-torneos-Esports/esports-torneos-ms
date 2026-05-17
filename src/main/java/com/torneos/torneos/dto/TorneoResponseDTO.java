package com.torneos.torneos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.torneos.torneos.model.EstadoTorneo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) /* esto es por si la cantidad de partidas es 0 no mostrar ese campo en vez de que salga
null, y si hay cierta cantidad de partidas se muestra con un numero y para ver el detalle de esas partida en x torneo, buscar el torneo por id*/
public class TorneoResponseDTO {

    private Long torneoId;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private Long idJuego;
    private EstadoTorneo estado;
    private Integer cantidadPartidas;
    private List<Object> partidas;
    private Set<Long> equiposInscritos;

}
