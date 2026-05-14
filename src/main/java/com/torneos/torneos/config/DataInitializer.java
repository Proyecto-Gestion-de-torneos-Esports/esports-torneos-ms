package com.torneos.torneos.config;

import com.torneos.torneos.model.EstadoTorneo;
import com.torneos.torneos.model.Torneo;
import com.torneos.torneos.repository.TorneoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final TorneoRepository torneoRepository;

    @Override
    public void run(String... args) {
        if (torneoRepository.count() > 0) {
            log.info("La base de datos ya tiene {} torneos. Omitiendo carga inicial.", torneoRepository.count());
            return;
        }

        log.info("Base de datos vacía. Cargando torneos de prueba para el sistema...");

        Torneo torneo1 = new Torneo();
        torneo1.setNombre("Copa Duoc UC CS2 2026");
        torneo1.setFecha(LocalDate.now().plusDays(20));
        torneo1.setLugar("Sede Valparaíso");
        torneo1.setIdJuego(1L);
        torneo1.setEstado(EstadoTorneo.PLANIFICACION);

        Torneo torneo2 = new Torneo();
        torneo2.setNombre("Liga Regional Apertura");
        torneo2.setFecha(LocalDate.now().minusDays(2));
        torneo2.setLugar("Online");
        torneo2.setIdJuego(2L);
        torneo2.setEstado(EstadoTorneo.EN_CURSO);

        Torneo torneo3 = new Torneo();
        torneo3.setNombre("Torneo Relámpago Valorant");
        torneo3.setFecha(LocalDate.now().plusMonths(1));
        torneo3.setLugar("Casablanca Arena");
        torneo3.setIdJuego(3L);
        torneo3.setEstado(EstadoTorneo.INSCRIPCION);

        torneoRepository.saveAll(List.of(torneo1, torneo2, torneo3));

        log.info("Carga de respaldo completada. {} torneos insertados.", torneoRepository.count());
    }
}
