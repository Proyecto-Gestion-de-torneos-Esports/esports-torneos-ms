package com.torneos.torneos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "TORNEOS")
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long torneoId;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 100)
    private String lugar;

    @Column(name = "juego_id", nullable = false)
    private Long idJuego;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTorneo estado;
}
