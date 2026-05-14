package com.torneos.torneos.dto;

import com.torneos.torneos.model.EstadoTorneo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TorneoRequestDTO {

    @NotBlank(message = "El nombre del torneo no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del torneo debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La fecha del torneo es obligatoria")
    @FutureOrPresent(message = "La fecha del torneo no puede ser en el pasado")
    private LocalDate fecha;

    @NotBlank(message = "El lugar del torneo es obligatorio")
    @Size(min = 3, max = 100, message = "El lugar debe tener entre 3 y 100 caracteres")
    private String lugar;

    @NotNull(message = "El ID del juego es obligatorio")
    @Positive(message = "El ID del juego debe ser un numero positivo")
    private Long idJuego;

    @NotNull(message = "El estado del torneo es obligatorio")
    private EstadoTorneo estado;

}
