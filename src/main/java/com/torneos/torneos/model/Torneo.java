package com.torneos.torneos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ElementCollection
    @CollectionTable(name = "torneo_equipos", joinColumns = @JoinColumn(name = "torneo_id"))
    @Column(name = "equipo_id")
    private Set<Long> equiposInscritos = new HashSet<>();
}
